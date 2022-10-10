package me.larr4k.utility

import me.larr4k.Main

import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.Particle
import org.bukkit.World
import org.bukkit.metadata.FixedMetadataValue

class LandedPackageEntity(private var loc: Location, m: Material) : PackageEntity() {
    private var world: World = loc.world
    private var material: Material

    init {
        material = m
        summon()
    }

    override fun summon() {
        loc.world.getBlockAt(loc).type = material
        world.getBlockAt(loc).setMetadata("isPackage", FixedMetadataValue(Main.instance, true))
        tick()
    }

    private fun tick() {
        if (loc.world.getBlockAt(loc).type == material) {
            ++counter
            loc.world.spawnParticle(Particle.SPELL_WITCH, loc, 1, 0.1, 0.1, 0.1, 0.1)
            retick()
        }
    }

    override fun remove() {}
}