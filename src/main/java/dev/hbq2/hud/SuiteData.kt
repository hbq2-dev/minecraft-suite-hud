package dev.hbq2.hud

import dev.hbq2.hud.SuiteLoader.getMusicTitle
import dev.hbq2.utils.Formatter
import dev.hbq2.utils.Formatter.capitalizeWords
import dev.hbq2.utils.Formatter.getInGameDay
import dev.hbq2.utils.Formatter.getInGameHour
import dev.hbq2.utils.Formatter.getInGameMinute
import net.minecraft.client.MinecraftClient
import net.minecraft.text.MutableText
import net.minecraft.text.Text
import net.minecraft.util.Identifier
import net.minecraft.world.World
import java.time.LocalTime
import java.time.format.DateTimeFormatter

object SuiteData {
    var currentMusicIdentifier: Identifier? = null

    private val client: MinecraftClient = MinecraftClient.getInstance()
    private val fpsHistory: MutableList<Int> = ArrayList()

    private fun getFPS(): Int {
        val fps: Int = client.currentFps
        fpsHistory.add(fps)
        if (fpsHistory.size > 500) fpsHistory.removeAt(0)
        return fps
    }

    private fun getMinimumFPS(): Int {
        var max = fpsHistory[0]
        for (i in fpsHistory) if (i < max) max = i
        return max
    }

    private fun getMaximumFPS(): Int {
        var max = 0
        for (i in fpsHistory) if (i > max) max = i
        return max
    }

    private fun getAverageFPS(): Int {
        var sum = 0
        for (fps in fpsHistory) sum += fps
        return sum / fpsHistory.size
    }

    @JvmStatic
    val coordinates: Text
        get() {
            val x = client.player?.x?.toInt() ?: 0
            val y = client.player?.y?.toInt() ?: 0
            val z = client.player?.z?.toInt() ?: 0

            return when (client.world?.registryKey) {
                World.OVERWORLD -> {
                    Formatter.stringToText(
                        "XYZ: §l%d %d %d (§c%.0f %d %.0f§f)",
                        x,
                        y,
                        z,
                        x.toFloat() / 8,
                        y,
                        z.toFloat() / 8,
                    )
                }
                World.NETHER -> {
                    Formatter.stringToText(
                        "XYZ: §l%d %d %d (§a%d %d %d§f)",
                        x,
                        y,
                        z,
                        x * 8,
                        y,
                        z * 8,
                    )
                }
                World.END -> {
                    Formatter.stringToText("XYZ: §l%d %d %d", x, y, z)
                } else -> {
                    Formatter.stringToText("XYZ: §l%d %d %d", x, y, z)
                }
            }
        }

    @JvmStatic
    val gameTime: Text
        get() {
            val time = client.player?.world?.timeOfDay ?: 0

            val worldDay = time.getInGameDay()
            val hour: Long = time.getInGameHour()
            val minute: Long = time.getInGameMinute()

            return Formatter.stringToText("Game Time: §lDays - %d (%d:%02d)", worldDay, hour, minute)
        }

    @JvmStatic
    val biome: Text
        get() {
            val biome: String? =
                client.player
                    ?.world
                    ?.getBiome(client.player?.blockPos)
                    ?.key
                    ?.get()
                    ?.value
                    ?.toShortTranslationKey()
                    ?.replace("_", " ")

            return Formatter.stringToText("Biome: §l%s", biome?.capitalizeWords())
        }

    @JvmStatic
    val actualTime: Text
        get() {
            val now = LocalTime.now().format(DateTimeFormatter.ofPattern("hh:mm:ss a"))
            return Formatter.stringToText("Actual Time: §l%s", now)
        }

    @JvmStatic
    val currentMusic: Text
        get() {
            return Formatter.stringToText("Music: §l%s", getMusicTitle(currentMusicIdentifier))
        }

    @JvmStatic
    val pingTitle: Text
        get() {
            return Text.of("Latency:") as MutableText
        }

    @JvmStatic
    val latencies: List<String>
        get() {
            return client.networkHandler
                ?.playerList
                ?.map { "${it.profile.name}: ${it.latency}ms" }
                .orEmpty()
        }

    @JvmStatic
    val fps: Text
        get() =
            Formatter.stringToText(
                "FPS: §l%s, §rAvg: §l%s, §rMin: §l%s, §rMax: §l%s",
                getFPS(),
                getAverageFPS(),
                getMinimumFPS(),
                getMaximumFPS(),
            )

    @JvmStatic
    val gpu: Text
        get() =
            Formatter.stringToText(
                "GPU: §l%s",
                client.gpuUtilizationPercentage,
            )

    @JvmStatic
    val numberOfPlayers: Text
        get() {
            val num =
                client.player
                    ?.networkHandler
                    ?.playerList
                    ?.size

            return Formatter.stringToText("Players Online: §l%s", num)
        }
}
