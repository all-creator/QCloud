package com.qcloud.server.spring.controllers;

import com.qcloud.server.bigdata.mysql.repository.ClientRepository;
import com.qcloud.server.bigdata.mysql.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class MainController<T> {

    final Logger log;


    public final UserRepository userRepository;
    public final ClientRepository clientRepository;

    MainController(UserRepository userRepository, ClientRepository clientRepository, Class<T> clazz) {
        this.userRepository = userRepository;
        this.clientRepository = clientRepository;
        log = LogManager.getLogger(clazz);
    }

}
