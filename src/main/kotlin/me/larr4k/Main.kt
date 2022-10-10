package me.larr4k

import me.larr4k.command.AddItem
import me.larr4k.handler.BlockInteractionListener
import me.larr4k.handler.ItemDropListener
import me.larr4k.manager.ConfigManager
import me.larr4k.manager.DropManager
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

class Main : JavaPlugin() {

     val dropManager: DropManager
    companion object {
        lateinit var dropManager: DropManager
        private var internalInstance: Main? = null

        @JvmStatic
        val instance: Main
            get() = internalInstance ?: throw IllegalStateException("tu clown?")




    }

    init {
        internalInstance = this
        dropManager = DropManager()
    }

    override fun onEnable() {
        server.pluginManager.registerEvents(BlockInteractionListener(),this)
        server.pluginManager.registerEvents(ItemDropListener(),this)
        Bukkit.getPluginCommand(AddItem().toString())
        ConfigManager.loadFiles()
        dropManager.loadDrops()

    }


    override fun onDisable() {

    }



}