package me.larr4k.command

import me.larr4k.Main
import me.larr4k.utility.Messager
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player



class AddItem : CommandExecutor {
    override fun onCommand(sender: CommandSender, cmd: Command, label: String, args: Array<String>): Boolean {
        if (cmd.label.equals("additem")) {


            val player = sender as Player
            return if (args.size > 1) {

                false
            } else {
                val itemInHand = player.inventory.itemInMainHand
                if (itemInHand.type != Material.AIR) {
                    Main.dropManager.addItem(itemInHand)
                    player.sendMessage(Messager.itemAddedMessage)
                }
                Main.dropManager.saveDrops()
                true
            }
        }
        return true
    }
}