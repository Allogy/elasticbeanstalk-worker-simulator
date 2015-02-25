package com.allogy.amazonaws.elasticbeanstalk.worker.simulator.model;

import com.amazonaws.services.sqs.model.Message;

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
}
