package li.ma.mchat;

import li.ma.mchat.inter.AuthorizeInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author: mario
 * @Date: 2018-09-30 5:49 PM
 * @Description:
 */
@Configuration
public class InterceptorConfigure implements WebMvcConfigurer{

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthorizeInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/auth/**","/client","/error", "/*.js");
    }

}
