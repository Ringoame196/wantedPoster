package com.github.Ringoame196

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.OfflinePlayer
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.MapMeta
import org.bukkit.map.MapView
import org.bukkit.plugin.java.JavaPlugin
import java.awt.image.BufferedImage

class Map {
    fun make(player: Player, targetPlayer: OfflinePlayer, plugin: JavaPlugin, reward: String): ItemStack {
        val map = ItemStack(Material.FILLED_MAP)
        val meta = map.itemMeta as MapMeta
        val image = Image().make(plugin, targetPlayer, reward)
        val mapView = makeMapView(player, image, plugin)
        meta.mapView = mapView
        meta.setDisplayName("${ChatColor.YELLOW}指名手配書")
        meta.lore = mutableListOf("※サーバーが再起動すると画像は自動でリセットされます")
        map.setItemMeta(meta)
        return map
    }
    private fun makeMapView(player: Player, image: BufferedImage?, plugin: JavaPlugin): MapView {
        val mapView = Bukkit.createMap(player.world)
        val mapRenderer = ImageMapRenderer(plugin, image)
        mapView.addRenderer(mapRenderer)
        return mapView
    }
}
