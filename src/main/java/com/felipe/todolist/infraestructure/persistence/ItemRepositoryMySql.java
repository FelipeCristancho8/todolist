package com.felipe.todolist.infraestructure.persistence;

import com.felipe.todolist.domain.model.Item;
import com.felipe.todolist.domain.persistence.ItemRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class ItemRepositoryMySql implements ItemRepository {

    private static final String SQL_FIND_ITEMS_BY_LIST_ID = "SELECT id, name, finished, created_at as createdAt FROM items where list_id = ?";
    private static final String SQL_CREATE_ITEM = "INSERT INTO items (name, list_id) values (?, ?)";
    private static final String SQL_EXISTS_BY_ID = "SELECT COUNT(1) FROM items WHERE id = ?";
    private static final String SQL_FIND_BY_ID = "SELECT id, name, finished, created_at as createdAt FROM items where id = ?";
    private static final String SQL_UPDATE_ITEM = "UPDATE items SET name = ? WHERE id = ?";
    private static final String SQL_FINISH_ITEM = "UPDATE items SET finished = 1 WHERE id = ?";
    private static final int IS_FINISHED = 1;
    private final JdbcTemplate jdbcTemplate;

    public ItemRepositoryMySql(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Item> findItemsByToDoListId(Long id) {
        return jdbcTemplate.query(SQL_FIND_ITEMS_BY_LIST_ID, BeanPropertyRowMapper.newInstance(Item.class), id);
    }

    @Override
    public Item save(Item item, Long idList) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(SQL_CREATE_ITEM, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, item.getName());
            ps.setLong(2, idList);
            return ps;
        }, keyHolder);
        return getSavedItem(item, Objects.requireNonNull(keyHolder.getKey()).longValue());
    }

    @Override
    public boolean existsById(Long id) {
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(SQL_EXISTS_BY_ID, Boolean.class, id));
    }

    @Override
    public Item findById(Long id) {
        try{
            return jdbcTemplate.queryForObject(SQL_FIND_BY_ID, BeanPropertyRowMapper.newInstance(Item.class), id);
        }catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    @Override
    public Item update(Item item) {
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(SQL_UPDATE_ITEM, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, item.getName());
            ps.setLong(2, item.getId());
            return ps;
        });
        return item;
    }

    @Override
    public boolean finishItem(Long idItem) {
        int result = jdbcTemplate.update(SQL_FINISH_ITEM, idItem);
        return result == IS_FINISHED;
    }

    private Item getSavedItem(Item item, Long key){
        return Item.builder().id(key)
                .name(item.getName())
                .finished(false)
                .createdAt(LocalDateTime.now())
                .build();
    }
}
