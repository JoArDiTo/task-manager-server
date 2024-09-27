package com.sd.todo_server.api.repositories.jpa;

import com.sd.todo_server.api.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, String> {

}
