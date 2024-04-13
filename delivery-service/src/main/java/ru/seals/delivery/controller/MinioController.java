package ru.seals.delivery.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.seals.delivery.service.MinioService;

@RestController
@RequestMapping("api/delivery-service/minio")
@RequiredArgsConstructor
public class MinioController {
    @Autowired
    private MinioService minioService;

    @PostMapping("/upload")
    public void uploadFile(
            @RequestPart("file") MultipartFile file,
            @RequestPart("bucketName") String bucketName,
            @RequestPart("fileName") String fileName) {
        minioService.saveImage(file, bucketName, fileName);
    }

    @GetMapping("/get")
    public String getFile(@RequestParam("bucketName") String bucketName,
                          @RequestParam("fileName") String fileName) {
        return minioService.getImage(bucketName, fileName);
    }

    @DeleteMapping("/delete") // test
    public void deleteFile(@RequestParam("bucketName") String bucketName,
                           @RequestParam("fileName") String fileName) {
        minioService.deleteImage(bucketName, fileName);
    }
}
