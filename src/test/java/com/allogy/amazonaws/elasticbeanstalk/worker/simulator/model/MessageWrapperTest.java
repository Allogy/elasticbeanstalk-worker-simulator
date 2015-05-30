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

import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class MessageWrapperTest
{
    private String accountId;
    private String queueName;
    private String queueUrl;

    @Before
    public void setUp()
    {
        accountId = UUID.randomUUID().toString();
        queueName = UUID.randomUUID().toString();

        queueUrl = String.format("https://sqs.%s.amazonaws.com/%s/%s",
                UUID.randomUUID().toString(), accountId, queueName);
    }

    private MessageWrapper createObjectUnderTest()
    {
        return new MessageWrapper();
    }

    @Test
    public void getQueueName_should_return_the_queue_name_from_the_queueUrl()
    {
        MessageWrapper objectUnderTest = createObjectUnderTest();

        objectUnderTest.withQueueUrl(queueUrl);

        assertThat(objectUnderTest.getQueueName(), is(queueName));
    }
}