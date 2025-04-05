package dev.hbq2.hud

import dev.hbq2.config.SuiteConfig
import dev.hbq2.data.DataPosition
import dev.hbq2.data.DataType
import dev.hbq2.hud.SuiteData.actualTime
import dev.hbq2.hud.SuiteData.biome
import dev.hbq2.hud.SuiteData.coordinates
import dev.hbq2.hud.SuiteData.currentMusic
import dev.hbq2.hud.SuiteData.fps
import dev.hbq2.hud.SuiteData.gameTime
import dev.hbq2.hud.SuiteData.latencies
import dev.hbq2.hud.SuiteData.numberOfPlayers
import dev.hbq2.hud.SuiteData.pingTitle
import dev.hbq2.utils.Formatter.capitalizeWords
import me.shedaniel.autoconfig.AutoConfig
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.DrawContext
import net.minecraft.text.Text
import net.minecraft.util.Identifier
import java.util.concurrent.CompletableFuture

object SuiteLoader {
    private val client: MinecraftClient = MinecraftClient.getInstance()
    private val config = AutoConfig.getConfigHolder(SuiteConfig::class.java).config

    @JvmStatic
    fun drawAsync(context: DrawContext) {
        val hud =
            CompletableFuture.runAsync(
                { drawHud(context) },
                { task: Runnable -> MinecraftClient.getInstance().executeTask(task) },
            )
        CompletableFuture.allOf(hud)
    }

    private fun drawHud(context: DrawContext) {
        val textRenderer = client.textRenderer
        val textHeight = textRenderer.fontHeight

        val x = 5
        val y = 5

        val topLeft = getConfigsForPosition(DataPosition.TopLeft)

        topLeft.forEach { p ->
            context.drawText(
                textRenderer,
                getTextForConfigType(p),
                x,
                y + topLeft.indexOf(p) * (textHeight + 2),
                p.textColor,
                true,
            )
            if (p.dataType == DataType.Latency) {
                latencies.forEach {
                    context.drawText(
                        textRenderer,
                        it,
                        x,
                        y + (topLeft.indexOf(p) + latencies.indexOf(it) + 1) * (textHeight + 2),
                        p.textColor,
                        true,
                    )
                }
            }
        }

        val topCenter = getConfigsForPosition(DataPosition.TopCenter)

        topCenter.forEach { p ->
            context.drawText(
                textRenderer,
                getTextForConfigType(p),
                (context.scaledWindowWidth / 2) - (textRenderer.getWidth(getTextForConfigType(p)) / 2),
                y + topCenter.indexOf(p) * (textHeight + 2),
                p.textColor,
                true,
            )
            if (p.dataType == DataType.Latency) {
                latencies.forEach {
                    context.drawText(
                        textRenderer,
                        it,
                        (context.scaledWindowWidth / 2) - (textRenderer.getWidth(it) / 2),
                        y + (topCenter.indexOf(p) + latencies.indexOf(it) + 1) * (textHeight + 2),
                        p.textColor,
                        true,
                    )
                }
            }
        }

        val topRight = getConfigsForPosition(DataPosition.TopRight)

        topRight.forEach { p ->
            context.drawText(
                textRenderer,
                getTextForConfigType(p),
                (context.scaledWindowWidth - textRenderer.getWidth(getTextForConfigType(p)) - x),
                y + topRight.indexOf(p) * (textHeight + 2),
                p.textColor,
                true,
            )

            if (p.dataType == DataType.Latency) {
                latencies.forEach {
                    context.drawText(
                        textRenderer,
                        it,
                        (context.scaledWindowWidth - textRenderer.getWidth(it) - x),
                        y + (topRight.indexOf(p) + latencies.indexOf(it) + 1) * (textHeight + 2),
                        p.textColor,
                        true,
                    )
                }
            }
        }

        val bottomLeft = getConfigsForPosition(DataPosition.BottomLeft)
        var numberOfLines = bottomLeft.size
        if (bottomLeft.any { it.dataType == DataType.Latency }) {
            numberOfLines += latencies.size
        }

        // Extra padding of 40 from bottom
        val startingY = (context.scaledWindowHeight - ((numberOfLines) * (textHeight + 2))) - 40

        bottomLeft.forEach { p ->
            context.drawText(
                textRenderer,
                getTextForConfigType(p),
                x,
                startingY + bottomLeft.indexOf(p) * (textHeight + 2),
                p.textColor,
                true,
            )
            if (p.dataType == DataType.Latency) {
                latencies.forEach {
                    context.drawText(
                        textRenderer,
                        it,
                        x,
                        startingY + (bottomLeft.indexOf(p) + latencies.indexOf(it) + 1) * (textHeight + 2),
                        p.textColor,
                        true,
                    )
                }
            }
        }

        val bottomCenter = getConfigsForPosition(DataPosition.BottomCenter)
        bottomCenter.forEach { p ->
            context.drawText(
                textRenderer,
                getTextForConfigType(p),
                (context.scaledWindowWidth / 2) - (textRenderer.getWidth(getTextForConfigType(p)) / 2),
                startingY + bottomCenter.indexOf(p) * (textHeight + 2),
                p.textColor,
                true,
            )
            if (p.dataType == DataType.Latency) {
                latencies.forEach {
                    context.drawText(
                        textRenderer,
                        it,
                        (context.scaledWindowWidth / 2) - (textRenderer.getWidth(getTextForConfigType(p)) / 2),
                        startingY + (bottomCenter.indexOf(p) + latencies.indexOf(it) + 1) * (textHeight + 2),
                        p.textColor,
                        true,
                    )
                }
            }
        }

        val bottomRight = getConfigsForPosition(DataPosition.BottomRight)
        bottomRight.forEach { p ->
            context.drawText(
                textRenderer,
                getTextForConfigType(p),
                (context.scaledWindowWidth - textRenderer.getWidth(getTextForConfigType(p)) - x),
                startingY + bottomRight.indexOf(p) * (textHeight + 2),
                p.textColor,
                true,
            )
            if (p.dataType == DataType.Latency) {
                latencies.forEach {
                    context.drawText(
                        textRenderer,
                        it,
                        (context.scaledWindowWidth - textRenderer.getWidth(it) - x),
                        startingY + (bottomRight.indexOf(p) + latencies.indexOf(it) + 1) * (textHeight + 2),
                        p.textColor,
                        true,
                    )
                }
            }
        }
    }

