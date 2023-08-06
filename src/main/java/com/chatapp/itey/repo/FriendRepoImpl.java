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
import java.util.HashMap;
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
        Relationship relationship = new Relationship(relationshipRequest);
        firestore.collection(relaPath).document(relationship.getId())
                .set(relationship);
        return firestore.collection(userPath).document(relationship.getUserToId()).get().get().toObject(User.class);
    }

    @Override
    public User confirmFriendRequest(String relationshipId) throws ExecutionException, InterruptedException {
        Relationship relationship = firestore.collection(relaPath).document(relationshipId).get().get().toObject(Relationship.class);
        if (relationship == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "There's an error. Please try again");
        }
        relationship.setStatus(RelationshipStatus.FRIEND);
        firestore.collection(relaPath).document(relationshipId).set(relationship);
        return firestore.collection(userPath).document(relationship.getUserToId()).get().get().toObject(User.class);
    }

    @Override
    public User deleteFriend(RelationshipRequest relationshipRequest) throws ExecutionException, InterruptedException {
        List<QueryDocumentSnapshot> docs = getRelaBetweenTwoUsers(relationshipRequest);

        if (docs.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "You're not friend");
        }

        Relationship relationship = docs.get(0).toObject(Relationship.class);
        firestore.collection(relaPath).document(relationship.getId()).delete();
        return firestore.collection(userPath).document(relationship.getUserToId()).get().get().toObject(User.class);
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
    public List<FriendResp> getFriends(String userId) throws ExecutionException, InterruptedException {
        List<QueryDocumentSnapshot> docs = firestore.collection(relaPath).where(Filter.or(
                Filter.equalTo("userFromId", userId),
                Filter.equalTo("userToId", userId)
        )).whereEqualTo("status", RelationshipStatus.FRIEND).get().get().getDocuments();

        List<String> ids = new ArrayList<>();
        HashMap<String, String> map = new HashMap<>();
        List<FriendResp> users = new ArrayList<>();

        docs.forEach(relationshipDoc -> {
            Relationship relationship = relationshipDoc.toObject(Relationship.class);
            if (!Objects.equals(relationship.getUserToId(), userId)) {
                ids.add(relationship.getUserToId());
            } else ids.add(relationship.getUserFromId());
            map.put(ids.get(ids.size() - 1), relationshipDoc.getId());
        });

        Iterable<DocumentReference> userDocs = firestore.collection(userPath).listDocuments();
        userDocs.forEach(userDoc -> {
            try {
                if (ids.contains(userDoc.getId())) {
                    UserResp user = userDoc.get().get().toObject(UserResp.class);
                    if (user != null) {
                        user.setId(userDoc.getId());
                    }
                    users.add(new FriendResp(map.get(userDoc.getId()), user));
                }
            } catch (InterruptedException | ExecutionException e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "There's an error. Please try again.");
            }
        });

        return users;
    }



    @Override
    public List<FriendResp> getFriendRequest(String userId) throws ExecutionException, InterruptedException {
        List<QueryDocumentSnapshot> docs = firestore.collection(relaPath).where(Filter.or(
                Filter.equalTo("userToId", userId)
        )).whereEqualTo("status", RelationshipStatus.PENDING).get().get().getDocuments();

        List<String> ids = new ArrayList<>();
        HashMap<String, String> map = new HashMap<>();
        List<FriendResp> users = new ArrayList<>();

        docs.forEach(relationshipDoc -> {
            Relationship relationship = relationshipDoc.toObject(Relationship.class);
            if (!Objects.equals(relationship.getUserToId(), userId)) {
                ids.add(relationship.getUserToId());

            } else ids.add(relationship.getUserFromId());
            map.put(ids.get(ids.size() - 1), relationshipDoc.getId());
        });

        Iterable<DocumentReference> userDocs = firestore.collection(userPath).listDocuments();
        userDocs.forEach(userDoc -> {
            try {
                if (ids.contains(userDoc.getId())) {
                    UserResp user = userDoc.get().get().toObject(UserResp.class);
                    if (user != null) {
                        user.setId(userDoc.getId());
                    }
                    users.add(new FriendResp(map.get(userDoc.getId()), user));
                }
            } catch (InterruptedException | ExecutionException e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "There's an error. Please try again.");
            }
        });

        return users;
    }

    @Override
    public Boolean checkFriendRequest(RelationshipRequest relationshipRequest) throws ExecutionException, InterruptedException {
        List<QueryDocumentSnapshot> docs = firestore.collection(relaPath).where(Filter.and(
                        Filter.equalTo("userFromId", relationshipRequest.getUserToId()),
                        Filter.equalTo("userToId", relationshipRequest.getUserFromId())
                )
        ).get().get().getDocuments();
        return !docs.isEmpty();
    }

    @Override
    public Relationship getRelationshipByUserId(RelationshipRequest relationshipRequest) throws ExecutionException, InterruptedException {
        List<QueryDocumentSnapshot> docs = getRelaBetweenTwoUsers(relationshipRequest);
        return docs.get(0).toObject(Relationship.class);
    }

    List<QueryDocumentSnapshot> getRelaBetweenTwoUsers(RelationshipRequest relationshipRequest) throws ExecutionException, InterruptedException {
        return firestore.collection(relaPath).where(Filter.or(
                Filter.and(
                        Filter.equalTo("userFromId", relationshipRequest.getUserFromId()),
                        Filter.equalTo("userToId", relationshipRequest.getUserToId())
                ),
                Filter.and(
                        Filter.equalTo("userFromId", relationshipRequest.getUserToId()),
                        Filter.equalTo("userToId", relationshipRequest.getUserFromId())
                )
        )).get().get().getDocuments();
    }

}
