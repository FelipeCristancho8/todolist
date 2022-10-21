package com.felipe.todolist.infraestructure.config;

import com.felipe.todolist.domain.lists.ListCreator;
import com.felipe.todolist.domain.lists.ListCreatorDefault;
import com.felipe.todolist.domain.persistence.ListRepository;
import com.felipe.todolist.infraestructure.persistence.MemoryListRepository;
import com.felipe.todolist.infraestructure.persistence.MySqlListRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ListsConfiguration {

    @Bean
    public ListRepository providesListRepositoryInstance(){
        return new MySqlListRepository();
    }

    @Bean
    public ListCreator providesListCreatorInstance(ListRepository listRepository){
        return new ListCreatorDefault(listRepository);
    }
}
