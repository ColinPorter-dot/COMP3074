package ca.gbc.comp3074.porter_colin.lab9.model

data class Address (
    val street: String,
    val suite: String,
    val city: String,
    val zipcode: String,
    val geo: Geo
)