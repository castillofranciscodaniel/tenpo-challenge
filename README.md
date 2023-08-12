# tenpo-challenge

## Get started

- Install docker with docker-compose

## Features

- calculate value
- history request paginate find 

## Run application

```bash
docker-compose up --build 
```

## Try challenge with Swagger OpenAPI

http://localhost:8080/webjars/swagger-ui/index.html#/


## Architecture Hexagonal

domain --> models
domain --> ports

application --> dto
application --> use-cases

infrastructure --> clients
infrastructure --> dto
infrastructure --> rest-web
infrastructure --> data-in-memory

La idea de como esta maquetado el proyecto, es que la capa aplicación solo dependa
del domain y las interfaces que el mismo provee con sus puertos.

La infraestructura es la encargada de implementar esos ports en sus adaptadores.

De este modo, aplicación no conoce nada de como infraestructura ha implementado
estos puertos.