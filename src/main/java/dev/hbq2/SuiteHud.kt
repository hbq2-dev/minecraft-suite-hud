package dev.hbq2

import dev.hbq2.config.SuiteConfig
import me.shedaniel.autoconfig.AutoConfig
import me.shedaniel.autoconfig.annotation.Config
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer
import net.fabricmc.api.ModInitializer
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class SuiteHud : ModInitializer {
    override fun onInitialize() {
        LOGGER.info("S.U.I.T.E. HUD loaded!")

        AutoConfig.register(
            SuiteConfig::class.java,
        ) { definition: Config?, configClass: Class<SuiteConfig?>? ->
            GsonConfigSerializer(
                definition,
                configClass,
            )
        }
    }

    companion object {
        private const val MOD_ID: String = "suiteHUD"
        val LOGGER: Logger = LoggerFactory.getLogger(MOD_ID)
    }
}
