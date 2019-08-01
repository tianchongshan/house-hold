package com.tcs.household.configuration;

import cn.hutool.core.util.StrUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedisPool;

import java.util.ArrayList;
import java.util.List;

/**
 * create by chongshan.tian01
 **/
@Configuration
@ConditionalOnClass(JedisPoolConfig.class)
public class RedisConfig {

    @Value("${spring.redis.database}")
    private int database = 0;

    @Value("${spring.redis.host}")
    private String host="localhost";

    @Value("${spring.redis.password}")
    private String password;

    @Value("${spring.redis.port}")
    private int port;

    @Value("${spring.redis.timeout}")
    private int timeout;

    @Value("${spring.redis.jedis.pool.max-idle}")
    private int maxIdle;

    @Value("${spring.redis.jedis.pool.max-active}")
    private int maxActive;

    @Value("${spring.redis.maxWait}")
    private int maxWait;

    @Bean
    public JedisPoolConfig poolConfig() {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxIdle(maxIdle);
        poolConfig.setMaxTotal(maxActive);
        poolConfig.setMaxWaitMillis(maxWait);
        return poolConfig;
    }

    @Bean(name = "jedisPool")
    public JedisPool constructJedisPool() {
        JedisPool pool = null;
        if (StrUtil.isBlank(password)) {
            pool = new JedisPool(poolConfig(), this.host, port, timeout);
        } else {
            pool = new JedisPool(poolConfig(), this.host, port, timeout, password, database);
        }
        return pool;
    }

    @Bean("shardedJedisPool")
    public ShardedJedisPool shardedJedisPool() {
        List<JedisShardInfo> listJsInfo = new ArrayList<>();
        JedisShardInfo info = new JedisShardInfo(host, port);
        info.setPassword(password);
        listJsInfo.add(info);
        ShardedJedisPool sjp = new ShardedJedisPool(poolConfig(), listJsInfo);
        return sjp;
    }
}
