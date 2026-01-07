package com.leen.hotelres_app.accesAuxDonnees
import com.leen.hotelres_app.presentation.model.entities.Chambre
import com.leen.hotelres_app.presentation.model.entities.ChambreFiltre
import com.leen.hotelres_app.presentation.model.entities.Reservation

class SourceDeDonneesException(message : String) : Exception( message ) {}

class SourceDeDonneesBidon() : ISourceDeDonnees {
    // Variables contenant les données factices
    companion object{

        var historiqueReservations = mutableListOf(
            Reservation(
                "101",
                1760390400000, // 14 Oct 2025
                1760649600000, // 17 Oct 2025
                "10 Oct 2025",
                130f,
                3
            ),
            Reservation(
                "102",
                1757280000000, // 8 Sep 2025
                1757712000000, // 13 Sep 2025
                "6 Sep 2025",
                160f,
                4
            ),
            Reservation(
                "103",
                1754092800000, // 2 Aug 2025
                1754764800000, // 10 Aug 2025
                "31 Jul 2025",
                145f,
                3
            ),
            Reservation(
                "104",
                1750032000000, // 15 Jun 2025
                1750550400000, // 21 Jun 2025
                "2 Jun 2025",
                130f,
                2
            ),
            Reservation(
                "105",
                1746835200000, // 10 May 2025
                1747267200000, // 15 May 2025
                "8 Mai 2025",
                150f,
                3
            ),
            Reservation(
                "106",
                1749513600000, // 10 Jun 2025
                1749772800000, // 13 Jun 2025
                "6 Jun 2025",
                165f,
                4
            )
        )
    }

