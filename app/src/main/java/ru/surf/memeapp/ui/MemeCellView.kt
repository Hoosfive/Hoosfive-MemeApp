package ru.surf.memeapp.ui

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.view_meme_cell.view.*
import ru.surf.memeapp.R
import java.net.URI

class MemeCellView @JvmOverloads constructor(context: Context, attributeSet: AttributeSet) :
    LinearLayout(context, attributeSet) {
    companion object {
        var isFavorite = false
    }

    init {
        View.inflate(context, R.layout.view_meme_cell, this)
        initListeners()
    }

    fun setData(text: String, imageURI: URI) {
        TV.text = text
        Glide.with(this).load(imageURI).into(IV)
    }

    private fun initListeners() {
        likeBtn.setOnClickListener {
            changeLikeState()
        }
        shareBtn.setOnClickListener {
            shareMeme()
        }
    }

    private fun changeLikeState() {
        if (isFavorite)
            likeBtn.setImageResource(R.drawable.like_btn)
        else likeBtn.setImageResource(R.drawable.like_btn_activated)
        isFavorite = !isFavorite
    }

    private fun shareMeme() {
        Toast.makeText(context,"Ok, you shared this meme with your friends, are you satisfied?",Toast.LENGTH_SHORT).show()
    }
}