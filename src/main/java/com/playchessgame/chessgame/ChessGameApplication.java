package com.playchessgame.chessgame;

import com.playchessgame.chessgame.ContextService.MyListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
@ServletComponentScan
public class ChessGameApplication {

    public static void main(String[] args) {

//        SpringApplication.run(ChessGameApplication.class, args);
//        System.setProperty("java.awt.headless", "false"); //Disables headless

        SpringApplicationBuilder builder = new SpringApplicationBuilder(ChessGameApplication.class);
        builder.headless(false);
        builder.run(args);

    }

}
