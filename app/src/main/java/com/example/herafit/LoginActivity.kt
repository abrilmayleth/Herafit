package com.example.herafit

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        val email = findViewById<TextInputEditText>(R.id.email)
        val password = findViewById<TextInputEditText>(R.id.password)
        val loginBtn = findViewById<Button>(R.id.loginBtn)
        val registerBtn = findViewById<Button>(R.id.registerBtn)

        val sharedPref = getSharedPreferences("loginPrefs", MODE_PRIVATE)
        val rememberCheck = findViewById<CheckBox>(R.id.rememberCheck)

        val savedEmail = sharedPref.getString("email", "")
        val savedPassword = sharedPref.getString("password", "")
        val isRemembered = sharedPref.getBoolean("remember", false)

        //para recordar los ultimos datos

        if (isRemembered) {
            email.setText(savedEmail)
            password.setText(savedPassword)
            rememberCheck.isChecked = true
        }
        // Olvidar la contraseña click
        val forgotPassword = findViewById<TextView>(R.id.forgotPassword)
        forgotPassword.setOnClickListener {

            val emailText = email.text.toString()
            if (emailText.isEmpty()) {
                Toast.makeText(this, "Introduce tu correo primero", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            FirebaseAuth.getInstance().sendPasswordResetEmail(emailText)
                .addOnSuccessListener {
                    Toast.makeText(this, "📧 Correo de recuperación enviado", Toast.LENGTH_LONG).show()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "❌ Error: ${e.message}", Toast.LENGTH_LONG).show()
                }
        }


        // login
        loginBtn.setOnClickListener {
            auth.signInWithEmailAndPassword(
                email.text.toString(),
                password.text.toString()
            ).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    startActivity(Intent(this, PerfilActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(
                        this,
                        "Error de login: ${task.exception?.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }


        loginBtn.setOnClickListener {
            val emailText = email.text.toString()
            val passwordText = password.text.toString()

            auth.signInWithEmailAndPassword(emailText, passwordText)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {

                        //  guardarlo
                        if (rememberCheck.isChecked) {
                            val editor = sharedPref.edit()
                            editor.putString("email", emailText)
                            editor.putString("password", passwordText)
                            editor.putBoolean("remember", true)
                            editor.apply()
                        } else {
                            sharedPref.edit().clear().apply()
                        }

                        startActivity(Intent(this, PerfilActivity::class.java))
                        finish()
                    }
                }
        }


        // registro
        registerBtn.setOnClickListener {
            Toast.makeText(this, "Click en registrar", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}