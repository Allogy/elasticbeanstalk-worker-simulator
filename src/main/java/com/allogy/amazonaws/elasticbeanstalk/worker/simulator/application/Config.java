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

    @Value("${waitTimeSeconds}")
    private Integer waitTimeSeconds;

    @Value("${maxNumberOfMessages}")
    private Integer maxNumberOfMessages;

    @Value("${pauseTimeMilliseconds}")
    private Integer pauseTimeMilliseconds;

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
    public String queueUrl()
    {
        return queueUrl;
    }

    @Bean
    public String targetUrl()
    {
        return targetUrl;
    }

    @Bean
    public Integer waitTimeSeconds()
    {
        return waitTimeSeconds;
    }

    @Bean
    public Integer maxNumberOfMessages()
    {
        return maxNumberOfMessages;
    }

    @Bean
    public Integer pauseTimeMilliseconds()
    {
        return pauseTimeMilliseconds;
    }
}
