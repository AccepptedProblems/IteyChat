package com.chatapp.itey.repo;

import com.chatapp.itey.model.entity.UniqueField;
import com.chatapp.itey.model.entity.User;
import com.chatapp.itey.model.payload.UserReq;
import com.chatapp.itey.model.payload.UserResp;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

@Slf4j
@Component
public class UserRepoImpl implements UserRepo {
    @Autowired
    Firestore firestore;

    @Value("${data.database.users}")
    private String userPath;

    @Value("${data.database.constraints}")
    private String constraintPath;

    @Value("${data.constraints.usernameConst}")
    private String userConstPath;

    @Value("${data.constraints.userEmailConst}")
    private String userEmailConstPath;

    @Override
    public User createUser(UserReq userReq, String id) {
        DocumentReference userDocRef = firestore.collection(userPath).document(id);
        try {
            User newUser = new User(userReq);
            return firestore.runTransaction(transaction -> {
                        DocumentReference usernameDocRef = firestore.collection(constraintPath).document(userPath).collection(userConstPath).document(newUser.getUsername());
                        DocumentReference userEmailDocRef = firestore.collection(constraintPath).document(userPath).collection(userEmailConstPath).document(newUser.getEmail());
                        UniqueField usernameKey = new UniqueField(id);
                        UniqueField userEmailKey = new UniqueField(id);

                        transaction.create(userDocRef, newUser)
                                .create(usernameDocRef, usernameKey)
                                .create(userEmailDocRef, userEmailKey);

                        return newUser;
                    }
            ).get();
        } catch (ExecutionException e) {
            log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "User with provided information already exist");
        } catch (InterruptedException e) {
            log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User with provided information already exist");
        }

    }

    @Override
    public User findById(String id) throws ExecutionException, InterruptedException {
        DocumentReference userDoc = firestore.collection(userPath).document(id);
        DocumentSnapshot snapshot = userDoc.get().get();
        return snapshot.toObject(User.class);
    }

    @Override
    public User findByEmail(String email) throws ExecutionException, InterruptedException {
        List<QueryDocumentSnapshot> userDocs = firestore.collection(userPath)
                .whereEqualTo("email", email).get().get().getDocuments();
        if (userDocs.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "There's no user with this email");
        }
        return userDocs.get(0).toObject(User.class);
    }

    @Override
    public UserResp findUserRespByEmail(String email) throws ExecutionException, InterruptedException {
        List<QueryDocumentSnapshot> userDocs = firestore.collection(userPath)
                .whereEqualTo("email", email).get().get().getDocuments();
        if (userDocs.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "There's no user with this email");
        }

        UserResp user = userDocs.get(0).toObject(UserResp.class);
        user.setId(userDocs.get(0).getId());
        return user;
    }

    @Override
    public UserResp findByUsername(String username) throws ExecutionException, InterruptedException {
        List<QueryDocumentSnapshot> userDocs = firestore.collection(userPath)
                .whereEqualTo("username", username).get().get().getDocuments();
        if(userDocs.isEmpty()) {
            log.error("No user has this username");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No user has this username");
        }
        String id = userDocs.get(0).getId();
        User user = userDocs.get(0).toObject(User.class);
        return new UserResp(user, id);
    }

    @Override
    public User findUserByUsername(String username) throws ExecutionException, InterruptedException {
        List<QueryDocumentSnapshot> userDocs = firestore.collection(userPath)
                .whereEqualTo("username", username).get().get().getDocuments();
        if(userDocs.isEmpty()) {
            log.error("No user has this username");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No user has this username");
        }
        return userDocs.get(0).toObject(User.class);
    }

    @Override
    public List<UserResp> findByDisplayName(String displayName) throws ExecutionException, InterruptedException {
        List<QueryDocumentSnapshot> userDocs = firestore.collection(userPath)
                .whereGreaterThanOrEqualTo("displayName", displayName)
                .get().get().getDocuments();

        if(userDocs.isEmpty()) {
            log.error("No user has this username");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No user has this username");
        }
        return userDocs.stream().map(value -> new UserResp(value.toObject(User.class), value.getId())).toList();
    }

    @Override
    public List<UserResp> getUsersContainIn(List<String> userIds) {
        return userIds.stream().map(id -> {
            try {
                UserResp user = firestore.collection(userPath).document(id).get().get().toObject(UserResp.class);
                if (user != null) {
                    user.setId(id);
                }
                return user;
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        }).toList();
    }

    @Override
    public List<UserResp> getAll() {
        Iterable<DocumentReference> userDocs = firestore.collection(userPath).listDocuments();
        List<UserResp> user = new ArrayList<>();
        userDocs.forEach(value -> {
            try {
                user.add(new UserResp(Objects.requireNonNull(value.get().get().toObject(User.class)), value.getId()));
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        });
        return user;
    }
}
