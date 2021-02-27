#!/bin/sh

# conf cron
CRN_TXT="$CRN /scripts/script.sh >> $LOGFILE"
echo "$CRN_TXT" >> crontab.txt
/usr/bin/crontab /crontab.txt

# config Company Name
sed -i "s|{COMPANY}|$COMPANY_NAME|" index.html

#config oracle jdbc if is needed
./scripts/orac-jdbc.sh

#run system at first execution
mvn package
./scripts/script.sh

# start cron
/usr/sbin/crond

#start nginx'
nginx -g "daemon off;"