package com.wistron.todoapi.repository;

import com.wistron.todoapi.model.TodoItem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoItemRepository extends CrudRepository<TodoItem, Long> {
    List<TodoItem> findByName(String name);
}
