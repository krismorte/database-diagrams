#!/bin/sh

FILE=$(find /dbconf/ -name "*.jar" | rev | cut -d '/' -f1 | rev)


if [ "$FILE" == "ojdbc6.jar" ] 
then
    echo "config $FILE"
    mvn install:install-file -Dfile=/dbconf/ojdbc6.jar -DgroupId=com.oracle -DartifactId=ojdbc6 -Dversion=11.2.0.4 -Dpackaging=jar
    sed -i "s/<dependencies>/<dependencies>\n<dependency>\n<groupId>com.oracle<\/groupId>\n<artifactId>ojdbc6<\/artifactId>\n<version>11.2.0.4<\/version>\n<\/dependency>\n/g" pom.xml 
elif [ "$FILE" == "ojdbc7.jar" ] 
then
    echo "config $FILE"
    mvn install:install-file -Dfile=/dbconf/ojdbc7.jar -DgroupId=com.oracle -DartifactId=ojdbc7 -Dversion=12.2.0.1 -Dpackaging=jar
    sed -i "s/<dependencies>/<dependencies>\n<dependency>\n<groupId>com.oracle<\/groupId>\n<artifactId>ojdbc7<\/artifactId>\n<version>12.2.0.1<\/version>\n<\/dependency>\n/g" pom.xml 
elif [ "$FILE" == "ojdbc8.jar" ] 
then
    echo "config $FILE"
    mvn install:install-file -Dfile=/dbconf/ojdbc8.jar -DgroupId=com.oracle -DartifactId=ojdbc8 -Dversion=19.3 -Dpackaging=jar
    sed -i "s/<dependencies>/<dependencies>\n<dependency>\n<groupId>com.oracle<\/groupId>\n<artifactId>ojdbc8<\/artifactId>\n<version>19.3<\/version>\n<\/dependency>\n/g" pom.xml 
fi
