package com.ratelimit.service;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Slf4j
@Service
public class RateLimitServiceImpl implements RateLimitService {

    private long rateLimitTimeInMin;

    private long requestLimit;

    private Refill refill;

    private Bucket bucket;

    public RateLimitServiceImpl(@Value("${request.time.in.min}") Long rateLimitTimeInMin,
                                @Value("${request.limit}") Long requestLimit) {
        this.rateLimitTimeInMin = rateLimitTimeInMin;
        this.requestLimit = requestLimit;
        this.refill = Refill.greedy(this.requestLimit, Duration.ofMinutes(this.rateLimitTimeInMin));
        this.bucket = Bucket4j.builder().addLimit(Bandwidth.classic(this.requestLimit, this.refill)).build();
    }

    @Override
    public boolean tryConsume() {

        log.info("Http Request Rate Consumed ::{}, And Remaining Tokens ::{}.", 1L, this.bucket.getAvailableTokens()-1L);
        return this.bucket.tryConsume(1);
    }

    @Override
    public ResponseEntity<?> getHttpErrorResponse() {
        ResponseEntity<?> responseEntity = new ResponseEntity<>("{\"message\":\"Too Many Requests. Please, Try again after some time\".}", HttpStatus.TOO_MANY_REQUESTS);
        log.info("Http Request Rate Consume Limit ended. Rendering Error Response :: {}.", responseEntity);
        return responseEntity;
    }

}
