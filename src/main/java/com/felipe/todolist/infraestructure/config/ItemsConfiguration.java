package com.felipe.todolist.infraestructure.config;

import com.felipe.todolist.domain.items.ItemMediator;
import com.felipe.todolist.domain.items.ItemMediatorDefault;
import com.felipe.todolist.domain.items.ItemValidator;
import com.felipe.todolist.domain.lists.ListMediator;
import com.felipe.todolist.domain.persistence.ItemRepository;
import com.felipe.todolist.infraestructure.persistence.ItemRepositoryMySql;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class ItemsConfiguration {

    @Bean
    public ItemRepository providesItemRepositoryInstance(JdbcTemplate jdbcTemplate){
        return new ItemRepositoryMySql(jdbcTemplate);
    }

    @Bean
    public ItemValidator providesItemValidatorInstance(){
        return new ItemValidator();
    }

    @Bean
    public ItemMediator providesItemMediatorInstance(ItemRepository itemRepository, ItemValidator itemValidator, ListMediator listMediator){
        return new ItemMediatorDefault(itemRepository, itemValidator, listMediator);
    }
}
