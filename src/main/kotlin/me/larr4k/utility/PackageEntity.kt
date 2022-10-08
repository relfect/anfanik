package me.larr4k.utility

import me.larr4k.Main

open class PackageEntity {
    protected var counter = 0
    open fun summon() {}
    open fun remove() {}
    private fun tick() {}
    protected fun retick() {
        Main.instance.server.scheduler.runTaskLater(Main.instance, { tick() }, 1L)
    }
}