package com.example.herafit

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class DeleteRutinaActivity : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore
    private var rutinaId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delete_rutina)

        db = FirebaseFirestore.getInstance()

        val nombre = findViewById<TextView>(R.id.nombre)
        val descripcion = findViewById<TextView>(R.id.descripcion)
        val deleteBtn = findViewById<Button>(R.id.deleteBtn)

        val backBtn = findViewById<ImageView>(R.id.backBtn)

        // quiero volver atras al perfil
        backBtn.setOnClickListener {
            finish()
        }

        // recibir datos
        rutinaId = intent.getStringExtra("RUTINA_ID")
        val rutinaNombre = intent.getStringExtra("RUTINA_NOMBRE")
        val rutinaDesc = intent.getStringExtra("RUTINA_DESC")

        nombre.text = rutinaNombre
        descripcion.text = rutinaDesc

        // eliminar
        deleteBtn.setOnClickListener {

            if (rutinaId == null) {
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            db.collection("rutinas")
                .document(rutinaId!!)
                .delete()
                .addOnSuccessListener {

                    Toast.makeText(this, "✅ Rutina eliminada", Toast.LENGTH_SHORT).show()

                    finish()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "❌ Error al eliminar", Toast.LENGTH_SHORT).show()
                }
        }

        val cancelBtn = findViewById<Button>(R.id.cancelBtn)

        cancelBtn.setOnClickListener {

            Toast.makeText(this, "❌ Cancelado", Toast.LENGTH_SHORT).show()

            finish()
        }
    }
}