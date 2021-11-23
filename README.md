# PetStore Application


## Introduction

MicroProfile Starter has generated this MicroProfile application for you.

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## Packaging and running the application

If you want to build an _??ber-jar_, execute the following command:

    ./gradlew build -Dquarkus.package.type=uber-jar

To run the application:

    java -jar build/petstore-runner.jar

The application can be also packaged using simple:

    ./gradlew build

It produces the `quarkus-run.jar` file in the `build/quarkus-app/` directory.
Be aware that it is not an _??ber-jar_ as the dependencies are copied into the `build/quarkus-app/lib/` directory.

To launch the test page, open your browser at the following URL

    http://localhost:8080/index.html

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:

    ./gradlew quarkusDev

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at http://localhost:8080/q/dev/.

## Creating a native executable

Mind having GRAALVM_HOME set to your Mandrel or GraalVM installation.

You can create a native executable using:

    ./gradlew build -Dquarkus.package.type=native

Or, if you don't have [Mandrel](https://github.com/graalvm/mandrel/releases/) or
[GraalVM](https://github.com/graalvm/graalvm-ce-builds/releases) installed, you can run the native executable
build in a container using:

    ./gradlew build -Dquarkus.package.type=native -Dquarkus.native.container-build=true

Or to use Mandrel distribution:

    ./gradlew build -Dquarkus.package.type=native -Dquarkus.native.container-build=true -Dquarkus.native.builder-image=quay.io/quarkus/ubi-quarkus-mandrel:20.3-java11

You can then execute your native executable with:

    ./build/petstore-runner

If you want to learn more about building native executables, please consult https://quarkus.io/guides/building-native-image.


## Deploying Application

<!-- To deploy the demo app on a docker-compose please visit [./deploy](https://github.com/rasika/petstore/tree/master/deploy) -->

## Curl Commands

### Pet Resource

Initialize the pets 
    
    curl http://localhost:8080/pets

Get all existing pets

    curl http://localhost:8080/pets/getPets

Get pet of ID: '5'

    curl http://localhost:8080/pets/getPet/5

Get pet of name 'Zuko'

    curl http://localhost:8080/pets/searchByName/Zuko

Delete pet of ID: '1'

    curl http://localhost:8080/pets/deletePet/1


Filter out all the pets of age '2'

    curl -i -X POST http://localhost:8080/pets/filterByAge -H "Authorization:OAuth MyToken" -H "Content-Type: application/x-www-form-urlencoded" -d "petAge=2"


Filter out all pets of type 'Cat'

    curl -i -X POST http://localhost:8080/pets/filterByType -H "Authorization:OAuth MyToken" -H "Content-Type: application/x-www-form-urlencoded" -d "petType=Cat"

Add pet with ID: 10, name: 'Momo', type: 'Moth', and age: 1

    curl -i -X POST http://localhost:8080/pets/addPet -H "Authorization:OAuth MyToken" -H "Content-Type: application/x-www-form-urlencoded" -d "pet_id=10&pet_name=Momo&pet_type=Moth&pet_age=1"

Update pet with ID: 10, to have it's name as Momo, type as Moth, and age as 2

    curl -X POST http://localhost:8080/pets/updatePet -H "Authorization:OAuth MyToken" -H "Content-Type: application/x-www-form-urlencoded" -d "pet_id=10&pet_name=Momi&pet_type=Moth&pet_age=2"


### PetType Resource

Initialize the pet types

    curl http://localhost:8080/petTypes

Get all existing pet types

    curl http://localhost:8080/petTypes/getPetTypes

Get pet type of ID: 1

    curl http://localhost:8080/petTypes/getPetType/1

Delete pet type of ID: 2

    curl http://localhost:8080/petTypes/deletePetType/2

Add pet type with ID: 7 and name 'Duck'

    curl -i -X POST http://localhost:8080/petTypes/addPetType -H "Authorization:OAuth MyToken" -H "Content-Type: application/x-www-form-urlencoded" -d "id=7&type='Duck'"

Update pet type with ID: 7 to have the name 'Crocodile'

    curl -i -X POST http://localhost:8080/petTypes/updatePetType -H "Authorization:OAuth MyToken" -H "Content-Type: application/x-www-form-urlencoded" -d "id=7&type='Crocodile'"
