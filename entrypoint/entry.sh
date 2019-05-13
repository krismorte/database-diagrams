#!/bin/sh

# conf cron
CRN_TXT="$CRN /script.sh >> $LOGFILE"

echo "$CRN_TXT" >> crontab.txt


/usr/bin/crontab /crontab.txt
chmod 755 /script.sh

# start cron
/usr/sbin/crond

#start nginx
nginx -g "daemon off;"