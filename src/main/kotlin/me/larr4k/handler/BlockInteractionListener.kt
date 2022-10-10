package me.larr4k.handler

import me.larr4k.Main
import me.larr4k.manager.ConfigManager
import me.larr4k.utility.LocationUtils
import me.larr4k.utility.Messager
import org.bukkit.*
import org.bukkit.entity.EntityType
import org.bukkit.entity.Firework
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.metadata.MetadataValue

class BlockInteractionListener : Listener {
    @EventHandler
    fun onPlayerInteract(event: PlayerInteractEvent) {
        if (event.action == Action.RIGHT_CLICK_BLOCK || event.action == Action.LEFT_CLICK_BLOCK && event.clickedBlock.hasMetadata(
                "isPackage"
            ) && (event.clickedBlock.getMetadata("isPackage")[0] as MetadataValue).asBoolean()
        ) {
            val clickedBlock = event.clickedBlock
            clickedBlock.type = Material.AIR
            clickedBlock.removeMetadata("isPackage", Main.instance)
            summonBreakFirework(clickedBlock.location)
            event.player.playSound(event.player.location, Sound.ENTITY_IRONGOLEM_STEP, 1.0f, 1.0f)
            var itemStack: Array<ItemStack?>
            val dropMan: Int = Main.dropManager.getDrops(ConfigManager.getConfig()?.getInt("options.items_per_drop")!!)
                .also { itemStack = it }.size
            for (on in 0 until dropMan) {
                val item = itemStack[on]
                clickedBlock.world.dropItem(clickedBlock.location, item)
            }
        }
        if (event.action == Action.RIGHT_CLICK_BLOCK && event.player.inventory.itemInMainHand.type == Material.FIREWORK && event.player.inventory.itemInMainHand.itemMeta.displayName === "§r§cSupply Signal") {
            ItemDropListener.handleSignal(event.player.location)
            event.player.inventory.itemInMainHand.type = Material.AIR
            Bukkit.getServer().broadcastMessage(Messager.getSignalDropMessage(event.player.location))
        }
    }

    private fun summonBreakFirework(loc: Location) {
        val fw = loc.world.spawnEntity(LocationUtils.offset(loc, 0.0, 1.0, 0.0), EntityType.FIREWORK) as Firework
        val fwm = fw.fireworkMeta
        fwm.addEffect(
            FireworkEffect.builder().with(FireworkEffect.Type.BURST).withColor(Color.YELLOW).withColor(Color.ORANGE)
                .withFlicker().build()
        )
        fw.fireworkMeta = fwm
        Main.instance.server.scheduler.runTaskLater(Main.instance, { fw.detonate() }, 2L)
    }
}