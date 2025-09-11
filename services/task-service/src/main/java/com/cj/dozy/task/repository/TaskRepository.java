package com.cj.dozy.task.repository;

import com.cj.dozy.task.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository

public interface TaskRepository extends JpaRepository<Task, Long> {

    Task findTaskById(Long id);

    @Query("SELECT t.id FROM Task t WHERE t.userId = :userId")
    List<Long> findTasksIdsByUserId(@Param("userId") Long userId);
}
