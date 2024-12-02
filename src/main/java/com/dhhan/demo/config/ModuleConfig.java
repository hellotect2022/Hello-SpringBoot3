package com.dhhan.demo.config;

import com.dhhan.customFramework.redis.PubSub;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.stereotype.Component;

@Component
@ComponentScans(
        @ComponentScan("com.dhhan.customFramework")
)
public class ModuleConfig {
    @Bean
    public PubSub pubsub() {return new PubSub();}

}
