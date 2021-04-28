package com.game.service;

import com.game.entity.Player;
import com.game.exceptions.BadRequestException;
import com.game.exceptions.NotFoundException;
import com.game.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Service
public class PlayerServiceImpl implements PlayerService {

    @Autowired
    private PlayerRepository repo;

    @Override
    public Page<Player> getAll(Specification<Player> specification, Pageable pageable) {
        return repo.findAll(specification, pageable);
    }

    @Override
    public Long countBy(Specification<Player> specification) {
        return repo.count(specification);
    }


    //    Мы не можем создать игрока, если:
    //    - указаны не все параметры из Data Params (кроме banned);
    //    - длина значения параметра “name” или “title” превышает
    //    размер соответствующего поля в БД (12 и 30 символов);
    //    - значение параметра “name” пустая строка;
    //    - опыт находится вне заданных пределов;
    //    - “birthday”:[Long] < 0;
    //    - дата регистрации находятся вне заданных пределов.
    //    В случае всего вышеперечисленного необходимо ответить
    //    ошибкой с кодом 400

    @Override
    public Player add(Player player) {
        if (player.getName() == null
                || player.getTitle() == null
                || player.getBirthday() == null
                || player.getRace() == null
                || player.getProfession() == null
                || player.getExperience() == null
                || player.getName().length() > 12
                || player.getTitle().length() > 30
                || player.getName().isEmpty()
                || player.getExperience() < 0
                || player.getExperience() > 10_000_000) {
            throw new BadRequestException("incorrect params");
        }

        if (!(player.getBirthday().after(new Date(946587600000L))
                && player.getBirthday().before(new Date(32503582800000L)))) {
            throw new BadRequestException("incorrect date params");
        }

        int level = (int) (Math.sqrt(2500 + 200 * player.getExperience()) - 50) / 100;
        int untilNextLevel = 50 * (level + 1) * (level + 2) - player.getExperience();
        player.setLevel(level);
        player.setUntilNextLevel(untilNextLevel);
        if (player.getBanned() == null) {
            player.setBanned(false);
        }
        return repo.saveAndFlush(player);
    }

    @Override
    public Player update(Long id, Player newPlayer) {
        Player updatedPlayer = getById(id);
        if (newPlayer.getName() != null) {
            if (updatedPlayer.getName().isEmpty() || updatedPlayer.getName().length() > 12) {
                throw new BadRequestException("");
            }
            updatedPlayer.setName(newPlayer.getName());
        }

        if (newPlayer.getTitle() != null) {
            if (newPlayer.getTitle().isEmpty() || newPlayer.getTitle().length() > 30) {
                throw new BadRequestException("");
            }
            updatedPlayer.setTitle(newPlayer.getTitle());
        }

        if (newPlayer.getRace() != null) {
            updatedPlayer.setRace(newPlayer.getRace());
        }
        if (newPlayer.getProfession() != null) {
            updatedPlayer.setProfession(newPlayer.getProfession());
        }
        if (newPlayer.getBirthday() != null) {
            if (!(newPlayer.getBirthday().after(new Date(946587600000L))
                    && newPlayer.getBirthday().before(new Date(32503582800000L)))) {
                throw new BadRequestException("incorrect date params");
            }
            updatedPlayer.setBirthday(newPlayer.getBirthday());
        }
        if (newPlayer.getBanned() != null) {
            updatedPlayer.setBanned(newPlayer.getBanned());
        }

        if (newPlayer.getExperience() != null) {
            if (newPlayer.getExperience() < 0 || newPlayer.getExperience() > 10_000_000) {
                throw new BadRequestException("");
            }
            updatedPlayer.setExperience(newPlayer.getExperience());
            int level = (int) (Math.sqrt(2500 + 200 * newPlayer.getExperience()) - 50) / 100;
            int untilNextLevel = 50 * (level + 1) * (level + 2) - newPlayer.getExperience();
            updatedPlayer.setLevel(level);
            updatedPlayer.setUntilNextLevel(untilNextLevel);

        }
        return repo.saveAndFlush(updatedPlayer);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        if (id == null || id <= 0) {
            throw new BadRequestException("incorrect id");
        }
        if (!repo.existsById(id)) {
            throw new NotFoundException(id + " not found");
        }
        repo.deleteById(id);
    }

    @Override
    public Player getById(Long id) {
        if (id == null || id <= 0) {
            throw new BadRequestException("incorrect id");
        }
        if (!repo.existsById(id)) {
            throw new NotFoundException(id + " not found");
        }
        Optional<Player> player = repo.findById(id);
        if (!player.isPresent()) {
            throw new NotFoundException("not found");
        }
        return player.get();
    }
}
