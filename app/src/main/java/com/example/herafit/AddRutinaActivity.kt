package com.example.herafit

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
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


        val nombre = findViewById<EditText>(R.id.nombre)
        val descripcion = findViewById<EditText>(R.id.descripcion)
        val backBtn = findViewById<ImageView>(R.id.backBtn)

        // flecha de volver
        backBtn.setOnClickListener {
            finish()
        }

        // boton de guardar,
        findViewById<Button>(R.id.saveBtn).setOnClickListener {
            val rutina = Rutina(
                nombre = nombre.text.toString(),
                descripcion = descripcion.text.toString()
            )

            val cancelBtn = findViewById<Button>(R.id.cancelBtn)
            cancelBtn.setOnClickListener {
                // Vuelve al perfil
                startActivity(Intent(this, PerfilActivity::class.java))
                finish()
            }

            // mensajes de guardado, cancelado

            db.collection("rutinas")
                .add(rutina)
                .addOnSuccessListener {

                    Toast.makeText(
                        this,
                        "✅ Rutina guardada con éxito",
                        Toast.LENGTH_SHORT
                    ).show()

                    finish() // vuelve a RutinasActivity
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