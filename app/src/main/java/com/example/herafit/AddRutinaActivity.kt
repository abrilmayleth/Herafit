package com.example.herafit

import com.google.android.material.textfield.TextInputEditText
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class AddRutinaActivity : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_rutina)

        db = FirebaseFirestore.getInstance()

        val nombre = findViewById<TextInputEditText>(R.id.nombre)
        val descripcion = findViewById<TextInputEditText>(R.id.descripcion)

        val backBtn = findViewById<ImageView>(R.id.backBtn)
        val saveBtn = findViewById<Button>(R.id.saveBtn)
        val cancelBtn = findViewById<Button>(R.id.cancelBtn)


        backBtn.setOnClickListener {
            finish()
        }


        cancelBtn.setOnClickListener {
            finish()
        }

        // guardar
        saveBtn.setOnClickListener {

            val rutina = hashMapOf(
                "nombre" to nombre.text.toString(),
                "descripcion" to descripcion.text.toString()
            )

            db.collection("rutinas")
                .add(rutina)
                .addOnSuccessListener {

                    Toast.makeText(
                        this,
                        "✅ Rutina guardada con éxito",
                        Toast.LENGTH_SHORT
                    ).show()

                    finish()
                }
                .addOnFailureListener { e ->

                    Toast.makeText(
                        this,
                        "❌ Error al guardar: ${e.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
        }
    }
}