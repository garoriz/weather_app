package com.example.a2sem.presentation.main.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.a2sem.data.api.response.cities.City
import com.example.a2sem.presentation.main.diffutils.CityDiffItemCallback

class CityListAdapter(
    private val action: (Int) -> Unit,
) : ListAdapter<City, CityHolder>(CityDiffItemCallback()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CityHolder = CityHolder.create(parent, action)

    override fun onBindViewHolder(holder: CityHolder, position: Int) =
        holder.bind(getItem(position))

    override fun submitList(list: MutableList<City>?) {
        super.submitList(if (list == null) null else ArrayList(list))
    }
}
