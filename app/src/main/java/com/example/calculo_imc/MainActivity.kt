package com.example.calculo_imc

import android.os.Bundle
import android.widget.EditText
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.util.Log
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private lateinit var databaseHelper: DataBaseHelper
    private lateinit var editTextCpf: EditText
    private lateinit var editTextNome: EditText
    private lateinit var editTextIdade: EditText
    private lateinit var editTextPeso: EditText
    private lateinit var editTextAltura: EditText
    private lateinit var buttonCalcular: Button
    private lateinit var buttonSalvarDados: Button
    private lateinit var textViewResultado: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        databaseHelper = DataBaseHelper(this)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        editTextCpf = findViewById(R.id.editTextCpf)
        editTextNome = findViewById(R.id.editTextNome)
        editTextIdade = findViewById(R.id.editTextIdade)
        editTextPeso = findViewById(R.id.editTextPeso)
        editTextAltura = findViewById(R.id.editTextAltura)
        buttonCalcular = findViewById(R.id.buttonCalcular)
        buttonSalvarDados = findViewById(R.id.buttonSalvarDados)
        textViewResultado = findViewById(R.id.textViewResultado)

        buttonCalcular.setOnClickListener {
            val pesoText = editTextPeso.text.toString()
            val alturaText = editTextAltura.text.toString()

            if (pesoText.isNotEmpty() && alturaText.isNotEmpty()){
            val peso = pesoText.toDouble()
            val altura = alturaText.toDouble()

            val imc = calcularIMC(peso, altura)
            val imcFormatted = String.format("%.2f",imc)

            val resultado = "Seu IMC é: $imcFormatted"
            textViewResultado.text = resultado
            }else{
                textViewResultado.text = "Preencha o seu peso e altura de forma correta"
            }

        }

        buttonSalvarDados.setOnClickListener {
            val cpf = editTextCpf.text.toString()
            val nome = editTextNome.text.toString()
            val idade = editTextIdade.text.toString().toInt()
            val peso = editTextPeso.text.toString().toDouble()
            val altura = editTextAltura.text.toString().toDouble()

//        talvez precise usar toIntOrNull() e toDoubleOrNull()
//        realiza validação dos dados trazido da inteface

            if (nome.isNotEmpty() && idade != null && altura != null && peso != null) {
                // instancia o objeto pessoa
                val pessoa = Pessoa(cpf, nome, idade, altura, peso)

                //chama método da classe databasehelper para persistir os dados no banco de dados
                databaseHelper.insertPessoa(pessoa.cpf1, pessoa.nome1, pessoa.idade1, pessoa.altura1, pessoa.peso1)

                Toast.makeText(this, "Sucesso!", Toast.LENGTH_SHORT)
                Log.i("appBD","${databaseHelper.getUltimasPessoas()}")

                editTextCpf.text.clear()
                editTextNome.text.clear()
                editTextIdade.text.clear()
                editTextAltura.text.clear()
                editTextPeso.text.clear()

                atualizarLista()
            }else {
                Toast.makeText(this, "Há erro no preenchimento de um ou mais dados", Toast.LENGTH_SHORT)
            }
        }

        atualizarLista()
    }

    private fun atualizarLista(){
        val textViewLista = findViewById<TextView>(R.id.textViewLista)
        val listaPessoas = databaseHelper.getUltimasPessoas()

        val textoFinal = StringBuilder()
        for (pessoa in listaPessoas) {
            textoFinal.append("CPF: ${pessoa.cpf1}\n")
            textoFinal.append("Nome: ${pessoa.nome1}\n")
            textoFinal.append("Idade: ${pessoa.idade1}\n")
            textoFinal.append("Altura: ${pessoa.altura1}\n")
            textoFinal.append("Peso: ${pessoa.peso1}\n")
            textoFinal.append("======================\n")
        }

        textViewLista.text = textoFinal.toString()
    }
    private fun calcularIMC(peso: Double, altura: Double): Double {
        val resultIMC = peso / (altura*altura)
        return resultIMC
    }
}