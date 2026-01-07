package com.leen.hotelres_app.services

import com.leen.hotelres_app.presentation.model.entities.Chambre
import com.leen.hotelres_app.accesAuxDonnees.ISourceDeDonneesFichier
import com.leen.hotelres_app.accesAuxDonnees.SourceDeDonneesHTTP
import com.leen.hotelres_app.presentation.model.entities.ChambreFiltre

class ChambresService(private val httpSource: SourceDeDonneesHTTP = SourceDeDonneesHTTP(), private var sourceFichiers : ISourceDeDonneesFichier) {

    fun recupérerChambres(filtre: ChambreFiltre): List<Chambre> {
        return httpSource.recupererChambres(filtre)
    }

    fun récupérerChambreparId(num: String): Chambre? {
        return httpSource.recupererChambresParNum(num)
    }

    fun enregristrerFiltre(filtre: ChambreFiltre): Boolean {
        return sourceFichiers.enregistrerFiltres(filtre)
    }

    fun recupererFiltre() : ChambreFiltre {
        return sourceFichiers.getFiltres()
    }
}