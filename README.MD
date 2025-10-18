# 🚧Under construction🚧
# Datsa API
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=edurbs_datsa&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=edurbs_datsa) [![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=edurbs_datsa&metric=sqale_rating)](https://sonarcloud.io/summary/new_code?id=edurbs_datsa) [![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=edurbs_datsa&metric=reliability_rating)](https://sonarcloud.io/summary/new_code?id=edurbs_datsa) [![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=edurbs_datsa&metric=security_rating)](https://sonarcloud.io/summary/new_code?id=edurbs_datsa) [![Coverage](https://sonarcloud.io/api/project_badges/measure?project=edurbs_datsa&metric=coverage)](https://sonarcloud.io/summary/new_code?id=edurbs_datsa)

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

