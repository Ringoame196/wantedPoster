package com.github.Ringoame196

import org.bukkit.OfflinePlayer

class TargetPlayer {
    fun convertingUuid(targetPlayer: OfflinePlayer): String {
        return targetPlayer.uniqueId.toString()
    }
}
