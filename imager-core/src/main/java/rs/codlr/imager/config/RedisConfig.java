package rs.codlr.imager.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Arrays;
import java.util.List;

/**
 * @ivan
 */
@Configuration
@EnableCaching
public class RedisConfig {

    @Value("${redis.cluster.node.urls:#{null}}")
    private String redisClusterNodeUrls;

    @Bean
    public RedisConnectionFactory connectionFactory() {
        if (redisClusterNodeUrls != null) {
            List<String> clusterNodes = Arrays.asList(redisClusterNodeUrls.split(","));
            if (clusterNodes.size()==1) {
                JedisConnectionFactory factory = new JedisConnectionFactory();
                factory.setHostName(clusterNodes.get(0));
                return factory;
            }
            return new JedisConnectionFactory(new RedisClusterConfiguration(clusterNodes));
        }
        return new JedisConnectionFactory();
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory());
        return template;
    }

    @Bean
    public CacheManager cacheManager(RedisTemplate redisTemplate) {
        RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);
        cacheManager.setDefaultExpiration(3000);
        return cacheManager;
    }

}
