package com.qcloud.server.telegram.utils.enums;

import lombok.Getter;

@Getter
public enum UpdateType {
    FULL_INFO("info", "get info about client", "full"),
    INFO("info", "get info about client", "low"),
    SHUTDOWN("shutdown","shutdown or reload client PC",  "s", "h", "0"),
    RELOAD("shutdown","shutdown or reload client PC", "r", "r", "0"),
    LIGHT("brightness","brightness control", "50"),
    SOUND("volume","volume control", "50"),
    COMMAND("command","anything"),
    FILE("file","work with files"),
    SCRIPT("script","run custom system scripts"),
    ;

    String typeName;
    String description;
    String[] arguments;

    UpdateType(String typeName, String description, String... arguments) {
        this.typeName = typeName;
        this.description = description;
        this.arguments = arguments;
    }

    public UpdateType withArgs(String... arguments){
        this.arguments = arguments;
        return this;
    }

    public UpdateType withArgsSplit(String arguments){
        this.arguments = arguments.split(" ");
        return this;
    }

    public UpdateType setCommand(String command, String... arguments){
        String[] strings = new String[arguments.length+1];
        strings[0] = command;
        System.arraycopy(arguments, 0, strings, 1, arguments.length);
        this.arguments = strings;
        return this;
    }

}
