package com.qcloud.server.telegram.utils.enums;

import com.qcloud.server.telegram.utils.ButtonAPI;
import com.qcloud.server.bigdata.mysql.models.Client;
import com.qcloud.server.bigdata.mysql.models.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

import java.util.ArrayList;
import java.util.List;

public enum Keyboard {
    HOME(){
        public ReplyKeyboard getKeyboard(User user) {
            return setUpResize(ButtonAPI.createKeyboard(new String[][]{{Holders.MENU.getString()},
                    {Holders.CERBERUS.getString()},
                    {Holders.INFO.getString(), Holders.ABOUT.getString()}}));
        }
    },
    MENU() {
        public ReplyKeyboard getKeyboard(User user) {
            return ButtonAPI.createKeyboard(new String[][]{{Holders.INFORMATION_OS.getString(), Holders.FILES.getString()},
                    {Holders.BRIGHTNESS.getString(), Holders.VOLUME.getString()},
                    {Holders.RESTART.getString(), Holders.END_OF_WORK.getString()},
                    {Holders.HOME.getString(), Holders.INFO_HOME.getString()},
                    {Holders.VERSION_CONTROL.getString()}});
        }
    },
    CLIENTS() {
        public ReplyKeyboard getKeyboard(User user) {
            List<Client> list = new ArrayList<>(user.getClients());
            var clientsList = new String[(int) Math.ceil(list.size()/2.0) + 1][];
            clientsList[0] = new String[]{Holders.ALL_CLIENT.getString()};
            var j = 1;
            var i = 1;
            for (Client client : list) {
                if (j % 2 != 0) {
                    if (list.size() % 2 == 0) clientsList[j] = new String[2];
                    else {
                        if (list.size() == j) clientsList[j] = new String[1];
                        else clientsList[j] = new String[2];
                    }
                }
                clientsList[j][i-1] = client.getIp();
                if (i % 2 == 0){
                    j++;
                    i = 0;
                }
                i ++;
            }
            if (clientsList.length < 2) return null;
            return ButtonAPI.createKeyboard(clientsList);
        }
    },
    ;

    public abstract ReplyKeyboard getKeyboard(User user);

    private static ReplyKeyboard setUpResize(ReplyKeyboardMarkup keyboard){
        keyboard.setResizeKeyboard(true);
        return keyboard;
    }
}
