# 🚧Under construction🚧
# Datsa API
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=com.github.edurbs%3Adatsa&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=com.github.edurbs%3Adatsa)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=com.github.edurbs%3Adatsa&metric=security_rating)](https://sonarcloud.io/summary/new_code?id=com.github.edurbs%3Adatsa)
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=com.github.edurbs%3Adatsa&metric=reliability_rating)](https://sonarcloud.io/summary/new_code?id=com.github.edurbs%3Adatsa)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=com.github.edurbs%3Adatsa&metric=sqale_rating)](https://sonarcloud.io/summary/new_code?id=com.github.edurbs%3Adatsa)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=com.github.edurbs%3Adatsa&metric=coverage)](https://sonarcloud.io/summary/new_code?id=com.github.edurbs%3Adatsa)
[![Duplicated Lines (%)](https://sonarcloud.io/api/project_badges/measure?project=com.github.edurbs%3Adatsa&metric=duplicated_lines_density)](https://sonarcloud.io/summary/new_code?id=com.github.edurbs%3Adatsa)
[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=com.github.edurbs%3Adatsa&metric=code_smells)](https://sonarcloud.io/summary/new_code?id=com.github.edurbs%3Adatsa)

[Datsa](https://www.webonary.org/xavante/gcc95fd56-b5b8-4710-bf6a-987cc05e1dd0/) means "food" in xavante indigenous language. This is a RestFull API with HATEOAS made while I was taking the Algaworks Spring Rest Specialist course. It was made with Spring boot 3 and Java 17.

### Some highlights

- Authorization Server with authorization code flow for frontend communication and client password for backend communication with other services.
- Full documentation with SpringDoc in address http://[your host]:8080/swagger-ui/index.html
- Flyway

## Running the backend

### Docker compose

```
wget https://raw.githubusercontent.com/edurbs/datsa/refs/heads/main/docker-compose.yml
docker compose up
```

### Maven

```
git clone https://github.com/edurbs/datsa.git
cd datsa
mvn spring-boot:run -Dspring-boot.run.fork=false -f pom.xml
```

