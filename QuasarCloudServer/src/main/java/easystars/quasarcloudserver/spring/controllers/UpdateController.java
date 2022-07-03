package easystars.quasarcloudserver.spring.controllers;

import easystars.quasarcloudserver.bigdata.mysql.repository.ClientRepository;
import easystars.quasarcloudserver.bigdata.mysql.repository.UserRepository;
import easystars.quasarcloudserver.spring.management.busnes.Updater;
import easystars.quasarcloudserver.spring.management.busnes.object.Message;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;

@Controller
public class UpdateController extends MainController<UpdateController> {

    UpdateController(UserRepository userRepository, ClientRepository clientRepository) {
        super(userRepository, clientRepository, UpdateController.class);
    }

    @RequestMapping(value = "/update/download")
    public void upgrader(HttpServletResponse response) throws IOException {
        try (FileInputStream inputStream = new FileInputStream("QuasarCloud.zip")) {

            response.setHeader("Content-Disposition", "attachment; filename=\"QuasarCloud.zip\"");
            response.setContentType("application/zip");
            response.setContentLength(inputStream.available());

            ServletOutputStream outputStream = response.getOutputStream();
            IOUtils.copy(inputStream, outputStream);

            outputStream.close();
        }
    }

    @RequestMapping(value = "/update/check", produces = "application/json", consumes = "application/json")
    @ResponseBody
    public ResponseEntity<String> checker(@RequestBody Message message, HttpServletRequest request) {
        log.info("Проверка обновления у пользователя {}", request.getRemoteAddr());
        String version = message.getText();
        if (new Updater().check(version)) {
            version = "find";
        } else version = "last";
        log.info("Значение новых обновлений {}", version);
        return new ResponseEntity<>(version, HttpStatus.OK);
    }
}
