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
    /**
     * The AWS access key for the IAM user which will read from
     * your SQS queue.
     */
    @Value("${accessKey}")
    private String accessKey;

    /**
     * The AWS secret key for the IAM user which will read from
     * your SQS queue.
     */
    @Value("${secretKey}")
    private String secretKey;

    /**
     * The URL of the SQS queue from which this simulator reads
     * messages.
     */
    @Value("${queueUrl}")
    private String queueUrl;

    /**
     * The URL of your local web application. When the simulator
     * receives a message, it will POST that message to this URL.
     */
    @Value("${targetUrl}")
    private String targetUrl;

    /**
     * The duration in seconds that the simulator will wait for a
     * message on the SQS queue. This value may be set to 0 which
     * will use SQS short polling. If set to a non-zero positive
     * value then SQS long polling is used. The valid values
     * are determined by SQS, but currently 1-20.
     */
    @Value("${waitTimeSeconds}")
    private Integer waitTimeSeconds;

    /**
     * The maximum number of messages which can be read from the
     * queue at a time. The valid values are determined by SQS,
     * but are currently 1-10.
     */
    @Value("${maxNumberOfMessages}")
    private Integer maxNumberOfMessages;

    /**
     * The amount of time, in milliseconds, to wait in between
     * SQS calls.
     */
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
