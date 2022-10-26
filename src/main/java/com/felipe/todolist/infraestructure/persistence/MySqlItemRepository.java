package com.felipe.todolist.infraestructure.persistence;

import com.felipe.todolist.domain.model.Item;
import com.felipe.todolist.domain.persistence.ItemRepository;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class MySqlItemRepository implements ItemRepository {

    private static final String SQl_FIND_ITEMS_BY_LIST_ID = "SELECT id, description, finished, created_at as createdAt FROM items where list_id = ?";
    private final JdbcTemplate jdbcTemplate;

    public MySqlItemRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Item> findItemsByToDoListId(Long id) {
        return jdbcTemplate.query(SQl_FIND_ITEMS_BY_LIST_ID, BeanPropertyRowMapper.newInstance(Item.class), id);
    }
}
