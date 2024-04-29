package ru.seals.delivery.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.seals.delivery.service.MinioService;

@RestController
@RequestMapping("api/delivery-service/minio")
@RequiredArgsConstructor
@CrossOrigin
public class MinioController {
    @Autowired
    private MinioService minioService;

    @PostMapping("/upload")
    @Operation(description = "Загрузка нового изображения")
    public void uploadFile(
            @RequestPart("file") MultipartFile file,
            @RequestPart("bucketName") String bucketName,
            @RequestPart("fileName") String fileName) {
        minioService.saveImage(file, bucketName, fileName);
    }

    @GetMapping("/get")
    @Operation(description = "Получение изображения")
    public String getFile(@RequestParam("bucketName") String bucketName,
                          @RequestParam("fileName") String fileName) {
        return minioService.getImage(bucketName, fileName);
    }

    @DeleteMapping("/delete")
    @Operation(description = "Удаление изображения")
    public void deleteFile(@RequestParam("bucketName") String bucketName,
                           @RequestParam("fileName") String fileName) {
        minioService.deleteImage(bucketName, fileName);
    }
}
