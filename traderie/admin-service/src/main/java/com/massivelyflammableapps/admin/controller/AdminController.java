package com.massivelyflammableapps.admin.controller;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.massivelyflammableapps.shared.dto.admin.AddCommandRequest;
import com.massivelyflammableapps.shared.dto.admin.DeleteCommandRequest;
import com.massivelyflammableapps.shared.dto.admin.ExecuteCommandRequest;
import com.massivelyflammableapps.shared.dto.admin.UpdateCommandRequest;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("api/v1/controller")
@Slf4j
public class AdminController {
    @Autowired
    private Environment environment;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping("command/{service}")
    public ResponseEntity<Object> executeCommand(@RequestBody ExecuteCommandRequest request,
            @PathVariable String service) {
        try {
            String propertyKey = service + "-service.queue.name";

            String queueName = environment.getProperty(propertyKey);

            Object result = rabbitTemplate.convertSendAndReceiveAsType("", queueName, request,
                    new ParameterizedTypeReference<Object>() {
                    });
            log.info("Command executed successfully.");
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
            log.info(e.getMessage());
            return ResponseEntity.status(500).build();
        }
    }

    @PostMapping("command/{service}")
    public ResponseEntity<Boolean> addCommand(@RequestBody AddCommandRequest request,
            @PathVariable String service) {
        try {
            String propertyKey = service + "-service.queue.name";

            String queueName = environment.getProperty(propertyKey);

            Boolean result = rabbitTemplate.convertSendAndReceiveAsType("", queueName, request,
                    new ParameterizedTypeReference<Boolean>() {
                    });
            log.info("Command added successfully.");

            return ResponseEntity.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
            log.info(e.getMessage());
            return ResponseEntity.status(500).build();
        }
    }

    @PutMapping("command/{service}")
    public ResponseEntity<Boolean> updateCommand(@RequestBody UpdateCommandRequest request,
            @PathVariable String service) {
        try {
            String propertyKey = service + "-service.queue.name";

            String queueName = environment.getProperty(propertyKey);

            Boolean result = rabbitTemplate.convertSendAndReceiveAsType("", queueName, request,
                    new ParameterizedTypeReference<Boolean>() {
                    });

            log.info("Command updated successfully.");

            return ResponseEntity.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
            log.info(e.getMessage());

            return ResponseEntity.status(500).build();
        }
    }

    @DeleteMapping("command/{service}")
    public ResponseEntity<Boolean> deleteCommand(@RequestBody DeleteCommandRequest request,
            @PathVariable String service) {
        try {
            String propertyKey = service + "-service.queue.name";

            String queueName = environment.getProperty(propertyKey);

            Boolean result = rabbitTemplate.convertSendAndReceiveAsType("", queueName, request,
                    new ParameterizedTypeReference<Boolean>() {
                    });
            log.info("Command deleted successfully.");

            return ResponseEntity.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
            log.info(e.getMessage());
            return ResponseEntity.status(500).build();
        }
    }

}
