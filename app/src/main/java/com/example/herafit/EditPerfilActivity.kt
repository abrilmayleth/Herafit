package com.example.herafit

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class EditPerfilActivity : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_perfil)

        db = FirebaseFirestore.getInstance()

        val nombreInput = findViewById<EditText>(R.id.nombreInput)
        val edadInput = findViewById<EditText>(R.id.edadInput)
        val pesoInput = findViewById<EditText>(R.id.pesoInput)
        val alturaInput = findViewById<EditText>(R.id.alturaInput)

        val saveBtn = findViewById<Button>(R.id.saveBtn)

        val backBtn = findViewById<ImageView>(R.id.backBtn)

        // quiero volver atras al perfil
        backBtn.setOnClickListener {
            finish()
        }


        // Cargar datos actuales correctamente
        db.collection("perfiles")
            .document("usuario_actual")
            .get()
            .addOnSuccessListener { doc ->
                if (doc.exists()) {
                    val nombre = doc.getString("nombre") ?: ""
                    val edad = doc.getLong("edad") ?: 0
                    val peso = doc.getDouble("peso") ?: 0.0
                    val altura = doc.getDouble("altura") ?: 0.0

                    nombreInput.setText(nombre)
                    edadInput.setText(edad.toString())
                    pesoInput.setText(peso.toString())
                    alturaInput.setText(altura.toString())
                }
            }
            .addOnFailureListener {
                Toast.makeText(this, "❌ Error al cargar datos", Toast.LENGTH_SHORT).show()
            }

        // 🔹 Guardar cambios al pulsar el botón
        saveBtn.setOnClickListener {

            val nombreText = nombreInput.text.toString().trim()
            val edadText = edadInput.text.toString().trim()
            val pesoText = pesoInput.text.toString().trim()
            val alturaText = alturaInput.text.toString().trim()

            // Validar campos vacíos
            if (nombreText.isEmpty() || edadText.isEmpty() || pesoText.isEmpty() || alturaText.isEmpty()) {
                Toast.makeText(this, "⚠️ Rellena todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val edad = edadText.toIntOrNull()
            val peso = pesoText.toDoubleOrNull()
            val altura = alturaText.toDoubleOrNull()

            // Validar números
            if (edad == null || peso == null || altura == null) {
                Toast.makeText(this, "⚠️ Introduce valores válidos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val nuevosDatos = mapOf(
                "nombre" to nombreText,
                "edad" to edad,
                "peso" to peso,
                "altura" to altura
            )

            db.collection("perfiles")
                .document("usuario_actual")
                .set(nuevosDatos, com.google.firebase.firestore.SetOptions.merge())
                .addOnSuccessListener {

                    Toast.makeText(
                        this,
                        "✅ Datos actualizados correctamente",
                        Toast.LENGTH_SHORT
                    ).show()

                    finish()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "💥 FALLA: ${e.message}", Toast.LENGTH_LONG).show()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(
                        this,
                        "❌ Error Firebase: ${e.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
        }
    }
}