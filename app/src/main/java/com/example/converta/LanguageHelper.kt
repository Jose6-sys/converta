package com.example.converta

import android.app.Activity
import android.content.Context
import android.widget.Button
import android.widget.TextView
import android.widget.EditText

object LanguageHelper {

    fun applyLanguage(activity: Activity) {
        val prefs = activity.getSharedPreferences("ConvertaPrefs", Context.MODE_PRIVATE)
        val languageCode = prefs.getString("language", "en") ?: "en"

        when (languageCode) {
            "zu" -> applyZulu(activity)
            "ts" -> applyTsonga(activity)
            else -> applyEnglish(activity)
        }
    }

    private fun applyEnglish(activity: Activity) {
        when (activity) {
            is MainActivity -> {
                activity.findViewById<Button>(R.id.loginButton)?.text = "Login"
                activity.findViewById<TextView>(R.id.registerLink)?.text = "Create an account"
            }
            is Register -> {
                activity.findViewById<TextView>(R.id.registerTitle)?.text = "Create Account"
                activity.findViewById<Button>(R.id.registerButton)?.text = "Register"
                activity.findViewById<TextView>(R.id.loginLink)?.text = "Already have an account? Log in"
            }
            is Converter -> {
                activity.findViewById<TextView>(R.id.titleText)?.text = "Currency Converter"
                activity.findViewById<Button>(R.id.convertButton)?.text = "Convert"
            }
            is Settings -> {
                activity.findViewById<TextView>(R.id.settingsTitle)?.text = "Settings"
                activity.findViewById<TextView>(R.id.languageLabel)?.text = "Language"
                activity.findViewById<Button>(R.id.saveButton)?.text = "Save"
                activity.findViewById<Button>(R.id.logoutButton)?.text = "Logout"
            }
        }
    }

    private fun applyZulu(activity: Activity) {
        when (activity) {
            is MainActivity -> {
                activity.findViewById<Button>(R.id.loginButton)?.text = "Ngena"
                activity.findViewById<TextView>(R.id.registerLink)?.text = "Yakha i-akhawunti"
            }
            is Register -> {
                activity.findViewById<TextView>(R.id.registerTitle)?.text = "Yakha i-akhawunti"
                activity.findViewById<Button>(R.id.registerButton)?.text = "Bhalisa"
                activity.findViewById<TextView>(R.id.loginLink)?.text = "Unayo i-akhawunti? Ngena"
            }
            is Converter -> {
                activity.findViewById<TextView>(R.id.titleText)?.text = "Isiguquli Semali"
                activity.findViewById<Button>(R.id.convertButton)?.text = "Guqula"
            }
            is Settings -> {
                activity.findViewById<TextView>(R.id.settingsTitle)?.text = "Izilungiselelo"
                activity.findViewById<TextView>(R.id.languageLabel)?.text = "Ulimi"
                activity.findViewById<Button>(R.id.saveButton)?.text = "Gcina"
                activity.findViewById<Button>(R.id.logoutButton)?.text = "Phuma"
            }
        }
    }

    private fun applyTsonga(activity: Activity) {
        when (activity) {
            is MainActivity -> {
                activity.findViewById<Button>(R.id.loginButton)?.text = "Nghena"
                activity.findViewById<TextView>(R.id.registerLink)?.text = "Vumba akhawunti"
            }
            is Register -> {
                activity.findViewById<TextView>(R.id.registerTitle)?.text = "Vumba Akhawunti"
                activity.findViewById<Button>(R.id.registerButton)?.text = "Tsarisa"
                activity.findViewById<TextView>(R.id.loginLink)?.text = "U na akhawunti? Nghena"
            }
            is Converter -> {
                activity.findViewById<TextView>(R.id.titleText)?.text = "Xihundzuluxi xa Mali"
                activity.findViewById<Button>(R.id.convertButton)?.text = "Hundzuluxa"
            }
            is Settings -> {
                activity.findViewById<TextView>(R.id.settingsTitle)?.text = "Switirhisiwa"
                activity.findViewById<TextView>(R.id.languageLabel)?.text = "Ririmi"
                activity.findViewById<Button>(R.id.saveButton)?.text = "Hlayisa"
                activity.findViewById<Button>(R.id.logoutButton)?.text = "Huma"
            }
        }
    }
}
