package li.ma.mchart;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @Author: mario
 * @Date: 2018-10-10 12:39 PM
 * @Description:
 */
@Configuration
public class WebSocketConfigure {

    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}
