# online-constats

Applications de démo d'applications réactives avec : 

 * play framework 2
 * akka et akka streams
 * mongo db 
 * kafka 
 * react js 


__Démarrer mongo db :__
 
page de download : https://www.mongodb.org/downloads

`cd $MONGO-HOME`

`bin/mongod`


__Démarrer kafka avec docker :__ 

`git clone https://github.com/wurstmeister/kafka-docker.git` 

`cd kafka-docker`
 
`docker-compose -f docker-compose-single-broker.yml up`


__Démarrer les applications__ 

`git clone https://github.com/larousso/online-constats.git`

Lancer l'application d'api constat : 

`cd api-constat-scala`

`activator -Dhttp.port=9000 ~run`


Lancer l'application d'admin : 

`cd admin-constat-scala`

`activator -Dhttp.port=9001 ~run`


Lancer l'application d'appli mobile : 

`cd constat-mobile-scala`

`activator -Dhttp.port=9002 ~run`

Lancer l'application le site web : 

`cd constat-web-java`

`activator -Dhttp.port=9003 ~run`