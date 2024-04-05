# Building custom Debezium Server distribution image

## Assembly project
```bash
mvn clean package -DskipITs -DskipTests -Passembly
```

## Build secure Docker image
```bash
docker build -build-arg keystore_password="KEYSTORE_PASSWORD" -build-arg truststore_password="TRUSTSTORE_PASSWORD" .
```

## Building insecure Docker image
```bash
docker build -f Dockerfile.insecure
```