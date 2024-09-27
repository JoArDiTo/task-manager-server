package com.sd.todo_server.api.schemas;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FileResponse {
    private String id;
    private String fileName;
    private String path;
    private String addedAt;
}
