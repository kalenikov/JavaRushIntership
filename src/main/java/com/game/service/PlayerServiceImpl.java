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

import java.util.Optional;

import static com.game.service.Utils.*;

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
            throw new BadRequestException();
        }
        if (!validateDate(player)) {
            throw new BadRequestException();
        }
        if (player.getBanned() == null) {
            player.setBanned(false);
        }
        int level = calcLevel(player);
        int untilNextLevel = calcNextLevel(player, level);
        player.setLevel(level);
        player.setUntilNextLevel(untilNextLevel);
        return repo.saveAndFlush(player);
    }

    @Override
    public Player update(Long id, Player newPlayer) {
        Player updatedPlayer = getById(id);
        if (newPlayer.getName() != null) {
            if (updatedPlayer.getName().isEmpty() || updatedPlayer.getName().length() > 12) {
                throw new BadRequestException();
            }
            updatedPlayer.setName(newPlayer.getName());
        }

        if (newPlayer.getTitle() != null) {
            if (newPlayer.getTitle().isEmpty() || newPlayer.getTitle().length() > 30) {
                throw new BadRequestException();
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
            if (!(validateDate(newPlayer))) {
                throw new BadRequestException();
            }
            updatedPlayer.setBirthday(newPlayer.getBirthday());
        }
        if (newPlayer.getBanned() != null) {
            updatedPlayer.setBanned(newPlayer.getBanned());
        }

        if (newPlayer.getExperience() != null) {
            if (newPlayer.getExperience() < 0 || newPlayer.getExperience() > 10_000_000) {
                throw new BadRequestException();
            }
            updatedPlayer.setExperience(newPlayer.getExperience());
            int level = calcLevel(newPlayer);
            int untilNextLevel = calcNextLevel(newPlayer, level);
            updatedPlayer.setLevel(level);
            updatedPlayer.setUntilNextLevel(untilNextLevel);
        }
        return repo.saveAndFlush(updatedPlayer);
    }

    @Override
    public void deleteById(Long id) {
        validateId(id);
        repo.deleteById(id);
    }

    @Override
    public Player getById(Long id) {
        validateId(id);
        Optional<Player> player = repo.findById(id);
        if (!player.isPresent()) {
            throw new NotFoundException();
        }
        return player.get();
    }

    private void validateId(Long id) {
        if (id == null || id <= 0) {
            throw new BadRequestException();
        }
        if (!repo.existsById(id)) {
            throw new NotFoundException();
        }
    }
}
