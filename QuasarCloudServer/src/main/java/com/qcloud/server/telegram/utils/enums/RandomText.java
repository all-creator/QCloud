package com.qcloud.server.telegram.utils.enums;

import java.util.Random;

@Deprecated(since = "4.0.0")
public enum RandomText {
    F_F("небольшая нагрузка на ЦП"),
    F_S("очередное подорожание видеокарт"),
    F_T("захват искуственным интелектом всего человечества"),
    S_F("Я пока что так не умею"),
    S_S("Уже добовляю в очередь разработки эту функцию"),
    S_T("Хм, а вы уверены в своих действиях?"),
    ;

    String string;

    RandomText(String text) {
        string = text;
    }

    public static String getFRandomString(){
        int random = new Random().nextInt(3);
        return switch (random) {
            case 0 -> F_F.string;
            case 1 -> F_S.string;
            default -> F_T.string;
        };
    }

    public static String getSRandomString(){
        int random = new Random().nextInt(3);
        return switch (random) {
            case 0 -> S_F.string;
            case 1 -> S_S.string;
            default -> S_T.string;
        };
    }
}
