package com.ratelimit.service;

import com.ratelimit.domain.FeatureBucket;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Service
public class PricingPlanService {

    private final Map<String, Bucket> cache = new ConcurrentHashMap<>();

    public Bucket resolveBucket(String apiKey) {
        return cache.computeIfAbsent(apiKey, this::newBucket);
    }

    private Bucket newBucket(String apiKey) {
        FeatureBucket  featureBucket = FeatureBucket.resolvePlanFromApiKey(apiKey);
        return Bucket.builder()
                .addLimit(featureBucket.getLimit())
                .build();
    }


    private Bucket bucket(Bandwidth limit) {
        return Bucket.builder()
                .addLimit(limit)
                .build();
    }
}

