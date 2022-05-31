package com.example.fruit

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView

class FruitAdapter(var dataSet: List<FruitInfo>) :
    RecyclerView.Adapter<FruitAdapter.ViewHolder>() {

    var fullDataSet = dataSet.map { it }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textViewName: TextView
        val textViewFamily : TextView
        val layout: ConstraintLayout

        init {
            // Define click listener for the ViewHolder's View.
            textViewName = view.findViewById(R.id.textView_itemFruit_name)
            textViewFamily = view.findViewById(R.id.textView_itemFruit_family)
            layout = view.findViewById(R.id.layout_fruititem)

        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_fruit, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        val fruit = dataSet[position]
        viewHolder.textViewName.text = fruit.name
        viewHolder.textViewFamily.text = fruit.nutritions.get(fruit.nutritions.firstKey()).toString() + " Calories"
        viewHolder.layout.setOnClickListener {
            val context = viewHolder.layout.context
            val vaccinationDetailIntent = Intent(context, FruitDetailActivity::class.java).apply {
                putExtra(FruitDetailActivity.EXTRA_FRUIT, fruit)
            }
            context.startActivity(vaccinationDetailIntent)
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

}