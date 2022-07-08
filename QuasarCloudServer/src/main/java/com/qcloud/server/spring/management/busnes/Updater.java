package com.qcloud.server.spring.management.busnes;

import lombok.Getter;
@Deprecated(since = "4.0.0")
public class Updater {
    @Getter
    private static final String LAUNCHER_VERSION = "1.0.4-beta";

    public boolean check(String version){
        return !version.equals(getLAUNCHER_VERSION());
    }
}
