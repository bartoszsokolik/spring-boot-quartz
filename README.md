[![Build Status](https://travis-ci.org/bartoszsokolik/spring-boot-quartz.svg?branch=master)](https://travis-ci.org/bartoszsokolik/spring-boot-quartz)

## About

Simple Spring Boot application with Quartz scheduling, postgresql, docker and fake smtp server.

## Usage

To run application first build artifact using Maven `./mvnw clean install`

Then you can use `docker-compose up` to run postgres, fake smtp server and spring application.

To schedule an email use below curl
```
curl -v -X POST -H "Content-type: application/json" http://localhost:8080/api/mail/schedule -d '{"email" : "example@test.com", "subject" : "subject", "body" : "body", "dateTime" : "2019-05-22T17:38:00"}'
``` 

After that on scheduled time you will be able to see fake email which was sent by scheduler at `http://localhost:15080/`
