package com.pluralsight;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import javax.sql.DataSource;

public class Application implements CommandLineRunner {
    @Autowired
    DataSource dataSource;

    @Override
    public void run(String... args) throws Exception {

    }


}
