package com.sd.todo_server.api.repositories.dynamo;

import com.sd.todo_server.api.entities.File;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

@EnableScan
public interface FileRepository extends CrudRepository<File, String> {
    List<File> findByTaskId(String taskId);
}