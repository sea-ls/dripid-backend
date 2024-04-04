package ru.seals.delivery.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.seals.delivery.service.MinioService;

@RestController
@RequestMapping("api/v1/minio")
@RequiredArgsConstructor
public class MinioController {
    @Autowired
    private MinioService minioService;

    @PostMapping("/upload")
    public void uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("fileName") String fileName) {
        minioService.saveImage(file, fileName);
    }

    @GetMapping(path = "/get/{fileName}")
    public String uploadFile(@PathVariable String fileName) {
        return minioService.getImage(fileName);
    }
}
