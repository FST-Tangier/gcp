package com.rinftech.gcp.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface GcpService {

    void uploadData(List<MultipartFile> files) throws IOException, InterruptedException;
}
