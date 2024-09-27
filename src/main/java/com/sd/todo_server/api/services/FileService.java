package com.sd.todo_server.api.services;

import com.sd.todo_server.api.entities.File;
import com.sd.todo_server.api.repositories.dynamo.FileRepository;
import com.sd.todo_server.api.repositories.jpa.TaskRepository;
import com.sd.todo_server.api.schemas.FileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class FileService {
    private final FileRepository fileRepository;
    private final TaskRepository taskRepository;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    @Value("${file.upload-dir}")
    private String uploadDir;

    public File addFile(MultipartFile file, String taskId) throws IOException {
        String fileName = file.getOriginalFilename();
        Path filePath = Paths.get(uploadDir, fileName);

        // Crear el directorio si no existe
        if (!Files.exists(filePath.getParent())) {
            Files.createDirectories(filePath.getParent());
        }

        // Guardar el archivo en la carpeta, sobrescribiendo si ya existe
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        // Guardar la referencia del archivo en la BD
        File newFile = new File();
        newFile.setId(UUID.randomUUID().toString());
        newFile.setFileName(fileName);
        newFile.setPath(filePath.toString());
        newFile.setAddedAt(LocalDateTime.now().format(FORMATTER));

        newFile.setTaskId(getTaskIdByFileId(taskId));

        return fileRepository.save(newFile);
    }

    public List<FileResponse> getFiles() {
        Iterable<File> filesIterable = fileRepository.findAll();
        List<File> files = StreamSupport.stream(filesIterable.spliterator(), false)
                .collect(Collectors.toList());
        return files.stream().map(this::mapToFileResponse).toList();
    }

    public List<FileResponse> getFilesByTaskId(String taskId) {
        Iterable<File> files = fileRepository.findByTaskId(taskId);
        return StreamSupport.stream(files.spliterator(), false)
                .map(this::mapToFileResponse)
                .toList();
    }

    private FileResponse mapToFileResponse(File file) {
        return FileResponse.builder()
                .id(file.getId())
                .fileName(file.getFileName())
                .path(file.getPath())
                .addedAt(file.getAddedAt())
                .build();
    }

    private String getTaskIdByFileId(String taskId) {
        return taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"))
                .getId();
    }
}