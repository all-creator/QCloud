package com.qcloud.server.spring.management.busnes;

import com.qcloud.server.bigdata.mysql.models.Client;
import com.qcloud.server.bigdata.mysql.models.User;
import com.qcloud.server.spring.management.busnes.object.Request;
import com.qcloud.server.telegram.system.Bot;
import com.qcloud.server.telegram.utils.enums.UpdateType;
import lombok.Data;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.ArrayList;
import java.util.List;

@Data
public class Session {
    User user;
    List<Client> clients = new ArrayList<>();
    Client currentClient;
    String state = "none";
    int volume = 0;
    int brightness = 0;
    String name = "user";

    public Session(User user) {
        this.user = user;
    }

    public static Session openNewSession(User user, Client client){
        Session session = new Session(user);
        session.getClients().add(client);
        session.setCurrentClient(client);
        Bot.getSessionMap().put(user.getTelegramId().toString(), session);
        Bot.getSessionMap().put(client.getHash(), session);
        UserServes.requestCreate(client);
        session.setInfo();
        return session;
    }

    public void setInfo(){
        if (currentClient.getInfo() != null && !currentClient.getInfo().equals("")){
            String[] data = currentClient.getInfo().split("!%");
            for (String param : data) {
                if (param.startsWith("vol:")) volume = Integer.parseInt(param.split(":")[1]);
                if (param.startsWith("bth:")) brightness = Integer.parseInt(param.split(":")[1]);
                if (param.startsWith("name:")) name = param.split(":")[1];
            }
        }
    }

    public void setupInfo(){
        currentClient.setInfo("vol:0!%bth:0!%name:user!%");
        Bot.getClientRepository().save(currentClient);
    }

    public void sessionAutoConfig(){
        SendMessage message = new SendMessage(user.getTelegramId().toString(),"Давайте настроим ваш клиент что бы вы смогли пользоваться QCloud комфортно");
        Bot.sendMessage(message);
        setupInfo();
        sessionVolumeConfig();
    }

    public void sessionVolumeConfig(){
        Bot.sendMessage(new SendMessage(user.getTelegramId().toString(),"Для начала задайте (в процентах) громкость которую мне установить на вашем компьютере:"));
        state = "volume";
    }

    public void sessionBrightnessConfig(){
        Bot.sendMessage(new SendMessage(user.getTelegramId().toString(),"Теперь задайте (в процентах) яркость которую мне установить на вашем компьютере:"));
        state = "brightness";
    }

    public void sessionAuthConfig(){
        Bot.sendMessage(new SendMessage(user.getTelegramId().toString(),"Теперь пришлите мне пожалуйста сообщение в виде: \"логин:пароль\" (без символов \") от ваших бас программ - что бы авторизироваться в системе. Не волнуйтесь я обрежу пустые символы и пробелы можете просто скопировать и вставить это сюда"));
        state = "auth";
    }

    public void sessionFinishConfig(){
        Bot.sendMessage(new SendMessage(user.getTelegramId().toString(),"Настройка завершена! Приятного пользования!"));
        state = "none";
    }

    public void setVolumeLevel(int volume){
        this.volume = volume;
        changeInfo("vol:", volume);
        addUpdate(new Request(UpdateType.SOUND.withArgs(String.valueOf(volume))));
    }

    public void setBrightnessLevel(int brightness){
        this.brightness = brightness;
        changeInfo("bth:", brightness);
        addUpdate(new Request(UpdateType.LIGHT.withArgs(String.valueOf(brightness))));
    }

    public void setClientName(String name){
        this.name = name;
    }

    public void changeInfo(String cParam, int cData){
        String[] data = currentClient.getInfo().split("!%");
        StringBuilder builder = new StringBuilder();
        for (String param : data) {
            if (param.startsWith(cParam)) param = cParam+cData;
            builder.append(param).append("!%");
        }
        currentClient.setInfo(builder.toString());
        Bot.getClientRepository().save(currentClient);
    }

    public void addUpdate(Request request){
        UserServes.requestAdd(currentClient, request);
    }

    public void addUpdate(UpdateType type){
        UserServes.requestAdd(currentClient, new Request(type));
    }
}
