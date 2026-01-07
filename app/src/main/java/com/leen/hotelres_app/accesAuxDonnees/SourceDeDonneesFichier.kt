package com.leen.hotelres_app.accesAuxDonnees

import android.content.Context
import com.google.gson.GsonBuilder
import com.leen.hotelres_app.presentation.model.entities.ChambreFiltre
import java.io.File


class SourceDeDonneesFichier(private val context: Context) : ISourceDeDonneesFichier {

    private val gson = GsonBuilder().create()
    private val nomFichierChambreFiltre = "chambre_filtre.json"


    override fun getFiltres(): ChambreFiltre {
        return try {
            val file = File(context.filesDir, nomFichierChambreFiltre)

            if (!file.exists()) return ChambreFiltre()

            val json = file.readText()
            gson.fromJson(json, ChambreFiltre::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
            ChambreFiltre()
        }
    }

    override fun enregistrerFiltres(filtre: ChambreFiltre): Boolean {
        return try {
            val json = gson.toJson(filtre)
            val file = File(context.filesDir , nomFichierChambreFiltre)
            file.writeText(json)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }

    }
}