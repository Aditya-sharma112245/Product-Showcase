package com.example.assignment

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.assignment.R
import products

class ProductAdapter(private val listener: OnItemClickListener) :
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    private val items = mutableListOf<products>()

    interface OnItemClickListener {
        fun onItemClick(product: products)
    }

    inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private val productNumberTextView: TextView = itemView.findViewById(R.id.productNumberTextView)
        private val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        private val priceTextView: TextView = itemView.findViewById(R.id.priceTextView)
        private val imageView: ImageView = itemView.findViewById(R.id.imageView)

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(product: products, position: Int) {

            productNumberTextView.text = "Product ${position + 1}"


            titleTextView.text = product.title
            priceTextView.text = "$${product.price}"


            Glide.with(itemView.context)
                .load(product.imageUrl ?: "https://cdn.dummyjson.com/products/images/beauty/Essence%20Mascara%20Lash%20Princess/thumbnail.png")
                .placeholder(R.drawable.placeholder_image)
                .error(R.drawable.error_image)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        Log.e("Glide", "Image load failed: ${e?.message}", e)
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        Log.d("Glide", "Image successfully loaded")
                        return false
                    }
                })
                .into(imageView)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(items[position])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.items_product, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(items[position], position)
    }

    override fun getItemCount(): Int = items.size

    fun setItems(newItems: List<products>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }
}
