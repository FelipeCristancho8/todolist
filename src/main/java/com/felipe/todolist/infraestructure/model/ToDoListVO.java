package com.felipe.todolist.infraestructure.model;

import com.felipe.todolist.domain.model.Item;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ToDoListVO {

    private Long id;
    private String name;
    private String description;
    private String user;
    @Builder.Default
    private List<Item> items = new ArrayList<>();
}
