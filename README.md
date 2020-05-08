# PACT CI WORKSHOP
![Pact Logo](imgs/pact-logo.PNG)

This repository is an example of how to implement PACT consumer driver contract and automatically verify if any integration is broken.

## Tools Used

 - Jenkins
 - Postgresql
 - Pact Broker

 Table of contents
=================

<!--ts-->
   * [Pact concept](#Pact-concept)
   * [Pact Workflow](#Pact-Workflow)
   * [Scenarios](#Scenarios)
      * [First Scenario](#First-Scenario)
      * [Second Scenario](#Second-Scenario)
      * [Third Scenario](#Third-Scenario)

<!--te-->

## Pact concept

Pact is a code-first tool for testing HTTP and message integrations using contract tests. Contract tests assert that inter-application messages conform to a shared understanding that is documented in a contract. Without contract testing, the only way to ensure that applications will work correctly together is by using expensive and brittle integration tests.
Do you set your house on fire to test your smoke alarm? No, you test the contract it holds with your ears by using the testing button. Pact provides that testing button for your code, allowing you to safely confirm that your applications will work together without having to deploy the world first.

![Pact Logo](imgs/slide_pact.gif)


## Pact Workflow 

![Pact Logo](imgs/PACT-CI-WORKSHOP.png)

We have two independent repositories here, to simulate a development environment, these two repositories can be found on my Github.

 - client-api (consumer) https://github.com/vinirib/pact-consumer-sample

  - account-api (provider) https://github.com/vinirib/pact-provider-sample

To make the automation where you can see on the picture the steps are.
 When you up Jenkins in Docker, automatically will create the necessary jobs, will be tree steps.

 1 - Jenkins will download repository and run a custom Jenkins file to package maven project and generate contracts, these contracts will be sent to the pact broker.

 2 - If the previous job is successful, this second job will be called, and call the provider repository, download and run JUnit tests where JUnit tests will be validated contracts.

 3 -  If the previous job is successful, Jenkins will call the last job can-i-deploy, this is a pact tool that will verify if this integration is ok, if all responses are ok, you will see all jobs result OK in Jenkins after all.

## Scenarios

On this repository, we will see tree scenarios, on this scenarios we will describe the most common interactions you can combine with Pact Broker and Jenkins with your contract integration tests.


### First Scenario
[Branch Master](https://github.com/vinirib/pact-ci-workshop#Scenarios)

In this first scenario we have the basic flow. Consumer created a code to make the integration call, and created the consumer contract test with the Pact framework, but, in this case, we have some Jenkins file on consumer repository to run CI events to run the tests, generate a contract and publish on our Pact Broker (in a container).

Next, if this Jenkins stage was done successfully, we will call the next job, this will run provider JUnit tests and see if the contract integration was right (by provider side).

If all works done, the final job will trigger another Jenkins file to run can-i-deploy to see if this integration result was successful or failed.

![Pact First Scenario](imgs/PACT-FIRST-SCENARIO.png)

### Second Scenario

[Branch feature/provider-changed-contract](https://github.com/vinirib/pact-ci-workshop/tree/feature/provider-changed-contract)

The second scenario, provider was made some changes on the endpoint of consumer call (without advice), when they trigger CI, the pact contracts will be break

![Pact Second Scenario](imgs/PACT-SECOND-SCENARIO.png)

### Third Scenario

[Branch feature/consumer-make-some-changes](https://github.com/vinirib/pact-ci-workshop/tree/feature/consumer-make-some-changes)

The third scenario, the consumer was made some improvements and trigger CI to see if have some changes on integration, but, for our surprise, the provider makes some change again without advice and CI will break again


![Pact Third Scenario](imgs/PACT-THIRD-SCENARIO.png)