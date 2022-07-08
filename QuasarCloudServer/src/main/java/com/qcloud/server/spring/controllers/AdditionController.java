package com.qcloud.server.spring.controllers;

import com.qcloud.server.bigdata.mysql.repository.ClientRepository;
import com.qcloud.server.bigdata.mysql.repository.UserRepository;
import com.qcloud.server.spring.management.busnes.object.Message;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;

@Controller
public class AdditionController extends MainController<AdditionController> {
    AdditionController(UserRepository userRepository, ClientRepository clientRepository) {
        super(userRepository, clientRepository, AdditionController.class);
    }

    @RequestMapping(value = "/status")
    @ResponseBody
    public ResponseEntity<Boolean> register() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/res/download")
    public void resDownload(@RequestBody Message message, HttpServletResponse response) throws IOException {
        try (FileInputStream inputStream = new FileInputStream(message.getText())) {

            response.setHeader("Content-Disposition", "attachment; filename=\""+message.getText()+"\"");
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            response.setContentLength(inputStream.available());

            ServletOutputStream outputStream = response.getOutputStream();
            IOUtils.copy(inputStream, outputStream);

            outputStream.close();
        }
    }
}
