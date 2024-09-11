package com.example.viacep.model

data class Endereco(
    val cep: String?,
    val logradouro: String?,
    val complemento: String?,
    val bairro: String?,
    val localidade: String?,
    val uf: String?,
    val ibge: String?,
    val gia: String?
) {
    override fun toString(): String {
        return "Endereco(cep='$cep', logradouro='$logradouro', complemento='$complemento', bairro='$bairro', localidade='$localidade', uf='$uf', ibge='$ibge', gia='$gia')"
    }
}
