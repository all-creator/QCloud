package com.qcloud.server.spring.controllers;

import com.qcloud.server.bigdata.mysql.models.Client;
import com.qcloud.server.bigdata.mysql.repository.ClientRepository;
import com.qcloud.server.bigdata.mysql.repository.UserRepository;
import com.qcloud.server.spring.management.busnes.object.Request;
import com.qcloud.server.telegram.system.Bot;
import com.qcloud.server.spring.management.busnes.Session;
import com.qcloud.server.spring.management.busnes.UserServes;
import com.qcloud.server.spring.management.busnes.object.Hash;
import com.qcloud.server.spring.management.busnes.object.LicenseKey;
import com.qcloud.server.spring.management.busnes.object.TelegramMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Queue;

@Controller
public class TelegramController extends MainController<TelegramController> {

    public TelegramController(UserRepository userRepository, ClientRepository clientRepository) {
        super(userRepository, clientRepository, TelegramController.class);
    }

    @PostMapping(value = "/tg/register", produces = "application/json", consumes = "application/json")
    @ResponseBody
    public ResponseEntity<Boolean> register(@RequestBody LicenseKey auth, HttpServletRequest request) {
        Client client = new Client();
        client.setUuid(auth.getUuidLocal());
        client.setIp(request.getRemoteAddr());
        client.setInfo("None");
        UserServes.unregisterUsers.put(new String(auth.getUuid()), client);
        log.info("Получен запрос на регистрацию: {}", client.getIp());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/tg/send", produces = "application/json", consumes = "application/json")
    @ResponseBody
    public ResponseEntity<Boolean> send(@RequestBody TelegramMessage message, HttpServletRequest request) {
        Client client = clientRepository.findById(message.getClientId()).orElse(null);
        if (client == null){
            log.fatal("Client not found: {}", message.getClientId());
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        client.getUsers().forEach(user -> Bot.sendMessage(new SendMessage(user.getTelegramId().toString(), message.getMessage())));
        log.info("Получено сообщение от клиента {}: {}", request.getRemoteAddr(), message.getMessage());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/tg/update", produces = "application/json", consumes = "text/plain")
    @ResponseBody
    public ResponseEntity<Request> safetyUpdate(@RequestBody String hash, HttpServletRequest request) {
        if (hash.startsWith("\"") && hash.endsWith("\"")) hash = hash.substring(1, hash.length()-1);
        Client client = clientRepository.findById(hash).orElse(null);
        if (client == null){
            log.fatal("Client not found: {}", hash);
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        if (!UserServes.requestCheck(client)){
            client.getUsers().forEach(user1 -> Session.openNewSession(user1, client));
            log.info("Client registered: {}", hash);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            Queue<Request> requests = UserServes.requestGet(client);
            Request update;
            if (!requests.isEmpty()) {
                update = requests.poll();
                log.info("Выполняю запрос: {} - {} от {}", update.getType(), update.getArgs(), request.getRemoteAddr());
            } else update = new Request("ping", "send status server", new String[]{});
            return new ResponseEntity<>(update, HttpStatus.OK);
        }
    }
}
