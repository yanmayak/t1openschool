package ru.yanmayak.t1openschool;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

public class AbstractContainerBaseTest {
    static final PostgreSQLContainer<?> postgreSQLContainer;

    static {
    postgreSQLContainer = new PostgreSQLContainer<>("postgres:11.13")
            .withDatabaseName("testbase")
            .withUsername("postgres")
            .withPassword("0000");
    postgreSQLContainer.start();
    }

    @DynamicPropertySource
    static void postgreSQLProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    }
}