package fr.ekito.gwt.server;

import org.jadice.gwt.spring.autoconfig.EnableGWTSpringBootApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@EnableGWTSpringBootApplication("fr.ekito.gwt.Learn")
@ServletComponentScan
@SpringBootApplication
public class ServerApplication extends SpringBootServletInitializer {

	final static Logger logger = LoggerFactory.getLogger(ServerApplication.class);

	/**
	 * entry point
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		SpringApplication.run(ServerApplication.class, args);
	}

}
