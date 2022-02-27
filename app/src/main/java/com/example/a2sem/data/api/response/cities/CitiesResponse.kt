package com.example.a2sem.data.api.response.cities

data class CitiesResponse(
    val cod: String,
    val count: Int,
    val list: List<City>,
    val message: String
)
