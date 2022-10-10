package me.larr4k.manager

import me.larr4k.Main
import org.bukkit.configuration.InvalidConfigurationException
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import java.io.*

object ConfigManager {
    private var configFile: File? = null
    private var config: FileConfiguration? = null
    private var itemsFile: File? = null
    private var items: FileConfiguration? = null
     fun loadFiles() {
        try {
            configFile = File(Main.instance.dataFolder, "config.yml")
            itemsFile = File(Main.instance.dataFolder, "items.yml")
            if (!configFile!!.exists()) {
                configFile!!.parentFile.mkdirs()
                copy(Main.instance.getResource("config.yml"), configFile)
            }
            if (!itemsFile!!.exists()) {
                itemsFile!!.parentFile.mkdirs()
                copy(Main.instance.getResource("items.yml"), itemsFile)
            }
            config = YamlConfiguration()
            items = YamlConfiguration()
            (config as YamlConfiguration).load(configFile)
            (items as YamlConfiguration).load(itemsFile)
        } catch (inval: InvalidConfigurationException) {
            inval.printStackTrace()
        } catch (exception: IOException) {
            exception.printStackTrace()
        }
        val curVersion = config!!.getInt("CONFIG_VERSION")
        if (curVersion != 2 && curVersion > 0) {
            updateConfig()
        }
    }

    fun saveFiles() {
        try {
            items!!.save(itemsFile)
        } catch (ex: IOException) {
            ex.printStackTrace()
        }
    }

    fun updateConfig() {
        val time_after_signal = config!!.getInt("options.time__after_signal")
        val items_per_drop = config!!.getInt("options.items_per_drop")
        val fireworks_on_fall = config!!.getBoolean("options.fireworks_on_fall")
        val announce_drop = config!!.getBoolean("options.announce_drop")
        configFile!!.delete()
        loadFiles()
        config!!["options.time_after_signal"] = time_after_signal
        config!!["options.items_per_drop"] = items_per_drop
        config!!["options.fireworks_on_fall"] = fireworks_on_fall
        config!!["options.announce_drop"] = announce_drop
    }

    private fun copy(`in`: InputStream, file: File?) {
        try {
            val out: OutputStream = file?.let { FileOutputStream(it) }!!
            val buf = ByteArray(1024)
            var len: Int
            while (`in`.read(buf).also { len = it } > 0) {
                out.write(buf, 0, len)
            }
            out.close()
            `in`.close()
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
    }

    fun getConfig(): FileConfiguration? {
        if (config == null) {
            loadFiles()
        }
        return config
    }

    fun getItems(): FileConfiguration? {
        if (items == null) {
            loadFiles()
        }
        return items
    }
}