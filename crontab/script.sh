#!/bin/sh

echo "starting database-diagrams processes"
date
cd /app
pwd

rm -rf *.txt
rm -rf *.sh
echo "folder cleaning is complete"

echo "Generating schemaspy scripts"
/usr/bin/java -cp database-diagrams.jar:drives/* com.github.krismorte.databasediagrams.Main
echo "Generating schemaspy scripts is complete"

chmod +x *.sh
echo "Runnig schemaspy scripts"
for f in *.sh; do  
  sh "$f" -H 
done
echo "Schemaspy scripts finished"

date
echo "database-diagrams proccesses is complete"