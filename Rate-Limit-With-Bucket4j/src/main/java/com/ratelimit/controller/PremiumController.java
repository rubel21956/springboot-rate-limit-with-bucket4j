package com.ratelimit.controller;


import com.ratelimit.service.RateLimitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/premium")
public class PremiumController {

    @Autowired
    private RateLimitService rateLimitService;

    @GetMapping("/consume2")
    public ResponseEntity consume2( @RequestHeader(value = "api-key") String apiKey){
        System.out.println("APIKEY:: "+apiKey);
        if(rateLimitService.tryConsume()){
            return ResponseEntity.status(HttpStatus.OK).body("{\"message\":\"Request successfully consumed.\"}");
        }else {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body("{\"message\":\"Too many requests found. Try after some time.\"}");
        }
    }
}
