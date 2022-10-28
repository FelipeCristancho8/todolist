package com.felipe.todolist.infraestructure.persistence;

import com.felipe.todolist.domain.model.Item;
import com.felipe.todolist.domain.persistence.ItemRepository;
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
    public void deleteById(Long id) {

    }

    @Override
    public boolean existsById() {
        return false;
    }

    @Override
    public Item update(Item item) {
        return null;
    }

    @Override
    public Item findById(Long id) {
        return null;
    }

    private Item getSavedItem(Item item, Long key){
        return Item.builder().id(key)
                .name(item.getName())
                .finished(false)
                .createdAt(LocalDateTime.now())
                .build();
    }
}
