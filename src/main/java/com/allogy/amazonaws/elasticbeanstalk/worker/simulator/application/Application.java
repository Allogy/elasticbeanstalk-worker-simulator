package com.allogy.amazonaws.elasticbeanstalk.worker.simulator.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

/**
 * @author David Venable
 */
@SpringBootApplication
public class Application
{
    public static void main(String[] args)
    {
        ApplicationContext applicationContext = SpringApplication.run(Application.class, args);

        BridgeService bridgeService = applicationContext.getBean(BridgeService.class);
        bridgeService.start();
    }
}