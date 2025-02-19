package com.pichincha.app.person.client.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.util.StreamUtils;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

@Configuration
public class DatabaseConfig {
    @Bean
    public CommandLineRunner initializeDatabase(DatabaseClient client) {
        return args -> {
            String schema = StreamUtils.copyToString(
                    new ClassPathResource("schema.sql").getInputStream(),
                    StandardCharsets.UTF_8);

            client.sql(schema)
                    .then()
                    .doOnSuccess(unused -> System.out.println("Database schema initialized successfully"))
                    .doOnError(error -> Mono.error(error))
                    .subscribe();
        };
    }

}
