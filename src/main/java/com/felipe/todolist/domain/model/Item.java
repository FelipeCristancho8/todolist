package com.felipe.todolist.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Item implements Serializable {

    private Long id;
    private String description;
    private boolean finished;
    private LocalDateTime createdAt;
}
