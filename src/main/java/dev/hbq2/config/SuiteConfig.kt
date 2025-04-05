package dev.hbq2.config

import dev.hbq2.data.DataPosition
import dev.hbq2.data.DataType
import me.shedaniel.autoconfig.ConfigData
import me.shedaniel.autoconfig.annotation.Config
import me.shedaniel.autoconfig.annotation.ConfigEntry
import me.shedaniel.autoconfig.annotation.ConfigEntry.ColorPicker

@Config(name = "suiteconfig")
class SuiteConfig : ConfigData {
    interface ConfigType {
        var visible: Boolean
        var position: DataPosition
        var textColor: Int

        var dataType: DataType
    }

    class CoordinatesConfig : ConfigType {
        override var visible: Boolean = true
        override var position: DataPosition = DataPosition.TopLeft

        @ColorPicker
        override var textColor: Int = 16776960

        @ConfigEntry.Gui.Excluded
        override var dataType: DataType = DataType.Coordinates
    }

    class GameTimeConfig : ConfigType {
        override var visible: Boolean = true
        override var position: DataPosition = DataPosition.TopLeft

        @ColorPicker
        override var textColor: Int = 16776960

        @ConfigEntry.Gui.Excluded
        override var dataType: DataType = DataType.GameTime
    }

    class ActualTimeConfig : ConfigType {
        override var visible: Boolean = true
        override var position: DataPosition = DataPosition.TopLeft

        @ColorPicker
        override var textColor: Int = 16776960

        @ConfigEntry.Gui.Excluded
        override var dataType: DataType = DataType.ActualTime
    }

    class BiomeConfig : ConfigType {
        override var visible: Boolean = true
        override var position: DataPosition = DataPosition.TopLeft

        @ColorPicker
        override var textColor: Int = 16776960

        @ConfigEntry.Gui.Excluded
        override var dataType: DataType = DataType.Biome
    }

    class MusicConfig : ConfigType {
        override var visible: Boolean = true
        override var position: DataPosition = DataPosition.TopLeft

        @ColorPicker
        override var textColor: Int = 16776960

        @ConfigEntry.Gui.Excluded
        override var dataType: DataType = DataType.Music
    }

    class PlayersConfig : ConfigType {
        override var visible: Boolean = true
        override var position: DataPosition = DataPosition.TopLeft

        @ColorPicker
        override var textColor: Int = 16776960

        @ConfigEntry.Gui.Excluded
        override var dataType: DataType = DataType.Players
    }

    class LatencyConfig : ConfigType {
        override var visible: Boolean = true
        override var position: DataPosition = DataPosition.TopLeft

        @ColorPicker
        override var textColor: Int = 16776960

        @ConfigEntry.Gui.Excluded
        override var dataType: DataType = DataType.Latency
    }

    class FPSConfig : ConfigType {
        override var visible: Boolean = true
        override var position: DataPosition = DataPosition.TopLeft

        @ColorPicker
        override var textColor: Int = 16776960

        @ConfigEntry.Gui.Excluded
        override var dataType: DataType = DataType.FPS
    }

    @ConfigEntry.Gui.CollapsibleObject(startExpanded = true)
    var coordinates: CoordinatesConfig = CoordinatesConfig()

    @ConfigEntry.Gui.CollapsibleObject(startExpanded = true)
    var gameTime: GameTimeConfig = GameTimeConfig()

    @ConfigEntry.Gui.CollapsibleObject(startExpanded = true)
    var actualTime: ActualTimeConfig = ActualTimeConfig()

    @ConfigEntry.Gui.CollapsibleObject(startExpanded = true)
    var biome: BiomeConfig = BiomeConfig()

    @ConfigEntry.Gui.CollapsibleObject(startExpanded = true)
    var music: MusicConfig = MusicConfig()

    @ConfigEntry.Gui.CollapsibleObject(startExpanded = true)
    var players: PlayersConfig = PlayersConfig()

    @ConfigEntry.Gui.CollapsibleObject(startExpanded = true)
    var latency: LatencyConfig = LatencyConfig()

    @ConfigEntry.Gui.CollapsibleObject(startExpanded = true)
    var fps: FPSConfig = FPSConfig()
}
