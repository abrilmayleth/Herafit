package com.example.herafit

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
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

        findViewById<Button>(R.id.saveBtn).setOnClickListener {
            val rutina = Rutina(
                nombre = nombre.text.toString(),
                descripcion = descripcion.text.toString()
            )

            db.collection("rutinas")
                .add(rutina)
                .addOnSuccessListener { finish() }
        }
    }
}