package com.ddevuss.tennisScoreboard.DAO;

import com.ddevuss.tennisScoreboard.model.Match;

import java.util.List;

public interface IFindAllByPlayerName {

    List<Match> findAllByPlayerName(String playerName);
}
