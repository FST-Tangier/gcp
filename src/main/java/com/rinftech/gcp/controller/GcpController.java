package com.rinftech.gcp.controller;

import com.rinftech.gcp.service.GcpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@RestController
public class GcpController {

    private GcpService gcpService;

    @Autowired
    public void setGcpService(GcpService gcpService) {
        this.gcpService = gcpService;
    }

    @PostMapping("/upload")
    public String post(@RequestParam("file") List<MultipartFile> files) {

        try {
            gcpService.uploadData(files);
        } catch (IOException | InterruptedException e) {
            return e.getMessage();
        }
        return "success";
    }

}
