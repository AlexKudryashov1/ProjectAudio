package com.example.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(name = "resource-service", url = "${resource.service.url}")
public interface ResourceServiceClient {

    @PostMapping(value = "/api/resources", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    String uploadResource(@RequestPart MultipartFile file);

    @DeleteMapping("/api/resources/{id}")
    void deleteResource(@PathVariable String id);
}
