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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * @author David Venable
 */
@Service
public class BridgeService
{
    private final WorkerDaemonSimulator workerDaemonSimulator;
    private final Integer pauseTimeMilliseconds;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public BridgeService(WorkerDaemonSimulator workerDaemonSimulator,
                         @Qualifier("pauseTimeMilliseconds") Integer pauseTimeMilliseconds)
    {
        this.workerDaemonSimulator = workerDaemonSimulator;
        this.pauseTimeMilliseconds = pauseTimeMilliseconds;
    }

    public void start()
    {
        logger.info("Starting Bridge Service.");

        while(true)
        {
            workerDaemonSimulator.process();

            try
            {
                logger.debug("Sleeping for {} milliseconds", pauseTimeMilliseconds);
                Thread.sleep(pauseTimeMilliseconds);
            }
            catch (InterruptedException e)
            {
                throw new RuntimeException(e);
            }
        }
    }
}
