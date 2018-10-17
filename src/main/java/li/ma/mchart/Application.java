package li.ma.mchart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
/**
 * @Author: mario
 * @Date: 2018-09-30 10:33 AM
 * @Description:
 */
@SpringBootApplication
@EnableCaching
public class Application {


    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
    }


}
