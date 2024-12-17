package com.pluralsight.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import javax.sql.DataSource;

@Configuration
public class DatabaseConfiguration {
    private BasicDataSource dataSource;

    @Bean
    DataSource dataSource(){
        return dataSource;
    }

//    @Value("${datasource.url}")
//    private String url;
//
//    @Value("${datasource.username}")
//    private String username;
//
//    @Value("${datasource.password}")
//    private String password;


    @Autowired
    public DatabaseConfiguration(@Value("${datasource.url}") String url, @Value("${datasource.username}") String username, @Value("${datasource.password}") String password) {
        dataSource = new BasicDataSource();
        //dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
    }
}