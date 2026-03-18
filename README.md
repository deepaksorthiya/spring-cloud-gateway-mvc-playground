<h1 style="text-align: center;">Spring Boot Cloud Gateway Playground Project</h1>

<p style="text-align: center;">
  <a href="https://github.com/deepaksorthiya/spring-cloud-gateway-mvc-playground/actions/workflows/maven-jvm-non-native-build.yml">
    <img src="https://img.shields.io/github/actions/workflow/status/deepaksorthiya/spring-cloud-gateway-mvc-playground/maven-jvm-non-native-build.yml?style=for-the-badge&label=JVM%20Maven%20Build" alt="JVM Maven Build"/>
  </a>  
  <a href="https://github.com/deepaksorthiya/spring-cloud-gateway-mvc-playground/actions/workflows/maven-graalvm-native-build.yml">
    <img src="https://img.shields.io/github/actions/workflow/status/deepaksorthiya/spring-cloud-gateway-mvc-playground/maven-graalvm-native-build.yml?style=for-the-badge&label=GraalVM%20Maven%20Build" alt="GraalVM Maven Build"/>
  </a>
  <a href="https://hub.docker.com/r/deepaksorthiya/spring-cloud-gateway-mvc-playground">
    <img src="https://img.shields.io/docker/pulls/deepaksorthiya/spring-cloud-gateway-mvc-playground?style=for-the-badge" alt="Docker"/>
  </a>
  <a href="https://spring.io/projects/spring-boot">
    <img src="https://img.shields.io/badge/spring--boot-3.5.11-brightgreen?logo=springboot&style=for-the-badge" alt="Spring Boot"/>
  </a>
</p>

## Live Demo

TBD

---

## 📑 Table of Contents

