package com.example.assignment

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import org.json.JSONObject
import products

class SecondActivity : AppCompatActivity(), ProductAdapter.OnItemClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var productAdapter: ProductAdapter
    private val products = mutableListOf<products>()
    private lateinit var requestQueue: RequestQueue

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        recyclerView = findViewById(R.id.recyclerView)
        productAdapter = ProductAdapter(this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = productAdapter

        requestQueue = Volley.newRequestQueue(this)

        fetchProducts()
    }

    private fun fetchProducts() {
        val url = "https://dummyjson.com/products"

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                try {
                    val jsonArray = response.getJSONArray("products")
                    products.clear()
                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)
                        val product = products(
                            id = jsonObject.getInt("id"),
                            title = jsonObject.getString("title"),
                            description = jsonObject.getString("description"),
                            price = jsonObject.getDouble("price"),
                            imageUrl = jsonObject.optJSONArray("images")?.optString(0, null), // Get the first image URL from the array
                            discountPercentage = jsonObject.getDouble("discountPercentage"),
                            rating = jsonObject.getDouble("rating"),
                            stock = jsonObject.optInt("stock", 0),
                            brand = jsonObject.optString("brand", "Unknown Brand")
                        )
                        products.add(product)
                    }
                    productAdapter.setItems(products)
                } catch (e: Exception) {
                    Toast.makeText(this, "Parsing error: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            },
            Response.ErrorListener { error: VolleyError ->
                Toast.makeText(this, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        )

        requestQueue.add(jsonObjectRequest)
    }

    override fun onItemClick(product: products) {
        val intent = Intent(this, ProductDetailActivity::class.java).apply {
            putExtra("product", product)
        }
        startActivity(intent)
    }
}
