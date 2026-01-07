package com.leen.hotelres_app.accesAuxDonnees

import com.leen.hotelres_app.presentation.model.entities.ChambreFiltre

interface ISourceDeDonneesFichier {
    fun getFiltres(): ChambreFiltre
    fun enregistrerFiltres(filtre: ChambreFiltre): Boolean
}