package com.felipe.todolist.infraestructure.persistence;

import com.felipe.todolist.domain.model.ToDoList;
import com.felipe.todolist.domain.persistence.ListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Objects;


public class MySqlListRepository implements ListRepository {

    private static final String SQL_CREAR_TAREA = "INSERT INTO todo_list (name, description, user) values (?,?,?)";
    @Autowired
    private final JdbcTemplate jdbcTemplate;

    public MySqlListRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public ToDoList save(ToDoList toDoList) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(SQL_CREAR_TAREA, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, toDoList.getName());
            ps.setString(2, toDoList.getDescription());
            ps.setString(3, toDoList.getUser());
            return ps;
        }, keyHolder);
        return getSavedTodoList(toDoList, Objects.requireNonNull(keyHolder.getKey()).longValue());
    }

    private ToDoList getSavedTodoList(ToDoList toDoList, Long key){
        ToDoList toDoListSaved = new ToDoList();
        toDoListSaved.setId(key);
        toDoListSaved.setName(toDoList.getName());
        toDoListSaved.setDescription(toDoList.getDescription());
        toDoListSaved.setUser(toDoList.getUser());
        return toDoListSaved;
    }
}
