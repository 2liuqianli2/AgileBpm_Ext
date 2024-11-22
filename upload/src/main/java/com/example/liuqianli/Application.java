package com.example.liuqianli;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Tool:IntelliJ IDEA
 * Description:启动类
 * Date：2024-11-22-15:28
 *
 * @ Author:两袖青蛇
 */
@SpringBootApplication
@Configurable
@EnableSwagger2
public class Application {
    public static void main(String[] args){
        SpringApplication.run(Application.class);
    }
}
