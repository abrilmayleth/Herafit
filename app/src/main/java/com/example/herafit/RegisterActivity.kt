package com.example.herafit

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        val nombreInput = findViewById<EditText>(R.id.nombre)
        val emailInput = findViewById<EditText>(R.id.email)
        val passwordInput = findViewById<EditText>(R.id.password)
        val edadInput = findViewById<EditText>(R.id.edadInput)
        val pesoInput = findViewById<EditText>(R.id.pesoInput)
        val alturaInput = findViewById<EditText>(R.id.alturaInput)

        val cancelBtn = findViewById<Button>(R.id.cancelBtn)

        val backBtn = findViewById<ImageView>(R.id.backBtn)

        // flechita para atrás
        backBtn.setOnClickListener {
            finish()
        }

        // para cancelar
        cancelBtn.setOnClickListener {
            finish()
        }

        // para poder registrar
        findViewById<Button>(R.id.registerBtn).setOnClickListener {

            val email = emailInput.text.toString()
            val password = passwordInput.text.toString()

            val edadText = edadInput.text.toString()
            val pesoText = pesoInput.text.toString()
            val alturaText = alturaInput.text.toString()

            if (email.isEmpty() || password.isEmpty() ||
                edadText.isEmpty() || pesoText.isEmpty() || alturaText.isEmpty()
            ) {
                Toast.makeText(this, "Rellena todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password.length < 6) {
                Toast.makeText(this, "La contraseña debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->

                    if (task.isSuccessful) {

                        val perfil = PerfilUsuario(
                            edad = edadText.toInt(),
                            peso = pesoText.toFloat(),
                            altura = alturaText.toFloat()
                        )

                        db.collection("perfiles")
                            .document("usuario_actual")
                            .set(perfil)
                            .addOnSuccessListener {
                                startActivity(Intent(this, PerfilActivity::class.java))
                                finish()
                            }

                    } else {
                        Toast.makeText(this, "Error: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                    }
                }
        }
    }
}
