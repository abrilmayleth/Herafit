package com.example.herafit

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class PerfilActivity : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil)

        db = FirebaseFirestore.getInstance()

        val nombreUsuario = findViewById<TextView>(R.id.nombreUsuario)
        val pesoBubble = findViewById<TextView>(R.id.pesoBubble)
        val alturaBubble = findViewById<TextView>(R.id.alturaBubble)
        val edadBubble = findViewById<TextView>(R.id.edadBubble)

        cargarPerfil(nombreUsuario, pesoBubble, alturaBubble, edadBubble)

        // modificar perfil
        findViewById<Button>(R.id.editBtn).setOnClickListener {
            startActivity(Intent(this, EditPerfilActivity::class.java))
        }

        //  ir a rutinas
        findViewById<Button>(R.id.rutinasLabel).setOnClickListener {
            startActivity(Intent(this, RutinasActivity::class.java))
        }

        // salir del registro y volver a bienvenida
        val logoutBtn = findViewById<Button>(R.id.logoutBtn)
        logoutBtn.setOnClickListener {
            //Cerrar sesión de Firebase
            FirebaseAuth.getInstance().signOut()

            //Volver a bienvenida
            startActivity(Intent(this, MainActivity::class.java))

            //Cerrar PerfilActivity
            finish()
        }

    }

    override fun onResume() {
        super.onResume()
        val nombreUsuario = findViewById<TextView>(R.id.nombreUsuario)
        val pesoBubble = findViewById<TextView>(R.id.pesoBubble)
        val alturaBubble = findViewById<TextView>(R.id.alturaBubble)
        val edadBubble = findViewById<TextView>(R.id.edadBubble)

        cargarPerfil(nombreUsuario, pesoBubble, alturaBubble, edadBubble)
    }

    private fun cargarPerfil(
        nombreUsuario: TextView,
        peso: TextView,
        altura: TextView,
        edad: TextView
    ) {
        db.collection("perfiles")
            .document("usuario_actual")
            .get()
            .addOnSuccessListener { doc ->
                val perfil = doc.toObject(PerfilUsuario::class.java)
                if (perfil != null) {
                    nombreUsuario.text = perfil.nombre
                    peso.text = "Peso\n${perfil.peso}"
                    altura.text = "Altura\n${perfil.altura}"
                    edad.text = "Edad\n${perfil.edad}"
                }
            }
    }

}
