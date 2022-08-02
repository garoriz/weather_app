package com.example.a2sem.presentation.main.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.a2sem.data.api.response.cities.City
import com.example.a2sem.databinding.ItemCityBinding

class CityHolder(
    private val binding: ItemCityBinding,
    private val action: (Int) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    private var city: City? = null

    init {
        itemView.setOnClickListener {
            city?.id?.also(action)
        }
    }

    fun bind(item: City) {
        this.city = item
        with(binding) {
            tvCityName.text = item.name
            tvTemp.text = item.main.temp.toString()
            when {
                item.main.temp < -20 -> {
                    tvTemp.setTextColor(Color.rgb(0, 0, 128))
                }
                item.main.temp < 0 -> {
                    tvTemp.setTextColor(Color.rgb(0, 102, 204))
                }
                item.main.temp == 0.0 -> {
                    tvTemp.setTextColor(Color.rgb(0, 255, 0))
                }
                item.main.temp < 20 -> {
                    tvTemp.setTextColor(Color.rgb(254, 0, 0))
                }
                else -> {
                    tvTemp.setTextColor(Color.rgb(178, 0, 0))
                }
            }
        }
    }

    companion object {

        fun create(
            parent: ViewGroup,
            action: (Int) -> Unit
        ) = CityHolder(
            ItemCityBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), action
        )
    }
}
