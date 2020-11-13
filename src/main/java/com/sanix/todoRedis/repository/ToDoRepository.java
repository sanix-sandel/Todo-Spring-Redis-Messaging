package com.sanix.todoRedis.repository;


import com.sanix.todoRedis.domain.ToDo;
import org.springframework.data.repository.CrudRepository;

public interface ToDoRepository extends CrudRepository<ToDo, String> {
}