package com.xiupeilian.carpart.configure;

import com.xiupeilian.carpart.model.SysUser;
import org.apache.ibatis.javassist.ClassPath;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @Description:
 * @Author: Tu Xu
 * @CreateDate: 2019/9/2 21:32
 * @Version: 1.0
 **/
@Configuration
@PropertySource(value = {"classpath:jdbc/db.properties","classpath:jdbc/redis.properties"})
public class JavaConfigure {
    @Value("${redis.host}")
    private String redis_host;
    @Value("${redis0.port}")
    private String port1;

    @Bean
    public SysUser getUser(){
        return new SysUser();
    }
    //生产jedis工厂
    @Bean
    public JedisConnectionFactory getConnectionFactory(){
        JedisPoolConfig config=new JedisPoolConfig();
        JedisConnectionFactory factory=new JedisConnectionFactory();

        return null;
    }
}
