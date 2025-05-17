package com.yourapp.passwordgen

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.yourapp.passwordgen.databinding.ViewSocialLinkBinding

class SocialLinkView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val binding: ViewSocialLinkBinding

    init {
        binding = ViewSocialLinkBinding.inflate(LayoutInflater.from(context), this, true)
    }

    fun setSocialLink(text: String, onClick: () -> Unit) {
        binding.socialLinkText.text = text
        binding.root.setOnClickListener { onClick() }
        binding.socialLinkText.setTextColor(ContextCompat.getColor(context, R.color.matrix_green))
    }
}