
<img align="left" width="250" src="https://user.oc-static.com/upload/2019/10/21/15716565363662_image1.png">

# PayMyBuddy 

PayMyBuddy est un projet 'springboot' dont le but est faire des transaction entre amis.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites

What things you need to install the software and how to install them

- Java 1.8
- Maven 3.6.2
- Mysql 8.0.17

### Installing

A step by step series of examples that tell you how to get a development env running:

1.Install Java:

https://docs.oracle.com/javase/8/docs/technotes/guides/install/install_overview.html

2.Install Maven:

https://maven.apache.org/install.html

3.Install MySql:

https://dev.mysql.com/downloads/mysql/

### UML
![Class Diagram](http://www.plantuml.com/plantuml/png/XPBBJkim48RtVWgVR0uNXOfjLuKYf6uG4l004tlQZkHBD9vm4UBTYLCJS8N4ofhFO__VE5ab0PR1E_LFzkXHTsx0dYc8-hyNwTDoiUWTXDnoOQ9V60SfjPPWn-2Lcafzpt5BxbsrcgnUwcTWimSUom0UAp2sozqnz42k0lruwKla-Yum9Wv1DfbcltEzGraxmY3fvFHdELKM7GeMK6rVlstiIDH7iNza26cBhES1ylMjjfWCKoyKGotYiyU8B1houAPJDzX9vAgFx9hHabJGWk08yYBaSVJgOv8lXTbq1aLsWhV4LUiymJg6W4QE1o3xL9jdpU7r6vNqShp4bB8apnbNX-S_pkZ0GJ1OlqO7uO-XTrYb-cXf-n2TnIbH7NuetLmskcrrSzRC7wdUcfPYzikX4g5M6EpWtIS0)

### Account
```mermaid
sequenceDiagram

    participant Front
    participant Controller
    participant Service
    participant Bdd
    
    Front->>Controller : getConnect() + token
    Controller->>Service : getConnect() + token
    Service->>Service : controle token
    Service->>Controller : 401 (token non valide)
    Controller->>Front : 401 (token non valide)
    Note right of Service : token valide
    Service->>Bdd : profilRepository.findByIdNot()
    Service->>Bdd : connectRepository.findByIdUn_Id()
    Bdd->>Service : List<Profil>
    Service->>Controller : return List<Profil>
    Controller->>Front : return List<Profil>
    
    Front->>Controller : modifSolde() + token
    Controller->>Service : modifSolde() + token
    Service->>Service : controle token
    Service->>Controller : 401 (token non valide)
    Controller->>Front : 401 (token non valide)
    Note right of Service : token valide
    Service->>Bdd : profilRepository.findUniqueProfil()
    Service->>Bdd :  profilRepository.save()
    Service->>Controller : return 200 ou 403
    Controller->>Front : return 200 ou 403
    
```
### Connect
```mermaid
sequenceDiagram

    participant Front
    participant Controller
    participant Service
    participant Bdd

    Front->>Controller : postConnect() + token
    Controller->>Service : connectService.postConnect() + token
    Service->>Service : controle token
    Service->>Controller : 401 (token non valide)
    Controller->>Front : 401 (token non valide)
    Note right of Service : token valide
    Service->>Bdd : profilService.existsByIdEquals()
    Service->>Bdd : connectRepository.existsByIdUn_IdAndIdDeux_Id()
    Service->>Bdd : connectRepository.save()
    Bdd->>Service : return 200 ou 400
    Service->>Controller : return 200 ou 400
    Controller->>Front : return 200 ou 400

    Front->>Controller : getConnectById() + token
    Controller->>Service : connectService.getConnectById() + token
    Service->>Service : controle token
    Service->>Controller : 401 (token non valide)
    Controller->>Front : 401 (token non valide)
    Note right of Service : token valide
    Service->>Bdd : connectRepository.findByIdUn_Id()
    Bdd->>Service : return 200
    Service->>Controller : return 200
    Controller->>Front : return 200
    
```
### Login
```mermaid
sequenceDiagram

    Front->>Controller : login()
    Controller->>Service : authService.login()
    Service->>Bdd : profilService.getOneUserByUserName()
    Bdd->>Service : return token
    Service->>Controller : return token
    Controller->>Front : return token

    Front->>Controller : register()
    Controller->>Service : authService.register()
    Service->>Bdd : profilService.getProfilByMail()
     Service->>Bdd : accountService.addProfil()
    Bdd->>Service : return 201 ou 400
    Service->>Controller : return 201 ou 400
    Controller->>Front : return 201 ou 400
    
```
### Profil
```mermaid
sequenceDiagram
  
    Front->>Controller : getClients() + token
    Controller->>Service : profilService.getClient() + token
    Service->>Service : controle token
    Service->>Controller : 401 (token non valide)
    Controller->>Front : 401 (token non valide)
    Note right of Service : token valide
    Service->>Bdd :  profilRepository.findAll()
    Bdd->>Service : return List<Profil>
    Service->>Controller: return List<Profil>
    Controller->>Front : return List<Profil>
    
    Front->>Controller : getClient() + token
    Controller->>Service : profilService.getClientById() + token
    Service->>Service : controle token
    Service->>Controller : 401 (token non valide)
    Controller->>Front : 401 (token non valide)
    Note right of Service : token valide
    Service->>Bdd : profilRepository.findByIdList()
    Bdd->>Service : return List<Profil>
    Service->>Controller : return List<Profil>
    Controller->>Front : return List<Profil>
    
    Front->>Controller : deleteClient() + token
    Controller->>Service : profilService.deleteClient() + token
    Service->>Service : controle token
    Service->>Controller : 401 (token non valide)
    Controller->>Front : 401 (token non valide)
    Note right of Service : token valide
    Service->>Bdd : profilRepository.deleteById()
    Bdd->>Service : return 200
    Service->>Controller : return 200
    Controller->>Front : return 200

    Front->>Controller : getClientById() + token
    Controller->>Service : profilService.getclientById() + token
    Service->>Service : controle token
    Service->>Controller : 401 (token non valide)
    Controller->>Front : 401 (token non valide)
    Note right of Service : token valide
    Service->>Bdd : profilRepository.findByProfilUuid()
    Bdd->>Service : return 200
    Service->>Controller : return 200
    Controller->>Front : return 200
    
```
### Transfert
```mermaid
sequenceDiagram
    
    Front->>Controller : postTransfert() + token
    Controller->>Service : transfertService.transfert() + token
    Service->>Service : controle token
    Service->>Controller : 401 (token non valide)
    Controller->>Front : 401 (token non valide)
    Note right of Service : token valide
    Service->>Bdd : profilService.existsByIdEquals()
    Service->>Bdd : profilService.existsByIdEquals()
    Service->>Bdd : profilService.getProfilDebtor()
    Service->>Bdd : profilService.getProfilCredit()
    Service->>Bdd : transferRepository.save()
    Bdd->>Service : return 200 ou 400
    Service->>Controller : return 200 ou 400
    Controller->>Front : return 200 ou 400
    
    Front->>Controller : getTransfertById() + token
    Controller->>Service : transfertService.getTransfertById() + token
    Service->>Service : controle token
    Service->>Controller : 401 (token non valide)
    Controller->>Front : 401 (token non valide)
    Note right of Service : token valide
    Service->>Bdd : transferRepository.findByIdDebtor_IdOrderByDateDesc()
    Bdd->>Service : return List<Transfer>
    Service->>Controller : return List<Transfer>
    Controller->>Front : return List<Transfer>

    Front->>Controller : getFirstTrasnfert() + token
    Controller->>Service : transfertService.getFirstTrasnfert() + token
    Service->>Service : controle token
    Service->>Controller : 401 (token non valide)
    Controller->>Front : 401 (token non valide)
    Note right of Service : token valide
    Service->>Bdd : transferRepository.findFirstByIdDebtor_IdOrderByDateDesc()
    Bdd->>Service : return  List<Transfer>
    Service->>Controller : return  List<Transfer>
    Controller->>Front : return  List<Transfer>
      
```
### MPD
<a href="https://ibb.co/PYcQ5Fx"><img src="https://i.ibb.co/SxtrK5N/Pay-My-Buddy-mpd.png" alt="Pay-My-Buddy-mpd" border="0"></a>

## GitHub Front en React Js

Link [PayMyBuddyFront](https://github.com/thorxx19/PayMyBuddyFront)

## GitHub Back SpingBoot
Link [PayMyBuddy](https://github.com/thorxx19/PayMyBuddy)

## Installation PayMyBuddy

Utiliser [maven](https://maven.apache.org/) pour compiler le projet

```bash
mvn verify site
```

## Usage

```bash
java -jar target/PayMyBuddy.jar
```

## Actuator
Link [Actuator](https://www.baeldung.com/spring-boot-actuators) documentation

```bash
http://localhost:9001/actuator
```

## Swagger
Link [Swagger](https://www.baeldung.com/spring-rest-openapi-documentation) documentation

```bash
http://localhost:9001/swagger-ui/index.html
```
