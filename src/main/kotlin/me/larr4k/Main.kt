package me.larr4k

import org.bukkit.plugin.java.JavaPlugin

class Main : JavaPlugin() {


    companion object {
        private var internalInstance: Main? = null

        @JvmStatic
        val instance: Main
            get() = internalInstance ?: throw IllegalStateException("tu clown?")




    }

    init {
        internalInstance = this
    }

    override fun onEnable() {

    }


    override fun onDisable() {

    }



}