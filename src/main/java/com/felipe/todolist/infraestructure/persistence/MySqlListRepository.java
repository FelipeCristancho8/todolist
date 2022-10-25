package com.felipe.todolist.infraestructure.persistence;

import com.felipe.todolist.domain.model.ToDoList;
import com.felipe.todolist.domain.persistence.ListRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Objects;


public class MySqlListRepository implements ListRepository {

    private static final String SQL_CRETE_LIST = "INSERT INTO todo_list (name, description, user) values (?,?,?)";
    private static final String SQL_DELETE_LIST = "DELETE FROM todo_list WHERE id = ?";
    private static final String SQL_EXISTS_BY_ID = "SELECT COUNT(1) FROM todo_list WHERE id = ?";
    private static final String SQL_UPDATE_BY_ID = "UPDATE todo_list SET name = ?, description = ? WHERE id = ?";
    private static final String SQL_FIND_BY_ID = "SELECT id, name, description, user FROM todo_list WHERE id = ?";

    private final JdbcTemplate jdbcTemplate;

    public MySqlListRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public ToDoList save(ToDoList toDoList) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(SQL_CRETE_LIST, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, toDoList.getName());
            ps.setString(2, toDoList.getDescription());
            ps.setString(3, toDoList.getUser());
            return ps;
        }, keyHolder);
        return getSavedTodoList(toDoList, Objects.requireNonNull(keyHolder.getKey()).longValue());
    }

    @Override
    public void delete(Long id) {
        this.jdbcTemplate.update(SQL_DELETE_LIST, id);
    }

    @Override
    public boolean existsById(Long id) {
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(SQL_EXISTS_BY_ID, Boolean.class, id));
    }

    @Override
    public ToDoList update(ToDoList toDoList) {
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(SQL_UPDATE_BY_ID, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, toDoList.getName());
            ps.setString(2, toDoList.getDescription());
            ps.setLong(3, toDoList.getId());
            return ps;
        });
        return toDoList;
    }

    @Override
    public ToDoList findById(Long id) {
        //se debe manejar la excepcion aqui?
        try{
            return jdbcTemplate.queryForObject(SQL_FIND_BY_ID, BeanPropertyRowMapper.newInstance(ToDoList.class), id);
        }catch (EmptyResultDataAccessException e){
            return null;
        }
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
