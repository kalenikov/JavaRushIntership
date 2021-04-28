package com.game.controller;

import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import com.game.service.PlayerService;
import com.game.service.Spec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<List<Player>> getAll(@RequestParam(required = false) String name,
                                               @RequestParam(required = false) String title,
                                               @RequestParam(required = false) Race race,
                                               @RequestParam(required = false) Profession profession,
                                               @RequestParam(required = false) Long after,
                                               @RequestParam(required = false) Long before,
                                               @RequestParam(required = false) Boolean banned,
                                               @RequestParam(required = false) Integer minExperience,
                                               @RequestParam(required = false) Integer maxExperience,
                                               @RequestParam(required = false) Integer minLevel,
                                               @RequestParam(required = false) Integer maxLevel,
                                               @RequestParam(required = false, defaultValue = "ID") PlayerOrder order,
                                               @RequestParam(required = false, defaultValue = "0") Integer pageNumber,
                                               @RequestParam(required = false, defaultValue = "3") Integer pageSize
    ) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(order.getFieldName()));
        List<Player> rsl = playerService.getAll(
                Specification.where(
                        Spec.byName(name)
                                .and(Spec.byTitle(title))
                                .and(Spec.byRace(race))
                                .and(Spec.byProfession(profession))
                                .and(Spec.byAfter(after))
                                .and(Spec.byBefore(before))
                                .and(Spec.byBanned(banned))
                                .and(Spec.byMinExp(minExperience))
                                .and(Spec.byMaxExp(maxExperience))
                                .and(Spec.byMinLevel(minLevel))
                                .and(Spec.byMaxLevel(maxLevel))
                ), pageable).getContent();
        return ResponseEntity.ok(rsl);

    }

    @GetMapping("/players/count")
    public ResponseEntity<Long> count(@RequestParam(required = false) String name,
                      @RequestParam(required = false) String title,
                      @RequestParam(required = false) Race race,
                      @RequestParam(required = false) Profession profession,
                      @RequestParam(required = false) Long after,
                      @RequestParam(required = false) Long before,
                      @RequestParam(required = false) Boolean banned,
                      @RequestParam(required = false) Integer minExperience,
                      @RequestParam(required = false) Integer maxExperience,
                      @RequestParam(required = false) Integer minLevel,
                      @RequestParam(required = false) Integer maxLevel
    ) {
        Long rsl = playerService.countBy(
                Specification.where(
                        Spec.byName(name)
                                .and(Spec.byTitle(title))
                                .and(Spec.byRace(race))
                                .and(Spec.byProfession(profession))
                                .and(Spec.byAfter(after))
                                .and(Spec.byBefore(before))
                                .and(Spec.byBanned(banned))
                                .and(Spec.byMinExp(minExperience))
                                .and(Spec.byMaxExp(maxExperience))
                                .and(Spec.byMinLevel(minLevel))
                                .and(Spec.byMaxLevel(maxLevel))));
        return ResponseEntity.ok(rsl);
    }


}
