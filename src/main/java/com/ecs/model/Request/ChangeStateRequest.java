package com.ecs.model.Request;

import lombok.Data;

@Data
public class ChangeStateRequest {
    private String id;
    private String token;

    public ChangeStateRequest(String id, String token) {
        this.id = id;
        this.token = token;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
