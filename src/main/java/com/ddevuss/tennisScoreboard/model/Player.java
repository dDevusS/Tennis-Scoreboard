package com.ddevuss.tennisScoreboard.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "PLAYERS")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder(builderMethodName = "of")
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "NAME")
    @NotNull
    @NotBlank
    private String name;
}
