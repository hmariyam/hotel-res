package com.leen.hotelres_app.presentation.presentateur

import com.leen.hotelres_app.presentation.model.entities.Chambre
import com.leen.hotelres_app.presentation.model.entities.ChambreFiltre

interface ContratFragmentPresentateurChambres {
    interface IVueChambres {

        fun afficherChambres(chambres: ArrayList<Chambre>)

        fun naviguerVersDetails()
        fun naviguerVersHistoriqueReservations()

        // faire une notification
        fun afficherNotification(texte: String)
        fun afficherAlerte(messageTitre: String,  message: String)

        // permet de recupérer les filtres à l'écran
        fun obtenirFiltres(): ChambreFiltre

        // permet d'afficher les filtres à l'ecran
        fun afficherFiltres(filtre: ChambreFiltre)

        fun afficherLoader()
        fun cacherLoader()
    }

    interface IPresentateurChambres{

        fun initialiserVue()
        fun traiterNaviguerVersDetails(numero : String)

        fun traiterFiltrer()

        fun traiterNaviguerVersHistoriqueReservations()
    }
}