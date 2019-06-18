package org.zahid.apps.web.pos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@SpringBootApplication
public class PointOfSaleApplication {

    public static void main(String[] args) {
        /*SpringApplicationBuilder builder = new SpringApplicationBuilder(PointOfSaleApplication.class);
        builder.headless(false);
        ConfigurableApplicationContext context = builder.run(args);*/
        SpringApplication.run(PointOfSaleApplication.class, args);
        System.out.println("Hello");
    }

}
