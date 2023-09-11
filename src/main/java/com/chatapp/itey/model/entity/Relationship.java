package com.chatapp.itey.model.entity;

import com.chatapp.itey.model.entity.modelType.RelationshipStatus;
import com.chatapp.itey.model.payload.RelationshipRequest;
import com.chatapp.itey.utils.IteyUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Relationship {
    private String id;
    private String userFromId;
    private String userToId;
    private RelationshipStatus status;

    public Relationship(RelationshipRequest relationshipRequest) {
        String currentUserId = id;
        this.id = IteyUtils.newUUID("");
        this.userFromId = currentUserId;
        this.userToId = relationshipRequest.getUserId();
        this.status = RelationshipStatus.PENDING;
    }
}
