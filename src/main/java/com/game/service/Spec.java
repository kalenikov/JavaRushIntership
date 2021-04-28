package com.game.service;

import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import org.springframework.data.jpa.domain.Specification;

import java.util.Date;

public class Spec {
    public static Specification<Player> byName(String param) {
        return (player, cq, cb) -> param == null ? null : cb.like(player.get("name"), "%" + param + "%");
    }

    public static Specification<Player> byTitle(String param) {
        return (player, cq, cb) -> param == null ? null : cb.like(player.get("title"), "%" + param + "%");
    }

    public static Specification<Player> byRace(Race param) {
        return (player, cq, cb) -> param == null ? null : cb.equal(player.get("race"), param);
    }

    public static Specification<Player> byProfession(Profession param) {
        return (player, cq, cb) -> param == null ? null : cb.equal(player.get("profession"), param);
    }

    public static Specification<Player> byAfter(Long param) {
        return (player, cq, cb) -> param == null ? null : cb.greaterThanOrEqualTo(player.get("birthday"), new Date(param));
    }

    public static Specification<Player> byBefore(Long param) {
        return (player, cq, cb) -> param == null ? null : cb.lessThanOrEqualTo(player.get("birthday"), new Date(param));
    }


    public static Specification<Player> byMinLevel(Integer param) {
        return (player, cq, cb) -> param == null ? null : cb.greaterThanOrEqualTo(player.get("level"), param);
    }

    public static Specification<Player> byMaxLevel(Integer param) {
        return (player, cq, cb) -> param == null ? null : cb.lessThanOrEqualTo(player.get("level"), param);
    }

    public static Specification<Player> byBanned(Boolean banned) {
        return (player, cq, cb) -> banned == null ? null : banned ? cb.isTrue(player.get("banned")) : cb.isFalse(player.get("banned"));
    }

    public static Specification<Player> byMinExp(Integer param) {
        return (player, cq, cb) -> param == null ? null : cb.greaterThanOrEqualTo(player.get("experience"), param);
    }

    public static Specification<Player> byMaxExp(Integer param) {
        return (player, cq, cb) -> param == null ? null : cb.lessThanOrEqualTo(player.get("experience"), param);
    }
}

