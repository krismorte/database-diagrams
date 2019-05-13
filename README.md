# database-diagrams

> Please support a project by simply putting a Github star. 
Share this library with friends on Twitter and everywhere else you can.

Thanks to the [SchemaSpy](https://github.com/schemaspy/schemaspy) developers

This code was made to implementing full database's documentation per server that wasn't implementing in the SchemaSpy original  project. Also was implementing a crontab scheduling and a nginx server to show the results.

## Run Docker Pull
```
docker pull krismorte/databasediagrams:1.0
```

## Run Docker Container
To run the container you will need to create some one properties file one per server as the ```example.prop``` in the dbconf dir
```
docker run -d --rm -p 80:80 -v dbconf_dir:/app/dbconf --name teste krismorte/databasediagrams:1.0
```
```example.prop``` is a SQL Server example the image supports postgresql, mysql and redis

Seek the schemaspy schemaspy.db.type [here](https://github.com/schemaspy/schemaspy/tree/master/src/main/resources/org/schemaspy/types) 

Below the queries `db.query`

- mssql select name from master..sysdatabases where name not in ('master','tempdb','msdb','model') order by 1
- pgsql select datname from pg_catalog.pg_database where datname not in ('template0','template1','postgres') order by 1
- mysql SELECT DISTINCT SCHEMA_NAME FROM information_schema.SCHEMATA WHERE  SCHEMA_NAME NOT IN ('information_schema', 'performance_schema', 'mysql','sys','tmp') ORDER BY SCHEMA_NAME
- redis select datname from pg_catalog.pg_database where datname not in ('template0','template1','postgres') order by 1

## Build the Image
```
#docker build -t krismorte/databasediagrams:1.0 .
```

the full documentarion of the container is [here](https://hub.docker.com/r/krismorte/databasediagrams) 

Finally access the url http://localhost/