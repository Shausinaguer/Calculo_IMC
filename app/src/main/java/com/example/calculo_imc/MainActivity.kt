package com.example.calculo_imc

import android.os.Bundle
import android.widget.EditText
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.div
import kotlin.times

class MainActivity : AppCompatActivity() {
    private lateinit var editTextPeso: EditText
    private lateinit var editTextAltura: EditText
    private lateinit var buttonCalcular: Button
    private lateinit var textViewResultado: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        editTextPeso = findViewById(R.id.editTextPeso)
        editTextAltura = findViewById(R.id.editTextAltura)
        buttonCalcular = findViewById(R.id.buttonCalcular)
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

    }

    private fun calcularIMC(peso: Double, altura: Double): Double {
        val resultIMC = peso / (altura*altura)
        return resultIMC
    }
}