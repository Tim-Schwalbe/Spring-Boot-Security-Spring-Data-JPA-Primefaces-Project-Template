package de.tim.schwalbe.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "de.tim.schwalbe.web")
public class SpringBootApp {

        public static void main(String[] args) {
                SpringApplication.run(SpringBootApp.class, args);

        }
}
