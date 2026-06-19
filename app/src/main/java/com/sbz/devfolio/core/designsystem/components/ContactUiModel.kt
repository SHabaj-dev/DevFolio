package com.sbz.devfolio.core.designsystem.components

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Message
import androidx.compose.material.icons.rounded.Call
import androidx.compose.material.icons.rounded.Code
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Person
import androidx.compose.ui.graphics.vector.ImageVector

enum class ContactOption(
    val title: String,
    val subtitle: String,
    val icon: ImageVector
) {
    EMAIL("Email Me", "Professional inquiries and opportunities", Icons.Rounded.Email),
    LINKEDIN("Connect on LinkedIn", "Professional networking", Icons.Rounded.Person),
    GITHUB("View GitHub", "Projects and contributions", Icons.Rounded.Code),
    WHATSAPP("Chat on WhatsApp", "Quick communication", Icons.AutoMirrored.Rounded.Message),
    PHONE("Call Me", "Direct contact", Icons.Rounded.Call)
}

object ContactAction {

    fun sendEmail(context: Context) {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:") // only email apps should handle this
            putExtra(Intent.EXTRA_EMAIL, arrayOf("shabajansari843@gmail.com"))
            putExtra(Intent.EXTRA_SUBJECT, "Android Developer Portfolio Inquiry")
            putExtra(
                Intent.EXTRA_TEXT,
                "Hello Shabaj,\n\nI came across your portfolio application and would like to connect regarding:\n\n[ ]\n\nLooking forward to hearing from you.\n\nRegards,"
            )
        }
        try {
            context.startActivity(Intent.createChooser(intent, "Send Email"))
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(context, "No email client installed.", Toast.LENGTH_SHORT).show()
        }
    }

    fun openLinkedIn(context: Context) {
        val linkedInId = "shabaj-ansari-696426202/"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("linkedin://profile/$linkedInId"))
        try {
            context.startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com/in/$linkedInId"))
            context.startActivity(browserIntent)
        }
    }

    fun openGitHub(context: Context) {
        val githubId = "SHabaj-dev"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/$githubId"))
        context.startActivity(intent)
    }

    fun openWhatsApp(context: Context) {
        val phoneNumber = "917238877335" 
        val message = "Hello Shabaj,\n\nI found your portfolio application and would like to connect."
        val uri = Uri.parse("https://api.whatsapp.com/send?phone=$phoneNumber&text=${Uri.encode(message)}")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        try {
            context.startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(context, "WhatsApp is not installed.", Toast.LENGTH_SHORT).show()
        }
    }

    fun dialPhone(context: Context) {
        val phoneNumber = "+917238877335"
        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:$phoneNumber")
        }
        context.startActivity(intent)
    }
}
