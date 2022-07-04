package com.qcloud.server.spring.management.managers;

import com.qcloud.server.spring.management.api.API;

public abstract class Manager {

    String trigger;

    protected Manager(String trigger) {
        this.trigger = trigger;
    }

    public abstract String manage();

    public static String getFromMainDataBase(String question){
        return API.getQuery(question);
    }
}
