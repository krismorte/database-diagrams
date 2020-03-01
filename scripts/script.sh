#!/bin/sh

echo "starting database-diagrams processes"
date
pwd

rm -rf *.txt
rm -rf *.sh
echo "folder cleaning is complete"

echo "Generating schemaspy scripts"
mvn exec:java
echo "Generating schemaspy scripts is complete"

chmod +x *.sh
echo "Running schemaspy scripts"
for f in *.sh; do  
  sh "$f" -H 
done
echo "Schemaspy scripts finished"

date
echo "database-diagrams proccesses is complete"