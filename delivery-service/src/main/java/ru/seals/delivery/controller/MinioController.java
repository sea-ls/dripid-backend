package ru.seals.delivery.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.seals.delivery.service.MinioService;

@RestController
@RequestMapping("api/v1/minio")
@RequiredArgsConstructor
@CrossOrigin
public class MinioController {
    @Autowired
    private MinioService minioService;

    @PostMapping("/upload")
    @Operation(description = "Загрузка новго изображения")
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        return minioService.saveImage(file);
    }

    @GetMapping(path = "/download/{fileName}")
    @Operation(description = "Получение изображения по его названию")
    public String uploadFile(@PathVariable String fileName) {
        return minioService.getImage(fileName);
    }
}
