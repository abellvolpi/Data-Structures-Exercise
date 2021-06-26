package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.Window
import android.widget.*
import androidx.appcompat.app.AlertDialog

const val EXTRA_CANDIDATO = "CANDIDATO"

class LoginActivity : AppCompatActivity() {

    private lateinit var username: EditText
    private lateinit var password: EditText
    private lateinit var buttonLogin: View
    private lateinit var textbutton: TextView
    private lateinit var progress: ProgressBar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_login)

        username = findViewById(R.id.username_field)
        password = findViewById(R.id.password_field)
        buttonLogin = findViewById(R.id.buttonLogin)
        progress = findViewById(R.id.progress)
        textbutton = findViewById(R.id.label_login_id)

        buttonLogin.setOnClickListener {
            changeState(true)
            delay {
                efetuaLogin(username.text.toString(), password.text.toString())?.let {
                    val intencao = Intent(this, MainActivity::class.java).apply {
                        putExtra(EXTRA_CANDIDATO, it)
                    }
                    startActivity(intencao)
                    finish()
                } ?: run {
                    loginInvalido()

                    changeState(false)

                }
            }
        }


        testMyArrayList()
        testMyLinkedList()


    }


    private fun buttonFunctions() {
        buttonLogin.isClickable = false
        buttonLogin.alpha = 0.5F
        textbutton.visibility = View.GONE
        progress.visibility = View.VISIBLE
    }

    private fun delay(delay: Long = 1500, action: () -> Unit) {
        Handler(Looper.getMainLooper()).postDelayed(action, delay)
    }


    private fun changeState(isLoading: Boolean) {
        if (isLoading) {
            progress?.visibility = View.VISIBLE
            buttonLogin.apply {
                isClickable = false
                isFocusable = false
                alpha = 0.5f
            }
        } else {
            progress?.visibility = View.GONE
            buttonLogin.apply {
                isClickable = true
                isFocusable = true
                alpha = 1f
            }
        }
    }

    // Função que lê o usário do edittext e verifica com a list do csv, caso ache, retorna o candidato
    private fun efetuaLogin(user_informed: String, password_informed: String): Candidato? {
        val list = Csv.fetchCandidates(this)
        list.forEach {
            if (it.username == user_informed && it.password.toString() == password_informed) {
                return it
            }
        }
        return null
    }

    private fun loginInvalido() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Erro")
        builder.setMessage("Usuário ou senha inválidos")
        builder.setNeutralButton("OK") { _, _ -> }
        val caixa_dialogo: AlertDialog = builder.create()
        caixa_dialogo.show()

    }

    // botao de voltar aparece um aviso na home pra confirmar
    //arrumar layout
//    override fun onBackPressed() {
//
//
//        super.onBackPressed()
//    }




    private fun testMyArrayList() {
        val simpleArrayList = MyArrayList<String>()
        val (passed, message) = testPsListImplementation(simpleArrayList, false)
        Toast.makeText(this, if (passed) "All tests passed" else "Test failed with: ${message ?: ""}", Toast.LENGTH_LONG).show()
    }

    private fun testMyLinkedList() {
        val simpleLinkedList = MyLinkedList<String>()
        val (passed, message) = testPsListImplementation(simpleLinkedList, true)
        Toast.makeText(this, if (passed) "All tests passed" else "Test failed with: ${message ?: ""}", Toast.LENGTH_LONG).show()
    }


    private fun testPsListImplementation(
        psList: PSList<String>,
        isLinkedList: Boolean
    ): Pair<Boolean, String?> {
        try {
            psList.add("String 1")
            psList.add("String 2")
            psList.add("String 3")

            if (psList.size() != 3 || (!isLinkedList && psList.realSize() != DEFAULT_LIST_INIT_SIZE)) {
                return Pair(false, "Expected size = 3 and realSize = $DEFAULT_LIST_INIT_SIZE")
            }

            psList.add("String 4")
            psList.add("String 5")

            if (psList.size() != 5 || (!isLinkedList && psList.realSize() != DEFAULT_LIST_INIT_SIZE)) {
                return Pair(false, "Expected size = 5 and realSize = $DEFAULT_LIST_INIT_SIZE")
            }

            if (!psList.remove(4)) {
                return Pair(false, "Failed to remove element at position 4")
            }

            if (psList.size() != 4 || (!isLinkedList && psList.realSize() != DEFAULT_LIST_INIT_SIZE)) {
                return Pair(false, "Expected size = 4 and realSize = $DEFAULT_LIST_INIT_SIZE")
            }

            psList.add("String 5")

            if (!psList.contains("String 5")) {
                return Pair(false, "Expected to contain \"String 5\"")
            }

            psList.add("String 6")

            if (psList.size() != 6 || (!isLinkedList && psList.realSize() != DEFAULT_LIST_INIT_SIZE * 2)) {
                return Pair(false, "Expected size = 6 and realSize = $DEFAULT_LIST_INIT_SIZE")
            }

            for (i in 0 until psList.size()) {
                if (psList[i] != "String ${i + 1}") {
                    return Pair(false, "Expected string \"String ${i + 1}\" at position $i")
                }
            }

            psList.remove(1)

            if (psList[0] != "String 1") {
                return Pair(false, "Expected string \"String 1\" at position 0")
            }

            for (i in 1 until psList.size()) {
                if (psList[i] != "String ${i + 2}") {
                    return Pair(false, "Expected string \"String ${i + 2}\" at position $i")
                }
            }

            psList.remove(2)

            if (psList.size() != 4 || (!isLinkedList && psList.realSize() != DEFAULT_LIST_INIT_SIZE)) {
                return Pair(false, "Expected size = 4 and realSize = $DEFAULT_LIST_INIT_SIZE")
            }

            for (i in 2 until psList.size()) {
                if (psList[i] != "String ${i + 3}") {
                    return Pair(false, "Expected string \"String ${i + 3}\" at position $i")
                }
            }

            psList.remove(0)
            psList.remove(0)

            if (!psList.contains("String 5")) {
                return Pair(false, "Expected to contain \"String 5\"")
            }

            psList.remove(0)

            if (!psList.contains("String 6")) {
                return Pair(false, "Expected to contain \"String 6\"")
            }

            psList.remove(0)

            if (psList.size() != 0 || (!isLinkedList && psList.realSize() != DEFAULT_LIST_INIT_SIZE)) {
                return Pair(false, "Expected size = 0 and realSize = $DEFAULT_LIST_INIT_SIZE")
            }

            psList.add("final test")

            if (psList.size() != 1 || (!isLinkedList && psList.realSize() != DEFAULT_LIST_INIT_SIZE)) {
                return Pair(false, "Expected size = 1 and realSize = $DEFAULT_LIST_INIT_SIZE")
            }

            if (psList[0] != "final test") {
                return Pair(false, "Expected string \"final test\" at position 0")
            }

            return Pair(true, null)
        } catch (e: Exception) {
            return Pair(false, e.message ?: "Unknown exception")
        }
    }
}
