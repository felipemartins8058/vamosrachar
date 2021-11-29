package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import java.util.*

class MainActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private var tts: TextToSpeech? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val valorDivido = findViewById<TextView>(R.id.textView3)
        val speakBtn = findViewById<Button>(R.id.button2)
        val valorConta = findViewById<EditText>(R.id.editTextTextPersonName3)
        val totalPessoas = findViewById<EditText>(R.id.editTextTextPersonName4)
        val shareBtn = findViewById<Button>(R.id.button)

        tts = TextToSpeech(this, this)

        speakBtn.setOnClickListener{
            tts!!.speak(valorDivido.text.toString() + "reais para cada pessoa", TextToSpeech.QUEUE_FLUSH, null,"")
        }

        valorConta.setSelectAllOnFocus(true)
        totalPessoas.setSelectAllOnFocus(true)

        totalPessoas.setOnClickListener {
            val val1 = valorConta.text.toString().toFloat()
            val val2 = totalPessoas.text.toString().toInt()

            val result = val1 / val2

            valorDivido.text = result.toString()
        }

        valorConta.setOnClickListener {
            val val1 = valorConta.text.toString().toFloat()
            val val2 = totalPessoas.text.toString().toInt()

            val result = val1 / val2

            valorDivido.text = result.toString()
        }

        shareBtn.setOnClickListener {
            val valorTotalShare: String = valorDivido.text.toString()
            val valorContaShare: String = valorConta.text.toString()
            val totalPessoasShare: String = totalPessoas.text.toString()


            val message = "O valor total é de R$" + valorContaShare + " e somos " + totalPessoasShare + " pessoas. O valor que cada um deve pagar é de R$" + valorTotalShare

            val intent = Intent()
            intent.action = Intent.ACTION_SEND
            intent.putExtra(Intent.EXTRA_TEXT, message)
            intent.type = "text/plain"

            startActivity(Intent.createChooser(intent, "Compartilhar valor com seus amigos"))
        }
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS){

            //Toast.makeText(this@MainActivity, "Aff", Toast.LENGTH_SHORT).show()
        }
    }

    private fun speakOut(text: String) {
        tts!!.speak(text, TextToSpeech.QUEUE_FLUSH, null,"")
    }

    override fun onDestroy() {
        if (tts != null){
            tts!!.stop()
            tts!!.shutdown()
        }
        super.onDestroy()
    }

}