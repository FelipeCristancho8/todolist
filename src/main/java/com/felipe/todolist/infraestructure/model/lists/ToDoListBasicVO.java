package com.felipe.todolist.infraestructure.model.lists;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ToDoListBasicVO {

    private String name;

    private String description;

}
