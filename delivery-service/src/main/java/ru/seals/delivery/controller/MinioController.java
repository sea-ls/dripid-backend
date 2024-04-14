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
    @Operation(description = "Загрузка новго изображения")
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

    @GetMapping(path = "/download/{fileName}")
    @Operation(description = "Получение изображения по его названию")
    public String uploadFile(@PathVariable String fileName) {
        return minioService.getImage(fileName);
    }

    @DeleteMapping("/delete")
    public void deleteFile(@RequestParam("bucketName") String bucketName,
                           @RequestParam("fileName") String fileName) {
        minioService.deleteImage(bucketName, fileName);
    }
}
