package com.tcs.household.codeUtils.utils;

import cn.hutool.core.util.StrUtil;
import com.tcs.household.util.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPipeline;
import redis.clients.jedis.ShardedJedisPool;
import redis.clients.jedis.Tuple;
import redis.clients.jedis.exceptions.JedisConnectionException;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 
 * @author xiaoj
 *
 */
@Slf4j
public class RedisUtils {

    private static ShardedJedisPool shardedJedisPool;

    static {
        shardedJedisPool = SpringUtil.getBean("shardedJedisPool");
    }

    /***************** Hash操作 start **********************/
    /**
     * 设置表数据
     *
     * @param tableName redis表名
     * @param fieldName redis字段名称
     * @param value     redis values 值修改
     * @return 返回成功与否
     */
    public static Long hset(String tableName, String fieldName, String value) {
        ShardedJedis shardedJedis = null;
        Long length = null;
        try {
            shardedJedis = getShardedJedis();
            length = shardedJedis.hset(tableName, fieldName, value);
        } catch (JedisConnectionException jedisE) {
            log.error("redis的连接出现问题了:", jedisE);
        } catch (Exception e) {
            log.error("redis的连接出现未知异常问题:", e);
        } finally {
            closeSharedJedis(shardedJedis);
        }
        return length;
    }

    /**
     * 移除Map的项目
     * @param tableName
     * @param fieldName
     * @return
     */
    public static Long hdel(String tableName, String fieldName) {
        ShardedJedis shardedJedis = null;
        Long length = null;
        try {
            shardedJedis = getShardedJedis();
            length = shardedJedis.hdel(tableName, fieldName);
        } catch (JedisConnectionException jedisE) {
            log.error("redis的连接出现问题了:", jedisE);
        } catch (Exception e) {
            log.error("redis的连接出现未知异常问题:", e);
        } finally {
            closeSharedJedis(shardedJedis);
        }
        return length;
    }


    /**
     * 设置表数据
     *
     * @param tableName redis表名
     * @param fieldName redis字段名称
     * @param value     redis values 值修改
     * @return 返回成功与否
     */
    public static Long hsetAndExpire(String tableName, String fieldName, String value, int seconds) {
        ShardedJedis shardedJedis = null;
        Long length = null;
        try {
            shardedJedis = getShardedJedis();
            length = shardedJedis.hset(tableName, fieldName, value);
            shardedJedis.expire(tableName, seconds);
        } catch (JedisConnectionException jedisE) {
            log.error("redis的连接出现问题了:", jedisE);
        } catch (Exception e) {
            log.error("redis的连接出现未知异常问题:", e);
        } finally {
            closeSharedJedis(shardedJedis);
        }
        return length;
    }

    /**
     * 设置表数据
     *
     * @param key redis values 值修改
     * @return 返回成功与否
     */
    public static Set<String> keys(String key) {
        ShardedJedis shardedJedis = null;
        Set<String> result = null;
        try {
            shardedJedis = getShardedJedis();
            result = shardedJedis.hkeys(key);
        } catch (JedisConnectionException jedisE) {
            log.error("redis的连接出现问题了:", jedisE);
        } catch (Exception e) {
            log.error("redis的连接出现未知异常问题:", e);
        } finally {
            closeSharedJedis(shardedJedis);
        }
        return result;
    }

    public static String hget(String tableName, String key) {
        ShardedJedis shardedJedis = null;
        String result = null;
        try {
            shardedJedis = getShardedJedis();
            result = shardedJedis.hget(tableName, key);
        } catch (JedisConnectionException jedisE) {
            log.error("redis的连接出现问题了:", jedisE);
        } catch (Exception e) {
            log.error("redis的连接出现未知异常问题:", e);
        } finally {
            closeSharedJedis(shardedJedis);
        }
        return result;
    }

    public static Map<String, String> hgetAll(String tableName) {
        ShardedJedis shardedJedis = null;
        Map<String, String> result = null;
        try {
            shardedJedis = getShardedJedis();
            result = shardedJedis.hgetAll(tableName);
        } catch (JedisConnectionException jedisE) {
            log.error("redis的连接出现问题了:", jedisE);
        } catch (Exception e) {
            log.error("redis的连接出现未知异常问题:", e);
        } finally {
            closeSharedJedis(shardedJedis);
        }
        return result;
    }
    /***************** Hash操作 end**********************/