- [Overview](#-overview)
- [Features](#-features)
- [Requirements](#-requirements)
- [Getting Started](#-getting-started)
    - [Clone the Repository](#1-clone-the-repository)
    - [Start Docker](#2-start-docker)
    - [Build the Project](#3-build-the-project)
    - [Run Project Locally](#4-run-the-project)
    - [Build Docker Image](#5-optional-build-docker-image-docker-should-be-running)
    - [Run Docker Image](#6-optional-running-on-docker)
- [Testing](#-testing)
- [Reference Documentation](#-reference-documentation)

---

## 🚀 Overview

A modern **Spring Boot** starter project gateway mvc.

---

## 🚀 Features

- RESTful API with CRUD endpoints with gateway mvc
- Actuator endpoints enabled
- Docker & multi-stage build
- Kubernetes manifests & Helm chart
- GitHub Actions CI/CD

---

## 📦 Requirements

- Git `2.51+`
- Java `25`
- Maven `3.9+`
- Spring Boot `3.5.11`
- (Optional)Docker Desktop (tested on `4.50+`)
- (Optional) Minikube/Helm for Kubernetes

---

## 🛠️ Getting Started

### 1. Clone the Repository

```bash
git clone https://github.com/deepaksorthiya/spring-cloud-gateway-mvc-playground.git
cd spring-cloud-gateway-mvc-playground
```

### 2. Start Docker

* this command will start all required services to start application

```bash
docker compose up
```

### 3. Build the Project

```bash
./mvnw clean package -DskipTests
```

* OR for native build run

```bash
./mvnw clean native:compile -Pnative
```

### 4. Run the Project

```bash
./mvnw spring-boot:run
```

* OR Jar file

```bash
java -jar .\target\spring-cloud-gateway-mvc-playground-0.0.1-SNAPSHOT.jar
```

* OR Run native image directly

```bash
target/spring-cloud-gateway-mvc-playground
```

### 5. (Optional) Build Docker Image (docker should be running)

```bash
./mvnw clean spring-boot:build-image -DskipTests
```

* OR To create the native container image, run the following goal:

```bash
./mvnw clean spring-boot:build-image -Pnative -DskipTests
```

* OR using dockerfile

```bash
docker build --progress=plain --no-cache -f <dockerfile> -t deepaksorthiya/spring-cloud-gateway-mvc-playground .
```

* OR Build Using Local Fat Jar In Path ``target/spring-cloud-gateway-mvc-playground-0.0.1-SNAPSHOT.jar``

```bash
docker build --build-arg JAR_FILE=target/spring-cloud-gateway-mvc-playground-0.0.1-SNAPSHOT.jar -f Dockerfile.jvm --no-cache --progress=plain -t deepaksorthiya/spring-cloud-gateway-mvc-playground .
```

* OR if above not work try below command

***you should be in jar file path to work build args***

```bash
cd target
docker build --build-arg JAR_FILE=spring-cloud-gateway-mvc-playground-0.0.1-SNAPSHOT.jar -f ./../Dockerfile.jvm --no-cache --progress=plain -t deepaksorthiya/spring-cloud-gateway-mvc-playground .
```

| Dockerfile Name                                    |                         Description                          |
|----------------------------------------------------|:------------------------------------------------------------:|    
| [Dockerfile](Dockerfile)                           | multi stage docker file with Spring AOT and JDK24+ AOT Cache |
| [Dockerfile.native-multi](Dockerfile.native-multi) |   multi stage using graalvm native image micro linux image   |

### 6. (Optional) Running On Docker

```bash
docker run -p 8080:8080 --name spring-cloud-gateway-mvc-playground deepaksorthiya/spring-cloud-gateway-mvc-playground:latest
```

---

## 🧪 Testing

- Access the API: [http://localhost:8080](http://localhost:8080)

### Postman API Collection

[All Rest API Endpoints](https://www.postman.com/deepaksorthiya/workspace/public-ws/collection/12463530-06a50a7f-3d47-4a15-8e8a-d2496829ebba?action=share&source=copy-link&creator=12463530)

### Rest API Endpoints

[All Rest API Endpoints](https://www.postman.com/deepaksorthiya/workspace/public-ws/collection/12463530-06a50a7f-3d47-4a15-8e8a-d2496829ebba?action=share&source=copy-link&creator=12463530)

### Run Unit-Integration Test Cases

```bash
./mvnw clean test
```

To run your existing tests in a native image, run the following goal:

```bash
./mvnw test -PnativeTest
```

---

### Reference Documentation

For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/3.5.11/maven-plugin)
* [Create an OCI image](https://docs.spring.io/spring-boot/3.5.11/maven-plugin/build-image.html)
* [GraalVM Native Image Support](https://docs.spring.io/spring-boot/3.5.11/reference/packaging/native-image/introducing-graalvm-native-images.html)
* [Spring Boot Actuator](https://docs.spring.io/spring-boot/3.5.11/reference/actuator/index.html)
* [OAuth2 Client](https://docs.spring.io/spring-boot/3.5.11/reference/web/spring-security.html#web.security.oauth2.client)
* [Spring Security](https://docs.spring.io/spring-boot/3.5.11/reference/web/spring-security.html)
* [Spring Web](https://docs.spring.io/spring-boot/3.5.11/reference/web/servlet.html)
* [Gateway](https://docs.spring.io/spring-cloud-gateway/reference/spring-cloud-gateway-server-mvc.html)
* [Resilience4J](https://docs.spring.io/spring-cloud-circuitbreaker/reference/spring-cloud-circuitbreaker-resilience4j.html)

---

### Guides

The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service with Spring Boot Actuator](https://spring.io/guides/gs/actuator-service/)
* [Securing a Web Application](https://spring.io/guides/gs/securing-web/)
* [Spring Boot and OAuth2](https://spring.io/guides/tutorials/spring-boot-oauth2/)
* [Authenticating a User with LDAP](https://spring.io/guides/gs/authenticating-ldap/)
* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)

---

### Additional Links

These additional references should also help you:

* [Configure AOT settings in Build Plugin](https://docs.spring.io/spring-boot/3.5.11/how-to/aot.html)

---

<p style="text-align: center;">
  <b>Happy Coding!</b> 🚀
</p>