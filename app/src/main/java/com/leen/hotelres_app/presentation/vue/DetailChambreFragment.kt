package com.leen.hotelres_app.presentation.vue

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.google.android.material.carousel.CarouselLayoutManager
import com.google.android.material.carousel.CarouselSnapHelper
import com.google.android.material.carousel.FullScreenCarouselStrategy
import com.leen.hotelres_app.presentation.presentateur.ContratFragmentPresentateurDetailsChambre.IPresentateurDetailsChambre
import com.leen.hotelres_app.presentation.presentateur.ContratFragmentPresentateurDetailsChambre.IFragmentDetailsChambre
import com.leen.hotelres_app.presentation.presentateur.PresentateurDetailsChambre
import com.leen.hotelres_app.R
import com.leen.hotelres_app.presentation.model.ModelHotelRes

class DetailChambreFragment : Fragment(), IFragmentDetailsChambre {

    var presentateur : IPresentateurDetailsChambre = PresentateurDetailsChambre(
        this,
        ModelHotelRes )
    private lateinit var titreDetailsChambre: TextView
    private lateinit var tvNbPersonne: TextView
    private lateinit var tvDescriptionDetailsChambre : TextView
    private lateinit var tvNbLitsDetailsChambre : TextView
    private lateinit var tvInclusDetailsChambre : TextView
    private lateinit var btnReserverChambre : Button
    private lateinit var iconHome : ImageView
    private lateinit var iconHistorique : ImageView
    var snapHelper: SnapHelper? = null

    private lateinit var carouselPhotosRecyclerView : RecyclerView
//    private lateinit var iconProfil : ImageView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val vue = inflater.inflate(R.layout.fragment_details_chambre, container, false)
        return vue
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        attacherTextViews(view)
        attacherBoutons(view)
        attacherEcouteursNavigation(view)
        Log.d("DEBUG", presentateur.toString())
        presentateur.initialiserVue()
    }

    private fun attacherTextViews(vue : View){
        Log.d("DEBUG", "Attach Text View")
        titreDetailsChambre = vue.findViewById<TextView>(R.id.titreDetailsChambre)
        tvDescriptionDetailsChambre = vue.findViewById<TextView>(R.id.tvDescriptionDetailsChambre)
        tvInclusDetailsChambre = vue.findViewById<TextView>(R.id.tvInclusDetailsChambre)
        tvNbLitsDetailsChambre = vue.findViewById<TextView>(R.id.tvNombreLitsDetailsChambre)
        tvNbPersonne = vue.findViewById<TextView>(R.id.tvNbPersonneDetailsChambre)
        carouselPhotosRecyclerView = vue.findViewById<RecyclerView>(R.id.carouselPhotosRecyclerView)
    }

    private fun attacherBoutons(vue : View){
        Log.d("DEBUG", "Attach Button")
        btnReserverChambre = vue.findViewById<Button>(R.id.btnReserverChambre)
        iconHome = vue.findViewById<ImageView>(R.id.home_icon)
        iconHistorique = vue.findViewById<ImageView>(R.id.save_icon)
    }

    private fun attacherEcouteursNavigation(vue: View){
        btnReserverChambre.setOnClickListener {
            presentateur.naviguerVersReservation()
            vue.findNavController().navigate(R.id.action_ecran_details_chambre_to_ecran_reservation_chambre)
        }

        iconHome.setOnClickListener {
            presentateur.naviguerVersAccueil()
            vue.findNavController().navigate(R.id.action_ecran_details_chambre_to_ecran_liste_chambres)
        }

        iconHistorique.setOnClickListener {
            presentateur.naviguerVersHistorique()
            vue.findNavController().navigate(R.id.action_ecran_details_chambre_to_ecran_historique_reservations)
        }
    }

    override fun chargerInfosChambre(
        numero: String,
        nbreLits: Int,
        description: String,
        inclusions: String,
        nbrePersonnes: Int,
        prixParNuit: String,
        urlsPhotos: Array<String>
    ) {
        titreDetailsChambre.text = getString(
            R.string.details_chambre_titre,
            numero
        )
        tvNbPersonne.text = getString(
            R.string.detail_chambre_nb_personnes,
            nbrePersonnes
        )
        tvDescriptionDetailsChambre.setText(description)
        tvNbLitsDetailsChambre.text = getString(
            R.string.details_chambre_nombre_de_lits,
            nbreLits
        )
        tvInclusDetailsChambre.text = getString(
            R.string.details_chambre_inclusion,
            inclusions
        )
        btnReserverChambre.text = getString(
            R.string.details_chambre_reserver_prix_nuit,
            prixParNuit
        )

        carouselPhotosRecyclerView.apply {
            layoutManager = CarouselLayoutManager(FullScreenCarouselStrategy())
            snapHelper?.attachToRecyclerView(null)
            snapHelper = CarouselSnapHelper()
            snapHelper?.attachToRecyclerView(this)
            adapter = CarouselPhotosChambreAdapter(urlsPhotos)
        }
    }
}