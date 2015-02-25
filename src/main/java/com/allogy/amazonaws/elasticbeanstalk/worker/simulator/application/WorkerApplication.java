package com.allogy.amazonaws.elasticbeanstalk.worker.simulator.application;

import com.allogy.amazonaws.elasticbeanstalk.worker.simulator.model.MessageWrapper;
import com.amazonaws.services.sqs.model.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 * This service provides the interaction of forwarding a message to the
 * application web service. Thus, it makes the HTTP call that the daemon
 * would make.
 *
 * @author David Venable
 */
@Service
public class WorkerApplication
{
    private final String targetUrl;
    private final RestTemplate restTemplate;
    private final Logger logger;

    @Autowired
    public WorkerApplication(@Qualifier("targetUrl") String targetUrl, RestTemplate restTemplate)
    {
        this.targetUrl = targetUrl;
        this.restTemplate = restTemplate;
        logger = LoggerFactory.getLogger(this.getClass());
    }

    public HttpStatus forward(MessageWrapper messageWrapper)
    {
        Message message = messageWrapper.getMessage();
        HttpEntity<String> messageEntity = createHttpEntity(messageWrapper, message);

        logger.info("Forwarding message: messageId={}", messageWrapper.getMessage().getMessageId());

        try
        {
            ResponseEntity<String> responseEntity = restTemplate.exchange(targetUrl, HttpMethod.POST, messageEntity, String.class);
            return responseEntity.getStatusCode();
        }
        catch (HttpStatusCodeException ex)
        {
            return ex.getStatusCode();
        }
        catch (RestClientException ex)
        {
            return HttpStatus.NOT_FOUND;
        }
    }

    private HttpEntity<String> createHttpEntity(MessageWrapper messageWrapper, Message message)
    {
        String messageId = message.getMessageId();

        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "aws-sqsd/1.1 (simulated bridge)");
        headers.set("X-Aws-Sqsd-Msgid", messageId);
        headers.set("X-Aws-Sqsd-Queue", messageWrapper.getQueueUrl());
        headers.set("X-Aws-Sqsd-Receive-Count", Integer.toString(messageWrapper.getMessageCount()));
        headers.setContentType(MediaType.APPLICATION_JSON);

        return new HttpEntity<>(message.getBody(), headers);
    }
}
