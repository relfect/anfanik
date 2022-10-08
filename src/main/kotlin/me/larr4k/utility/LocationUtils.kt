package me.larr4k.utility

import org.bukkit.Location

object LocationUtils {
    fun offset(original: Location, offx: Double, offy: Double, offz: Double): Location {
        val newX = original.x + offx
        var newY = original.y + offy
        val newZ = original.z + offz
        if (newY > 255.0) {
            newY = 255.0
        } else if (newY < 0.0) {
            newY = 0.0
        }
        return Location(original.world, newX, newY, newZ)
    }
}