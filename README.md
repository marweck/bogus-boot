# bogus-boot

## How to use it
This is a simple example of containerized spring-boot java application.

It depends on a mongodb instance available at port 27017.

Use the script ```start-mongo.sh``` to start a dockerized MongoDb instance, which is the
same as issuing the following command:

```
$. docker run -p 27017:27017 --name bogus-mongo -d mongo
```

To build a docker container with this spring-boot app, use in the terminal:

```
$. mvn package docker:build
```

We use the great docker-maven-plugin from Spotify (also my favorite music streaming service) to create 
a docker image with the just built app.jar.

After building our docker image, we can start our app using the ```start-app.sh``` script,
which is the same as typing the following command:

```
$. docker run -p 8080:8080 --name bogus -d com.minone/bogus
```

If you prefer running the app outside Docker, just issue:

```
$. mvn spring-boot:run
```