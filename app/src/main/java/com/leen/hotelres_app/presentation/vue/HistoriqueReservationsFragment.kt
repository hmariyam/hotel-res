package com.leen.hotelres_app.presentation.vue

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.leen.hotelres_app.presentation.model.entities.Reservation
import com.leen.hotelres_app.presentation.presentateur.ContratFragmentPresentateurHistoriqueReservations
import com.leen.hotelres_app.R
import com.leen.hotelres_app.presentation.presentateur.HistoriqueReservationsPresentateur

class HistoriqueReservationsFragment : Fragment(),
    ContratFragmentPresentateurHistoriqueReservations.IFragmentHistoriqueReservations {

    private lateinit var adapter: HistoriqueReservationsAdapter
    private lateinit var recyclerView: RecyclerView

    private lateinit var presenter: HistoriqueReservationsPresentateur
    private lateinit var loadHistoriqueReservation: TextView
    private var isLoading = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_historique_reservations, container, false)

        //Icon de la page principale
        val IconAccueil: ImageView = view.findViewById(R.id.home_icon)
        IconAccueil.setOnClickListener {
            findNavController().navigate(R.id.action_ecran_historique_reservations_to_ecran_liste_chambres)
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerReservations)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        adapter = HistoriqueReservationsAdapter(emptyList())
        recyclerView.adapter = adapter

        loadHistoriqueReservation = view.findViewById(R.id.loadHistoriqueReservation)
        presenter = HistoriqueReservationsPresentateur(this)

        afficherLoader()
        presenter.chargerReservations()
    }

    override fun afficherReservations(reservations: List<Reservation>) {
        cacherLoader()
        adapter.updateData(reservations)
    }

    override fun aucuneHistorique() {
        cacherLoader()

        Toast.makeText(requireContext(),
            getString(R.string.aucune_historique_afficher), Toast.LENGTH_SHORT).show()
    }

    override fun erreurDeAffichage(messageTitre: String, message: String) {
        cacherLoader()

        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(messageTitre)
            .setMessage(message)
            .setPositiveButton("OK", null)

        builder.show()
    }

    override fun afficherLoader() {
        isLoading = true
        loadHistoriqueReservation.visibility = View.VISIBLE

        loadHistoriqueReservation.animate()
            .rotationBy(360f)
            .setDuration(700)
            .withEndAction { if (isLoading) afficherLoader() }
            .start()
    }

    override fun cacherLoader() {
        isLoading = false
        loadHistoriqueReservation.clearAnimation()
        loadHistoriqueReservation.visibility = View.GONE
    }

}