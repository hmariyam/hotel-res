package com.leen.hotelres_app.presentation.vue

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.leen.hotelres_app.R
import com.leen.hotelres_app.presentation.model.entities.Reservation
import com.leen.hotelres_app.presentation.utils.viewUtils

class HistoriqueReservationsAdapter(
    private var reservations: List<Reservation>
) : RecyclerView.Adapter<HistoriqueReservationsAdapter.ReservationViewHolder>() {

    class ReservationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitre: TextView = itemView.findViewById(R.id.tvTitre)
        val tvDates: TextView = itemView.findViewById(R.id.tvDates)
        val tvDateReservation: TextView = itemView.findViewById(R.id.tvDateReservation)
        val tvPrix: TextView = itemView.findViewById(R.id.tvPrix)
        val tvNbrPersonnes: TextView = itemView.findViewById(R.id.tvNbrPersonnes)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReservationViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_historique, parent, false)
        return ReservationViewHolder(view)
    }

    override fun getItemCount(): Int = reservations.size

    override fun onBindViewHolder(holder: ReservationViewHolder, position: Int) {
        val reservation = reservations[position]

        val dateDebut = viewUtils.convertirEpochEnDateFormattee(reservation.dateDébut)
        val dateFin = viewUtils.convertirEpochEnDateFormattee(reservation.dateFin)

        val dateRange = "$dateDebut   ->    $dateFin"

        holder.tvTitre.text = holder.itemView.context.getString(
            R.string.historique_reservation_titre,
            reservation.numero
        )
        holder.tvDates.text = dateRange
        if(reservation.nbPersonnes == null){
            holder.tvNbrPersonnes.text = holder.itemView.context.getString(
                R.string.nombre_de_personnes, 0
            )
        } else {
            holder.tvNbrPersonnes.text = holder.itemView.context.getString(
                R.string.nombre_de_personnes, reservation.nbPersonnes
            )
        }
        holder.tvDateReservation.text = holder.itemView.context.getString(
            R.string.date_de_reservation, reservation.dateReservation
        )
        holder.tvPrix.text = holder.itemView.context.getString(
            R.string.prix_nuit, String.format("%.2f", reservation.prix)
        )
    }

    fun updateData(newReservations: List<Reservation>) {
        reservations = newReservations
        notifyDataSetChanged()
    }
}