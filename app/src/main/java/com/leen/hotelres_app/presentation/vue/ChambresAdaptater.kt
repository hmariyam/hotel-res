package com.leen.hotelres_app.presentation.vue

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.google.android.material.carousel.CarouselLayoutManager
import com.google.android.material.carousel.CarouselSnapHelper
import com.google.android.material.carousel.FullScreenCarouselStrategy
import com.leen.hotelres_app.presentation.presentateur.ContratFragmentPresentateurChambres
import com.leen.hotelres_app.presentation.model.entities.Chambre
import com.leen.hotelres_app.R

class ChambresAdaptater(private var chambres: ArrayList<Chambre>, private val presentateurChambre: ContratFragmentPresentateurChambres.IPresentateurChambres) : RecyclerView.Adapter<ChambresAdaptater.ViewHolder>() {

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val tvTitre : TextView
        val btnDetails: Button
        val tvPrix: TextView
        val carouselPhotosRecyclerView: RecyclerView
        var snapHelper: SnapHelper? = null
        init{
            tvTitre = view.findViewById<TextView>(R.id.tv_chambre_description)
            btnDetails = view.findViewById<Button>(R.id.btn_details)
            tvPrix = view.findViewById<TextView>(R.id.tv_chambre_prix)
            carouselPhotosRecyclerView = view.findViewById<RecyclerView>(R.id.carouselPhotosRecyclerView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val createdView: View = LayoutInflater.from(parent.context).inflate(R.layout.item_chambre, parent, false)
        return ViewHolder(createdView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val chambre = chambres[position]
        holder.tvTitre.text = "Chambre de ${chambre.nombreLits} lits"

        holder.tvPrix.text = "$ ${chambre.prix}"

        holder.carouselPhotosRecyclerView.apply {
            layoutManager = CarouselLayoutManager(FullScreenCarouselStrategy())
            holder.snapHelper?.attachToRecyclerView(null)
            holder.snapHelper = CarouselSnapHelper()
            holder.snapHelper?.attachToRecyclerView(this)
            adapter = CarouselPhotosChambreAdapter(chambre.images)
        }

        holder.btnDetails.setOnClickListener {
            presentateurChambre.traiterNaviguerVersDetails(chambres[position].numero)
        }

    }
    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newData: ArrayList<Chambre>) {
        chambres = newData
        notifyDataSetChanged()
    }


    override fun getItemCount(): Int {
        return chambres.size
    }
}