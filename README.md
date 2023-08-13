# tenpo-challenge

Mi intención no era hacer sobre ingeniera del proyecto, si no, intentar plasmar y demostrar mi capacidad con este
challenge.

## Get started

- Install docker with docker-compose

## Features

- Calculate value
- Paginated search of the request history.

## Run application

Correr en la terminal, parado en la carpeta base del proyecto:

```bash
docker-compose up --build 
```

## Try the challenge with Swagger OpenAPI

http://localhost:8080/webjars/swagger-ui/index.html#/

# Spring WebFlux
Se optó por utilizar WebFlux de forma transversal, para tener una api reactiva y de alto rendimiento.

# PostgreSQL 
El driver utilizado es r2dbc-postgresql, que permite realizar peticiones reactivas a la misma.

# Cache
Dado que el valor externo varía poco, y podemos considerar que el valor que devuelve ese servicio no va a cambiar 
por 30 minutos, y que además puede existir más de una réplica del servicio funcionando en paralelo, se opto por
utilizar redis para "cachear" el valor, de tal manera que basta con que una réplica obtenga el valor para que el resto
la use. 

Además, se utilizó un driver reactivo, spring-boot-starter-data-redis-reactive, para comunicarse con el servicio de 
redis para no generar llamadas
bloqueantes.

# Rate Limit
Para suplir con la restriction de 3 request/minuto, use la librería bucket4j-core, que de manera free, te deja
limitar tu api hasta 20 request/minuto

## Hexagonal architecture

La idea de como está maquetado el proyecto, es que la capa core solo dependa de sí misma de tal modo que los casos de
uso se valen de los puertos sin conocer las implementaciones de las mismas

### Core: 
Esta capa contiene todo nuestro negocio, libre de tecnologías "particulares" e implementaciones de tal modo que podemos 
migrar de motores de bd, drivers, librerías http, etc. sin requerir tocar nuestro negocio.

    - exeption: las exepciones que utilizaremos en nuestro negocio.
    - models: las modelos que utilizaremos en nuestro negocio.
    - ports: contiene las interfaces que representan cierta funcionalidad que requerimos que luego seran implementadas 
      por los adapters (patron Ports & Adapters).
    - usecase: contiene la lógica de negocio sirviendose de los ports. 

### Adapter:
Esta capa contiene las implementaciones de los puertos.

    - gateway: contiene los adapters que utilizan peticiones http.
    - repository: contiene los adapters que utilizan peticiones a una bd.

### Infrastructure:
Esta la he utilizado para centralizar las configuraciones y los accesos externos via api rest.

    - config: contiene las configuraciones usadas en spring.
    - delivery: contiene los controladores api rest.

# Tests
Los test fueron realizados haciendo hincapie en la parte core, ya que contiene nustro negocio. 

# Docker Hub
https://hub.docker.com/r/castillofranciscodaniel/tenpo-challenge_app

```bash
docker pull castillofranciscodaniel/tenpo-challenge_app
```

# Fin!

¡Saludos!