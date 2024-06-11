package com.example.connectmusic.data

import android.content.Context
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

/**
 * Kontrola, ci databaza existuje.
 * Ak neexistuje, tak ju skopiruje z [assets]
 */
object DatabaseInitializer {
    fun initialize(context: Context) {
        val dbFile = context.getDatabasePath("CM_database")
        if (!dbFile.exists()) {
            try {
                copyDatabase(context)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    @Throws(IOException::class)
    private fun copyDatabase(context: Context) {
        val inputStream: InputStream = context.assets.open("database/CM_database.db")
        val outFileName = context.getDatabasePath("CM_database").absolutePath
        val outputStream: OutputStream = FileOutputStream(outFileName)

        val buffer = ByteArray(1024)
        var length: Int
        while (inputStream.read(buffer).also { length = it } > 0) {
            outputStream.write(buffer, 0, length)
        }

        outputStream.flush()
        outputStream.close()
        inputStream.close()
    }
}
