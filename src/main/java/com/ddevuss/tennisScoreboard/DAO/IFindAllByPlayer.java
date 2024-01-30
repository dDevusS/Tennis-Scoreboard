package com.ddevuss.tennisScoreboard.DAO;

import com.ddevuss.tennisScoreboard.model.Match;
import com.ddevuss.tennisScoreboard.model.Player;

import java.util.List;

public interface IFindAllByPlayer {

    List<Match> findAllByPlayer(Player player);
}
