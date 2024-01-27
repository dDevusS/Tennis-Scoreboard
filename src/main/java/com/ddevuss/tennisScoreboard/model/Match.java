package com.ddevuss.tennisScoreboard.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "MATCHES")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder(builderMethodName = "of")
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "PLAYER1")
    private Player player1;

    @ManyToOne
    @JoinColumn(name = "PLAYER2")
    private Player player2;

    @ManyToOne
    @JoinColumn(name = "WINNER")
    private Player winner;

}
