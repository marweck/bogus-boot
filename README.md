# bogus-boot

## How to use it

This is a simple example of containerized spring-boot java application.

It depends on a mongodb instance available at port 27017.

Use the command below to start a dockerized MongoDb instance:

```
$. docker run -p 27017:27017 --name bogus-mongo -d mongo
```

### Docker Image

To build a docker image for this spring-boot app, use in the terminal:

```
$. gradle bootBuildImage
```
After building our docker image, we can start our app using the ```start-app.sh``` script,
which is the same as typing the following command:

```
$. docker run -p 8080:8080 --name bogus -it --rm com.minone/bogus:0.0.1
```

### Native App

To create a graalvm native executable for this application, execute:

```
$. gradle nativeCompile
```
NOTE: GraalVM 22.3+ is required.

After completed, this command will generate the executable file in the folder:
`./build/native/nativeCompile`
