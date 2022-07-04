package com.qcloud.server.bigdata.logging;

import org.apache.logging.log4j.Level;

public enum LogLevel {
    LOG(org.apache.logging.log4j.Level.forName("LOG", 450)),
    SUCCESS(org.apache.logging.log4j.Level.forName("SUCCESS", 400)),
    SUCCESS_SEND(org.apache.logging.log4j.Level.forName("SUCCESS_SEND", 400)),
    ERROR(org.apache.logging.log4j.Level.forName("ERROR", 300)),
    CLIENT_ERROR(org.apache.logging.log4j.Level.forName("CLIENT_ERROR", 200)),
    ;

    public final Level level;

    LogLevel(Level level) {
        this.level = level;
    }
}
