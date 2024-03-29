package com.ddevuss.tennisScoreboard.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "Players", indexes = @Index(columnList = "Name", name = "ind_name", unique = true))
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderMethodName = "of")
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "Name")
    @NotBlank
    @NotNull
    private String name;
}
