package com.github.Ringoame196

import org.bukkit.OfflinePlayer
import org.bukkit.plugin.java.JavaPlugin
import java.awt.Color
import java.awt.Font
import java.awt.Image
import java.awt.image.BufferedImage
import java.io.File
import java.net.URL
import javax.imageio.ImageIO

class Image {
    fun make(plugin: JavaPlugin, targetPlayer: OfflinePlayer, reward: String): BufferedImage? {
        return processing(plugin, targetPlayer, reward)
    }
    private fun acquisitionTargetPlayerHead(playerUUID: String): BufferedImage? {
        val url = "https://api.mineatar.io/face/$playerUUID?scale=32"
        return ImageIO.read(URL(url))
    }
    private fun processing(plugin: JavaPlugin, targetPlayer: OfflinePlayer, reward: String): BufferedImage? {
        val baseImageFile = File(plugin.dataFolder, "wanted.png")
        val baseImage = ImageIO.read(baseImageFile)
        val targetPlayerUUID = TargetPlayer().convertingUuid(targetPlayer)
        val playerHead = acquisitionTargetPlayerHead(targetPlayerUUID)
        val smallImage = resizeImage(playerHead ?: return null, 50, 50)
        val imageCoalescence = imageCoalescence(baseImage, smallImage)
        val imageWithText = turnOnCharacterImage(imageCoalescence, "$$reward")
        return imageWithText
    }
    private fun resizeImage(originalImage: Image, targetWidth: Int, targetHeight: Int): BufferedImage {
        val resizedImage = BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_ARGB)
        val g = resizedImage.createGraphics()
        g.drawImage(originalImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH), 0, 0, null)
        g.dispose()
        return resizedImage
    }

    private fun imageCoalescence(baseImage: BufferedImage, resizedOverlay: BufferedImage): BufferedImage {
        // ImageMapRendererのサイズを取得
        val baseWidth = baseImage.width
        val baseHeight = baseImage.height

        // 合成された画像を作成
        val combinedImage = BufferedImage(baseWidth, baseHeight, BufferedImage.TYPE_INT_ARGB)

        // グラフィックスオブジェクトを取得し、ベースの画像に描画
        val g = combinedImage.createGraphics()
        g.drawImage(baseImage, 0, 0, null)

        // オーバーレイ画像をベース画像の中央に描画
        val x = (baseWidth - resizedOverlay.width) / 2
        val y = (baseHeight - resizedOverlay.height) / 2
        g.drawImage(resizedOverlay, x, y, null)

        // 描画を終了
        g.dispose()
        return combinedImage
    }
    private fun turnOnCharacterImage(image: BufferedImage, reward: String): BufferedImage {
        val g = image.createGraphics()
        val fontSize = 30
        g.color = Color.RED
        g.font = Font("Arial", Font.PLAIN, fontSize)

        // FontMetrics を取得
        val fontMetrics = g.fontMetrics

        // 文字列の幅と高さを取得
        val textWidth = fontMetrics.stringWidth(reward)

        // 画像の中央に配置
        val yDifference = 55
        val x = (image.width - textWidth) / 2
        val y = (image.height / 2) + yDifference

        g.drawString(reward, x, y)
        g.dispose()

        return image
    }
}
