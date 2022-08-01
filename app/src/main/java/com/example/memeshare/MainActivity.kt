package com.example.memeshare

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings.Global.getString
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.core.content.res.TypedArrayUtils.getString
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

class MainActivity : AppCompatActivity() {
    var crrurl :String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadme()
    }
    private fun loadme(){
        val progress_Bar=findViewById<ProgressBar>(R.id.progress_Bar)
        progress_Bar.visibility=View.VISIBLE

        val queue = Volley.newRequestQueue(this)
        val url = "https://meme-api.herokuapp.com/gimme"

        val JsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url,null,
            { response ->
                crrurl=response.getString("url")

                val meme_imageView=findViewById<ImageView>(R.id.meme_imageView)
                Glide.with(this).load(crrurl).listener(object: RequestListener<Drawable>{
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progress_Bar.visibility=View.GONE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progress_Bar.visibility=View.GONE
                        return false
                    }
                }).into(meme_imageView)
                },
            {  })

        // Add the request to the RequestQueue.
        queue.add(JsonObjectRequest)
    }
    fun shareme(view: View) {
        val intent=Intent(Intent.ACTION_SEND)
        intent.type="text/plain"
        intent.putExtra(Intent.EXTRA_TEXT,"Hey! Check out this amazing meme! $crrurl")
        val chooser=Intent.createChooser(intent,"Share this meme using....")
        startActivity(chooser)
    }
    fun nextmeme(view: View) {
        loadme()
    }
}