package com.qcloud.server.spring.controllers;

import com.qcloud.server.bigdata.logging.LogLevel;
import com.qcloud.server.bigdata.mysql.repository.ClientRepository;
import com.qcloud.server.bigdata.mysql.repository.UserRepository;
import com.qcloud.server.spring.management.busnes.object.LogMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

public class LogController extends MainController<LogController> {
    public LogController(UserRepository userRepository, ClientRepository clientRepository) {
        super(userRepository, clientRepository, LogController.class);
    }

    @PostMapping(value = "/log", produces = "application/json", consumes = "application/json")
    @ResponseBody
    public ResponseEntity<Boolean> log(@RequestBody LogMessage message, HttpServletRequest request) {
        log.log(LogLevel.LOG.level, "{} - v{}: {}", message.getClient(), message.getVersion(), message.getMessage());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
