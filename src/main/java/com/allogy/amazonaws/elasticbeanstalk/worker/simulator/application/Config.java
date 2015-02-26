/*
 * Copyright (c) 2015 Allogy Interactive.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

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
