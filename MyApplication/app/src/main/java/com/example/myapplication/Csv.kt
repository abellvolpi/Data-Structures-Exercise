package com.example.myapplication

import android.content.Context
import java.io.BufferedReader
import java.io.InputStreamReader

object Csv {
    private var candidates: List<Candidato>? = null

    fun fetchCandidates(context: Context): List<Candidato> {
        return candidates ?: arrayListOf<Candidato>().also { list ->
            val streamReader = InputStreamReader(context.assets.open("AppAcademy_Candidates.csv"))
            val bufferedReader = BufferedReader(streamReader)
            var row: List<String>

            bufferedReader.readLine()

            while (bufferedReader.ready()) {
                row = bufferedReader.readLine().split(';')
                list.add(
                    Candidato(
                        row[0],
                        row[1].lowercase(),
                        row[2].filter { it.isDigit() }.toInt(),
                        row[3].lowercase()
                    )
                )
            }

        }
    }
}