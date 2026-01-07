package com.leen.hotelres_app.presentation.presentateur

interface ContratFragmentPresentateurDetailsChambre {
    interface IFragmentDetailsChambre{
        fun chargerInfosChambre(
            numero : String,
            nbreLits : Int,
            description : String,
            inclusions : String,
            nbrePersonnes : Int,
            prixParNuit : String,
            urlsPhotos : Array<String>
        )
    }

    interface IPresentateurDetailsChambre{
        fun initialiserVue()
        fun naviguerVersReservation()
        fun naviguerVersAccueil()
        fun naviguerVersHistorique()
        //fun naviguerVersProfil()
    }
}
