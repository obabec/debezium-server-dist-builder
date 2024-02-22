FROM openjdk:17
LABEL authors="obabec"

RUN microdnf install -y zip

ADD target/debezium-server-custom-dist-$dbz_version.zip distribution.zip

RUN mkdir "keystore"
RUN mkdir "truststore"

ARG keystore_password
ARG truststore_password

ADD truststore.jks debezium-server/truststore/truststore.jks
ADD keystore.jks debezium-server/keystore/keystore.jks

RUN unzip distribution.zip

WORKDIR debezium-server

RUN chmod +x run.sh

ENV JAVA_OPTS="-Djavax.net.ssl.keyStore=keystore/keystore.jks \
  -Djavax.net.ssl.keyStorePassword=$keystore_password \
  -Djavax.net.ssl.trustStore=truststore/truststore.jks \
  -Djavax.net.ssl.trustStorePassword=$truststore_password"

RUN ls
ENTRYPOINT ["/bin/bash"]
CMD ["/debezium-server/run.sh"]