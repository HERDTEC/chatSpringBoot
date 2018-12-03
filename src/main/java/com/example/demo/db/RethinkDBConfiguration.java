package com.example.demo.db;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;

@Configuration
public class RethinkDBConfiguration {

    @Autowired
    private Environment env;

    public static String DBHOST = "172.1.0.2";
    public static Integer DBPORT;
    public static String DBNAME = "log";

    @PostConstruct
    public void init() {
        this.DBHOST = this.env.getProperty("rethinkdb.dbhost");
        this.DBPORT = Integer.parseInt(this.env.getProperty("rethinkdb.dbport"));
        this.DBNAME = this.env.getProperty("rethinkdb.dbname");
    }

    @Bean
    public RethinkDBConnectionFactory connectionFactory() {
        return new RethinkDBConnectionFactory(DBHOST, DBPORT, DBNAME);
    }

    @Bean
    DbInitializer dbInitializer() {
        return new DbInitializer();
    }
}