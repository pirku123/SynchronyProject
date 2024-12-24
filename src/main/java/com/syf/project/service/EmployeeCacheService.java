package com.syf.project.service;

import com.syf.project.config.RedisUtil;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class EmployeeCacheService {

    private static final Logger logger = org.apache.logging.log4j.LogManager.getLogger(EmployeeCacheService.class);

    @Autowired
    private RedisUtil<Object> redisUtil;

    @Value("${redis.cachingTime}")
    private long cachingTime;

    @Retryable(value = {DataAccessException.class},maxAttempts = 3 ,backoff= @Backoff(delay= 1000))
    public Object readFromLocalRedis(String key){
        try{
            logger.info("START_OF_PROCESSING", Thread.currentThread().getStackTrace()[1].getMethodName());
            return redisUtil.getValue(key);
        }catch(Exception e){
            logger.error("INTERMEDIATE_PROCESSING_ERROR", Thread.currentThread().getStackTrace()[1],e.getLocalizedMessage());
            throw e;
        }
    }

    @Retryable(value = {DataAccessException.class},maxAttempts = 3 ,backoff= @Backoff(delay= 1000))
    public void insetIntoLocalRedis(String key, Object value){
        try{
            logger.info("START_OF_PROCESSING", Thread.currentThread().getStackTrace()[1].getMethodName());
            redisUtil.putValueWithExpireTime(key,value,cachingTime, TimeUnit.MINUTES);
        }catch(Exception e){
            logger.error("INTERMEDIATE_PROCESSING_ERROR", Thread.currentThread().getStackTrace()[1],e.getLocalizedMessage());
            throw e;
        }
    }

    @Retryable(value = {DataAccessException.class},maxAttempts = 3 ,backoff= @Backoff(delay= 1000))
    public void updateIntoLocalRedis(String key, Object value){
        try{
            logger.info("START_OF_PROCESSING", Thread.currentThread().getStackTrace()[1].getMethodName());
            redisUtil.updateValue(key,value);
        }catch(Exception e){
            logger.error("INTERMEDIATE_PROCESSING_ERROR", Thread.currentThread().getStackTrace()[1],e.getLocalizedMessage());
            throw e;
        }
    }

    @Retryable(value = {DataAccessException.class},maxAttempts = 3 ,backoff= @Backoff(delay= 1000))
    public void deleteFromLocalRedis(String key){
        try{
            logger.info("START_OF_PROCESSING", Thread.currentThread().getStackTrace()[1].getMethodName());
            redisUtil.deleteValue(key);
        }catch(Exception e){
            logger.error("INTERMEDIATE_PROCESSING_ERROR", Thread.currentThread().getStackTrace()[1],e.getLocalizedMessage());
            throw e;
        }
    }

    @Recover
    public void redisCacheRecover(DataAccessException ex,String key,Object value){
        logger.error("ERROR OCCURRED WHILE ACCESSING CACHE", Thread.currentThread().getStackTrace()[1],ex.getLocalizedMessage());
    }
}
