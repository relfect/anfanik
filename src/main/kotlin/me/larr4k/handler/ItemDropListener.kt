package me.larr4k.handler

import me.larr4k.Main
import me.larr4k.utility.Messager
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.ItemSpawnEvent

class ItemDropListener : Listener {
    @EventHandler
    fun onItemSpawn(event: ItemSpawnEvent) {
        if (event.entity.itemStack.type == Material.FIREWORK && event.entity.itemStack.itemMeta.lore[0] as String == "§7Заспавнился") {
            Main.instance.server.scheduler.runTaskLater(Main.instance, {
                Bukkit.getServer().broadcastMessage(Messager.getSignalDropMessage(event.location))
                handleSignal(event.entity.location)
                event.entity.remove()
            }, 20L)
        }
    }

    companion object {
        fun handleSignal(loc: Location?) {
            if (loc != null) {
                SignalHandler(loc)
            }
        }
    }
}