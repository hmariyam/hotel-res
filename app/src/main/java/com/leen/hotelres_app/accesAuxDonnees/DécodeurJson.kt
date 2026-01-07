package com.leen.hotelres_app.accesAuxDonnees

import com.leen.hotelres_app.presentation.model.entities.Chambre
import com.leen.hotelres_app.presentation.model.entities.Reservation
import org.json.JSONArray
import java.text.SimpleDateFormat
import java.util.Locale

class DécodeurJson {
    private val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    fun decodeHistorique(json: String): List<Reservation> {
        val arr = JSONArray(json)
        val list = mutableListOf<Reservation>()
        //val sdf = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.US)

        for (i in 0 until arr.length()) {
            val obj = arr.getJSONObject(i)

            val dateDebut = sdf.parse(obj.getString("date_debut"))?.time ?: 0L
            val dateFin = sdf.parse(obj.getString("date_fin"))?.time ?: 0L
            val prix = obj.optDouble("prix", 0.0).toFloat()

            list.add(
                Reservation(
                    numero = obj.optInt("chambre_id").toString(),
                    dateDébut = dateDebut,
                    dateFin = dateFin,
                    dateReservation = obj.optString("date_reservation", ""),
                    prix = prix,
                    nbPersonnes = obj.optInt("nb_personnes", 0)
                )
            )
        }

        return list
    }

    fun decodeChambres(json: String): List<Chambre> {
        val arr = JSONArray(json)
        val list = mutableListOf<Chambre>()

        for (i in 0 until arr.length()) {
            val obj = arr.getJSONObject(i)

            val id = obj.optInt("id", 0)
            val numero = obj.optString("numero_appart", "N/A")
            val nombreLits = obj.optInt("nbre_lits", 0)
            val description = obj.optString("description", "")
            val suite = obj.optString("type", "")
            val prix = obj.optDouble("prix_par_nuit", 0.0).toFloat()
            val nombrePersonnes = obj.optInt("nbre_lits", 0)

            val sourcesJson = obj.optJSONArray("sources")
            val images = if (sourcesJson != null && sourcesJson.length() > 0) {
                Array(sourcesJson.length()) { idx ->
                    sourcesJson.getJSONObject(idx).optString("source", "")
                }.filter { it.isNotBlank() }.toTypedArray()
            } else {
                arrayOf(
                    "https://picsum.photos/800/400?image=10",
                    "https://picsum.photos/800/400?image=20",
                    "https://picsum.photos/800/400?image=30"
                )
            }

            val inclusionsJson = obj.optJSONArray("inclusions")
            val inclusions = if(inclusionsJson != null && inclusionsJson.length() > 0){
                Array(inclusionsJson.length()) { idx ->
                    inclusionsJson.getJSONObject(idx).optString("nom", "")
                }.filter { it.isNotBlank() }.joinToString(", ")
            }else {
                "Non repertoriés"
            }

            list.add(
                Chambre(
                    id = id,
                    numero = numero,
                    nombreLits = nombreLits,
                    nombrePersonnes = nombrePersonnes,
                    inclusions = inclusions,
                    description = description,
                    images = images,
                    suite = suite,
                    prix = prix
                )
            )
        }

        return list
    }

    fun decodeChambreParNumero(json: String, numeroRecherche: String): Chambre? {
        val arr = JSONArray(json)

        for (i in 0 until arr.length()) {
            val obj = arr.getJSONObject(i)
            val id = obj.optInt("id", 0)
            val numero = obj.optString("numero_appart", "N/A")

            if (numero == numeroRecherche) {
                val nombreLits = obj.optInt("nbre_lits", 0)
                val description = obj.optString("description", "")
                val suite = obj.optString("type", "")
                val prix = obj.optDouble("prix_par_nuit", 0.0).toFloat()
                val nombrePersonnes = obj.optInt("nbre_personnes", 0)
                val inclusions = ""

                val sourcesJson = obj.optJSONArray("sources")
                val images = if (sourcesJson != null && sourcesJson.length() > 0) {
                    Array(sourcesJson.length()) { idx ->
                        sourcesJson.getJSONObject(idx).optString("source", "")
                    }
                } else {
                    arrayOf(
                        "https://picsum.photos/800/400?image=10",
                        "https://picsum.photos/800/400?image=20",
                        "https://picsum.photos/800/400?image=30"
                    )
                }

                return Chambre(
                    id=id,
                    numero = numero,
                    nombreLits = nombreLits,
                    nombrePersonnes = nombrePersonnes,
                    inclusions = inclusions,
                    description = description,
                    images = images,
                    suite = suite,
                    prix = prix
                )
            }
        }
        return null
    }
}