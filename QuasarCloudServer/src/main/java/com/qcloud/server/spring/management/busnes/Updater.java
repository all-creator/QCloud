package com.qcloud.server.spring.management.busnes;

import lombok.Getter;

import java.io.File;

@Deprecated(since = "4.0.0")
public class Updater {
    @Getter
    private static final String LAUNCHER_VERSION = "1.0.4-beta";

    public boolean check(String version){
        return !version.equals(getLAUNCHER_VERSION());
    }
    
    public File getCurrentVersion(OS os) {
        return switch (os) {
            case UNIX -> new File("QuasarCloud-unix.zip");
            case MACOSX -> new File("QuasarCloud-mac.zip");
            case SOLARIS -> new File("QuasarCloud-solaris.zip");
            default -> new File("QuasarCloud.zip");
        };
    }
    
    enum OS {
        WINDOWS,
        MACOSX,
        UNIX,
        SOLARIS,
        OTHER
    }
}
