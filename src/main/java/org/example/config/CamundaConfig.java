package org.example.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class CamundaConfig {
    @Bean
    @ConfigurationProperties("spring.datasource.first")
    @Primary
    public DataSourceProperties dataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties("spring.datasource.second")
    public DataSourceProperties dataSourceProperties2() {
        return new DataSourceProperties();
    }

    @Bean(name = "appDataSource")
    @ConfigurationProperties("spring.datasource.first")
    @Primary
    public DataSource firstDataSource(@Qualifier("dataSourceProperties") DataSourceProperties properties) {
        return properties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }

    @Bean(name = "camundaBpmDataSource")
    @ConfigurationProperties("spring.datasource.second")
    public DataSource secondDataSource(@Qualifier("dataSourceProperties2") DataSourceProperties properties) {
        return properties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }
}