package com.stock.inventory.stockinventory.config;

import javax.sql.DataSource;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatasourceConfig {
    @Bean
    public DataSource datasource() {
        return DataSourceBuilder.create()
          .driverClassName("org.postgresql.Driver")
          .url("jdbc:postgresql://localhost:5432/postgres")
          .username("postgres")
          .password("postgres")
          .build(); 
    }
}