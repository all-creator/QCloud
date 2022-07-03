package easystars.quasarcloudserver.spring.controllers;

import easystars.quasarcloudserver.bigdata.logging.LogLevel;
import easystars.quasarcloudserver.bigdata.mysql.repository.ClientRepository;
import easystars.quasarcloudserver.bigdata.mysql.repository.UserRepository;
import easystars.quasarcloudserver.spring.management.busnes.object.LogMessage;
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
