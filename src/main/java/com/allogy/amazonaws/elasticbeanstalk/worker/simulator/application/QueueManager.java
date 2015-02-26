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

import com.allogy.amazonaws.elasticbeanstalk.worker.simulator.model.MessageWrapper;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author David Venable
 */
@Service
public class QueueManager
{
    private final AmazonSQS amazonSQS;
    private final String queueUrl;
    private final Integer waitTimeSeconds;
    private final Integer maxNumberOfMessages;
    private final Logger logger;

    @Autowired
    public QueueManager(AmazonSQS amazonSQS,
                        @Qualifier("queueUrl") String queueUrl,
                        @Qualifier("waitTimeSeconds") Integer waitTimeSeconds,
                        @Qualifier("maxNumberOfMessages") Integer maxNumberOfMessages)
    {
        this.amazonSQS = amazonSQS;
        this.queueUrl = queueUrl;
        this.waitTimeSeconds = waitTimeSeconds;
        this.maxNumberOfMessages = maxNumberOfMessages;
        logger = LoggerFactory.getLogger(this.getClass());
    }

    public Stream<MessageWrapper> readQueue()
    {
        ReceiveMessageRequest receiveMessageRequest = new ReceiveMessageRequest()
                .withQueueUrl(queueUrl)
                .withWaitTimeSeconds(waitTimeSeconds)
                .withMaxNumberOfMessages(maxNumberOfMessages);

        logger.debug("Reading from queue. queueUrl={}", queueUrl);

        ReceiveMessageResult receiveMessageResult = amazonSQS.receiveMessage(receiveMessageRequest);

        List<Message> messages = receiveMessageResult.getMessages();
        int messageCount = messages.size();
        logger.debug("Received {} messages from queue. queueUrl={}", messageCount, queueUrl);

        return messages.stream()
                .map(m -> new MessageWrapper()
                        .withQueueUrl(queueUrl)
                        .withMessage(m)
                        .withMessageCount(messageCount));
    }

    public void deleteMessages(Stream<MessageWrapper> messages)
    {
        List<DeleteMessageBatchRequestEntry> deleteEntries = messages.map(MessageWrapper::getMessage)
                .map(m -> new DeleteMessageBatchRequestEntry()
                        .withId(m.getMessageId())
                        .withReceiptHandle(m.getReceiptHandle()))
                .collect(Collectors.toList());

        if(deleteEntries.isEmpty())
            return;

        DeleteMessageBatchRequest deleteRequest =
                new DeleteMessageBatchRequest().withQueueUrl(queueUrl).withEntries(deleteEntries);

        logger.debug("About to delete {} messages from queue. queueUrl={}", deleteEntries.size(), queueUrl);

        amazonSQS.deleteMessageBatch(deleteRequest);
    }
}
