package com.example.converta

import android.content.Context
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class Settings : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings)

        LanguageHelper.applyLanguage(this)

        val languageSpinner = findViewById<Spinner>(R.id.languageSpinner)
        val saveButton = findViewById<Button>(R.id.saveButton)
        val logoutButton = findViewById<Button>(R.id.logoutButton)

        val languages = arrayOf("English", "isiZulu", "Xitsonga")

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, languages)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        languageSpinner.adapter = adapter

        val prefs = getSharedPreferences("ConvertaPrefs", Context.MODE_PRIVATE)
        val savedLang = prefs.getString("language", "en")

        val selectedIndex = when (savedLang) {
            "zu" -> 1
            "ts" -> 2
            else -> 0
        }
        languageSpinner.setSelection(selectedIndex)

        saveButton.setOnClickListener {
            val languageCode = when (languageSpinner.selectedItemPosition) {
                1 -> "zu"
                2 -> "ts"
                else -> "en"
            }

            prefs.edit().putString("language", languageCode).apply()

            Toast.makeText(this, "Language updated!", Toast.LENGTH_SHORT).show()

            recreate() // refresh this screen
        }

        logoutButton.setOnClickListener {
            Toast.makeText(this, "Logged out!", Toast.LENGTH_SHORT).show()
            finish()
        }

        applyLanguage(prefs.getString("language", "en")!!)
    }

    private fun applyLanguage(languageCode: String) {
        val settingsTitle = findViewById<TextView>(R.id.settingsTitle)
        val languageLabel = findViewById<TextView>(R.id.languageLabel)
        val saveButton = findViewById<Button>(R.id.saveButton)
        val logoutButton = findViewById<Button>(R.id.logoutButton)

        val res = resources
        when (languageCode) {
            "zu" -> {
                settingsTitle.text = "Izilungiselelo"
                languageLabel.text = "Ulimi"
                saveButton.text = "Gcina"
                logoutButton.text = "Phuma"
            }
            "ts" -> {
                settingsTitle.text = "Switirhisiwa"
                languageLabel.text = "Ririmi"
                saveButton.text = "Hlayisa"
                logoutButton.text = "Huma"
            }
            else -> {
                settingsTitle.text = "Settings"
                languageLabel.text = "Language"
                saveButton.text = "Save"
                logoutButton.text = "Logout"
            }
        }
    }
}
