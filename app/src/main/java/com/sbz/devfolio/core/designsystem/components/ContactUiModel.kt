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

    fun sendEmail(context: Context, email: String) {
        // Extract plain email if it's markdown format like [email](mailto:email)
        val plainEmail = if (email.startsWith("[")) email.substringAfter("mailto:").substringBefore(")") else email
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:") // only email apps should handle this
            putExtra(Intent.EXTRA_EMAIL, arrayOf(plainEmail))
            putExtra(Intent.EXTRA_SUBJECT, "Android Developer Portfolio Inquiry")
            putExtra(
                Intent.EXTRA_TEXT,
                "Hello,\n\nI came across your portfolio application and would like to connect regarding:\n\n[ ]\n\nLooking forward to hearing from you.\n\nRegards,"
            )
        }
        try {
            context.startActivity(Intent.createChooser(intent, "Send Email"))
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(context, "No email client installed.", Toast.LENGTH_SHORT).show()
        }
    }

    fun openLinkedIn(context: Context, linkedInUrl: String) {
        if (linkedInUrl.isBlank()) {
            Toast.makeText(context, "LinkedIn profile not available.", Toast.LENGTH_SHORT).show()
            return
        }
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(linkedInUrl))
        try {
            context.startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(context, "Could not open LinkedIn.", Toast.LENGTH_SHORT).show()
        }
    }

    fun openGitHub(context: Context, githubUrl: String) {
        if (githubUrl.isBlank()) {
            Toast.makeText(context, "GitHub profile not available.", Toast.LENGTH_SHORT).show()
            return
        }
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(githubUrl))
        try {
            context.startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(context, "Could not open GitHub.", Toast.LENGTH_SHORT).show()
        }
    }

    fun openWhatsApp(context: Context, phone: String) {
        if (phone.isBlank()) return
        // clean up phone number to remove + or spaces
        val cleanPhone = phone.replace("+", "").replace(" ", "")
        val message = "Hello,\n\nI found your portfolio application and would like to connect."
        val uri = Uri.parse("https://api.whatsapp.com/send?phone=$cleanPhone&text=${Uri.encode(message)}")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        try {
            context.startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(context, "WhatsApp is not installed.", Toast.LENGTH_SHORT).show()
        }
    }

    fun dialPhone(context: Context, phone: String) {
        if (phone.isBlank()) return
        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:$phone")
        }
        try {
            context.startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(context, "No dialer app available.", Toast.LENGTH_SHORT).show()
        }
    }
}
