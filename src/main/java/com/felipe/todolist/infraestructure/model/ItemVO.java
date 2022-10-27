package com.felipe.todolist.infraestructure.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemVO {

    private Long id;
    private String description;
    private boolean finished;
    private LocalDateTime createdAt;
}
