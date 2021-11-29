package com.playchessgame.chessgame;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class ChessGameApplication {

    public static void main(String[] args) {

//        SpringApplication.run(ChessGameApplication.class, args);
//        System.setProperty("java.awt.headless", "false"); //Disables headless

        SpringApplicationBuilder builder = new SpringApplicationBuilder(ChessGameApplication.class);
        builder.headless(false);
        builder.run(args);

    }

}
