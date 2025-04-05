package dev.hbq2.config

import com.terraformersmc.modmenu.api.ConfigScreenFactory
import com.terraformersmc.modmenu.api.ModMenuApi
import me.shedaniel.autoconfig.AutoConfig
import net.minecraft.client.gui.screen.Screen

class SuiteConfigMenuIntegration : ModMenuApi {
    override fun getModConfigScreenFactory(): ConfigScreenFactory<*> =
        ConfigScreenFactory { parent: Screen? ->
            AutoConfig
                .getConfigScreen(
                    SuiteConfig::class.java,
                    parent,
                ).get()
        }
}
