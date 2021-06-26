package com.example.myapplication

import java.io.Serializable
import java.util.*

class Candidato : Serializable {

    var nome: String
        private set(nome) {
            field = nome.lowercase()
        }
        get() {
            return field
        }
    val vaga: String
    val idade: Int
    val estado: String
    var username: String
        private set(value) {
            field = Utilidades.getLogin(value)
        }
    var password: Int
        private set(value) {
            field = Calendar.getInstance().get(Calendar.YEAR) - idade
        }


    constructor(nome: String, vaga: String, idade: Int, estado: String) {
        this.nome = nome
        this.vaga = vaga.lowercase()
        this.idade = idade
        this.estado = estado.lowercase()
        this.username = nome
        this.password = idade
    }

    constructor(nome: String, ano: Int) {
        this.nome = nome
        this.vaga = "qa"
        this.idade = Calendar.getInstance().get(Calendar.YEAR) - ano
        this.estado = "sc"
        this.username = nome
        this.password = idade
    }

}
