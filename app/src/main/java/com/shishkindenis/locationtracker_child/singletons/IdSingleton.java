package com.shishkindenis.locationtracker_child.singletons;

public class IdSingleton {
    private String userId;
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getUserId() {
        return userId;
    }
    public IdSingleton() {
    }
}
