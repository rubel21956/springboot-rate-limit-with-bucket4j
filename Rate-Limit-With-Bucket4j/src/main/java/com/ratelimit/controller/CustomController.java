package com.ratelimit.controller;


import com.ratelimit.service.RateLimitService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@Slf4j
@RestController
@RequestMapping(value = "/test")
public class CustomController {

    @Autowired
    private RateLimitService rateLimitService;

    @GetMapping("/consume1")
    public ResponseEntity<?> consume1(){
        log.info("/consume1 invoked.");
//        if(rateLimitService.tryConsume()) {
            return ResponseEntity.status(HttpStatus.OK).body("{\n \"message\":\"Request url='/api/test/consume1' consumed successfully.\"}");
//        }else{
//            return rateLimitService.getHttpErrorResponse();
//        }
    }
}
