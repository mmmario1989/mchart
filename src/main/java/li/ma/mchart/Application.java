package li.ma.mchart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * @Author: mario
 * @Date: 2018-09-30 10:33 AM
 * @Description:
 */
@SpringBootApplication
@RestController
public class Application {
    @RequestMapping("/")
    public String test(){
        return "hello world!";
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
    }
}
