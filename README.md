# elasticbeanstalk-worker-simulator

AWS Elastic Beanstalk provides a [worker tier environment](http://docs.aws.amazon.com/elasticbeanstalk/latest/dg/using-features-managing-env-tiers.html) which can read from an AWS SQS queue and send the contents to a service you are running. This project simulates the worker process which AWS automatically adds for you when deploying an Elastic Beanstalk environment. This way you can test your worker application locally.

You must configure an SQS queue to read messages from and your application's target URL. Then when you run the application, it periodically reads from the queue, and makes an HTTP request to your application.

## Installation

You must install JDK 8 and above to build and run the simulator. To install the simulator, clone this repository.

## Configuration.

Add a _config/application.properties_ file to the root of the cloned Git directory.

```
accessKey=<Your AWS Access Key>
secretKey=<Your AWS Secret Key>
queueUrl=<Your full SQS Queue URL>
targetUrl=<The URL of Your Service>
```

It might look some somthing like the following.

```
accessKey=AKI*************
secretKey=kY7ak*******************
queueUrl=https://sqs.us-east-1.amazonaws.com/34**********/my-queue-name
targetUrl=http://localhost:8080/myendpoint
```

## Running

Run `./gradlew bootRun`
(or on Windows: `./gradle.bat bootRun`)

## Extended Configuration

See the [application.properties](src/main/resources/application.properties) file for the default values that the simulator uses when running. You can override any of these in your _config/application.properties_ file.
