package com.game.service;

import com.game.entity.Player;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

//1. получать список всех зарегистрированных игроков;
//2. создавать нового игрока;
//3. редактировать характеристики существующего игрока;
//4. удалять игрока;
//5. получать игрока по id;
//6. получать отфильтрованный список игроков в соответствии с переданными фильтрами;
//7. получать количество игроков, которые соответствуют фильтрам.


public interface PlayerService {
    Page<Player> getAll(Specification<Player> specification, Pageable pageable);

    Long countBy(Specification<Player> specification);

    Player add(Player player);

    Player update(Long id, Player player);

    void deleteById(Long id);

    Player getById(Long id);

}
