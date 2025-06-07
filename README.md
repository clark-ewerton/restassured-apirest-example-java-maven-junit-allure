# Project to demonstrate knowledge in tools such as RestAssured + Java + Junit 4 + Maven + CI/CD (Github Actions and Pages) + Allure Reports

[![Api CI/CD](https://github.com/clark-ewerton/restassured-apirest-example-java-maven-junit-allure/workflows/API%20Tests%20CI/badge.svg)](https://github.com/clark-ewerton/restassured-apirest-example-java-maven-junit-allure/actions/workflows/cicd.yml)
[![Pages Status](https://img.shields.io/badge/GitHub%20Pages-Online-green)](https://clark-ewerton.github.io/restassured-apirest-example-java-maven-junit-allure/)
![License](https://img.shields.io/badge/license-MIT-blue)

Don't forget to give this project a ⭐

* [Required Software](#required-software)
* [How to execute the tests](#how-to-execute-the-tests)
   * [Running the test suites](#running-the-test-suites)
   * [Generating the test report](#generating-the-test-report)
* [About the Project Structure](#about-the-project-structure)
* [Libraries](#libraries)
* [Patterns applied](#patterns-applied)

This project was created to start the initial steps with test automation for a REST API using Rest-Assured.
It tests the API: [The API Cat](https://thecatapi.com/).
Which is a public API REST, pretty simple to handle on and perform some requests. If for some reason the ai-key is expired, just log into the website and generate a new one using your own account.

> :warning: **Disclaimer**
> 
> This project has an educational objective and does not have the best practices that could be applied.
>

## Required software
* Java JDK 8+
* Maven installed and in your classpath

## How to execute the tests
You can open each test class on `src\test\java` and execute all of them, but I recommend you run it by the
command line. It enables us to run in different test execution strategies.

### Running the test suites

The test suites can be run directly by your IDE or by command line.
If you run `mvn test` all the tests will execute because it's the regular Maven lifecycle to run all the tests.

*** VERY IMPORTANT: this project was built using JUNIT4, don't try to add any extra Junit 4 as a library into your project. The project already has the library in the POM.xml.

To run different suites based on the groups defined for each test you must inform the property `-Dgroups` and the group names.
The example below shows how to run the test for each pipeline stage:

### Generating the test report

This project uses Allure Report to automatically generate the test report.
There are some configuration to make it happen:
* aspectj configuration on `pom.xml` file
* `allure.properties` file on `src/test/resources`

You can use the command line to generate it in two ways:
* `mvn allure:serve`: will open the HTML report into the browser
* `mvn allure:report`: will generate the HTML port at `target/site/allure-maven-plugin` folder

## About the Project Structure

### src/main/java

#### client
Classes that do some actions in their endpoints. It's used my the `FullE2ETest` to demonstrate and e2e
scenario.

#### config
The class `Configuration` is the connections between the property file `api.properties` located in `src/test/resources/`.

The `@Config.Sources` load the properties file and match the attributes with the `@Key`, so you automatically have the value.
You can see two sources.
The first one will get the property values from the system (as environment variables or from the command line) in the case you want to change it, for example, in a pipeline.
The second will load the `api.properties` file from the classpath.
```java
@Config.Sources({
    "system:properties",
    "classpath:api.properties"})
```

The environment variable is read on the `ConfiguratorManager`.
This class reduces the amount of code necessary to get any information on the properties file.

This strategy uses [Owner](http://owner.aeonbits.org/) library

#### data

##### factory
Test Data Factory classes are design to generate simple static data to our project.

##### suite
It contains a class having the data related to the test groups.

##### support
Contains method to help in process of formating some data or managing some path.

#### model
Builder class to create the json body and pass it into body's method of RestAssured.

#### specs
Request and Response specifications used by the clients and e2e tests.
The class `InitialStepsSpec` set the basePath, baseURI, and token (pass into header) for the custom specs.
The classes `ImagesSpecs` and `FavouriteSpecs` contains the implementation of request and response specifications.

### src/test/java

#### e2e
End to End test using both endpoints to simulate the user journey thought the API.

#### general
Health check test to assure the endpoint is available.

#### images
Contract and Functional tests to the Image endpoint.

#### favourite
Contract and Functional tests to the Favourite endpoint

#### BaseAPI
Base Test that sets the initial aspects to make the requests using RestAssured. It's use it's recommendable to be extendable by any class.

### src/test/resources
It has a `schemas` folder with the JSON Schemas to enable Contract Testing using Rest-Assured. Also, the properties file to easily configure the API URI.

*** VERY IMPORTANT: the api key might be outdated. So needs to be generated from the website thecatapi for you to signup an generate another one.

## Libraries
* [RestAssured](http://rest-assured.io/) library to test REST APIs
* [JUnit 4](https://junit.org/junit4/) to support the test creation
* [Owner](http://owner.aeonbits.org/) to manage the property files
* [Allure Report](https://docs.qameta.io/allure/) as the testing report strategy

## Patterns applied
* Test Data Factory
* Builder
* Request and Response Specification
* BaseAPI

## GitHub Actions CI/CD
The workflow file `.github/workflows/cicd.yml` includes two jobs:

`run-api-tests`: runs tests on a local emulator on GitHub-hosted runners using Ubuntu.

`deploy-report`: generates an Allure report and publishes it to GitHub Pages.

Allure results are stored as artifacts and published after every execution, regardless of test results.

## Allure Reports
After every test execution, Allure reports are generated and published to:

🔗 https://clark-ewerton.github.io/restassured-apirest-example-java-maven-junit-allure

## Contributing
Contributions are welcome!
Feel free to open issues, fork the repository, and submit pull requests.

If you find this project useful, please consider giving it a star to help increase its visibility.

## License
This project is licensed under the MIT License.
See the LICENSE file for more details.

## Inspiration
This project was inspired by this repo: https://github.com/eliasnogueira/restassured-complete-basic-example done by @eliasnogueira
