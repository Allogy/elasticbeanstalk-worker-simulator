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

package com.allogy.amazonaws.elasticbeanstalk.worker.simulator.model;

import com.amazonaws.services.sqs.model.Message;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;

/**
 * Wraps an SQS message and information about that message
 * into one single class.
 *
 * @author David Venable
 */
public class MessageWrapper
{
    private Message message;
    private String queueUrl;
    private int messageCount;

    public Message getMessage()
    {
        return message;
    }

    private void setMessage(Message message)
    {
        this.message = message;
    }

    public String getQueueUrl()
    {
        return queueUrl;
    }

    private void setQueueUrl(String queueUrl)
    {
        this.queueUrl = queueUrl;
    }

    public int getMessageCount()
    {
        return messageCount;
    }

    private void setMessageCount(int messageCount)
    {
        this.messageCount = messageCount;
    }

    public MessageWrapper withMessage(Message message)
    {
        setMessage(message);
        return this;
    }

    public MessageWrapper withQueueUrl(String queueUrl)
    {
        setQueueUrl(queueUrl);
        return this;
    }

    public MessageWrapper withMessageCount(int messageCount)
    {
        setMessageCount(messageCount);
        return this;
    }

    public String getQueueName()
    {
        Objects.requireNonNull(queueUrl, "queueUrl must not be null");

        URI queueUri;
        try
        {
            queueUri = new URI(queueUrl);
        }
        catch (URISyntaxException e)
        {
            throw new RuntimeException(e);
        }

        return queueUri.getPath().split("/", 3)[2];
    }
}
