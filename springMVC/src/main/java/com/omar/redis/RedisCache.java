package com.omar.redis;

import org.apache.commons.lang3.SerializationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.concurrent.Callable;

/**
 * @Author omar
 * Company:   csii
 * @version: 1.0
 * @since: JDK 1.6.0_26
 * Create at:   2018/4/19
 * Description:
 */
public class RedisCache implements Cache{

    /**
     * Redis
     */
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 缓存名称
     */
    private String name;

    /**
     * 超时时间
     */
    private long timeout=86400;

    Logger log= LoggerFactory.getLogger(getClass());



    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Object getNativeCache() {
        return this.redisTemplate;
    }

    @Override
    public ValueWrapper get(Object key) {
        log.debug("------缓存获取-------key="+key.toString());
        if (StringUtils.isEmpty(key)) {
            log.warn("key is null");
            log.debug("------缓存不存在-------");
            return null;
        } else {
            final String finalKey;
            if (key instanceof String) {
                finalKey = (String) key;
            } else {
                finalKey = key.toString();
            }
            Object object = null;
            object = redisTemplate.execute(new RedisCallback<Object>() {
                public Object doInRedis(RedisConnection connection) throws DataAccessException {
                    byte[] key = finalKey.getBytes();
                    byte[] value = connection.get(key);
                    if (value == null) {
                        log.debug("------缓存不存在-------");
                        return null;
                    }
                    return SerializationUtils.deserialize(value);
                }
            });
            ValueWrapper obj=(object != null ? new SimpleValueWrapper(object) : null);
            log.debug("------获取到内容-------"+obj);
            return obj;
        }
    }

    @Override
    public <T> T get(Object key, Class<T> type) {
        log.debug("------缓存获取-------key="+key.toString()+" type="+type.getName());
        if (StringUtils.isEmpty(key) || null == type) {
            log.warn("key is null");
            log.debug("------缓存不存在-------");
            return null;
        } else {
            final String finalKey;
            final Class<T> finalType = type;
            if (key instanceof String) {
                finalKey = (String) key;
            } else {
                finalKey = key.toString();
            }
            final Object object = redisTemplate.execute(new RedisCallback<Object>() {
                public Object doInRedis(RedisConnection connection) throws DataAccessException {
                    byte[] key = finalKey.getBytes();
                    byte[] value = connection.get(key);
                    if (value == null) {
                        log.debug("------缓存不存在-------");
                        return null;
                    }
                    return SerializationUtils.deserialize(value);
                }
            });
            if (finalType != null && finalType.isInstance(object) && null != object) {
                log.debug("------获取到内容-------"+object);
                return (T) object;
            } else {
                log.debug("------未找到对应类型的缓存-------");
                return null;
            }
        }
    }

    @Override
    public <T> T get(Object key, Callable<T> callable) {
        return null;
    }

    @Override
    public void put(Object key, Object value) {
        log.debug("-------加入缓存------");
        log.debug("key----:"+key);
        log.debug("value----:"+value);
        if (StringUtils.isEmpty(key) || StringUtils.isEmpty(value)) {
            log.warn("key or value is null");
            return;
        } else {
            final String finalKey;
            if (key instanceof String) {
                finalKey = (String) key;
            } else {
                finalKey = key.toString();
            }
            if (!StringUtils.isEmpty(finalKey)) {
                final Object finalValue = value;
                redisTemplate.execute(new RedisCallback<Boolean>() {
                    @Override
                    public Boolean doInRedis(RedisConnection connection) {
                        connection.set(finalKey.getBytes(), SerializationUtils.serialize((Serializable)finalValue));
                        // 设置超时间
                        connection.expire(finalKey.getBytes(), timeout);
                        return true;
                    }
                });
            }
        }
    }

    @Override
    public ValueWrapper putIfAbsent(Object key, Object value) {
        return null;
    }

    @Override
    public void evict(Object key) {
        log.debug("-------緩存刪除------key="+key);
        if (null != key) {
            final String finalKey;
            if (key instanceof String) {
                finalKey = (String) key;
            } else {
                finalKey = key.toString();
            }
            if (!StringUtils.isEmpty(finalKey)) {
                redisTemplate.execute(new RedisCallback<Long>() {
                    public Long doInRedis(RedisConnection connection) throws DataAccessException {
                        return connection.del(finalKey.getBytes());
                    }
                });
            }
        }
    }

    @Override
    public void clear() {
        log.debug("-------緩存清理------");
        redisTemplate.execute(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                connection.flushDb();
                return "ok";
            }
        });
    }

    public RedisTemplate<String, Object> getRedisTemplate() {
        return redisTemplate;
    }

    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getTimeout() {
        return timeout;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }
}
