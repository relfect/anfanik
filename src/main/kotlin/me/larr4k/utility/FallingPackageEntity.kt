package me.larr4k.utility

import me.larr4k.Main
import me.larr4k.manager.ConfigManager
import me.larr4k.utility.LocationUtils.offset
import org.bukkit.*
import org.bukkit.entity.EntityType
import org.bukkit.entity.FallingBlock
import org.bukkit.entity.Firework
import java.util.*

class FallingPackageEntity(loc: Location, m: Material) : PackageEntity() {
    var world: World
    var startLoc: Location
    var material: Material
    private var blocky: FallingBlock? = null

    init {
        startLoc = applyOffset(loc)
        world = loc.world
        material = m
        summon()
    }

    override fun summon() {
        blocky = world.spawnFallingBlock(startLoc, material, 0.toByte())
        summonSpawnFireworks()
        tick()
    }

    private fun tick() {

        if (world.getBlockAt(offset(blocky!!.location, 0.0, -1.0, 0.0)).type == Material.AIR) {
            ++counter
            world.spawnParticle(Particle.SMOKE_NORMAL, blocky!!.location, 50, 0.1, 0.1, 0.1, 0.1)
            if (blocky!!.isDead) {
                val oldLoc = blocky!!.location
                val oldVelocity = blocky!!.velocity
                blocky = world.spawnFallingBlock(oldLoc, material, 0.toByte())

            }
            if (counter % 5 == 0) {
                summonUpdateFireworks()
            }
            retick()
        } else {
            this.remove()
        }
    }

    override fun remove() {
        blocky!!.remove()

    }

    private fun summonUpdateFireworks() {
        if (ConfigManager.getConfig()?.getBoolean("options.fireworks_on_fall") == true) {
            val fw = world.spawnEntity(blocky!!.location, EntityType.FIREWORK) as Firework
            val fwm = fw.fireworkMeta
            fwm.addEffect(
                FireworkEffect.builder().with(FireworkEffect.Type.BALL).withColor(Color.RED).withColor(Color.WHITE)
                    .build()
            )
            fw.fireworkMeta = fwm
            Main.instance.server.scheduler.runTaskLater(Main.instance, { fw.detonate() }, 1L)
        }
    }

    private fun summonSpawnFireworks() {
        if (ConfigManager.getConfig()?.getBoolean("options.fireworks_on_fall") == true) {
            val fw = world.spawnEntity(blocky!!.location, EntityType.FIREWORK) as Firework
            val fwm = fw.fireworkMeta
            fwm.addEffect(
                FireworkEffect.builder().with(FireworkEffect.Type.BALL_LARGE).withColor(Color.RED).withColor(
                    Color.WHITE
                ).build()
            )
            fw.fireworkMeta = fwm
            Main.instance.server.scheduler.runTaskLater(Main.instance, { fw.detonate() }, 1L)
        }
    }

    private fun applyOffset(loc: Location): Location {
        val bounds: Int = ConfigManager.getConfig()?.getInt("options.drop_location_offset")!!
        return if (bounds < 1) {
            loc
        } else {
            val r = Random()
            val zOff = r.nextInt(bounds * 2) + 1 - bounds
            val xOff = r.nextInt(bounds * 2) + 1 - bounds
            offset(loc, xOff.toDouble(), 0.0, zOff.toDouble())
        }
    }
}