    private val listeChambres: ArrayList<Chambre> = arrayListOf(
        Chambre(
            id = 0,
            numero = "101",
            nombreLits = 1,
            prix = 480F,
            suite = "vip",
            inclusions = "Wifi haut débit, Service personnalisé, Petit-déjeuner inclus, Minibar offert",
            description = "Suite lumineuse avec vue sur la mer, accès handicapé, insonorisation complète, literie premium",
            nombrePersonnes = 2
        ),
        Chambre(
            id = 0,
            numero = "102",
            nombreLits = 2,
            prix = 480F,
            suite = "vip",
            inclusions = "Wifi, Service à la porte, Machine à café premium, Peignoirs et chaussons",
            description = "Chambre élégante avec vue sur la mer, accès handicapé, ambiance chaleureuse",
            nombrePersonnes = 2
        ),
        Chambre(
            id = 0,
            numero = "103",
            nombreLits = 2,
            prix = 650F,
            suite = "vip",
            inclusions = "Jacuzzi privé, Wifi haut débit, Service en chambre 24h/24, Coffre-fort",
            description = "Suite VIP spacieuse avec coin salon, décoration moderne et vue panoramique",
            nombrePersonnes = 2
        ),
        Chambre(
            id = 0,
            numero = "202",
            nombreLits = 2,
            prix = 680F,
            suite = "familliale",
            inclusions = "Wifi, Mini-frigo, Télévision 4K, Jeux de société",
            description = "Chambre familiale confortable avec espace de rangement, ambiance chaleureuse",
            nombrePersonnes = 2
        ),
        Chambre(
            id = 0,
            numero = "203",
            nombreLits = 2,
            prix = 720F,
            suite = "familliale",
            inclusions = "Wifi, Coin salon, Plateau de bienvenue, Télévision 4K",
            description = "Chambre familiale très lumineuse avec vue sur le jardin",
            nombrePersonnes = 2
        ),

        Chambre(
            id = 0,
            numero = "301",
            nombreLits = 3,
            prix = 850F,
            suite = "familliale",
            inclusions = "Wifi, Cuisine équipée, Espace salon, Climatisation",
            description = "Grande suite familiale parfaitement adaptée aux séjours prolongés",
            nombrePersonnes = 3
        ),
        Chambre(
            id = 0,
            numero = "302",
            nombreLits = 3,
            prix = 890F,
            suite = "familliale",
            inclusions = "Wifi, Machine à café, Coin repas, Télévision connectée",
            description = "Suite familiale moderne avec décoration épurée et vue sur la ville",
            nombrePersonnes = 3
        ),
        Chambre(
            id = 0,
            numero = "401",
            nombreLits = 4,
            prix = 1200F,
            suite = "economique",
            inclusions = "Wifi, Ventilateur, Télévision, Serviettes fournies",
            description = "Chambre économique spacieuse idéale pour les groupes",
            nombrePersonnes = 4
        ),
        Chambre(
            id = 0,
            numero = "402",
            nombreLits = 4,
            prix = 1250F,
            suite = "economique",
            inclusions = "Wifi, Climatisation légère, Télévision, Kit de toilette basique",
            description = "Chambre pratique et fonctionnelle pour un groupe ou une grande famille",
            nombrePersonnes = 4
        ),

        Chambre(
            id = 0,
            numero = "501",
            nombreLits = 2,
            prix = 750F,
            suite = "standard",
            inclusions = "Wifi, Mini-frigo, Climatisation, Télévision HD",
            description = "Chambre moderne avec décor relaxant, idéale pour les couples",
            nombrePersonnes = 2
        ),
        Chambre(
            id = 0,
            numero = "502",
            nombreLits = 1,
            prix = 520F,
            suite = "standard",
            inclusions = "Wifi, Bureau de travail, Télévision HD",
            description = "Chambre simple confortable, parfaite pour les voyageurs seuls",
            nombrePersonnes = 1
        ),
        Chambre(
            id = 0,
            numero = "601",
            nombreLits = 3,
            prix = 920F,
            suite = "standard",
            inclusions = "Wifi, Climatisation, Coin salon, Télévision connectée",
            description = "Grande chambre moderne adaptée aux familles ou groupes",
            nombrePersonnes = 3
        ),
        Chambre(
            id = 0,
            numero = "602",
            nombreLits = 2,
            prix = 700F,
            suite = "standard",
            inclusions = "Wifi, Télévision HD, Petit réfrigérateur",
            description = "Chambre simple mais élégante avec un bon confort général",
            nombrePersonnes = 2
        ),

        Chambre(
            id = 0,
            numero = "701",
            nombreLits = 2,
            prix = 950F,
            suite = "standard",
            inclusions = "Wifi, TV connectée, Machine à café, Climatisation",
            description = "Chambre moderne située en étage élevé avec excellente vue",
            nombrePersonnes = 2
        ),
        Chambre(
            id = 0,
            numero = "702",
            nombreLits = 1,
            prix = 600F,
            suite = "standard",
            inclusions = "Wifi, Bureau ergonomique, Télévision HD",
            description = "Chambre idéale pour séjour d’affaires avec espace de travail",
            nombrePersonnes = 1
        )
    )



    // Implémentation des méthodes de l'interface ISourceDeDonnees
    override fun getHistoriqueReservations(): List<Reservation> {
        return historiqueReservations
    }

    override fun enregistrerReservation(reservation: Reservation){
        try {
            Thread.sleep(3000)
        }
        catch (e: Exception){
            throw SourceDeDonneesException("Erreur lors de l'enregistrement de la réservation")
        }
    }

    override fun getListeChambres(): ArrayList<Chambre> {
        return listeChambres
    }

    override fun getListeChambresFiltre(filtre: ChambreFiltre): ArrayList<Chambre> {
        Thread.sleep(1000)
        return listeChambres.filter {
            if(filtre.prix != null){
                return@filter it.prix <= filtre.prix!!
            }else{
                return@filter true
            }

        } as ArrayList<Chambre>
    }

    override fun getChambreParId(id: Int): Chambre {
        if (id in 0..listeChambres.size){
            return listeChambres[id]
        }else{
            throw Exception("Chambre non trouvée")
        }
    }
}