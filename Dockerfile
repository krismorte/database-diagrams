FROM alpine
MAINTAINER krisnamourt_ti@hotmail.com


ENV CRN='0 7 * * *'
ENV OUTPUT=/www
ENV LOGFILE=/var/log/script.log

#COPY FILES
RUN mkdir /app
RUN mkdir /app/dbconf
RUN mkdir -p /run/nginx 
RUN mkdir /www
COPY index.html /app/index.html
COPY /schemaspy/*.jar /app/schemaspy.jar
COPY /dist/*.jar /app/database-diagrams.jar
COPY /drives /app/drives/
COPY /template /www/
ADD /crontab/script.sh /script.sh
RUN chmod 755 /script.sh

VOLUME /app/dbconf


#ADD LIBS
RUN apk update
RUN apk add --no-cache graphviz 
RUN apk add ttf-opensans
RUN apk add openjdk8-jre


#NGINX
RUN apk add nginx
RUN adduser -D -g 'www' www
RUN chown -R www:www /var/lib/nginx & chown -R www:www /www
RUN mv /etc/nginx/nginx.conf /etc/nginx/nginx.conf.orig

COPY /nginx-conf/nginx.conf /etc/nginx/


#ENTRYPOINT
COPY /entrypoint/entry.sh /entry.sh
RUN chmod 755 /entry.sh


EXPOSE 80


WORKDIR /

ENTRYPOINT [ "/entry.sh"]