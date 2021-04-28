package com.game.controller;

import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import com.game.service.Filter;
import com.game.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rest")
public class PlayerController {

    private PlayerService playerService;

    @Autowired
    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @PostMapping("/players")
    public ResponseEntity<Player> add(@RequestBody Player player) {
        return ResponseEntity.ok(playerService.add(player));
    }

    @DeleteMapping("/players/{id}")
    public ResponseEntity<Player> delete(@PathVariable Long id) {
        playerService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/players/{id}")
    public ResponseEntity<Player> getBy(@PathVariable Long id) {
        return ResponseEntity.ok(playerService.getById(id));
    }

    @PostMapping("/players/{id}")
    public ResponseEntity<Player> update(@PathVariable Long id,
                                         @RequestBody Player player) {
        return ResponseEntity.ok(playerService.update(id, player));
    }


    @GetMapping("/players")
    public List<Player> getAll(@RequestParam String name,
                               @RequestParam String title,
                               @RequestParam Race race,
                               @RequestParam Profession profession,
                               @RequestParam Long after,
                               @RequestParam Long before,
                               @RequestParam Boolean banned,
                               @RequestParam Integer minExperience,
                               @RequestParam Integer maxExperience,
                               @RequestParam Integer minLevel,
                               @RequestParam(defaultValue = "id") PlayerOrder order,
                               @RequestParam(defaultValue = "0") Integer pageNumber,
                               @RequestParam(defaultValue = "3") Integer pageSize
    ) {
//        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(order.getFieldName()));
//        return playerService.getAll(
//                Specification.where(Filter.byTitle(title)
//                        .and(Filter.byTitle(title))), pageable);
        return null;
    }

    @GetMapping("/players/count")
    public long count(@RequestParam String name,
                      @RequestParam String title,
                      @RequestParam Race race,
                      @RequestParam Profession profession,
                      @RequestParam Long after,
                      @RequestParam Long before,
                      @RequestParam Boolean banned,
                      @RequestParam Integer minExperience,
                      @RequestParam Integer maxExperience,
                      @RequestParam Integer minLevel) {
//        return playerService.countBy();
        return 0;
    }


}
