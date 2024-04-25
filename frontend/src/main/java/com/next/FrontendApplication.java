package com.next;

import com.google.common.collect.Lists;
import com.next.common.LoginFilter;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedisPool;

import java.util.List;

@SpringBootApplication
@EnableAutoConfiguration(exclude = DataSourceAutoConfiguration.class)
@EnableTransactionManagement //开启注解事务管理，等同于xml配置 <tx:annotation-driver>
public class FrontendApplication {

    public static void main(String[] args) {
        SpringApplication.run(FrontendApplication.class, args);
    }

    @Bean(name = "shardedJedisPool")
    public ShardedJedisPool shardedJedisPool() {
        JedisShardInfo shardInfo = new JedisShardInfo("127.0.0.1", 6379);
        List<JedisShardInfo> shardInfoList = Lists.newArrayList(shardInfo);
        GenericObjectPoolConfig config = new GenericObjectPoolConfig();
        ShardedJedisPool shardedJedisPool = new ShardedJedisPool(config, shardInfoList);
        return shardedJedisPool;
    }

    @Bean
    public FilterRegistrationBean loginFilter() {
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(new LoginFilter());
        bean.addUrlPatterns("/user/*", "/front/*");
        bean.setOrder(1);
        bean.setName("loginFilter");
        return bean;
    }

}
