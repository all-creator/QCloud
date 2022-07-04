package com.qcloud.server.telegram.system;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class TGServerCore extends TelegramLongPollingCommandBot {

    @Getter
    @Value("${bot.name}")
    private String botUsername;

    @Getter
    @Value("${launcher.ver}")
    private String clientVersion;

    @Getter
    @Value("${bot.token}")
    private String botToken;


    @Override
    public void processNonCommandUpdate(Update update) {
        ExecutorService exec = Executors.newCachedThreadPool();
        exec.execute(new Messenger(update));
    }

    public static class Messenger implements Runnable{

        private final Update message;

        Messenger(Update message){
            this.message = message;
        }

        @Override
        public void run() {

        }
    }
}
