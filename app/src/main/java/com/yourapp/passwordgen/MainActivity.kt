package com.yourapp.passwordgen

import android.content.ClipboardManager
import android.content.ClipData
import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.yourapp.passwordgen.databinding.ActivityMainBinding
import java.security.SecureRandom

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    // adding words soon
    private val lowercase = "abcdefghijklmnopqrstuvwxyz"
    private val uppercase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
    private val numbers = "0123456789"
    private val symbols = "!@#$%^&*()-_=+"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.headerText.addGlowAnimation()

        setupPasswordGenerator()
        setupSocialLinks()
    }

    private fun setupPasswordGenerator() {
        binding.generateButton.setOnClickListener {
            if (!binding.checkLowercase.isChecked && !binding.checkUppercase.isChecked &&
                !binding.checkNumbers.isChecked && !binding.checkSymbols.isChecked) {
                Toast.makeText(this, "Select at least one character type", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val length = binding.lengthSeekBar.progress
            val password = generatePassword(
                length,
                binding.checkLowercase.isChecked,
                binding.checkUppercase.isChecked,
                binding.checkNumbers.isChecked,
                binding.checkSymbols.isChecked
            )

            binding.passwordDisplay.text = password
            binding.passwordDisplay.alpha = 0f
            binding.passwordDisplay.scaleX = 0.9f
            binding.passwordDisplay.scaleY = 0.9f
            binding.passwordDisplay.animate()
                .alpha(1f)
                .scaleX(1f)
                .scaleY(1f)
                .setDuration(300)
                .start()
        }

        binding.copyButton.setOnClickListener {
            val password = binding.passwordDisplay.text.toString()
            if (password.isNotEmpty() && password != "••••••••••••••••") {
                try {
                    val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                    val clip = ClipData.newPlainText("password", password)
                    clipboard.setPrimaryClip(clip)

                    binding.copyButton.setImageResource(R.drawable.ic_check)
                    Handler(Looper.getMainLooper()).postDelayed({
                        binding.copyButton.setImageResource(R.drawable.ic_copy)
                    }, 1000)

                    Toast.makeText(this, "Copied!", Toast.LENGTH_SHORT).show()
                } catch (e: Exception) {
                    Toast.makeText(this, "Copy failed", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.lengthSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                binding.lengthText.text = "Length: $progress"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }

    private fun setupSocialLinks() {
        val socialLinks = listOf(
            Pair("PayPal", "https://paypal.me/damroyaltyxxii"),
            Pair("Instagram", "https://www.instagram.com/damroyalty"),
            Pair("Twitter", "https://www.x.com/damroyalty"),
            Pair("GitHub", "https://www.github.com/damroyalty"),
            Pair("Linktree", "https://linktr.ee/damroyalty"),
            Pair("Twitch", "https://www.twitch.tv/devroyalty"),
            Pair("Discord", "https://discord.gg/kDs2mmQwwS")
        )

        binding.socialFooter.removeAllViews()

        socialLinks.forEachIndexed { index, (name, url) ->
            val linkView = TextView(this).apply {
                text = name
                setTextColor(ContextCompat.getColor(this@MainActivity, R.color.matrix_green))
                textSize = 12f
                typeface = Typeface.MONOSPACE
                setOnClickListener { openUrl(url) }
                setPadding(4.dpToPx(), 0, 4.dpToPx(), 0)
            }
            binding.socialFooter.addView(linkView)

            if (index < socialLinks.size - 1) {
                val separator = TextView(this).apply {
                    text = "•"
                    setTextColor(ContextCompat.getColor(this@MainActivity, R.color.matrix_green))
                    setPadding(4.dpToPx(), 0, 4.dpToPx(), 0)
                }
                binding.socialFooter.addView(separator)
            }
        }
    }

    private fun generatePassword(
        length: Int,
        useLowercase: Boolean,
        useUppercase: Boolean,
        useNumbers: Boolean,
        useSymbols: Boolean
    ): String {
        val charPool = buildString {
            if (useLowercase) append(lowercase)
            if (useUppercase) append(uppercase)
            if (useNumbers) append(numbers)
            if (useSymbols) append(symbols)
        }

        return if (charPool.isNotEmpty()) {
            val random = SecureRandom()
            (1..length)
                .map { charPool[random.nextInt(charPool.length)] }
                .joinToString("")
        } else {
            ""
        }
    }

    private fun openUrl(url: String) {
        try {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
        } catch (e: Exception) {
            Toast.makeText(this, "Couldn't open link", Toast.LENGTH_SHORT).show()
        }
    }

    private fun Int.dpToPx(): Int = (this * resources.displayMetrics.density).toInt()
}