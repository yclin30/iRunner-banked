package com.yclin.irunnerbanked;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.yclin.irunnerbanked.mapper")
@SpringBootApplication
public class IRunnerBankedApplication {

    public static void main(String[] args) {
        SpringApplication.run(IRunnerBankedApplication.class, args);
    }

}
