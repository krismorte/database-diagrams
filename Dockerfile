FROM openjdk:8-jdk-alpine3.9
LABEL maintainer = krisnamourt_ti@hotmail.com

ENV CRN='0 7 * * *'
ENV OUTPUT=/www
ENV LOGFILE=/var/log/script.log
ENV TMP_IDX=index.html
ENV COMPANY_NAME='by krismorte'
ENV SCHEMASPY_VERSION='6.0.0'

COPY src/ src/
COPY pom.xml index.html /
COPY /template /www/
COPY scripts/ /scripts/
RUN chmod 755 /scripts/*.sh


RUN apk update && apk add --no-cache graphviz && apk add ttf-opensans && apk add maven && apk add nginx
RUN mkdir -p /run/nginx && adduser -D -g 'www' www && chown -R www:www /var/lib/nginx && chown -R www:www /www && wget github.com/schemaspy/schemaspy/releases/download/v$SCHEMASPY_VERSION/schemaspy-$SCHEMASPY_VERSION.jar -O schemaspy.jar

RUN mv /etc/nginx/nginx.conf /etc/nginx/nginx.conf.orig
COPY /nginx-conf/nginx.conf /etc/nginx/

EXPOSE 80
VOLUME /dbconf

ENTRYPOINT [ "sh","/scripts/entry.sh"]
