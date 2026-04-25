package com.example.herafit

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RutinaAdapter(private val lista: List<Rutina>) : RecyclerView.Adapter<RutinaAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nombre: TextView = view.findViewById(R.id.nombre)
        val descripcion: TextView = view.findViewById(R.id.descripcion)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_rutina, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val rutina = lista[position]
        holder.nombre.text = rutina.nombre
        holder.descripcion.text = rutina.descripcion
    }

    override fun getItemCount() = lista.size
}