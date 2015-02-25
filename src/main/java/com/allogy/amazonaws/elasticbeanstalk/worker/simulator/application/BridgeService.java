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
                Thread.sleep(pauseTimeMilliseconds);
            }
            catch (InterruptedException e)
            {
                throw new RuntimeException(e);
            }
        }
    }
}
