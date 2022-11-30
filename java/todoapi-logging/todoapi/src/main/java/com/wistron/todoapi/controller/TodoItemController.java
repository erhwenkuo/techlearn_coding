package com.wistron.todoapi.controller;

import com.wistron.todoapi.model.TodoItem;
import com.wistron.todoapi.repository.TodoItemRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static net.logstash.logback.argument.StructuredArguments.keyValue;

@Tag(name="TodoItems")
@RestController
public class TodoItemController {
    private static final Logger logger = LoggerFactory.getLogger(TodoItemController.class);

    @Autowired
    private TodoItemRepository todoItemRepository;

    public TodoItemController() {
    }

    /**
     * HTTP GET
     */
    @RequestMapping(value = "/api/TodoItems/{id}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> getTodoItem(@PathVariable("id") Long id) {
        try {
            Optional<TodoItem> todoItemFromRepo = todoItemRepository.findById(id);
            if (todoItemFromRepo.isPresent()) {
                return new ResponseEntity<TodoItem>(todoItemFromRepo.get(), HttpStatus.OK);
            }
            logger.warn("{} not found", keyValue("id", id));
            return new ResponseEntity<>(id + " not found", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseEntity<String>(id + " not found", HttpStatus.NOT_FOUND);
        }
    }

    /**
     * HTTP GET ALL
     */
    @RequestMapping(value = "/api/TodoItems", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> getAllTodoItems() {
        try {
            List<TodoItem> todoItems = new ArrayList<>();
            Iterable<TodoItem> iterable = todoItemRepository.findAll();
            if (iterable != null) {
                iterable.forEach(todoItems::add);
            }
            return new ResponseEntity<>(todoItems, HttpStatus.OK);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>("Nothing found", HttpStatus.NOT_FOUND);
        }
    }

    /**
     * HTTP POST NEW ONE
     */
    @RequestMapping(value = "/api/TodoItems", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addNewTodoItem(@RequestBody TodoItem todoItem) {
        try {
            todoItemRepository.save(todoItem);
            return new ResponseEntity<>("Entity created", HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>("Entity creation failed", HttpStatus.CONFLICT);
        }
    }

    /**
     * HTTP PUT DELETE
     */
    @RequestMapping(value = "/api/TodoItems", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateTodoItem(@RequestBody TodoItem todoItem) {
        try {
            Optional<TodoItem> todoItemFromRepo = todoItemRepository.findById(todoItem.getId());
            if (todoItemFromRepo.isPresent()) {
                TodoItem existItem = todoItemFromRepo.get();
                existItem.setName(todoItem.getName());
                existItem.setComplete(todoItem.isComplete());
                todoItemRepository.save(existItem);
                return new ResponseEntity<>("Entity updated", HttpStatus.OK);
            }
            logger.warn("Not found the entity {}", keyValue("id", todoItem.getId()));
            return new ResponseEntity<>("Not found the entity", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>("Entity updating failed", HttpStatus.NOT_FOUND);
        }
    }

    /**
     * HTTP DELETE
     */
    @RequestMapping(value = "/api/TodoItems/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteTodoItem(@PathVariable("id") Long id) {
        try {
            Optional<TodoItem> todoItem = todoItemRepository.findById(id);
            if (todoItem.isPresent()) {
                todoItemRepository.deleteById(id);
                return new ResponseEntity<>("Entity deleted", HttpStatus.OK);
            }
            logger.warn("Not found the entity {}", keyValue("id", id));
            return new ResponseEntity<>("Not found the entity", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>("Entity deletion failed", HttpStatus.NOT_FOUND);
        }
    }
}
