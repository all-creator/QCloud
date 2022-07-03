package easystars.quasarcloudserver.telegram.utils;

import easystars.quasarcloudserver.telegram.system.Bot;

public class Scripts {

    public static boolean hasClient(long id){
        return !Bot.getUserRepository().findById(id).get().getClients().isEmpty();
    }

}
