package com.playchessgame.chessgame;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * The initialization class of the project (spring boot)
 */
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
@ServletComponentScan
public class ChessGameApplication {

    /**
     * The starting point of the project
     * @param args
     */
    public static void main(String[] args) {

        SpringApplicationBuilder builder = new SpringApplicationBuilder(ChessGameApplication.class);
        builder.headless(false);
        builder.run(args);

    }

}