    /***************** List操作 start**********************/
    /**
     * 从名称为key的list取值value
     *
     * @param key Key值
     */
    public static String lpop(String key) {
        ShardedJedis shardedJedis = null;
        String value = null;
        try {
            shardedJedis = getShardedJedis();
            value = shardedJedis.lpop(key);
        } catch (JedisConnectionException jedisE) {
            log.error("redis的连接出现问题:", jedisE);
        } catch (Exception e) {
            log.error("redis的连接出现未知异常:", e);
        } finally {
            closeSharedJedis(shardedJedis);
        }
        return value;
    }

    /**
     * 阻塞Pop
     * @param key
     * @param timeout
     * @return
     */
    public static String blpop(String key, int timeout) {
        ShardedJedis shardedJedis = null;
        String value = null;
        try {
            shardedJedis = getShardedJedis();
            List<String> list = shardedJedis.blpop(timeout, key);
            if (list != null && list.size() > 1) {
                return list.get(1);
            }
        } catch (JedisConnectionException jedisE) {
            log.error("redis的连接出现问题:", jedisE);
        } catch (Exception e) {
            log.error("redis的连接出现未知异常:", e);
        } finally {
            closeSharedJedis(shardedJedis);
        }
        return value;
    }


    /**
     * 在名称为key的list尾添加一个值为value的元素
     *
     * @param key   key值
     * @param value 值
     */
    public static long rpush(String key, String value) {
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = getShardedJedis();
            return shardedJedis.rpush(key, value);
        } catch (JedisConnectionException jedisE) {
            log.error("redis的连接出现问题:", jedisE);
        } catch (Exception e) {
            log.error("redis的连接出现未知异常:", e);
        } finally {
            closeSharedJedis(shardedJedis);
        }
        return 0;
    }
    /***************** List操作 end**********************/

    /***************** Stirng操作 start**********************/
    /**
     * @param key     存储key
     * @param value   存储数据
     * @param seconds 有效时间 单位秒
     */
    public static void set(String key, String value, int seconds) {
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            shardedJedis.setex(key, seconds, value);
        } catch (JedisConnectionException jedisE) {
            log.error("redis的连接出现问题:", jedisE);
        } catch (Exception e) {
            log.error("redis的连接出现未知异常:", e);
        } finally {
            closeSharedJedis(shardedJedis);
        }

    }

    public static boolean setNx(String key, String value, int seconds) {
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = getShardedJedis();
            Long result = shardedJedis.setnx(key, value);
            if (result == 1) {
                shardedJedis.expire(key, seconds);
                return true;
            }
        } catch (JedisConnectionException jedisE) {
            log.error("redis的连接出现问题:", jedisE);
        } catch (Exception e) {
            log.error("redis的连接出现未知异常:", e);
        } finally {
            closeSharedJedis(shardedJedis);
        }
        return false;
    }


    /**
     * 存储键值对
     *
     * @param key   存储数据key
     * @param value 存储数据
     */
    public static void set(String key, String value) {
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = getShardedJedis();
            shardedJedis.set(key, value);
        } catch (JedisConnectionException jedisE) {
            log.error("redis的连接出现问题:", jedisE);
        } catch (Exception e) {
            log.error("redis的连接出现未知异常:", e);
        } finally {
            closeSharedJedis(shardedJedis);
        }
    }

    /**
     * 根据存储key获取相应的存储value数据
     *
     * @param key 存储数据key
     * @return string
     */
    public static String get(String key) {
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = getShardedJedis();
            return shardedJedis.get(key);
        } catch (Exception e) {
            log.error("redis的连接出现未知异常:", e);
        } finally {
            closeSharedJedis(shardedJedis);
        }
        return null;
    }

    /**
     * 根据存储key设置值为value,并返回key的旧值old value数据
     * 当key没有旧值时，也即是，key不存在时，返回nil
     *
     * @param key 存储数据key
     * @return string
     */
    public static String getSet(String key, String value, int seconds) {
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = getShardedJedis();
            String redisValue = shardedJedis.getSet(key, value);
            shardedJedis.expire(key, seconds);
            return redisValue;
        } catch (Exception e) {
            log.error("redis的连接出现未知异常:", e);
        } finally {
            closeSharedJedis(shardedJedis);
        }
        return null;
    }

    /**
     * 根据存储key删除相应的数据
     *
     * @param key 存储数据key
     */
    public static Long delete(String key) {
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = getShardedJedis();
            if (!shardedJedis.exists(key)) {
                return 0L;
            }
            return shardedJedis.del(key);
        } catch (JedisConnectionException jedisE) {
            log.error("redis的连接出现问题:", jedisE);
        } catch (Exception e) {
            log.error("redis的连接出现未知异常:", e);
        } finally {
            closeSharedJedis(shardedJedis);
        }
        return 0L;
    }

    /**
     * 将一个或多个 member 元素加入到集合 key 当中，已经存在于集合的 member 元素将被忽略
     *
     * @param key     存储数据key
     * @param value   是否添加元素成功
     * @param seconds 添加存在秒数
     * @return 是否成功添加
     */
    public static boolean sadd(String key, String value, int seconds) {
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = getShardedJedis();
            shardedJedis.expire(key, seconds);
            Long result = shardedJedis.sadd(key, value);
            return result > 0;
        } catch (JedisConnectionException jedisE) {
            log.error("redis的连接出现问题:", jedisE);
        } catch (Exception e) {
            log.error("redis的连接出现未知异常:", e);
        } finally {
            closeSharedJedis(shardedJedis);
        }
        return false;
    }

    /**
     * 将 key 中储存的数字值增一
     *
     * @param key key值
     * @return 真实的数量
     */
    public static Long incr(String key) {
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = getShardedJedis();
            return shardedJedis.incr(key);
        } catch (JedisConnectionException jedisE) {
            log.error("redis的连接出现问题:", jedisE);
        } catch (Exception e) {
            log.error("redis的连接出现未知异常:", e);
        } finally {
            closeSharedJedis(shardedJedis);
        }
        return -1L;
    }

    /**
     * 将 key 中储存的数字值变成
     *
     * @param key key值
     * @return 真实的数量
     */
    public static Long incrBy(String key, Long value) {
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = getShardedJedis();
            return shardedJedis.incrBy(key, value);
        } catch (JedisConnectionException jedisE) {
            log.error("redis的连接出现问题:", jedisE);
        } catch (Exception e) {
            log.error("redis的连接出现未知异常:", e);
        } finally {
            closeSharedJedis(shardedJedis);
        }
        return -1L;
    }

    /**
     * 将 key 中储存的数字值增一
     *
     * @param key key值
     * @return 真实的数量
     */
    public static Long incrAndExpire(String key, int secords) {
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = getShardedJedis();
            Long result = shardedJedis.incr(key);
            shardedJedis.expire(key, secords);
            return result;
        } catch (JedisConnectionException jedisE) {
            log.error("redis的连接出现问题:", jedisE);
        } catch (Exception e) {
            log.error("redis的连接出现未知异常:", e);
        } finally {
            closeSharedJedis(shardedJedis);
        }
        return -1L;
    }

    /**
     * 设置失效时间
     *
     * @param key     key值
     * @param seconds 失效时间 秒
     * @return
     */
    public static Long expire(String key, int seconds) {
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = getShardedJedis();
            return shardedJedis.expire(key, seconds);
        } catch (JedisConnectionException jedisE) {
            log.error("redis的连接出现问题:", jedisE);
        } catch (Exception e) {
            log.error("redis的连接出现未知异常:", e);
        } finally {
            closeSharedJedis(shardedJedis);
        }
        return 0L;
    }

    public static void batchInsert(Map<String, String> batchData, int secords) {
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = getShardedJedis();
            ShardedJedisPipeline shardedJedisPipeline = shardedJedis.pipelined();
            for (Map.Entry<String, String> entry : batchData.entrySet()) {
                shardedJedisPipeline.set(entry.getKey(), entry.getValue());
                shardedJedisPipeline.expire(entry.getKey(), secords);
            }
            shardedJedisPipeline.syncAndReturnAll();
        } finally {
            closeSharedJedis(shardedJedis);
        }
    }
    /***************** Stirng操作 End**********************/

    /***************** Zset 操作 Start**********************/
    public static Long zadd(String key, String value, long timestamp) {
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = getShardedJedis();
            return shardedJedis.zadd(key, timestamp, value);
        } catch (JedisConnectionException jedisE) {
            log.error("redis的连接出现问题:", jedisE);
        } catch (Exception e) {
            log.error("redis的连接出现未知异常:", e);
        } finally {
            closeSharedJedis(shardedJedis);
        }
        return 0L;
    }

    /**
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    public static Set<Tuple> zrangeWithScores(String key, long start, long end) {
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = getShardedJedis();
            Set<Tuple> tuples = shardedJedis.zrangeWithScores(key, start, end);
            return tuples;
        } catch (JedisConnectionException jedisE) {
            log.error("redis的连接出现问题:", jedisE);
        } catch (Exception e) {
            log.error("redis的连接出现未知异常:", e);
        } finally {
            closeSharedJedis(shardedJedis);
        }
        return null;
    }

    /**
     * 删除
     * @param key
     * @param member
     * @return
     */
    public static long zrem(String key, String member) {
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = getShardedJedis();
            return shardedJedis.zrem(key, member);
        } catch (JedisConnectionException jedisE) {
            log.error("redis的连接出现问题:", jedisE);
        } catch (Exception e) {
            log.error("redis的连接出现未知异常:", e);
        } finally {
            closeSharedJedis(shardedJedis);
        }
        return 0L;
    }

    /***************** Zset 操作 End**********************/

    /**
     * 获取ShardedJedis连接
     *
     * @return redis 连接
     */
    public static ShardedJedis getShardedJedis() {
        return shardedJedisPool.getResource();
    }

    /**
     * 将连接返回给连接池
     *
     * @param shardedJedis redis连接
     */
    @SuppressWarnings("deprecation")
	public static void closeSharedJedis(ShardedJedis shardedJedis) {
        if (null != shardedJedis) {
            shardedJedisPool.returnResourceObject(shardedJedis);
        }
    }

    /**
     * map缓存
     * @param key
     * @param mapValue
     * @param seconds
     */
    public static void hmSet(String key, Map<String, String> mapValue, Integer seconds) {
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = getShardedJedis();
            shardedJedis.hmset(key, mapValue);
            if (seconds != null && seconds > 0) {
                shardedJedis.expire(key, seconds);
            }
        } catch (JedisConnectionException jedisE) {
            log.error("redis的连接出现问题:", jedisE);
        } catch (Exception e) {
            log.error("redis的连接出现未知异常:", e);
        } finally {
            closeSharedJedis(shardedJedis);
        }
    }

    /**
     * map获取value
     * @param key
     * @param fields
     * @return
     */
    public static List<String> hmGet(String key, String ... fields) {
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = getShardedJedis();
            return shardedJedis.hmget(key, fields);
        } catch (JedisConnectionException jedisE) {
            log.error("redis的连接出现问题:", jedisE);
        } catch (Exception e) {
            log.error("redis的连接出现未知异常:", e);
        } finally {
            closeSharedJedis(shardedJedis);
        }
        return null;
    }

    /**
     * 删除object Key
     * @param key
     */
    public static void objDel(String key) {
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = getShardedJedis();
            if (!shardedJedis.exists(key.getBytes())) {
                return;
            }
            shardedJedis.del(key.getBytes());
        } catch (JedisConnectionException jedisE) {
            log.error("redis的连接出现问题:", jedisE);
        } catch (Exception e) {
            log.error("redis的连接出现未知异常:", e);
        } finally {
            closeSharedJedis(shardedJedis);
        }
    }

    /**
     * 对象缓存
     * @param key
     * @param obj
     * @param seconds
     */
    public static void objSet(String key, Object obj, Integer seconds){
        if (StrUtil.isEmpty(key) || obj == null) {
            return;
        }
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = getShardedJedis();
            shardedJedis.set(key.getBytes(), ObjectTransCoderUtils.serialize(obj));
            if (seconds != null && seconds > 0) {
                shardedJedis.expire(key, seconds);
            }
        } catch (JedisConnectionException jedisE) {
            log.error("redis的连接出现问题:", jedisE);
        } catch (Exception e) {
            log.error("redis的连接出现未知异常:", e);
        } finally {
            closeSharedJedis(shardedJedis);
        }
    }

    /**
     * 获取对象value
     * @param key
     * @return
     */
    public static Object objGet(String key) {
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = getShardedJedis();
            return ObjectTransCoderUtils.deserialize(shardedJedis.get(key.getBytes()));
        } catch (JedisConnectionException jedisE) {
            log.error("redis的连接出现问题:", jedisE);
        } catch (Exception e) {
            log.error("redis的连接出现未知异常:", e);
        } finally {
            closeSharedJedis(shardedJedis);
        }
        return null;
    }

    /**
     * 检查对象存储key是否存在
     * @param objKey
     * @return
     */
    public static boolean objKeyExists(String objKey) {
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = getShardedJedis();
            return shardedJedis.exists(objKey.getBytes());
        } catch (JedisConnectionException jedisE) {
            log.error("redis的连接出现问题:", jedisE);
        } catch (Exception e) {
            log.error("redis的连接出现未知异常:", e);
        } finally {
            closeSharedJedis(shardedJedis);
        }
        return true;
    }

    /**
     * 检查key是否存在
     * @param key
     * @return
     */
    public static boolean strKeyExists(String key) {
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = getShardedJedis();
            return shardedJedis.exists(key);
        } catch (JedisConnectionException jedisE) {
            log.error("redis的连接出现问题:", jedisE);
        } catch (Exception e) {
            log.error("redis的连接出现未知异常:", e);
        } finally {
            closeSharedJedis(shardedJedis);
        }
        return true;
    }

}
