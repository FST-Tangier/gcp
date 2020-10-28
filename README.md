In application.properties change values:

spring.cloud.gcp.credentials.location={path to file with you credentials}

spring.cloud.gcp.bigquery.datasetName={name of your dataset}

bucketName={name of your bucket}

bucketPath={path to your bucket}

tableNameAllData={name of your BigQuery table with all data}

tableNameNonOptionalData={name of your BigQuery table with non-optional data}

**Using Postman send POST request to path "localhost:8080/upload" including avro file with @RequestParam("file").**

![alt text](https://github.com/dmitryaushev/gcp/blob/main/diagram.png?raw=true)
