package com.example.herafit

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class EditRutinaActivity : AppCompatActivity() {
    private lateinit var db: FirebaseFirestore
    private lateinit var rutinaId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_rutina)

        db = FirebaseFirestore.getInstance()

        val nombreInput = findViewById<EditText>(R.id.nombre)
        val descripcionInput = findViewById<EditText>(R.id.descripcion)
        val updateBtn = findViewById<Button>(R.id.updateBtn)

        val backBtn = findViewById<ImageView>(R.id.backBtn)

        backBtn.setOnClickListener {
            finish()
        }

        // recibir datos

        updateBtn.setOnClickListener {

            val nombreText = nombreInput.text.toString().trim()
            val descText = descripcionInput.text.toString().trim()

            if (nombreText.isEmpty() || descText.isEmpty()) {
                Toast.makeText(this, "Rellena todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val nuevosDatos = mapOf(
                "nombre" to nombreText,
                "descripcion" to descText
            )

            db.collection("rutinas")
                .document(rutinaId!!)
                .set(nuevosDatos, com.google.firebase.firestore.SetOptions.merge())
                .addOnSuccessListener {

                    Toast.makeText(
                        this,
                        "✅ Rutina actualizada correctamente",
                        Toast.LENGTH_SHORT
                    ).show()

                    finish() //
                }
                .addOnFailureListener { e ->
                    Toast.makeText(
                        this,
                        "❌ Error al actualizar: ${e.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
        }

    }
}