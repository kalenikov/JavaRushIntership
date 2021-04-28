package com.game.service;

import com.game.entity.Player;

import java.util.Date;

public class Utils {
    public static boolean validateDate(Player player) {
        return player.getBirthday().after(new Date(946587600000L))
                && player.getBirthday().before(new Date(32503582800000L));
    }

        public static int calcNextLevel(Player newPlayer, int level) {
        return 50 * (level + 1) * (level + 2) - newPlayer.getExperience();
    }

    public static int calcLevel(Player newPlayer) {
        return (int) (Math.sqrt(2500 + 200 * newPlayer.getExperience()) - 50) / 100;
    }

}
