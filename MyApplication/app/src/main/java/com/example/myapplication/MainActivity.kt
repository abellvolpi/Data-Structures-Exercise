package com.example.myapplication

import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.Utilidades.procuraInstrutorAndroid
import com.example.myapplication.Utilidades.procuraInstrutoriOS
import com.example.myapplication.Utilidades.unaccent
import java.io.*
import java.lang.reflect.Array
import java.text.Collator
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    private lateinit var labelName : TextView
    private lateinit var labelMessage : TextView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)




        val list: ArrayList<Candidato> = ArrayList()
        var soma_qa = 0.0F
        var soma_android = 0.0F
        var soma_ios = 0.0F
        var total_inscritos = 0.0F
        var idade_qas = 0.0F

        val file = File(cacheDir.absolutePath + "/AppAcademy_Candidates.csv")
        val fileLer = FileReader(file)
        //FileReader identifica o arquivo
        val br = BufferedReader(fileLer)
        //BufferedReader é a classe que le o arquivo
        var line: String = br.readLine()


        while (br.ready()) {

            var lista = br.readLine().split(";")
            list.add(
                Candidato(
                    lista[0],
                    lista[1],
                    lista[2].replace(" anos", "").toInt(),
                    lista[3]
                )
            )
        }

        for (candidato in list) {
            if (candidato.vaga == "qa") {
                soma_qa++ //contador de QA's
                total_inscritos++  // contador do total de candidatos
                idade_qas += candidato.idade
            } else if (candidato.vaga == "android") {
                soma_android++ //contador  de dev's Android
                total_inscritos++
            } else if (candidato.vaga == "ios") {
                soma_ios++ //contador  de dev's Android
                total_inscritos++
            }
            println();
        }

        var media_and = (soma_android * 100) / total_inscritos
        var media_ios = soma_ios * 100 / total_inscritos
        var media_qa = ((soma_qa * 100) / total_inscritos)
        var media_idade_qa = idade_qas / soma_qa


        println("Proporção de candidatos por vaga:")
        println(media_qa)
        System.out.printf("Android: %.2f %%\n", media_and)
        System.out.printf("iOS: %.2f %%\n", media_ios)
        System.out.printf("QA: %.2f %%\n\n", media_qa)
        System.out.printf("Idade média dos candidatos de QA: %.0f\n", media_idade_qa)
        println();

//        Collections.sort(list, object : Comparator<Candidato?> {
//            //pro programa ordenar  em ordem alfabética
//            var collator: Collator = Collator.getInstance(Locale("pt", "BR"))
//
//            override fun compare(c1: Candidato?, c2: Candidato?): Int {
//                if (c1 != null) {
//                    if (c2 != null) {
//                        return collator.compare(c1.nome, c2.nome)
//                    }
//                }
//            }
//        })

        //Gerador csv


//        println("Gerando lista ordenada...")
//        var fw: FileWriter = FileWriter("Sorted_AppAcademy_Candidates.csv")
//        var bw : BufferedWriter = BufferedWriter(fw)
//        var sb: StringBuilder = StringBuilder()
//        for (candidato in list){
//            sb.append(candidato.nome)
//            sb.append(";")
//            sb.append(candidato.vaga)
//            sb.append(";")
//            sb.append(candidato.idade)
//            sb.append(";")
//            sb.append(candidato.estado)
//            sb.append(";")
//            sb.append("\n")
//        }
//        bw.write(sb.toString())
//        bw.close()
//        println("Lista ordenada salva como: Sorted_AppAcademy_Candidates.csv")

        val map: MutableMap<String, Int> = TreeMap()

        for (candidato in list) {
            val nome_estado: String = candidato.estado
            if (!map.containsKey(nome_estado)) map[nome_estado] = 0
            map[nome_estado] = map[nome_estado]!! + 1
        }
        var j = 0
        var menor_valor = 0
        var menor_valor2 = 0
        var contador = 0
        var menor_estado: String? = null
        var menor_estado2: String? = null

        for ((key, value) in map) {
//            printador estados  a quantidade
//            System.out.printf("%s => %d%n", entry.getKey(), entry.getValue());
            j++
            if (contador == 0) {
                menor_valor = value
                menor_estado = key
            }
            if (contador == 1) {
                if (value < menor_valor) {
                    menor_valor2 = menor_valor
                    menor_estado2 = menor_estado
                    menor_valor = value
                    menor_estado = key
                } else {
                    menor_valor2 = value
                    menor_estado2 = key
                }
            } else if (value < menor_valor) {
                menor_valor = value
                menor_estado = key
            } else if (value < menor_valor2) {
                menor_valor2 = value
                menor_estado2 = key
            }
            contador++
        }

        System.out.printf("\nRank dos 2 estados com menos ocorrências:\n")
        if (menor_valor == 1) {
            System.out.printf("#1 %s - %d candidato\n", menor_estado, menor_valor)
        } else {
            System.out.printf("#1 %s - %d candidatos\n", menor_estado, menor_valor)
        }
        if (menor_valor == 1) {
            System.out.printf("#2 %s - %d candidato\n", menor_estado2, menor_valor2)
        } else {
            System.out.printf("#2 %s - %d candidatos\n", menor_estado2, menor_valor2)
        }
        System.out.printf("\nNúmero de estados distintos presentes na lista: %d\n", j)


        procuraInstrutorAndroid(list)?.let{
            println("O nome do instrutor de Android é: ${it.nome}")
            println("A idade do instrutor de Android é: ${it.idade}")
        }
        procuraInstrutoriOS(list)?.let{
            println("O nome do instrutor de iOS é: ${it.nome}")
            println("A idade do instrutor de iOS é: ${it.idade}")
        }


        labelMessage = findViewById(R.id.dados_usuario)
        labelName = findViewById(R.id.nome_usuario)

        (intent.getSerializableExtra(EXTRA_CANDIDATO) as Candidato?)?.let{
            labelName.text = it.nome
            labelMessage.text = it.vaga + it.estado + it.idade
        }





    }





}