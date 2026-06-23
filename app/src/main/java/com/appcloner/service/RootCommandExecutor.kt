package com.appcloner.service

import com.appcloner.utils.Logger
import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.InputStreamReader
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RootCommandExecutor @Inject constructor() {

    fun isRootAvailable(): Boolean {
        return try {
            val process = Runtime.getRuntime().exec("su -c echo test")
            val reader = BufferedReader(InputStreamReader(process.inputStream))
            val result = reader.readLine()
            process.waitFor()
            result == "test"
        } catch (e: Exception) {
            false
        }
    }

    fun execute(command: String): Boolean {
        return try {
            val process = Runtime.getRuntime().exec("su")
            val os = DataOutputStream(process.outputStream)
            os.writeBytes("$command\n")
            os.writeBytes("exit\n")
            os.flush()
            process.waitFor() == 0
        } catch (e: Exception) {
            Logger.e("RootCommandExecutor", "Command failed: $command", e)
            false
        }
    }

    fun executeChmod(path: String, permissions: String): Boolean =
        execute("chmod $permissions $path")

    fun executeChown(path: String, owner: String, group: String): Boolean =
        execute("chown $owner:$group $path")

    fun bindMount(source: String, target: String): Boolean =
        execute("mount --bind $source $target")

    fun hideProcess(pid: Int): Boolean =
        execute("echo 0 > /proc/$pid/oom_score_adj")
}
