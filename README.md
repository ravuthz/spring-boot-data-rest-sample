# POS System

### Using Techology
- Generate by: [Spring Initialzer](https://start.spring.io/) with [Gradle build tool](https://gradle.org)
- Java Framework: [Spring boot version 1.5.6.RELEASE](https://projects.spring.io/spring-boot/)
- Database: [Hibernate](http://hibernate.org/) with [PostgreSQL](https://www.postgresql.org/)
- Library:
    + Project Lombok
    + Spring Data-JPA
    + Spring Data-REST
    + [Srpingfox Swagger 2 (v3.0.0)](https://springfox.github.io/springfox/docs/current/#gradle)
    + [Springfox Swagger UI 2 (v3.0.0)](https://springfox.github.io/springfox/docs/current/#springfox-swagger-ui)
    + [Springfox Swagger Data REST (v3.0.0)](https://springfox.github.io/springfox/docs/current/#gradle-2)
- IDE: [Idea IntelliJ 2020.1](https://www.jetbrains.com/idea/whatsnew/#2020-1)


### SetUp, Build & Start Development Server
```bash
git clone https://github.com/ravuthz/spring-boot-data-rest-sample.git

cd spring-boot-data-rest-sample
gradle build -x test
gradle bootRun
```

### Start Server End Points
```bash
# API URL
start http://localhost:9999/

# Swagger URL
start http://localhost:9999/swagger-ui/

# Settings & Setting Items API URL
http http://localhost:9999/api/settings
open http://localhost:9999/api/setting-items

curl -H 'Accept:application/json' http://localhost:9999/api/settings
curl -H 'Accept:application/schema+json' http://localhost:9999/api/settings
```


#### Build & Start Server
```bash
gradlew build -x test
gradlew bootRun
```

#### Docker in Gradle
```bash
gradlew docker --info
gradlew dockerRun --info
gradlew dockerStop --info

gradlew dockerStop docker dockerRun --info
```

#### DockerCompose in Gradle
```bash
gradlew dockerComposeUp --info
gradlew dockerComposeDown --info

gradlew dockerComposeDown dockerComposeUp --info
```

