# POS System

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

#### API URL
http://localhost:9999/api/

#### Swagger URL
http://localhost:9999/swagger-ui/


#### Setting API URL
http://localhost:9999/api/settings
http://localhost:9999/api/setting-items

http http://localhost:9999/api/setting-items
open http://localhost:9999/api/setting-items

curl -H 'Accept:application/json' http://localhost:9999/api/settings
curl -H 'Accept:application/schema+json' http://localhost:9999/api/settings

