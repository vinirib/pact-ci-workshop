# PACT CI WORKSHOP
![Pact Logo](imgs/pact-logo.PNG)

This repository is an example to how implement PACT consumer driver contract and automatically verify if any integration is broken.

## Tools Used

 - Jenkins
 - Postgresql
 - Pact Broker

## Pact concept

Pact is a code-first tool for testing HTTP and message integrations using contract tests. Contract tests assert that inter-application messages conform to a shared understanding that is documented in a contract. Without contract testing, the only way to ensure that applications will work correctly together is by using expensive and brittle integration tests.
Do you set your house on fire to test your smoke alarm? No, you test the contract it holds with your ears by using the testing button. Pact provides that testing button for your code, allowing you to safely confirm that your applications will work together without having to deploy the world first.

![Pact Logo](imgs/slide_pact.gif)


## Pact Workflow 

![Pact Logo](imgs/PACT-CI-WORKSHOP.png)

We have two independent repositories here, to simulate an development environment, this two repositories can be found on my github.

 - client-api (consumer) https://github.com/vinirib/pact-consumer-sample

  - account-api (provier) https://github.com/vinirib/pact-provider-sample

To make the automation were you can see on the picture the steps are:

 - When you up jenkins in docker, automatically will create the necessary jobs, will be tree steps.

 1 - Jenkins will download repository and run a custom jenkins file to package maven project and generate contracts, this contracts will be send to the pact broker.

 2 - If the previous job is successful, this second job will be called, and call the provider repository, download and run junit tests where junit tests will be validate contracts.

 3 -  If the previous job is successful, Jenkins will call the last job can-i-deploy, this is a pact tool that will verify if this integration is ok, if all responses is ok, you will see all jobs result OK in jenkins after all.

 