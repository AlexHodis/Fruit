package com.example.fruit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.fruit.databinding.ActivityFruitDetailBinding

class FruitDetailActivity : AppCompatActivity() {

    private lateinit var binding : ActivityFruitDetailBinding

    companion object{
        val EXTRA_FRUIT = "fruit"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFruitDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val fruitInfo = intent.getParcelableExtra<FruitInfo>(EXTRA_FRUIT)

        binding.textViewFruitDetailName.text = fruitInfo?.name

        binding.textViewFruitDetailNutritions.text =
            fruitInfo?.nutritions?.toList()?.joinToString{
                it.first + ": " + it.second + "\n \n"
            }?.replace(", ", "")

        binding.textViewFruitDetailOrder.text = fruitInfo?.order

        binding.textViewFruitDetailFamily.text = fruitInfo?.family

        binding.textViewFruitDetailGenus.text = fruitInfo?.genus
    }
}