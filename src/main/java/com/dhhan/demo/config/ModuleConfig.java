package com.dhhan.demo.config;

import com.dhhan.customFramework.config.RedisConfig;
import com.dhhan.customFramework.redis.PubSub;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@Import(RedisConfig.class)
public class ModuleConfig {
    @Bean
    public PubSub pubsub() {return new PubSub();}

}
