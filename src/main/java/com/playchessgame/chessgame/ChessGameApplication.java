package com.playchessgame.chessgame;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class ChessGameApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChessGameApplication.class, args);
    }

}
