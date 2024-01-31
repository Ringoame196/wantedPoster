package com.github.Ringoame196.Commands

import com.github.Ringoame196.Map
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

class WantedPoster(private val plugin: JavaPlugin) : CommandExecutor, TabExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (args.size != 2) { return false }
        val targetPlayer = Bukkit.getOfflinePlayer(args[0])
        val reward = args[1]
        if (sender !is Player) { return true }
        val generatedItem = Map().make(sender, targetPlayer, plugin, reward)
        sender.inventory.addItem(generatedItem)
        return true
    }

    override fun onTabComplete(sender: CommandSender, command: Command, label: String, args: Array<out String>): MutableList<String>? {
        return when (args.size) {
            1 -> (Bukkit.getOnlinePlayers().map { it.name } + "[プレイヤー名]").toMutableList()
            2 -> mutableListOf("[懸賞金]")
            else -> mutableListOf()
        }
    }
}
