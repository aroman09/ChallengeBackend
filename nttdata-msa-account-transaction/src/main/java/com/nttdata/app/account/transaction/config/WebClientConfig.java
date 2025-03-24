package com.nttdata.app.account.transaction.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${client.service.url}")
    private String clienteServiceUrl;

    @Bean
    public WebClient webClient() {
        System.out.println(clienteServiceUrl);
        return WebClient.builder()
                .baseUrl(this.clienteServiceUrl+"/app/clients")
                .build();
    }
}
