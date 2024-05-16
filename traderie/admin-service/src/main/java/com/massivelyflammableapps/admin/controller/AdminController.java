package com.massivelyflammableapps.admin.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("api/v1/controller")
public class AdminController {
    @PostMapping("command")
    public ResponseEntity<String> postMethodName(@RequestBody String entity) {
        try {
            return ResponseEntity.ok(entity);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }
    
    @PutMapping("command")
    public ResponseEntity<String> putMethodName(@RequestBody String entity) {
        try {
            return ResponseEntity.ok(entity);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    @DeleteMapping("command")
    public ResponseEntity<String> deleteMethodName(@RequestBody String entity) {
        try {
            return ResponseEntity.ok(entity);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    @PostMapping("queue")
    public ResponseEntity<String> postMethodName2(@RequestBody String entity) {
        try {
            return ResponseEntity.ok(entity);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }
}
