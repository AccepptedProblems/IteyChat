package com.chatapp.itey.repo;

import com.chatapp.itey.model.entity.ChannelName;
import com.chatapp.itey.model.entity.ChatChannel;
import com.chatapp.itey.model.entity.InChatChannel;
import com.chatapp.itey.model.entity.User;
import com.chatapp.itey.model.entity.modelType.ChannelType;
import com.chatapp.itey.model.payload.ChatChannelReq;
import com.chatapp.itey.model.payload.UserResp;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Component
public class ChannelRepoImpl implements ChannelRepo {

    @Autowired
    Firestore firestore;

    @Autowired
    UserRepo userRepo;

    @Value("${data.database.channel}")
    private String channelPath;

    @Value("${data.database.inChatChannel}")
    private String inChannelPath;

    @Value("${data.database.channelName}")
    private String channelNamePath;

    @Override
    public ChatChannel addChannel(ChatChannelReq chatChannelReq) throws ExecutionException, InterruptedException {
        ChatChannel chatChannel = new ChatChannel(chatChannelReq);

        firestore.collection(channelPath).document(chatChannel.getId()).set(chatChannel);

        if (chatChannelReq.getType() == ChannelType.DIRECT) {
            User user1 = userRepo.findById(chatChannelReq.getUserIds().get(0));
            User user2 = userRepo.findById(chatChannelReq.getUserIds().get(1));
            ChannelName userChannelName1 = new ChannelName(chatChannel.getId(), chatChannelReq.getUserIds().get(0), user2.getDisplayName());
            ChannelName userChannelName2 = new ChannelName(chatChannel.getId(), chatChannelReq.getUserIds().get(1), user1.getDisplayName());

            firestore.collection(channelNamePath).add(userChannelName1);
            firestore.collection(channelNamePath).add(userChannelName2);
        }

        chatChannelReq.getUserIds().forEach(id -> firestore.collection(inChannelPath).add(new InChatChannel(chatChannel.getId(), id)));

        return chatChannel;
    }

    @Override
    public List<UserResp> getUserInChannel(String channelId) throws ExecutionException, InterruptedException {
        List<QueryDocumentSnapshot> userDocs = firestore.collection(inChannelPath)
                .whereEqualTo("channelId", channelId)
                .get().get().getDocuments();
        if (userDocs.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "There's no people in channel");
        }

        List<String> userIds = userDocs.stream().map(value -> value.toObject(InChatChannel.class).getUserId()).toList();
        return userRepo.getUsersContainIn(userIds);
    }

    @Override
    public ChatChannel getChannelById(String channelId) throws ExecutionException, InterruptedException {
        return firestore.collection(channelPath).document(channelId).get().get().toObject(ChatChannel.class);
    }

    @Override
    public List<String> getChannelIdsByUserId(String userId) throws ExecutionException, InterruptedException {
        List<QueryDocumentSnapshot> inChannelDocs = firestore.collection(inChannelPath)
                .whereEqualTo("userId", userId).get().get().getDocuments();

        if (inChannelDocs.isEmpty()) {
            return List.of();
        }

        return inChannelDocs
                .stream().map(value -> value.toObject(InChatChannel.class)
                        .getChannelId()).toList();
    }

    @Override
    public List<ChatChannel> getChannelsByUserId(String userId) throws ExecutionException, InterruptedException {
        List<String> channelIds = getChannelIdsByUserId(userId);
        return getChannelsContainIn(channelIds);
    }

    @Override
    public List<ChatChannel> getChannelsContainIn(List<String> channelIds) {
        List<ChatChannel> channels = new ArrayList<>();
        channelIds.forEach(value -> {
            try {
                ChatChannel channel = firestore.collection(channelPath)
                        .whereEqualTo("id", value).get().get().getDocuments()
                        .get(0).toObject(ChatChannel.class);
                channels.add(channel);
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        });

        return channels;
    }

    @Override
    public String getChannelName(ChatChannel channel) throws ExecutionException, InterruptedException {
        if (channel.getType() == ChannelType.GROUP) return channel.getName();

        String currentUserId = UserResp.currentUser().getId();
        List<QueryDocumentSnapshot> channelNameDocs = firestore.collection(channelNamePath)
                .whereEqualTo("channelId", channel.getId())
                .whereEqualTo("userId", currentUserId).get().get().getDocuments();

        if (channelNameDocs.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Don't find any channel name for userId");
        }



        return channelNameDocs.get(0).toObject(ChannelName.class).getName();
    }

    @Override
    public ChatChannel deleteChannel(String channelId) throws ExecutionException, InterruptedException {
        DocumentReference channelDocs = firestore.collection(channelPath).document(channelId);
        ChatChannel channel = channelDocs.get().get().toObject(ChatChannel.class);
        channelDocs.delete();

        List<QueryDocumentSnapshot> inChannelDocs = firestore.collection(inChannelPath)
                .whereEqualTo("channelId", channelId)
                .get().get().getDocuments();

        inChannelDocs.forEach(value -> firestore.collection(inChannelPath).document(value.getId()).delete());
        return channel;
    }
}
