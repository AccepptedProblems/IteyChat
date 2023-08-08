package com.chatapp.itey.repo;

import com.chatapp.itey.model.entity.ChatChannel;
import com.chatapp.itey.model.entity.Message;
import com.chatapp.itey.model.payload.MessageReq;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Component
public class MessageRepoImpl implements  MessageRepo{
    @Autowired
    Firestore firestore;

    @Value("${data.database.messages}")
    private String messagePath;

    @Value("${data.database.channel}")
    private String channelPath;

    @Override
    public Message addMessage(MessageReq messageReq) throws ExecutionException, InterruptedException {
        Message message = new Message(messageReq);
        return firestore.runTransaction(transaction -> {
            DocumentReference messDoc = firestore.collection(messagePath).document(message.getId());


            DocumentReference channelDoc = firestore.collection(channelPath).document(message.getChannelId());
            ChatChannel channel = channelDoc.get().get().toObject(ChatChannel.class);
            assert channel != null;
            channel.setLastMessageId(message.getId());

            transaction.create(messDoc, message)
                    .set(channelDoc, channel);

            return message;
        }).get();
    }

    @Override
    public Message findMessageById(String id) throws ExecutionException, InterruptedException {
        return firestore.collection(messagePath).document(id).get().get().toObject(Message.class);
    }

    @Override
    public List<Message> getMessagesFromChannel(String channelId) throws ExecutionException, InterruptedException {
        List<QueryDocumentSnapshot> messDocs = firestore.collection(messagePath)
                .whereEqualTo("channelId", channelId)
                .get().get().getDocuments();
        List<Message> messages = messDocs.stream().map(value -> value.toObject(Message.class)).toList();
        Collections.sort(messages);
        return messages;
    }

    @Override
    public Message deleteMessage(String messageId) throws ExecutionException, InterruptedException {
        Message message = firestore.collection(messagePath).document(messageId).get().get().toObject(Message.class);
        firestore.collection(messagePath).document(messageId).delete();
        return message;
    }
}
