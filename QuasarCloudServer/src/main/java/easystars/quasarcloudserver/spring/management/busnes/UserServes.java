package easystars.quasarcloudserver.spring.management.busnes;

import easystars.quasarcloudserver.bigdata.mysql.models.Client;
import easystars.quasarcloudserver.bigdata.mysql.models.User;
import easystars.quasarcloudserver.bigdata.mysql.repository.UserRepository;
import easystars.quasarcloudserver.spring.management.busnes.object.Update;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

public class UserServes {
    public static Map<String, Client> unregisterUsers = new HashMap<>();

    private static Map<String, Queue<Update>> request = new HashMap<>();

    public static boolean requestCheck(Client client){
        return request.containsKey(client.getHash());
    }

    public static void requestCreate(Client client){
         request.put(client.getHash(), new ArrayDeque<>());
    }

    public static Queue<Update> requestGet(Client client){
        return request.get(client.getHash());
    }

    public static void requestAdd(Client client, Update update){
        System.out.println(request.get(client.getHash()));
        request.get(client.getHash()).add(update);
        System.out.println(request.get(client.getHash()));
    }

    public static void userSave(UserRepository repository, User user){
        repository.save(user);
    }

    public static User userGet(UserRepository repository, org.telegram.telegrambots.meta.api.objects.User user){
        return repository.findById(user.getId()).orElse(null);
    }

    public static boolean userCheck(UserRepository repository, User user){
        return repository.existsById(user.getTelegramId());
    }

    public static boolean userCheck(UserRepository repository, long user){
        return repository.existsById(user);
    }

    public static String userCheckLog(UserRepository repository, User user){
        if (userCheck(repository, user)){
            return "Пользователь уже зарегестрирован в базе данных!";
        } else {
            return "Пользователь добавлен в базу давнных!";
        }
    }
}
