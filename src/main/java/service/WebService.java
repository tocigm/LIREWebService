package service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/**
 * Created by SONY on 3/14/2017.
 */
@SpringBootApplication
public class WebService {
    public static void main(String[] args) {
        SearchService.searcher.init();
        SpringApplication.run(WebService.class, args);
        System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
    }
}
