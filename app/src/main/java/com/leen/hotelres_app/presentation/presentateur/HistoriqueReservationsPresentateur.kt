package com.leen.hotelres_app.presentation.presentateur

import com.leen.hotelres_app.presentation.model.ModelHotelRes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HistoriqueReservationsPresentateur(
    private val view: ContratFragmentPresentateurHistoriqueReservations.IFragmentHistoriqueReservations,
    private val modele: ModelHotelRes = ModelHotelRes
) :
    ContratFragmentPresentateurHistoriqueReservations.IPresentateurHistoriqueReservations {

    override fun chargerReservations() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                //Thread.sleep(2000)
                val reservations = modele.recupererHistoriqueReservation()
                CoroutineScope(Dispatchers.Main).launch {
                    if (reservations.isEmpty()) {
                        view.aucuneHistorique()
                    } else {
                        view.afficherReservations(reservations)
                    }
                }
            } catch (e: Exception) {
                CoroutineScope(Dispatchers.Main).launch {
                    view.erreurDeAffichage("Erreur", "Erreur lors du chargement des réservations.")
                }
            }
        }
    }
}