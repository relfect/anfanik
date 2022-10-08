package me.larr4k.utility

import net.minecraft.server.v1_12_R1.Entity
import net.minecraft.server.v1_12_R1.EntityLiving
import net.minecraft.server.v1_12_R1.NBTTagCompound
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftEntity

object EntityUtils {
    fun setEntityNBT(e: Entity, tag: NBTTagCompound?) {
        val nmsEntity: Entity = (e as CraftEntity).handle
        nmsEntity.c(tag)
        (nmsEntity as EntityLiving).a(tag)
    }
}