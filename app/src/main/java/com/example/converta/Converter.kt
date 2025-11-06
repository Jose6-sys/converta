package com.example.converta

import com.example.converta.data.local.AppDatabase
import com.example.converta.data.local.ConversionEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.converter)

        db = AppDatabase.getInstance(this)

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
        progressBar = findViewById(R.id.progressBar)

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
                            val result = body.conversion_result
                            resultText.text = "Result: %.2f $to".format(result)

                            // ✅ Save to Room for offline mode
                            CoroutineScope(Dispatchers.IO).launch {
                                db.conversionDao().insertConversion(
                                    ConversionEntity(
                                        fromCurrency = from,
                                        toCurrency = to,
                                        amount = amount,
                                        result = result
                                    )
                                )
                            }
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
                    resultText.text = "Offline mode: showing last result..."

                    // ✅ Fetch last saved result when offline
                    CoroutineScope(Dispatchers.IO).launch {
                        val last = db.conversionDao().getAllConversions().firstOrNull {
                            it.fromCurrency == from && it.toCurrency == to
                        }

                        withContext(Dispatchers.Main) {
                            if (last != null) {
                                resultText.text =
                                    "Offline Result: %.2f $to (cached)".format(last.result)
                            } else {
                                Toast.makeText(
                                    this@Converter,
                                    "No offline data available",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                    }
                }
            })
        }
    }
}
