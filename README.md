# TODO Api

Development Process

## 1. Run project

This project uses maven to run as it, is a spring application you can run the project
with the following command in the root directory:


```shell
mvn spring-boot:run
```

Create package
```shell
mvn packge
```

run application with the package created
```shell
java -jar target/todoapi-0.0.1-SNAPSHOT.jar
```


## 2. GitHub Actions

**CI Pipeline** GitHub Action name

This project has Continuos Delivery (CI) through a GitHub Action, this action simply
runs the test and makes sure that the test always pass.

The GitHub Action **CI Pipeline** will be triggered every time a pull request is made
to the main branch of the project.


<footer style="display: flex; align-items: center; justify-content: center; margin: 10px">
    Made with Love by Pablo Hernandez
</footer>