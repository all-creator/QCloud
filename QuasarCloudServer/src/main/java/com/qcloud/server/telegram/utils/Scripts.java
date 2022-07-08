package com.qcloud.server.telegram.utils;

import com.qcloud.server.telegram.system.Bot;

public class Scripts {

    @Deprecated(since = "4.0.0")
    public static boolean hasClient(long id){
        return !Bot.getUserRepository().findById(id).get().getClients().isEmpty();
    }

}
