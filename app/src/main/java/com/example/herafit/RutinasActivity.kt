package com.example.herafit

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class RutinasActivity : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore
    private lateinit var adapter: RutinaAdapter
    private val lista = mutableListOf<Rutina>()
    private var rutinaSeleccionada: Rutina? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rutinas)

        db = FirebaseFirestore.getInstance()

        val recycler = findViewById<RecyclerView>(R.id.recycler)
        recycler.layoutManager = LinearLayoutManager(this)

        // crear adapter
        adapter = RutinaAdapter(
            lista,
            onSelect = { rutina ->
                rutinaSeleccionada = rutina
            },
            onEditClick = { rutina ->
                val intent = Intent(this, EditRutinaActivity::class.java)
                intent.putExtra("RUTINA_ID", rutina.id)
                intent.putExtra("RUTINA_NOMBRE", rutina.nombre)
                intent.putExtra("RUTINA_DESC", rutina.descripcion)
                startActivity(intent)
            },
            onDeleteClick = { rutina ->
                val intent = Intent(this, DeleteRutinaActivity::class.java)
                intent.putExtra("RUTINA_ID", rutina.id)
                intent.putExtra("RUTINA_NOMBRE", rutina.nombre)
                intent.putExtra("RUTINA_DESC", rutina.descripcion)
                startActivity(intent)
            }
        )

        recycler.adapter = adapter

        //flecha
        findViewById<ImageView>(R.id.backBtn).setOnClickListener {
            finish()
        }

        // añadir
        findViewById<Button>(R.id.addBtn).setOnClickListener {
            startActivity(Intent(this, AddRutinaActivity::class.java))
        }
    }

    // recargar
    override fun onResume() {
        super.onResume()
        cargarRutinas()
    }

    // para rellenar
    private fun cargarRutinas() {
        db.collection("rutinas")
            .get()
            .addOnSuccessListener { result ->
                lista.clear()
                for (doc in result) {
                    val rutina = doc.toObject(Rutina::class.java)
                    rutina.id = doc.id
                    lista.add(rutina)
                }
                adapter.notifyDataSetChanged()
            }
    }
}



