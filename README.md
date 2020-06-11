# Micronaut Sample

This is a sample application created to test the functionality of micronaut framework.
Sample Application is an authenticated API that proxies [OMDB API](https://www.omdbapi.com/) at `getMovie` and [Open Library API](https://openlibrary.org/developers/api) at `getBook`.
  
## Authentication

The credentials can be found in `src/main/resources/application.yml` that you can use to authenicate at `login`.

## OMDB API

The OMDB API is a private API which requires you to access it via an API key. The API key must be put in `src/main/resources/application.yml` before running the project.

## Run

To run this project, you need Java 11 only. Just run 

```bash
./gradlew run
```

to get the webapp running on `http://localhost:8080`.

## Testing

There are a few tests written for the API. They leverage Micronaut Http Client to easily test the API.
