package com.example.myapplication

import android.view.View
import android.widget.EditText
import android.widget.Toast
import java.text.Normalizer
import java.util.ArrayList
import java.util.regex.Pattern

object Utilidades {

    fun instrutorAndroid(candidato: ArrayList<Candidato>): ArrayList<Candidato> {
        return candidato
    }

    fun semAcento(str: String): String {
        var semacento: String = Normalizer.normalize(str, Normalizer.Form.NFD)
        var pattern: Pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+")
        return pattern.matcher(semacento).replaceAll("")
    }


    fun contarVogais(nome: String): Int {
        var n_vogais: Int = 0
        nome.lowercase().split(" ")[0].forEach {
            when (it) {
                'a', 'e', 'i', 'o', 'u' -> n_vogais++
            }
        }
        return n_vogais
    }


    fun numeroPrimo(numero: Int): Boolean {
        for (i in 2 until numero) {
            if (numero % i == 0) {
                return false
            }
        }
        return true
    }


    private val REGEX_UNACCENT = "\\p{InCombiningDiacriticalMarks}+".toRegex()
    fun String.unaccent(): String {
        val temp = Normalizer.normalize(this, Normalizer.Form.NFD)
        return REGEX_UNACCENT.replace(temp, "")
    }


    fun procuraInstrutoriOS(list: ArrayList<Candidato>): Candidato? {
        var instrutor = list.filter {
            it.idade <= 31 && it.idade % 2 != 0 && it.vaga != "ios" && it.estado == "sc" && it.idade >= 20 && it.nome.split(
                " "
            ).get(1).first() == 'v'
        }

        if (instrutor.size > 1) {
            return null
        }
        return instrutor.firstOrNull()
    }


    fun procuraInstrutorAndroid(list: ArrayList<Candidato>): Candidato? {
        var instrutor_android = list.filter {
            it.estado == "sc" && it.idade <= 31 && it.vaga != "android" && it.idade % 2 != 0 && Utilidades.contarVogais(
                it.nome.unaccent()
            ) == 3 && it.nome.split(" ")[0].last() == 'o'
        }
        return instrutor_android.firstOrNull()
    }

    fun getLogin(name: String): String {
        return name.replace(oldChar = ' ', newChar = '_').lowercase()
    }




}