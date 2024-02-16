FROM openjdk:17
LABEL authors="obabec"

ADD target/custom-debezium-server-dist-2.1.4.Final.zip distribution.zip

RUN mkdir "keystore"
RUN mkdir "truststore"

ARG keystore_password
ARG truststore_password

ADD truststore.jks truststore/truststore.jks
ADD keystore.jks keystore/keystore.jsk

RUN unzip distribution.zip

ENV JAVA_OPTS="-Djavax.net.ssl.keyStore=keystore/keystore.jsk \
  -Djavax.net.ssl.keyStorePassword=$keystore_password \
  -Djavax.net.ssl.trustStore=truststore/truststore.jks \
  -Djavax.net.ssl.trustStorePassword=$truststore_password"

ENTRYPOINT ["debezium-server/run.sh"]