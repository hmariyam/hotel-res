package com.leen.hotelres_app.presentation.vue

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.util.Pair
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.CalendarConstraints.DateValidator
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.leen.hotelres_app.presentation.presentateur.ContratFragmentPresentateurChambres
import com.leen.hotelres_app.presentation.model.entities.Chambre
import com.leen.hotelres_app.presentation.model.entities.ChambreFiltre
import com.leen.hotelres_app.presentation.presentateur.PresentateurChambres
import com.leen.hotelres_app.R
import com.leen.hotelres_app.presentation.utils.viewUtils
import java.time.LocalDate
import java.time.ZoneId
import kotlin.String
import kotlin.time.Duration.Companion.days
import kotlin.time.DurationUnit

class ChambresFragment : Fragment(), ContratFragmentPresentateurChambres.IVueChambres {

    private lateinit var chambresAdaptater: ChambresAdaptater
    private lateinit var chambresRecyclerView: RecyclerView
    private lateinit var presentateurChambres: PresentateurChambres
    private lateinit var navController: NavController
    private lateinit var btnDateDebut: Button
    private lateinit var btnDateFin: Button
    private lateinit var btnRefresh: ImageView
    private lateinit var etMontantMaximum: EditText
    private lateinit var btnVersHistoriqueDeReservation: ImageView
    private lateinit var loadChambresAccueil: TextView
    private var isLoading = false

    private var dateDebut: Long = System.currentTimeMillis()
    private var dateFin: Long = System.currentTimeMillis()  + 7.days.toLong(DurationUnit.MILLISECONDS)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_chambres, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadChambresAccueil = requireActivity().findViewById(R.id.loadChambresAccueil)
        chambresRecyclerView = requireActivity().findViewById(R.id.rv_chambres)
        btnDateFin = requireActivity().findViewById(R.id.btn_date_fin)
        btnDateDebut = requireActivity().findViewById(R.id.btn_date_debut)
        btnRefresh = requireActivity().findViewById(R.id.btn_refresh_prix)
        etMontantMaximum = requireActivity().findViewById(R.id.et_prix_maximum)
        btnVersHistoriqueDeReservation = requireActivity().findViewById(R.id.vers_historique_reservation)

        chambresRecyclerView.layoutManager = LinearLayoutManager(context)

        presentateurChambres = PresentateurChambres(this)

        chambresAdaptater = ChambresAdaptater(ArrayList(), presentateurChambres)
        chambresRecyclerView.adapter = chambresAdaptater

        navController = Navigation.findNavController(view)

        attacherEvenements()

        afficherLoader()
        presentateurChambres.initialiserVue()
        presentateurChambres.traiterFiltrer()
    }

    private fun attacherEvenements() {

        btnVersHistoriqueDeReservation.setOnClickListener {
            presentateurChambres.traiterNaviguerVersHistoriqueReservations()
        }

        btnDateDebut.setOnClickListener {
            selectionnerDate()
        }

        btnDateFin.setOnClickListener {
            selectionnerDate()
        }

        etMontantMaximum.setOnFocusChangeListener { _, focus ->
            if (!focus) {
                afficherLoader()
                presentateurChambres.traiterFiltrer()
            }
        }

        btnRefresh.setOnClickListener {
            etMontantMaximum.text.clear()
            afficherLoader()
            presentateurChambres.traiterFiltrer()
        }
    }

    override fun afficherChambres(chambres: ArrayList<Chambre>) {
        cacherLoader()
        chambresAdaptater.updateData(chambres)
    }

    override fun naviguerVersDetails() {
        navController.navigate(R.id.action_ecran_liste_chambres_to_ecran_details_chambre)
    }

    override fun naviguerVersHistoriqueReservations() {
        navController.navigate(R.id.action_ecran_liste_chambres_to_ecran_historique_reservations)
    }

    override fun afficherNotification(texte: String) {
        Toast.makeText(context, texte, Toast.LENGTH_SHORT).show()
    }

    override fun afficherAlerte(messageTitre: String, message: String) {
        AlertDialog.Builder(requireContext())
            .setTitle(messageTitre)
            .setMessage(message)
            .setPositiveButton("OK", null)
            .show()
    }

    override fun obtenirFiltres(): ChambreFiltre {
        Log.d("DEBUG", "Obtention des filtres ${viewUtils.convertirEpochEnDateFormattee(dateDebut)} | ${viewUtils.convertirEpochEnDateFormattee(dateFin)} ")
        return ChambreFiltre(
            dateDebut,
            dateFin,
            prix = etMontantMaximum.text.toString().toFloatOrNull()
        )
    }

    override fun afficherFiltres(filtre: ChambreFiltre) {
        dateDebut = filtre.dateDebut
        dateFin = filtre.dateFin
        btnDateFin.text = viewUtils.convertirEpochEnDateFormattee(filtre.dateFin)
        btnDateDebut.text = viewUtils.convertirEpochEnDateFormattee(filtre.dateDebut)
        etMontantMaximum.setText(filtre.prix?.toString() ?: "")
    }

    override fun afficherLoader() {
        isLoading = true
        loadChambresAccueil.visibility = View.VISIBLE
        chambresRecyclerView.visibility = View.INVISIBLE

        loadChambresAccueil.animate()
            .rotationBy(360f)
            .setDuration(700)
            .withEndAction { if (isLoading) afficherLoader() }
            .start()
    }

    override fun cacherLoader() {
        isLoading = false
        loadChambresAccueil.clearAnimation()
        loadChambresAccueil.visibility = View.GONE
        chambresRecyclerView.visibility = View.VISIBLE
    }

    private fun selectionnerDate(){
        val dateValidator : DateValidator = DateValidatorPointForward.from(
            LocalDate.now()
                .atStartOfDay(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli()
                    - 1.days.toLong(DurationUnit.MILLISECONDS))
        val constraints = CalendarConstraints
            .Builder()
            .setValidator(dateValidator)
            .build()

        val sélecteurDates = MaterialDatePicker.Builder.dateRangePicker()
            .setTheme(R.style.MaterialCalendarTheme)
            .setTitleText(getString(R.string.choissisez_vos_dates_de_reservation))
            .setSelection(Pair(dateDebut, dateFin))
            .setCalendarConstraints(constraints)
            .build()

        sélecteurDates.show(parentFragmentManager, "TAG")

        sélecteurDates.addOnPositiveButtonClickListener {
            dateDebut = it.first
            dateFin = it.second

            btnDateDebut.text = viewUtils.convertirEpochEnDateFormattee(dateDebut)
            btnDateFin.text = viewUtils.convertirEpochEnDateFormattee(dateFin)

            presentateurChambres.traiterFiltrer()
        }

        sélecteurDates.addOnNegativeButtonClickListener{
            sélecteurDates.dismiss()
        }

    }
}