package com.example.a2sem.diffutils

import androidx.recyclerview.widget.DiffUtil
import com.example.a2sem.data.api.response.cities.City

class CityDiffItemCallback : DiffUtil.ItemCallback<City>() {

    override fun areItemsTheSame(
        oldItem: City,
        newItem: City
    ): Boolean = oldItem.name == newItem.name

    override fun areContentsTheSame(
        oldItem: City,
        newItem: City
    ): Boolean = oldItem == newItem
}
