package edu.hm.webtec;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Controller
@SpringBootApplication
@EnableWebMvc
public class ItsApplication {


    public static void main(String[] args) {
        SpringApplication.run(ItsApplication.class, args);
    }

}
