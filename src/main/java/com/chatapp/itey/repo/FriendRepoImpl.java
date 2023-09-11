package com.chatapp.itey.repo;

import com.chatapp.itey.model.entity.Relationship;
import com.chatapp.itey.model.entity.User;
import com.chatapp.itey.model.entity.modelType.RelationshipStatus;
import com.chatapp.itey.model.payload.FriendResp;
import com.chatapp.itey.model.payload.RelationshipRequest;
import com.chatapp.itey.model.payload.UserResp;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Filter;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

@Component
public class FriendRepoImpl implements FriendRepo {
    @Autowired
    Firestore firestore;

    @Value("${data.database.relationships}")
    private String relaPath;

    @Value("${data.database.users}")
    private String userPath;

    @Override
    public User addFriend(RelationshipRequest relationshipRequest) throws ExecutionException, InterruptedException {
        String id = UserResp.currentUser().getId();
        User requestedUser = firestore.collection(userPath).document(relationshipRequest.getUserId())
                .get().get().toObject(User.class);

        assert requestedUser != null;
        if(requestedUser.getFriendRequestIds().contains(id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can not request friend to user more than 1 times");
        }
        requestedUser.getFriendRequestIds().add(id);
        firestore.collection(userPath).document(relationshipRequest.getUserId()).set(requestedUser);

        return requestedUser;
    }

    @Override
    public User confirmFriendRequest(String userId) throws ExecutionException, InterruptedException {
        String currentUserId = UserResp.currentUser().getId();
        User currentUser = firestore.collection(userPath).document(currentUserId)
                .get().get().toObject(User.class);
        User requestedUser = firestore.collection(userPath).document(userId)
                .get().get().toObject(User.class);

        assert currentUser != null;
        if(!currentUser.getFriendRequestIds().contains(userId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "There's an error. Please try again");
        }
        List<String> requestIds = currentUser.getFriendRequestIds();
        List<String> friendIds = currentUser.getFriendIds();
        List<String> userToFriendIds = requestedUser.getFriendIds();

        if (requestIds.remove(userId)) {
            friendIds.add(userId);
            currentUser.setFriendRequestIds(requestIds);
            currentUser.setFriendIds(friendIds);

            userToFriendIds.add(currentUserId);
            requestedUser.setFriendIds(userToFriendIds);
        }


        System.out.println(currentUser);
        firestore.collection(userPath).document(currentUserId).set(currentUser);
        firestore.collection(userPath).document(userId).set(requestedUser);

        return currentUser;
    }

    @Override
    public User deleteFriend(String userId) throws ExecutionException, InterruptedException {
        String currentUserId = UserResp.currentUser().getId();
        User currentUser = firestore.collection(userPath).document(currentUserId).get().get().toObject(User.class);

        assert currentUser != null;
        if (currentUser.getFriendIds().contains(userId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "You're not friend");
        }

        List<String> requestIds = currentUser.getFriendRequestIds();
        List<String> friendIds = currentUser.getFriendIds();
        requestIds.remove(userId);
        friendIds.remove(userId);
        currentUser.setFriendIds(friendIds);
        currentUser.setFriendRequestIds(requestIds);

        firestore.collection(userPath).document(currentUserId).set(currentUser);

        return currentUser;
    }

    @Override
    public List<String> getFriendIds() throws ExecutionException, InterruptedException {
        String currentUserId = UserResp.currentUser().getId();
        List<QueryDocumentSnapshot> docs = firestore.collection(relaPath).where(Filter.or(
                Filter.equalTo("userFromId", currentUserId),
                Filter.equalTo("userToId", currentUserId)
        )).whereEqualTo("status", RelationshipStatus.FRIEND).get().get().getDocuments();

        List<String> ids = new ArrayList<>();
        List<FriendResp> users = new ArrayList<>();

        docs.forEach(relationshipDoc -> {
            Relationship relationship = relationshipDoc.toObject(Relationship.class);
            if (!Objects.equals(relationship.getUserToId(), currentUserId)) {
                ids.add(relationship.getUserToId());
            } else ids.add(relationship.getUserFromId());
        });
        return ids;
    }

    @Override
    public List<UserResp> getFriends() throws ExecutionException, InterruptedException {
        String userId = UserResp.currentUser().getId();
        User currentUser = firestore.collection(userPath).document(userId).get().get().toObject(User.class);
        List<UserResp> users = new ArrayList<>();
        Iterable<DocumentReference> userDocs = firestore.collection(userPath).listDocuments();

        userDocs.forEach(userDoc -> {
            try {
                assert currentUser != null;
                if (currentUser.getFriendIds().contains(userDoc.getId())) {
                    UserResp user = userDoc.get().get().toObject(UserResp.class);
                    if (user != null) {
                        user.setId(userDoc.getId());
                    }
                    users.add(user);
                }
            } catch (InterruptedException | ExecutionException e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "There's an error. Please try again.");
            }
        });

        return users;
    }



    @Override
    public List<UserResp> getFriendRequest() throws ExecutionException, InterruptedException {
        String userId = UserResp.currentUser().getId();
        User currentUser = firestore.collection(userPath).document(userId).get().get().toObject(User.class);
        List<UserResp> users = new ArrayList<>();
        Iterable<DocumentReference> userDocs = firestore.collection(userPath).listDocuments();
        userDocs.forEach(userDoc -> {
            try {
                assert currentUser != null;
                if (currentUser.getFriendRequestIds().contains(userDoc.getId())) {
                    UserResp user = userDoc.get().get().toObject(UserResp.class);
                    if (user != null) {
                        user.setId(userDoc.getId());
                    }
                    users.add(user);
                }
            } catch (InterruptedException | ExecutionException e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "There's an error. Please try again.");
            }
        });

        return users;
    }

    @Override
    public Boolean checkFriendRequest(RelationshipRequest relationshipRequest) throws ExecutionException, InterruptedException {
        String id = UserResp.currentUser().getId();
        User user = firestore.collection(userPath).document(id).get().get().toObject(User.class);
        assert user != null;
        return user.getFriendRequestIds().contains(relationshipRequest.getUserId());
    }

}