    fun getMusicTitle(musicResourceLocation: Identifier?): String {
        if (musicResourceLocation == null) {
            return "Not Playing"
        }

        val musicPath = musicResourceLocation.path
        val split = musicPath.split("/".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val rawName = split[split.size - 1]
        val name = rawName.replace("_", " ").split(".").first()

        return name.capitalizeWords()
    }

    private fun getConfigsAndPositions(): List<Pair<SuiteConfig.ConfigType, DataPosition>> =
        listOf(
            Pair(config.coordinates, config.coordinates.position),
            Pair(config.gameTime, config.gameTime.position),
            Pair(config.actualTime, config.actualTime.position),
            Pair(config.biome, config.biome.position),
            Pair(config.music, config.music.position),
            Pair(config.fps, config.fps.position),
            Pair(config.players, config.players.position),
            Pair(config.latency, config.latency.position),
        )

    private fun getConfigsForPosition(dataPosition: DataPosition): List<SuiteConfig.ConfigType> =
        getConfigsAndPositions()
            .filter {
                it.second == dataPosition && it.first.visible
            }.map { it.first }

    private fun getTextForConfigType(configType: SuiteConfig.ConfigType): Text =
        when (configType.dataType) {
            DataType.Coordinates -> coordinates
            DataType.GameTime -> gameTime
            DataType.ActualTime -> actualTime
            DataType.Biome -> biome
            DataType.Music -> currentMusic
            DataType.FPS -> fps
            DataType.Players -> numberOfPlayers
            DataType.Latency -> pingTitle
        }
}
