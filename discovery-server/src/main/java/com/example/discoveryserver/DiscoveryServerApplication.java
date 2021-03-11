package com.example.discoveryserver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.ConfigurableApplicationContext;
import java.io.IOException;
@SpringBootApplication
@EnableEurekaServer
public class DiscoveryServerApplication extends SpringBootServletInitializer {
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(DiscoveryServerApplication.class);
	}
	public static void main(String[] args) throws IOException {
		ConfigurableApplicationContext ctx = SpringApplication.run(DiscoveryServerApplication.class, args);
		System.out.println("Eureka server running.\nHit enter to stop it...");
		System.in.read();
		System.exit(0);
	}
}