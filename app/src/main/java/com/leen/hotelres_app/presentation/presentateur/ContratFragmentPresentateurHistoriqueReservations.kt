package com.leen.hotelres_app.presentation.presentateur

import com.leen.hotelres_app.presentation.model.entities.Reservation

interface ContratFragmentPresentateurHistoriqueReservations {

    interface IFragmentHistoriqueReservations{
        fun afficherReservations(reservations: List<Reservation>)
        fun aucuneHistorique()

        fun afficherLoader()
        fun cacherLoader()
        fun erreurDeAffichage(messageTitre: String, message: String)
    }


    interface IPresentateurHistoriqueReservations{
        fun chargerReservations()
    }
}