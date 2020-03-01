#!/bin/sh

# conf cron
CRN_TXT="$CRN /scripts/script.sh >> $LOGFILE"

echo "$CRN_TXT" >> crontab.txt


/usr/bin/crontab /crontab.txt
chmod 755 /scripts/script.sh

#run system at first execution
./scripts/script.sh

# start cron
/usr/sbin/crond

#start nginx
nginx -g "daemon off;"