package com.felipe.todolist.infraestructure.config;

import com.felipe.todolist.domain.lists.ListMediator;
import com.felipe.todolist.domain.lists.ListMediatorDefault;
import com.felipe.todolist.domain.lists.ListValidator;
import com.felipe.todolist.domain.persistence.ItemRepository;
import com.felipe.todolist.domain.persistence.ListRepository;
import com.felipe.todolist.infraestructure.persistence.ListRepositoryMySql;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class ListsConfiguration {

    @Bean
    public ListRepository providesListRepositoryInstance(JdbcTemplate jdbcTemplate){
        return new ListRepositoryMySql(jdbcTemplate);
    }

    @Bean
    public ListValidator providesListValidatorInstance(){
        return new ListValidator();
    }

    @Bean
    public ListMediator providesListMediatorInstance(ListRepository listRepository, ItemRepository itemRepository,ListValidator listValidator){
        return new ListMediatorDefault(listRepository, itemRepository, listValidator);
    }
}
