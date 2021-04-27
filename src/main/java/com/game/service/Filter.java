package com.game.service;

import com.game.entity.Player;
import org.springframework.data.jpa.domain.Specification;

public class Filter {
    public static Specification<Player> byTitle(String title) {
        return ((root, query, cb) -> title == null ? null : cb.like(root.get("title"), "%" + title + "%"));
    }
}
