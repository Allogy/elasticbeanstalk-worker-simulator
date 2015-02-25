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
