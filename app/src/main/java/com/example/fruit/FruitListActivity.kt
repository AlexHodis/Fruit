package com.example.fruit

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fruit.databinding.ActivityFruitListBinding
import com.google.android.material.appbar.AppBarLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.log


class FruitListActivity : AppCompatActivity() {

    val TAG = "FruitListActivity"

    private lateinit var binding: ActivityFruitListBinding
    lateinit var adapter: FruitAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFruitListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val vaccineApi = RetrofitHelper.getInstance().create(FruitService::class.java)
        val fruitCall = vaccineApi.getFruit()


        fruitCall.enqueue(object : Callback<List<FruitInfo>> {
            override fun onResponse(
                call: Call<List<FruitInfo>>,
                response: Response<List<FruitInfo>>
            ) {
                Log.d(TAG, "onResponse: ${response.body()}")
                var fruitList = response.body() ?: listOf<FruitInfo>()
                adapter = FruitAdapter(fruitList)
                binding.recyclerViewFruitList.adapter = adapter
                binding.recyclerViewFruitList.layoutManager =
                    LinearLayoutManager(this@FruitListActivity)
            }

            override fun onFailure(call: Call<List<FruitInfo>>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message}")
            }

        }
        )
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.name -> {
                Toast.makeText(this, "You sorted by name", Toast.LENGTH_SHORT).show()
                adapter.dataSet = adapter.dataSet.sortedBy { it.name }
                adapter.notifyDataSetChanged()
                true
            }
            R.id.calories -> {
                Toast.makeText(this, "You sorted by calories", Toast.LENGTH_SHORT).show()
                adapter.dataSet = adapter.dataSet.sortedByDescending { it.nutritions.get(it.nutritions.firstKey()) }
                adapter.notifyDataSetChanged()
                true
            }
            R.id.order -> {
                Toast.makeText(this, "You sorted by order", Toast.LENGTH_SHORT).show()
                adapter.dataSet = adapter.dataSet.sortedBy { it.order }
                adapter.notifyDataSetChanged()
                true
            }
            R.id.family -> {
                Toast.makeText(this, "You sorted by family", Toast.LENGTH_SHORT).show()
                adapter.dataSet = adapter.dataSet.sortedBy { it.family }
                adapter.notifyDataSetChanged()
                true
            }
            R.id.genus -> {
                Toast.makeText(this, "You sorted by genus", Toast.LENGTH_SHORT).show()
                adapter.dataSet = adapter.dataSet.sortedBy { it.genus }
                adapter.notifyDataSetChanged()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the options menu from XML
        val inflater = menuInflater
        inflater.inflate(R.menu.sorting_menu, menu)

        // Get the SearchView and set the searchable configuration
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        (menu.findItem(R.id.searchBar).actionView as SearchView).apply {
            // Assumes current activity is the searchable activity
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
            setIconifiedByDefault(false) // Do not iconify the widget; expand it by default
        }
        val searchItem = menu.findItem(R.id.searchBar)


        val searchView = searchItem.actionView as SearchView
        searchView.queryHint = "Search Fruit"
        searchView.setOnQueryTextListener(object  : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                Log.d(TAG, "onQueryTextSubmit: submitted")
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Log.d(TAG, "onQueryTextChange: typing is happening")
                adapter.dataSet = adapter.fullDataSet.filter {
                    if (newText != null) {
                        it.name.lowercase().contains(newText.lowercase())

                    } else {
                        true
                    }
                }
                adapter.notifyDataSetChanged()
                return true
            }
        })
        searchView.isIconified = false

        return true
    }
}