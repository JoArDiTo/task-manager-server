package com.sd.todo_server.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(
        basePackages = "com.sd.todo_server.api.repositories.jpa" // Cambia a un paquete específico para repositorios JPA
)
public class JpaConfig {
}