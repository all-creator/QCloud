package com.qcloud.server.spring.management.managers;

@Deprecated(since = "3.0.0")
public class RegisterManager extends Manager{

    public RegisterManager(String trigger) {
        super(trigger);
    }

    @Override
    public String manage() {
        return null;
    }
}
