package com.ratelimit.domain;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Refill;

import java.time.Duration;

public enum FeatureBucket {

    FREE(2),
    BASIC(3),
    PROFESSIONAL(5),
    PREMIUM(7),
    SILVER(9),
    PLATINUM(11);




    public static FeatureBucket resolvePlanFromApiKey(String apiKey) {
        if (apiKey == null || apiKey.isEmpty()|| apiKey.startsWith("FR001")) {
            return FREE;
        } else if (apiKey.startsWith("BA001")) {
            return BASIC;
        } else if (apiKey.startsWith("PS001")) {
            return PROFESSIONAL;
        } else if (apiKey.startsWith("PR001")) {
            return PREMIUM;
        } else if (apiKey.startsWith("SL001")) {
            return SILVER;
        } else if (apiKey.startsWith("PT001")) {
            return PLATINUM;
        }
        return FREE;
    }


    private int bucketCapacity;
    public int getBucketCapacity() {
        return bucketCapacity;
    }
    private FeatureBucket(int bucketCapacity) {
        this.bucketCapacity = bucketCapacity;
    }

    public Bandwidth getLimit() {
        return Bandwidth.classic(bucketCapacity, Refill.intervally(bucketCapacity, Duration.ofMinutes(1)));
    }
}
