package com.example.fruit
import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class FruitInfo(
    val name: String,
    val order: String,
    val family: String,
    val genus: String,
    val nutritions: SortedMap<String, Double>
) : Parcelable
