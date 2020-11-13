package com.example.messagingstompwebsocket;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOServer;

@SpringBootApplication
public class MessagingStompWebsocketApplication {

	public static void main(String[] args) {
		SpringApplication.run(MessagingStompWebsocketApplication.class, args);
	}

    @Bean
    public SocketIOServer socketIOServer() {
        Configuration config = new Configuration();
<<<<<<< HEAD
        config.setHostname("127.0.0.1");
        config.setPort(3070);
=======
        //config.setHostname("brainbright.herokuapp.com");
        config.setPort(Integer.valueOf(System.getenv("PORT")));
>>>>>>> 2c77381c93af043a62cf0f320e2cb5b39c20c06c
        return new SocketIOServer(config);
    }
}
