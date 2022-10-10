package me.larr4k.manager

import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import java.util.*
import kotlin.collections.ArrayList

class DropManager {
    private var drops: ArrayList<ItemStack?>? = ArrayList()
    private var isDefault = false
    fun getDrops(amount: Int): Array<ItemStack?> {
        var amount = amount
        val items = arrayOfNulls<ItemStack>(amount)
        val r = Random()
        if (drops == null || drops!!.size == 0) {
            loadDefaultDrops()
        }
        while (amount > 0) {
            --amount
            items[amount] = drops!![r.nextInt(drops!!.size)]
        }
        if (isDefault) {
            resetItems()
            isDefault = false
        }
        return items
    }

    fun addItem(item: ItemStack?) {
        drops!!.add(item)
    }

    private fun resetItems() {
        drops!!.clear()
        saveDrops()
    }

    private fun loadDefaultDrops() {
        val apple = ItemStack(Material.APPLE, 5)
        val appleMeta: ItemMeta = apple.itemMeta
        appleMeta.displayName = "§cЯблуко"
        appleMeta.lore = listOf("", "§eИспользуйте /airdrop additem to add", "§eваш предмет")
        apple.itemMeta = appleMeta
        val bread = ItemStack(Material.BREAD, 5)
        val breadMeta: ItemMeta = bread.itemMeta
        breadMeta.displayName = "§cХлеб"
        breadMeta.lore = listOf("", "§eИспользуйте /airdrop additem to add", "§eваш предмет")
        bread.itemMeta = breadMeta
        val arrow = ItemStack(Material.ARROW, 5)
        val arrowMeta: ItemMeta = arrow.itemMeta
        arrowMeta.displayName = "§cСтрелы"
        arrowMeta.lore = listOf("", "§eИспользуйте /airdrop additem to add", "§eваш предмет")
        arrow.itemMeta = arrowMeta
        drops!!.add(apple)
        drops!!.add(bread)
        drops!!.add(arrow)
        isDefault = true
    }

    fun saveDrops() {
        ConfigManager.getItems()!!["items"] = drops!!.toTypedArray()
        ConfigManager.saveFiles()
    }

    fun loadDrops() {
        val maybeDropsList = ConfigManager.getItems()!!.getList("items")
        val maybeDrops: ArrayList<ItemStack?> = ArrayList()
        if (maybeDropsList != null) {
            val it: Iterator<*> = maybeDropsList.iterator()
            while (it.hasNext()) {
                val o = it.next()!!
                val item = o as ItemStack
                maybeDrops.add(item)
            }
            drops = maybeDrops
        }
    }
}