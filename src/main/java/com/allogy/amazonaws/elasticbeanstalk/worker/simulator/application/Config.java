package com.allogy.amazonaws.elasticbeanstalk.worker.simulator.application;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author David Venable
 */
@Configuration
public class Config
{
    @Value("${accessKey}")
    private String accessKey;

    @Value("${secretKey}")
    private String secretKey;

    @Value("${queueUrl}")
    private String queueUrl;

    @Value("${targetUrl}")
    private String targetUrl;

    @Bean
    public AmazonSQS amazonSQS()
    {
        return new AmazonSQSClient(awsCredentials());
    }

    @Bean
    public BasicAWSCredentials awsCredentials()
    {
        return new BasicAWSCredentials(accessKey, secretKey);
    }

    @Bean
    public RestTemplate restTemplate()
    {
        return new RestTemplate();
    }

    @Bean
    public Integer waitTimeSeconds()
    {
        return 20;
    }

    @Bean
    public Integer maxNumberOfMessages()
    {
        return 10;
    }

    @Bean
    public Integer pauseTimeMilliseconds()
    {
        return 5 * 1000;
    }

    @Bean
    public String queueUrl()
    {
        return queueUrl;
    }

    @Bean
    public String targetUrl()
    {
        return targetUrl;
    }
}
