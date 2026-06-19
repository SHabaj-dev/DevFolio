package com.sbz.devfolio.core.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

object ResumeDownloader {
    fun downloadAndOpenResume(context: Context) {
        try {
            val assetManager = context.assets
            val fileName = "resume.pdf"
            val inputStream: InputStream = assetManager.open(fileName)
            
            // Create a shared_docs directory in cache
            val docsDir = File(context.cacheDir, "docs")
            if (!docsDir.exists()) {
                docsDir.mkdirs()
            }
            
            val outFile = File(docsDir, fileName)
            val outputStream = FileOutputStream(outFile)
            
            inputStream.copyTo(outputStream)
            
            inputStream.close()
            outputStream.flush()
            outputStream.close()
            
            // Get URI using FileProvider
            val uri: Uri = FileProvider.getUriForFile(
                context,
                "${context.packageName}.fileprovider",
                outFile
            )
            
            val intent = Intent(Intent.ACTION_VIEW)
            intent.setDataAndType(uri, "application/pdf")
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            
            context.startActivity(intent)
        } catch (e: Exception) {
            Log.e("ResumeDownloader", "Error opening resume", e)
        }
    }
}
