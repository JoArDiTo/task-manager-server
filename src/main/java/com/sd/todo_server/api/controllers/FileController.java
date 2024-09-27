package com.sd.todo_server.api.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.sd.todo_server.api.entities.File;
import com.sd.todo_server.api.schemas.FileRequest;
import com.sd.todo_server.api.schemas.FileResponse;
import com.sd.todo_server.api.services.FileService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/files")
@RequiredArgsConstructor
public class FileController {
    private final FileService fileService;

    @PostMapping("/{taskId}")
    @ResponseStatus(HttpStatus.CREATED)
    public FileResponse uploadFile(@RequestParam("file") MultipartFile file, @PathVariable String taskId) throws IOException {
        File newFile = fileService.addFile(file, taskId);
        FileResponse fileResponse = new FileResponse();
        fileResponse.setId(newFile.getId());
        fileResponse.setFileName(newFile.getFileName());
        fileResponse.setPath(newFile.getPath());
        fileResponse.setAddedAt(newFile.getAddedAt());
        return fileResponse;
    }

    @GetMapping("/{task_id}")
    @ResponseStatus(HttpStatus.OK)
    public List<FileResponse> getFilesByTaskId(@PathVariable String task_id) {
        return fileService.getFilesByTaskId(task_id);
    }

    @GetMapping("/prueba")
    @ResponseStatus(HttpStatus.OK)
    public List<FileResponse> getFiles() {
        return fileService.getFiles();
    }
}
