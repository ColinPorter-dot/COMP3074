package ca.gbc.comp3074.porter_colin.lab9.model

data class User (
    val id: Int,
    val name: String,
    val username: String,
    val email: String,
    val address: String,
    val phone: String,
    val website: String,
    val company: String
)