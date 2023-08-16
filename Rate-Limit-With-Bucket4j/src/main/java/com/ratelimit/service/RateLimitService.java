package com.ratelimit.service;


import org.springframework.http.ResponseEntity;

public interface RateLimitService {

    boolean tryConsume();

    ResponseEntity<?> getHttpErrorResponse();

}
