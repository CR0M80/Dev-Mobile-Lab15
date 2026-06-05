package projet.fst.ma.app.classes

/**
 * Modèle de données pour un Étudiant.
 * Utilisation de Kotlin Data Class pour la concision.
 */
data class Etudiant(
    var id: Int = 0,
    var nom: String = "",
    var prenom: String = ""
) {
    // Constructeur pour faciliter l'ajout (l'ID est géré par SQLite)
    constructor(nom: String, prenom: String) : this(0, nom, prenom)
}
