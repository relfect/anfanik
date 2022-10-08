package me.larr4k.handler

import me.larr4k.Main
import me.larr4k.manager.ConfigManager
import me.larr4k.utility.FallingPackageEntity
import me.larr4k.utility.LocationUtils
import org.bukkit.*
import org.bukkit.entity.EntityType
import org.bukkit.entity.Firework

internal class SignalHandler(private var signalLoc: Location) {
    private var world: World = signalLoc.world
    private var counter = 0

    init {
        summonSignalFirework(LocationUtils.offset(signalLoc, 0.0, 10.0, 0.0))
        updateSignal()
    }

   private fun updateSignal() {
        Main.instance.server.scheduler.runTaskLater(Main.instance, {
            if (counter < ConfigManager.getConfig()!!.getInt("options.time__after_signal")) {
                if (counter % 2 == 0) {
                    summonSignalFirework(LocationUtils.offset(signalLoc, 0.0, 10.0, 0.0))
                }
                val single = this@SignalHandler
                single.counter = single.counter + 1
                updateSignal()
            } else {
                FallingPackageEntity(LocationUtils.offset(signalLoc, 0.0, 100.0, 0.0), Material.NOTE_BLOCK)
            }
        }, 20L)
    }

    private fun summonSignalFirework(loc: Location) {
        val fw = world.spawnEntity(loc, EntityType.FIREWORK) as Firework
        val fwm = fw.fireworkMeta
        fwm.addEffect(
            FireworkEffect.builder().with(FireworkEffect.Type.BALL_LARGE).withColor(Color.RED).withFade(Color.WHITE)
                .withFlicker().withTrail().build()
        )
        fwm.power = 2
        fw.fireworkMeta = fwm
    }
}