package com.unbound.pravega.PravegaTwitterConsumer;

import com.unbound.pravega.service.PravegaReaderService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan("com.unbound.pravega")
public class PravegaTwitterConsumerApplication {
	public static void main(String[] args) {
		ApplicationContext applicationContext = SpringApplication.run(PravegaTwitterConsumerApplication.class, args);
		PravegaReaderService readerService = applicationContext.getBean(PravegaReaderService.class);
		readerService.readStream();
	}
}
