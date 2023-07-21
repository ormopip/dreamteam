package com.formacion.androidavanzado.presentation.list

import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import okhttp3.Credentials
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request

const val BASE_URL = "https://dragonball.keepcoding.education/api/"
var token = ""

class HeroListViewModel: ViewModel() {

    suspend fun descargarListaHerores(): HeroListState {
        val client = OkHttpClient()
        val url = "${BASE_URL}heros/all"
        val formBody =
            FormBody.Builder()
                .add("name", "")
                .build() // Esto dice que es un POST
        val request = Request.Builder()
            .url(url)
            .addHeader("Authorization", "Bearer $token")
            .post(formBody)
            .build()
        val call = client.newCall(request)
        val response = call.execute()
        if (response.isSuccessful)
            response.body?.let {
                val herosDto = Gson().fromJson(it.string(), Array<HeroDto>::class.java)

                return HeroListState.OnSuccess(
                    herosDto.map {
                        Hero(it.name, it.photo)
                    }
                )
            } ?: return HeroListState.OnError("No se ha recibido ningún token")
        else
            return HeroListState.OnError(response.message)
    }

    data class HeroDto(
        val id: String,
        val photo: String,
        var favorite: Boolean,
        val name: String,
        val description: String,
    )

    data class Hero(
        val nombre: String,
        val imageUrl: String,
    )

    sealed class HeroListState {
        data class OnSuccess(val list: List<Hero>) : HeroListState()
        data class OnError(val message: String): HeroListState()
    }

    fun loguear(): LoginState {
        val client = OkHttpClient()
        val url = "${BASE_URL}auth/login"
        val credentials = Credentials.basic("carlos.bellmont1@pruebmail.es", "123456")
        val formBody = FormBody.Builder().build() // Esto dice que es un POST
        val request = Request.Builder()
            .url(url)
            .addHeader("Authorization", credentials)
            .post(formBody)
            .build()
        val call = client.newCall(request)
        val response = call.execute()
        if (response.isSuccessful)
            response.body?.let {
                token = it.string()
                return LoginState.OnSuccess
            } ?: return LoginState.OnError("No se ha recibido ningún token")
        else
            return LoginState.OnError(response.message)
    }

    sealed class LoginState {
        object OnSuccess : LoginState()
        data class OnError(val message: String): LoginState()
    }
}