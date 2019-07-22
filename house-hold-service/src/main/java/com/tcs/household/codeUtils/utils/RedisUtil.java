package com.tcs.household.codeUtils.utils;

import com.tcs.household.util.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Tuple;
import redis.clients.jedis.exceptions.JedisConnectionException;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
@Deprecated
public class RedisUtil {

    private static final String NX = "NX";

    private static final String XX = "XX";

    private static final String EX = "EX";

    private static final String PX = "PX ";

    private static final String SUCCESS_STATUS = "OK";

    private static JedisPool jedisPool;

    static {
        jedisPool = SpringUtil.getBean("jedisPool");
    }

    /** String 操作 **/
    /**
     * 设置表数据
     *
     * @param key redis values 值修改
     * @return 返回成功与否
     */
    public static Set<String> keys(String key) {
        Jedis jedis = null;
        Set<String> result = null;
        try {
            jedis = getJedis();
            result = jedis.keys(key);
        } catch (JedisConnectionException jedisE) {
            log.error("redis的连接出现问题了:", jedisE);
        } catch (Exception e) {
            log.error("redis的连接出现未知异常问题:", e);
        } finally {
            closeSharedJedis(jedis);
        }
        return result;
    }


    /**
     * 存储键值对
     *
     * @param key   存储数据key
     * @param value 存储数据
     */
    public static void set(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.set(key, value);
        } catch (JedisConnectionException jedisE) {
            log.error("redis的连接出现问题:", jedisE);
        } catch (Exception e) {
            log.error("redis的连接出现未知异常:", e);
        } finally {
            closeSharedJedis(jedis);
        }
    }

    /**
     * @param key     存储key
     * @param value   存储数据
     * @param seconds 有效时间 单位秒
     */
    public static void set(String key, String value, int seconds) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.setex(key, seconds, value);
        } catch (JedisConnectionException jedisE) {
            log.error("redis的连接出现问题:", jedisE);
        } catch (Exception e) {
            log.error("redis的连接出现未知异常:", e);
        } finally {
            closeSharedJedis(jedis);
        }
    }

    /**
     * 不存在插入
     * @param key
     * @param value
     * @param seconds
     * @return
     */
    public static boolean setNx(String key, String value, int seconds) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            String result = "";
            if (seconds > 0) {
                result = jedis.set(key, value, NX, EX, seconds);
            } else {
                result = jedis.set(key, value, NX);
            }
            return SUCCESS_STATUS.equalsIgnoreCase(result);
        } catch (JedisConnectionException jedisE) {
            log.error("redis的连接出现问题:", jedisE);
        } catch (Exception e) {
            log.error("redis的连接出现未知异常:", e);
        } finally {
            closeSharedJedis(jedis);
        }
        return false;
    }

    /**
     * 存在才更新
     * @param key
     * @param value
     * @param seconds
     * @return
     */
    public static boolean setXx(String key, String value, int seconds) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            String result = "";
            if (seconds > 0) {
                result = jedis.set(key, value, XX, EX, seconds);
            } else {
                result = jedis.set(key, value, XX);
            }
            return SUCCESS_STATUS.equalsIgnoreCase(result);
        } catch (JedisConnectionException jedisE) {
            log.error("redis的连接出现问题:", jedisE);
        } catch (Exception e) {
            log.error("redis的连接出现未知异常:", e);
        } finally {
            closeSharedJedis(jedis);
        }
        return false;
    }

    /**
     * 根据存储key获取相应的存储value数据
     *
     * @param key 存储数据key
     * @return string
     */
    public static String get(String key) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.get(key);
        } catch (Exception e) {
            log.error("redis的连接出现未知异常:", e);
        } finally {
            closeSharedJedis(jedis);
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
        Jedis jedis = null;
        try {
            jedis = getJedis();
            String redisValue = jedis.getSet(key, value);
            jedis.expire(key, seconds);
            return redisValue;
        } catch (Exception e) {
            log.error("redis的连接出现未知异常:", e);
        } finally {
            closeSharedJedis(jedis);
        }
        return null;
    }

    /**
     * 根据存储key删除相应的数据
     *
     * @param key 存储数据key
     */
    public static Long delete(String key) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            if (!jedis.exists(key)) {
                return 0L;
            }
            return jedis.del(key);
        } catch (JedisConnectionException jedisE) {
            log.error("redis的连接出现问题:", jedisE);
        } catch (Exception e) {
            log.error("redis的连接出现未知异常:", e);
        } finally {
            closeSharedJedis(jedis);
        }
        return 0L;
    }

    /**
     * 将 key 中储存的数字值增一
     *
     * @param key key值
     * @return 真实的数量
     */
    public static Long incr(String key) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.incr(key);
        } catch (JedisConnectionException jedisE) {
            log.error("redis的连接出现问题:", jedisE);
        } catch (Exception e) {
            log.error("redis的连接出现未知异常:", e);
        } finally {
            closeSharedJedis(jedis);
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
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.incrBy(key, value);
        } catch (JedisConnectionException jedisE) {
            log.error("redis的连接出现问题:", jedisE);
        } catch (Exception e) {
            log.error("redis的连接出现未知异常:", e);
        } finally {
            closeSharedJedis(jedis);
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
        Jedis jedis = null;
        try {
            jedis = getJedis();
            Long result = jedis.incr(key);
            jedis.expire(key, secords);
            return result;
        } catch (JedisConnectionException jedisE) {
            log.error("redis的连接出现问题:", jedisE);
        } catch (Exception e) {
            log.error("redis的连接出现未知异常:", e);
        } finally {
            closeSharedJedis(jedis);
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
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.expire(key, seconds);
        } catch (JedisConnectionException jedisE) {
            log.error("redis的连接出现问题:", jedisE);
        } catch (Exception e) {
            log.error("redis的连接出现未知异常:", e);
        } finally {
            closeSharedJedis(jedis);
        }
        return 0L;
    }

    /**
     * key存在判断
     * @param key
     * @return
     */
    public static boolean exists(String key) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.exists(key);
        } catch (JedisConnectionException jedisE) {
            log.error("redis的连接出现问题:", jedisE);
        } catch (Exception e) {
            log.error("redis的连接出现未知异常:", e);
        } finally {
            closeSharedJedis(jedis);
        }
        return false;
    }

    /**
     * 批量保存
     * @param batchData
     * @param secords
     */
    public static void batchInsert(Map<String, String> batchData, int secords) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            Pipeline pipeline = jedis.pipelined();
            for (Map.Entry<String, String> entry : batchData.entrySet()) {
                pipeline.set(entry.getKey(), entry.getValue());
                pipeline.expire(entry.getKey(), secords);
            }
            pipeline.syncAndReturnAll();
        } finally {
            closeSharedJedis(jedis);
        }
    }


    /***************** List操作 start**********************/
    /**
     * 从名称为key的list取值value
     *
     * @param key Key值
     */
    public static String lpop(String key) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.lpop(key);
        } catch (JedisConnectionException jedisE) {
            log.error("redis的连接出现问题:", jedisE);
        } catch (Exception e) {
            log.error("redis的连接出现未知异常:", e);
        } finally {
            closeSharedJedis(jedis);
        }
        return null;
    }

    /**
     * 阻塞Pop
     * @param key
     * @param timeout
     * @return
     */
    public static String blpop(String key, int timeout) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            List<String> list = jedis.blpop(timeout, key);
            if (list != null && list.size() > 1) {
                return list.get(1);
            }
        } catch (JedisConnectionException jedisE) {
            log.error("redis的连接出现问题:", jedisE);
        } catch (Exception e) {
            log.error("redis的连接出现未知异常:", e);
        } finally {
            closeSharedJedis(jedis);
        }
        return null;
    }


    /**
     * 在名称为key的list尾添加一个值为value的元素
     *
     * @param key   key值
     * @param value 值
     */
    public static long rpush(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.rpush(key, value);
        } catch (JedisConnectionException jedisE) {
            log.error("redis的连接出现问题:", jedisE);
        } catch (Exception e) {
            log.error("redis的连接出现未知异常:", e);
        } finally {
            closeSharedJedis(jedis);
        }
        return 0;
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
        Jedis jedis = null;
        Long length = null;
        try {
            jedis = getJedis();
            length = jedis.hset(tableName, fieldName, value);
        } catch (JedisConnectionException jedisE) {
            log.error("redis的连接出现问题了:", jedisE);
        } catch (Exception e) {
            log.error("redis的连接出现未知异常问题:", e);
        } finally {
            closeSharedJedis(jedis);
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
        Jedis jedis = null;
        Long length = null;
        try {
            jedis = getJedis();
            length = jedis.hdel(tableName, fieldName);
        } catch (JedisConnectionException jedisE) {
            log.error("redis的连接出现问题了:", jedisE);
        } catch (Exception e) {
            log.error("redis的连接出现未知异常问题:", e);
        } finally {
            closeSharedJedis(jedis);
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
        Jedis jedis = null;
        Long length = null;
        try {
            jedis = getJedis();
            length = jedis.hset(tableName, fieldName, value);
            jedis.expire(tableName, seconds);
        } catch (JedisConnectionException jedisE) {
            log.error("redis的连接出现问题了:", jedisE);
        } catch (Exception e) {
            log.error("redis的连接出现未知异常问题:", e);
        } finally {
            closeSharedJedis(jedis);
        }
        return length;
    }

    /**
     * 获取表的Key
     *
     * @param key redis values 值修改
     * @return 返回成功与否
     */
    public static Set<String> hkeys(String key) {
        Jedis jedis = null;
        Set<String> result = null;
        try {
            jedis = getJedis();
            result = jedis.hkeys(key);
        } catch (JedisConnectionException jedisE) {
            log.error("redis的连接出现问题了:", jedisE);
        } catch (Exception e) {
            log.error("redis的连接出现未知异常问题:", e);
        } finally {
            closeSharedJedis(jedis);
        }
        return result;
    }

    /**
     * 获取表的部分内容
     * @param tableName
     * @param key
     * @return
     */
    public static String hget(String tableName, String key) {
        Jedis jedis = null;
        String result = null;
        try {
            jedis = getJedis();
            result = jedis.hget(tableName, key);
        } catch (JedisConnectionException jedisE) {
            log.error("redis的连接出现问题了:", jedisE);
        } catch (Exception e) {
            log.error("redis的连接出现未知异常问题:", e);
        } finally {
            closeSharedJedis(jedis);
        }
        return result;
    }

    /**
     * 获取表的所有内容
     * @param tableName
     * @return
     */
    public static Map<String, String> hgetAll(String tableName) {
        Jedis jedis = null;
        Map<String, String> result = null;
        try {
            jedis = getJedis();
            result = jedis.hgetAll(tableName);
        } catch (JedisConnectionException jedisE) {
            log.error("redis的连接出现问题了:", jedisE);
        } catch (Exception e) {
            log.error("redis的连接出现未知异常问题:", e);
        } finally {
            closeSharedJedis(jedis);
        }
        return result;
    }


    /***************** set操作 start **********************/
    /**
     * 添加Set集合内容
     * @param key
     * @param values
     */
    public static void sadd(String key, String... values) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.sadd(key, values);
        } catch (JedisConnectionException jedisE) {
            log.error("redis的连接出现问题了:", jedisE);
        } catch (Exception e) {
            log.error("redis的连接出现未知异常问题:", e);
        } finally {
            closeSharedJedis(jedis);
        }
    }

    /**
     * 删除Set内容
     * @param key
     * @param values
     */
    public static void srem(String key, String... values) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.srem(key, values);
        } catch (JedisConnectionException jedisE) {
            log.error("redis的连接出现问题了:", jedisE);
        } catch (Exception e) {
            log.error("redis的连接出现未知异常问题:", e);
        } finally {
            closeSharedJedis(jedis);
        }
    }

    /**
     * 计算Set大小
     * @param key
     * @return
     */
    public static long scard(String key) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.scard(key);
        } catch (JedisConnectionException jedisE) {
            log.error("redis的连接出现问题了:", jedisE);
        } catch (Exception e) {
            log.error("redis的连接出现未知异常问题:", e);
        } finally {
            closeSharedJedis(jedis);
        }
        return 0L;
    }

    /**
     * 获取Set所有内容
     * @param key
     * @return
     */
    public static Set<String> smember(String key) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.smembers(key);
        } catch (JedisConnectionException jedisE) {
            log.error("redis的连接出现问题了:", jedisE);
        } catch (Exception e) {
            log.error("redis的连接出现未知异常问题:", e);
        } finally {
            closeSharedJedis(jedis);
        }
        return null;
    }



    /***************** Zset 操作 Start**********************/
    /**
     * 添加Zset元素
     * @param key
     * @param value
     * @param timestamp
     * @return
     */
    public static Long zadd(String key, String value, long timestamp) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.zadd(key, timestamp, value);
        } catch (JedisConnectionException jedisE) {
            log.error("redis的连接出现问题:", jedisE);
        } catch (Exception e) {
            log.error("redis的连接出现未知异常:", e);
        } finally {
            closeSharedJedis(jedis);
        }
        return 0L;
    }

    /**
     * 获取指定分数范围的成员
     * @param key
     * @param start
     * @param end
     * @return
     */
    public static Set<Tuple> zrangeWithScores(String key, long start, long end) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            Set<Tuple> tuples = jedis.zrangeWithScores(key, start, end);
            return tuples;
        } catch (JedisConnectionException jedisE) {
            log.error("redis的连接出现问题:", jedisE);
        } catch (Exception e) {
            log.error("redis的连接出现未知异常:", e);
        } finally {
            closeSharedJedis(jedis);
        }
        return null;
    }

    /**
     * 删除成员
     * @param key
     * @param member
     * @return
     */
    public static long zrem(String key, String member) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.zrem(key, member);
        } catch (JedisConnectionException jedisE) {
            log.error("redis的连接出现问题:", jedisE);
        } catch (Exception e) {
            log.error("redis的连接出现未知异常:", e);
        } finally {
            closeSharedJedis(jedis);
        }
        return 0L;
    }

    /**
     * 计算成员排名
     * @param key
     * @param member
     * @return
     */
    public static long zrank(String key, String member) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.zrank(key, member);
        } catch (JedisConnectionException jedisE) {
            log.error("redis的连接出现问题:", jedisE);
        } catch (Exception e) {
            log.error("redis的连接出现未知异常:", e);
        } finally {
            closeSharedJedis(jedis);
        }
        return 0L;
    }


    /**
     * 获取jedis连接
     *
     * @return redis 连接
     */
    public static Jedis getJedis() {
        return jedisPool.getResource();
    }

    /**
     * 将连接返回给连接池
     *
     * @param jedis redis连接
     */
    @SuppressWarnings("deprecation")
    public static void closeSharedJedis(Jedis jedis) {
        if (null != jedis) {
            jedisPool.returnResourceObject(jedis);
        }
    }
}
