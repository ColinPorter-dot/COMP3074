package ca.gbc.comp3074.porter_colin.lab9.api

import retrofit2.http.GET
import ca.gbc.comp3074.porter_colin.lab9.model.User
import retrofit2.http.Path

interface JsonPlaceholderApi {
    @GET("users")
    suspend fun getUsers():List<User>

    @GET("users/{id}")
    suspend fun getUser(@Path("id") id: Int): User
}