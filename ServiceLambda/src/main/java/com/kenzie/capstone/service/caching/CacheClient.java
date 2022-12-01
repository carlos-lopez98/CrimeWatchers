package com.kenzie.capstone.service.caching;

import com.kenzie.capstone.service.dependency.DaggerServiceComponent;

import com.kenzie.capstone.service.model.ExampleRecord;
import redis.clients.jedis.Jedis;

import java.util.Optional;

public class CacheClient {

    // Put your Cache Client Here

    // Since Jedis is being used multithreaded, you MUST get a new Jedis instances and close it inside every method.
    // Do NOT use a single instance across multiple of these methods

    // Use Jedis in each method by doing the following:
    // Jedis cache = DaggerServiceComponent.create().provideJedis();
    // ... use the cache
    // cache.close();

    // Remember to check for null keys!

    public void setValue(String key, int seconds, String value) {
        // Check for non-null key
        // Set the value in the cache
        Jedis cache = DaggerServiceComponent.create().provideJedis();
        if(key != null && value != null){
            cache.set(key, String.valueOf(value));
        }

        cache.close();
    }
    public Optional<String> getValue(String key) {
        // Check for non-null key
        // Retrieves the Optional values from the cache
        Jedis cache = DaggerServiceComponent.create().provideJedis();
        if (key != null){
            return Optional.ofNullable(cache.get(key));
        }else{
            cache.close();
            return Optional.empty();
        }
    }
    public void invalidate(String key) {
        // Check for non-null key
        // Delete the key
        Jedis cache = DaggerServiceComponent.create().provideJedis();

        if(key != null){
            cache.del(key);
        }

        cache.close();
    }
    private void checkNonNullKey(String key) {
        // Ensure the key isn't null
        // What should you do if the key *is* null?
        Jedis cache = DaggerServiceComponent.create().provideJedis();
        if(key == null){
            throw new IllegalArgumentException("Key for Cache must not be null");
        }
        cache.close();
    }










}
