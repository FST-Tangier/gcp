package com.rinftech.gcp.service;

import com.google.cloud.bigquery.*;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class GcpServiceImpl implements GcpService {

    @Value("${bucketPath}")
    private String bucketPath;

    @Value("${bucketName}")
    private String bucketName;

    @Value("${spring.cloud.gcp.bigquery.datasetName}")
    private String datasetName;

    @Value("${tableNameAllData}")
    private String tableNameAllData;

    @Value("${tableNameNonOptionalData}")
    private String tableNameNonOptionalData;

    @Override
    public void uploadData(List<MultipartFile> files) throws IOException, InterruptedException {

        BigQuery bigQuery = BigQueryOptions.getDefaultInstance().getService();
        Storage storage = StorageOptions.getDefaultInstance().getService();
        Bucket bucket = storage.get(bucketName);

        for (MultipartFile file : files) {
            String filename = file.getOriginalFilename();
            String uri = String.format("%s%s", bucketPath, filename);

            bucket.create(filename, file.getBytes());
            uploadDataToBigQuery(bigQuery, uri);
        }
    }

    private void uploadDataToBigQuery(BigQuery bigQuery, String uri) throws InterruptedException {

        LoadJobConfiguration jobConfigurationAllData = createLoadJobConfiguration(datasetName, tableNameAllData, uri);
        LoadJobConfiguration jobConfigurationNonOptionalData =
                createLoadJobConfiguration(datasetName, tableNameNonOptionalData, uri);

        bigQuery.create(JobInfo.of(jobConfigurationAllData)).waitFor();
        bigQuery.create(JobInfo.of(jobConfigurationNonOptionalData)).waitFor();
    }

    private LoadJobConfiguration createLoadJobConfiguration(String datasetName, String tableName, String uri) {

        TableId tableId = TableId.of(datasetName, tableName);
        return LoadJobConfiguration.newBuilder(tableId, uri)
                .setFormatOptions(FormatOptions.avro())
                .setIgnoreUnknownValues(true)
                .build();
    }

}
