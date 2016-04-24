package edu.hm.webtec;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;

@Controller
@SpringBootApplication
public class ItsApplication {


    public static void main(String[] args) {
        SpringApplication.run(ItsApplication.class, args);
    }

}
