# database-diagrams

> Please support a project by simply putting a Github star. 
Share this library with friends on Twitter and everywhere else you can.

Thanks to the [SchemaSpy](https://github.com/schemaspy/schemaspy) developers

This code was made to implementing full database documentation per server that wasn't implementing in the SchemaSpy originally project. This project also include a crontab scheduling, a nginx server to show the results and a java application to manager and coordenate the execution and build a main page to redirect to all databases sites made.

## Run Docker Pull
```
docker pull krismorte/databasediagrams:2.0
```

## Run a test

This project include a docker-compose file to test the solution locally running against 2 mysql and 1 postgres containers 

```
git clone https://github.com/krismorte/database-diagrams.git
cd database-diagrams/
docker-compose up
```

on dbconf folder you will find .prop files with the database servers configuration and also a mysql and postgres script used to create the diagram_test database when the container is created.

## Run Docker Container
To use this solution you have to edit the .prop files with your own servers and run this follow command:

```
docker run -it --rm -p 80:80 -v $PWD/dbconf:/dbconf --name databasediagrams krismorte/databasediagrams:2.0
```
_*this command will show the output and lock the terminal, to run in production please remove the -it option_

access the url http://localhost/

The output don't show the schemaspy log to see that run the follow command:

```
docker exec databasediagrams cat /var/log/script.log
```

Depends on the amount of databases and tables this process can take a long time to run, be aware about this. By default the script will run everyday at as 07AM you can overrite the env __CRN__

, but you can run manually to see the result. This process can take several minutes depends the database sizes

This solution was prepared adn tested on mysql, sql server and postgres. The Schemaspy support much more databases as can see [here](https://github.com/schemaspy/schemaspy/tree/master/src/main/resources/org/schemaspy/types) but my solution wrapper the schemaspy so I will add more database as soon as possible.

## Build the Image
``` #docker build -t krismorte/databasediagrams:2.0 .```


the full documentarion of the container is [here](https://hub.docker.com/r/krismorte/databasediagrams) 

## PROP file
- __Required__
- _optional_

File's configuration
- __schemaspy.db.type=mysql__ this fild is necessary to schemaspy knows about the database 
- __db.server=172.17.0.1__ server name
- __db.user=root__ database user name
- __db.password=secret__ user's password
- _db.port=3307_ just necessary if you are using a different port
- _db.query=SELECT DISTINCT SCHEMA_NAME FROM information_schema.SCHEMATA WHERE SCHEMA_NAME ='performance_schema'_ you can customize the dabase query to include or exclude some database

## Under the hook

The entry point will config the cron job, running the application for the first time and up the ngixn server. The cron job call a second script who contains the main java application.

