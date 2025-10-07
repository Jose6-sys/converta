package com.example.converta

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Converter : AppCompatActivity() {

    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.converter)

        try {
            LanguageHelper.applyLanguage(this)
        } catch (e: Exception) {
            Toast.makeText(this, "Lang error: ${e.message}", Toast.LENGTH_LONG).show()
        }

        val settingsButton: ImageButton = findViewById(R.id.settingsButton)
        val convertButton: Button = findViewById(R.id.convertButton)
        val amountField: EditText = findViewById(R.id.amountField)
        val fromSpinner: Spinner = findViewById(R.id.fromSpinner)
        val toSpinner: Spinner = findViewById(R.id.toSpinner)
        val resultText: TextView = findViewById(R.id.resultText)
        progressBar = findViewById(R.id.progressBar) // 👈 add this to your XML

        settingsButton.setOnClickListener {
            startActivity(Intent(this, Settings::class.java))
        }

        // Currency list
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

            resultText.text = ""
            progressBar.visibility = View.VISIBLE

            val call = RetrofitClient.instance.convertCurrency(from, to, amount)
            call.enqueue(object : Callback<ConversionResponse> {
                override fun onResponse(
                    call: Call<ConversionResponse>,
                    response: Response<ConversionResponse>
                ) {
                    progressBar.visibility = View.GONE

                    if (response.isSuccessful) {
                        val body = response.body()
                        if (body != null && body.result == "success" && body.conversion_result != null) {
                            resultText.text = "Result: %.2f $to".format(body.conversion_result)
                        } else {
                            resultText.text = "API Error: ${body?.error_type ?: "Unknown"}"
                        }
                    } else {
                        val errorMsg = response.errorBody()?.string() ?: "Unknown API error"
                        resultText.text = "Error: $errorMsg"
                    }
                }

                override fun onFailure(call: Call<ConversionResponse>, t: Throwable) {
                    progressBar.visibility = View.GONE
                    resultText.text = "Failed: ${t.message}"
                    Toast.makeText(this@Converter, "Network error: ${t.message}", Toast.LENGTH_LONG).show()
                }
            })
        }
    }
}
