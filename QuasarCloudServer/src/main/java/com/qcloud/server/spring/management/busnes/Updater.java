package com.qcloud.server.spring.management.busnes;

import lombok.Getter;

public class Updater {
    @Getter
    private static final String LAUNCHER_VERSION = "1.0.4-beta";

    public boolean check(String version){
        return !version.equals(getLAUNCHER_VERSION());
    }
}
