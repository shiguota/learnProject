package com.redis.module.util.lock;

import redis.clients.jedis.Jedis;

import java.util.Collections;

public class Rlock {

    private static final String LOCK_SUCCESS = "OK";
    private static final String SET_IF_NOT_EXIST = "NX";
    private static final String SET_WITH_EXPIRE_TIME = "PX";
    private static final Long RELEASE_SUCCESS = 1L;
    /**
     * 设置分布式锁
     * @param jedis Redis客户端
     * @param lockKey 锁
     * @param clientId 请求标识
     * @param expireTime 超期时间
     * @return 是否设置成功
     */
    public static boolean setLock(Jedis jedis, String lockKey, String clientId, int expireTime) {

        String result = jedis.set(lockKey, clientId, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, expireTime);

        if (LOCK_SUCCESS.equals(result)) {
            return true;
        }
        return false;

    }

    /**
     * 释放分布式锁
     * @param jedis Redis客户端
     * @param lockKey 锁
     * @param clientId 客户端标识
     * @return 是否释放成功
     */
    public static boolean releaseLock(Jedis jedis, String lockKey, String clientId) {

        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        Object result = jedis.eval(script, Collections.singletonList(lockKey), Collections.singletonList(clientId));

        if (RELEASE_SUCCESS.equals(result)) {
            return true;
        }
        return false;

    }
}