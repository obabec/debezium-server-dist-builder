Build

```bash
mvn clean package -DskipITs -DskipTests -Passembly
```

Run

```bash
docker build -build-arg keystore_password="KEYSTORE_PASSWORD" -build-arg truststore_password="TRUSTSTORE_PASSWORD" .
```