package com.leen.hotelres_app.presentation.vue

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.provider.CalendarContract
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import androidx.core.util.Pair
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.google.android.material.datepicker.MaterialDatePicker
import com.leen.hotelres_app.presentation.presentateur.ContratFragmentPresentateurFormulaireReservation.IFragmentFormulaireReservations
import com.leen.hotelres_app.presentation.presentateur.ContratFragmentPresentateurFormulaireReservation.IPresentateurFormulaireReservations
import com.leen.hotelres_app.presentation.presentateur.FormulaireReservationsPresentateur
import com.leen.hotelres_app.R
import com.leen.hotelres_app.presentation.model.ModelHotelRes
import com.leen.hotelres_app.presentation.model.entities.AjustementReservation
import com.leen.hotelres_app.presentation.utils.viewUtils

class FormulaireReservationsFragment : Fragment(), IFragmentFormulaireReservations {

    var presentateur: IPresentateurFormulaireReservations = FormulaireReservationsPresentateur(
        this,
        ModelHotelRes)

    private lateinit var btnConfirmation : Button
    private lateinit var btnModifierDatesChoisi : Button
    private lateinit var tvDatesChoisi : TextView
    private lateinit var title: TextView
    private lateinit var btnRetour : ImageButton
    private lateinit var spinnerNbPersonnes: Spinner
    private lateinit var tvPrixSelonNbPersonnes: TextView
    private lateinit var iconeAccueil: ImageView
    private lateinit var iconeHistorique: ImageView
    private var dateDébutVar: Long = 0L
    private var dateFinVar: Long = 0L
    private var ajustementPrix: Float = 0f
    private var ajustementDateDebut: Long = 0L
    private var ajustementDateFin: Long = 0L
    private var ajustementNbrePersonnes: Int = 0
    private var prixinitial: Float = 0f

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        val vue = inflater.inflate(R.layout.fragment_formulaire_reservation, container, false)
        return vue
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        attacherElementsVue(view)
        attacherBoutons(view)
        attacherEcouteursNavigation(view)
        Log.d("DEBUG", presentateur.toString())
        presentateur.initialiserVue()
    }

    private fun attacherBoutons(vue : View){
        Log.d("DEBUG", "Attach Button")
        iconeAccueil = vue.findViewById<ImageView>(R.id.home_icon)
        iconeHistorique = vue.findViewById<ImageView>(R.id.save_icon)
    }

    private fun attacherElementsVue(vue: View){
        Log.d("DEBUG", "Attach Element")
        btnConfirmation = vue.findViewById<Button>(R.id.btnConfirmerReservation)
        btnModifierDatesChoisi = vue.findViewById<Button>(R.id.btnModifierDates)
        tvDatesChoisi = vue.findViewById<TextView>(R.id.tvDatesChoisis)
        btnRetour = vue.findViewById<ImageButton>(R.id.btnRetour)
        spinnerNbPersonnes = vue.findViewById<Spinner>(R.id.spinnerNbPersonnes)
        tvPrixSelonNbPersonnes = vue.findViewById<TextView>(R.id.tvPrixParPersonnes)
        title = vue.findViewById<TextView>(R.id.title)
    }

    private fun attacherEcouteursNavigation(vue : View){

        iconeAccueil.setOnClickListener {
            vue.findNavController().navigate(R.id.action_ecran_reservation_chambre_to_ecran_liste_chambres)
        }

        iconeHistorique.setOnClickListener {
            vue.findNavController().navigate(R.id.action_ecran_reservation_chambre_to_ecran_historique_reservations)
        }

        btnConfirmation.setOnClickListener {
            presentateur.enregistrerReservation()
            vue.findNavController().navigate(R.id.action_ecran_reservation_chambre_to_ecran_historique_reservations)
        }

        btnRetour.setOnClickListener {
            vue.findNavController().navigate(R.id.action_ecran_reservation_chambre_to_ecran_details_chambre)
        }
    }

    fun ajouterEvenementAuCalendrier(startMillis: Long, endMillis: Long) {
        val intent = Intent(Intent.ACTION_INSERT).apply {
            data = CalendarContract.Events.CONTENT_URI
            putExtra(CalendarContract.Events.EVENT_TIMEZONE, "UTC")
            putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startMillis)
            putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endMillis)
            putExtra(CalendarContract.Events.TITLE, "Réservation d'hôtel")
            putExtra(CalendarContract.Events.DESCRIPTION, "Votre réservation est confirmée!")
            putExtra(CalendarContract.Events.EVENT_LOCATION, "HotelRes")
        }
        startActivity(intent)
    }

    override fun afficherDialogue(message: String, isSaved: Boolean){

        if (isSaved) {
            AlertDialog.Builder(requireContext())
                .setTitle("Confirmation")
                .setMessage(message)
                .setPositiveButton("OK") { _, _ ->
                    ajouterEvenementAuCalendrier(dateDébutVar, dateFinVar)

                }
                .show()
        } else {
            AlertDialog.Builder(requireContext())
                .setTitle("Erreur")
                .setMessage(message)
                .setPositiveButton("OK") { _, _ ->
                }
                .show()
        }
    }

    override fun chargerInfoReservation(titre: String, dateDébut: Long, dateFin: Long, nbPersonnes: Int, prix: Float) {

        ajustementNbrePersonnes = nbPersonnes
        ajustementDateDebut = dateDébut
        ajustementDateFin = dateFin

        prixinitial = prix

        title.text = getString(
            R.string.reservation_pour, titre
        )

        dateDébutVar = dateDébut
        dateFinVar = dateFin
        val dateDébutString = viewUtils.convertirEpochEnDateFormattee(dateDébut)
        val dateFinString = viewUtils.convertirEpochEnDateFormattee(dateFin)

        tvDatesChoisi.text = tvDatesChoisi.context.getString(
            R.string.dates_reservationForm,
            dateDébutString,
            dateFinString
        )

        btnModifierDatesChoisi.setOnClickListener{
            val sélecteurDates = MaterialDatePicker.Builder.dateRangePicker()
                .setTheme(R.style.MaterialCalendarTheme)
                .setTitleText(getString(R.string.choissisez_vos_dates_de_reservation))
                .setSelection(Pair(dateDébut, dateFin))
                .setCalendarConstraints(null) // À implémenter au plus tard pour éviter que l'utilisateur sélectionne des dates hors de la sélection
                .build()

            sélecteurDates.show(parentFragmentManager, "TAG")

            sélecteurDates.addOnPositiveButtonClickListener {


                dateDébutVar = it.first
                dateFinVar = it.second

                ajustementDateDebut = dateDébutVar
                ajustementDateFin = dateFinVar

                val dateDébutString = viewUtils.convertirEpochEnDateFormattee(it.first)
                val dateFinString = viewUtils.convertirEpochEnDateFormattee(it.second)

                presentateur.ajusterReservation()

                tvDatesChoisi.text = tvDatesChoisi.context.getString(
                    R.string.dates_reservationForm,
                    dateDébutString,
                    dateFinString
                )
            }

            sélecteurDates.addOnNegativeButtonClickListener{
                sélecteurDates.dismiss()
            }
        }

        val nbPersonnesSpinner = 1
        val nbPersonnesList = (nbPersonnesSpinner ..nbPersonnes + 3).toList()

        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, nbPersonnesList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerNbPersonnes.adapter = adapter

        spinnerNbPersonnes.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val personnesSelectionné = nbPersonnesList[position]
                if (personnesSelectionné == nbPersonnes + 2) {
                    ajustementPrix = 30f
                    tvPrixSelonNbPersonnes.text = "Le prix augmente de 30$"
                } else if (personnesSelectionné == nbPersonnes + 3) {
                    ajustementPrix = 60f
                    tvPrixSelonNbPersonnes.text = "Le prix augmente de 60$"
                } else {
                    tvPrixSelonNbPersonnes.text = getString(R.string.aucun_changement_au_prix)
                }
                ajustementNbrePersonnes = personnesSelectionné

                presentateur.ajusterReservation()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                //
            }
        }
    }

    override fun recupererAjustementsReservation(): AjustementReservation {
        return AjustementReservation(
            prix = prixinitial + ajustementPrix,
            dateDebut = ajustementDateDebut,
            dateFin = ajustementDateFin,
            nbrePersonnes = ajustementNbrePersonnes
        )
    }
}