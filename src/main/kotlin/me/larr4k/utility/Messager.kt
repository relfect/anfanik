package me.larr4k.utility

import me.larr4k.manager.ConfigManager
import org.bukkit.Location

object Messager {
    fun getSignalDropMessage(loc: Location): String {
        return "§7[§eСокровища§7]§e Сокровище упадет сдесь §6" + loc.blockX + "§e, §6" + loc.blockY + "§e, §6" + loc.blockZ + "§e in §6" + (ConfigManager.getConfig()
            ?.getInt("options.time__after_signal")) + " секунд"
    }

    val itemResetMessage: String
        get() = "§7[§eСокровища§7]§e изменено!"
    val itemAddedMessage: String
        get() = "§7[§eСокровища§7]§e предмет добавлен"

    fun packageCalledMessage(): String {
        return "§7[§eСокровища§7]§e Исчезнул."
    }

}