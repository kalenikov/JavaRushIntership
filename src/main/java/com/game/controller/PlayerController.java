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

//    Get players list /rest/players
//    Get players count /rest/players/count
//    Get player /rest/players/{id}

//    Create player /rest/players
//    Update player /rest/players/{id}
//    Delete player /rest/players/{id}

    @PostMapping("/players")
    public ResponseEntity<Player> add(@RequestBody Player player) {
        return ResponseEntity.ok(playerService.add(player));
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


    //Если игрок не найден в БД, необходимо ответить ошибкой с
    //кодом 404.
    //Если значение id не валидное, необходимо ответить ошибкой
    //с кодом 400.
    @GetMapping("/players/{id}")
    public ResponseEntity<Player> getBy(@PathVariable @Validated int id) {
        return ResponseEntity.ok(playerService.getById(id));
    }


    //    Update player
    //Обновлять нужно только те поля, которые не null.
    //Если игрок не найден в БД, необходимо ответить ошибкой с
    //кодом 404.
    //Если значение id не валидное, необходимо ответить ошибкой
    //с кодом 400.
    @PostMapping("/players/{id}")
    public ResponseEntity<Player> update(@PathVariable int id,
                                         @RequestParam Optional<String> name,
                                         @RequestParam Optional<String> title,
                                         @RequestParam Optional<Race> race,
                                         @RequestParam Optional<Profession> profession,
                                         @RequestParam Optional<Long> birthday,
                                         @RequestParam Optional<Boolean> banned,
                                         @RequestParam Optional<Integer> experience) {

        Player player;
        try {
            player = playerService.getById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
        if (player == null) {
            return ResponseEntity.notFound().build();
        }
        playerService.add(player);
        return ResponseEntity.ok().build();

    }


    //Delete player
    //Если игрок не найден в БД, необходимо ответить ошибкой с
    //кодом 404.
    //Если значение id не валидное, необходимо ответить ошибкой
    //с кодом 400.

    @DeleteMapping("/players/{id}")
    public ResponseEntity<Player> delete(@PathVariable int id) {
//        playerService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
