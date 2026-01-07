package com.leen.hotelres_app.presentation.presentateur

import com.leen.hotelres_app.presentation.model.entities.Chambre
import com.leen.hotelres_app.presentation.model.entities.ChambreFiltre
import com.leen.hotelres_app.presentation.model.ModelHotelRes
import com.leen.hotelres_app.presentation.utils.NoInternetException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PresentateurChambres(
    private val vue: ContratFragmentPresentateurChambres.IVueChambres,
    private val model: ModelHotelRes = ModelHotelRes,
    private val secondaryDispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val mainDispatcher: CoroutineDispatcher = Dispatchers.Main
) : ContratFragmentPresentateurChambres.IPresentateurChambres {

    private var chambresDisponibles: ArrayList<Chambre> = ArrayList<Chambre>()



    override fun initialiserVue() {
        vue.afficherFiltres(
            model.recupererFiltre()
        )
    }

    override fun traiterNaviguerVersDetails(numero: String) {
        try {
            val chambre = chambresDisponibles.first() { it.numero == numero }
            model.selectionnerChambre(chambre)
            vue.naviguerVersDetails()
        }catch (ex: NoSuchElementException){
            vue.afficherAlerte(
                "impossible de voir les détails",
                "Impossible de voir les détails de la chambre. Elle n'existe peut-être plus")
        }

    }

    override fun traiterFiltrer() {
        val filtre: ChambreFiltre = vue.obtenirFiltres()

        filtre.prix?.let {
            if(it < 0 ){
                vue.afficherNotification("Le prix maximal ne peut pas être négatif..")
                return
            }
        }

        CoroutineScope(secondaryDispatcher).launch {
            try{
                model.enregristrerFiltre(filtre)
                chambresDisponibles= model.recupererListeChambres(filtre) as ArrayList<Chambre>
                CoroutineScope(mainDispatcher).launch {
                    vue.afficherChambres(
                        chambresDisponibles
                    )
                }

            } catch (e: NoInternetException) {
                CoroutineScope(mainDispatcher).launch {
                    vue.afficherAlerte("Pas d'internet frero", "Veuillez vérifier votre connexion Internet.")
                }
            }
        }
    }

    override fun traiterNaviguerVersHistoriqueReservations() {
        vue.naviguerVersHistoriqueReservations()
    }
}