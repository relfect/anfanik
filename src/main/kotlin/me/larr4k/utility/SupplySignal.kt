package me.larr4k.utility

import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack

object SupplySignal {
    val item: ItemStack
        get() {
            val item = ItemStack(Material.FIREWORK, 1)
            val data = item.itemMeta
            data.displayName = "§r§cСигнал"
            val lore: ArrayList<String?> = ArrayList()
            lore.add("§7Чтобы открыть")
            data.lore = lore
            data.addEnchant(Enchantment.DURABILITY, 1, true)
            data.addItemFlags(*arrayOf(ItemFlag.HIDE_ENCHANTS))
            item.itemMeta = data
            return item
        }
}