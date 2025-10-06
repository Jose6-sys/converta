package com.example.converta

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Converter : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.converter)
        LanguageHelper.applyLanguage(this)

        val settingsButton: ImageButton = findViewById(R.id.settingsButton)
        val convertButton: Button = findViewById(R.id.convertButton)
        val amountField: EditText = findViewById(R.id.amountField)
        val fromSpinner: Spinner = findViewById(R.id.fromSpinner)
        val toSpinner: Spinner = findViewById(R.id.toSpinner)
        val resultText: TextView = findViewById(R.id.resultText)

        settingsButton.setOnClickListener {
            startActivity(Intent(this, Settings::class.java))
        }

        // Currency lists
        val currencies = arrayOf("USD", "ZAR", "EUR", "GBP", "JPY")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, currencies)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        fromSpinner.adapter = adapter
        toSpinner.adapter = adapter

        convertButton.setOnClickListener {
            val amountText = amountField.text.toString()
            if (amountText.isEmpty()) {
                Toast.makeText(this, "Enter amount", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val amount = amountText.toDouble()
            val from = fromSpinner.selectedItem.toString()
            val to = toSpinner.selectedItem.toString()

            resultText.text = "Converting..."

            val call = RetrofitClient.instance.convertCurrency(from, to, amount)
            call.enqueue(object : Callback<ConversionResponse> {
                override fun onResponse(
                    call: Call<ConversionResponse>,
                    response: Response<ConversionResponse>
                ) {
                    if (response.isSuccessful && response.body()?.result != null) {
                        val result = response.body()!!.result
                        resultText.text = "Result: %.2f $to".format(result)
                    } else {
                        resultText.text = "Error converting"
                    }
                }

                override fun onFailure(call: Call<ConversionResponse>, t: Throwable) {
                    resultText.text = "Failed: ${t.message}"
                }
            })
        }
    }
}
