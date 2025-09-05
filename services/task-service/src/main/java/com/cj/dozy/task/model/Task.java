package com.cj.dozy.task.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "task")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = true)
    private String description;

    @Column(nullable = false)
    private String date;

    @Column(nullable = true)
    private String category;

    @Column(nullable = true)
    private String color;

    @Column(nullable = false)
    private String status;
}
