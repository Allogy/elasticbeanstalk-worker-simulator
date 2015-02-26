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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;

/**
 * This simulates the entire worker deamon which runs on the ElasticBeanstalk EC2
 * instance for a worker role.
 *
 * @author David Venable
 * @see http://docs.aws.amazon.com/elasticbeanstalk/latest/dg/using-features-managing-env-tiers.html
 */
@Service
public class WorkerDaemonSimulator
{
    private final QueueManager queueManager;
    private final WorkerApplication workerApplication;

    @Autowired
    public WorkerDaemonSimulator(QueueManager queueManager,
                                 WorkerApplication workerApplication)
    {
        this.queueManager = queueManager;
        this.workerApplication = workerApplication;
    }

    public void process()
    {
        Stream<MessageWrapper> handledOk =
                queueManager.readQueue().filter(m -> HttpStatus.OK.equals(workerApplication.forward(m)));

        queueManager.deleteMessages(handledOk);
    }
}
