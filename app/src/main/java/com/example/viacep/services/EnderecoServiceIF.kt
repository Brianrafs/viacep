package com.example.viacep.services

import com.example.viacep.model.Endereco
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path


interface EnderecoServiceIF {
    @GET("ws/{cep}/json/")
    fun getEndereco(@Path("cep") cep: String): Call<Endereco>
}

