package com.example.herafit

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RutinaAdapter(
    private val lista: List<Rutina>,
    private val onSelect: (Rutina) -> Unit,
    private val onEditClick: (Rutina) -> Unit,
    private val onDeleteClick: (Rutina) -> Unit
) : RecyclerView.Adapter<RutinaAdapter.ViewHolder>() {

    private var selectedId: String? = null

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nombre: TextView = view.findViewById(R.id.nombre)
        val descripcion: TextView = view.findViewById(R.id.descripcion)
        val editBtn: Button = view.findViewById(R.id.editBtn)
        val deleteBtn: Button = view.findViewById(R.id.deleteBtn)
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

        // cambio de color
        if (rutina.id == selectedId) {
            holder.itemView.setBackgroundColor(
                holder.itemView.context.getColor(R.color.rosaHerafit)
            )
        } else {
            holder.itemView.setBackgroundColor(android.graphics.Color.TRANSPARENT)
        }

        holder.itemView.setOnClickListener {
            selectedId = rutina.id
            onSelect(rutina)
            notifyDataSetChanged()
        }

        // editar
        holder.editBtn.setOnClickListener {
            holder.itemView.performClick()
            onEditClick(rutina)
        }

        // eliminar
        holder.deleteBtn.setOnClickListener {
            holder.itemView.performClick()
            onDeleteClick(rutina)

        }

    }

    override fun getItemCount(): Int = lista.size
}