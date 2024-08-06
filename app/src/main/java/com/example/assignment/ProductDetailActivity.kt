package com.example.assignment

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.assignment.R
import products

class ProductDetailActivity : AppCompatActivity() {

    private lateinit var titleTextView: TextView
    private lateinit var brandTextView: TextView
    private lateinit var descriptionTextView: TextView
    private lateinit var priceTextView: TextView
    private lateinit var stockTextView: TextView
    private lateinit var imageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)

        titleTextView = findViewById(R.id.productTitle)
        brandTextView = findViewById(R.id.productBrand)
        descriptionTextView = findViewById(R.id.productDescription)
        priceTextView = findViewById(R.id.productPrice)
        stockTextView = findViewById(R.id.productStock)
        imageView = findViewById(R.id.productImage)

        val product = intent.getParcelableExtra<products>("product")

        product?.let {
            titleTextView.text = it.title
            brandTextView.text = it.brand ?: "Unknown Brand"
            descriptionTextView.text = it.description
            priceTextView.text = " Price: $${it.price}"
            stockTextView.text = "Stock: ${it.stock ?: "N/A"}"

            Glide.with(this)
                .load(it.imageUrl ?: "https://cdn.dummyjson.com/products/images/beauty/Essence%20Mascara%20Lash%20Princess/1.png")
                .placeholder(R.drawable.placeholder_image)
                .error(R.drawable.error_image)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        return false
                    }
                })
                .into(imageView)
        }
    }
}
