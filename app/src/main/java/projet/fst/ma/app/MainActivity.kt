package projet.fst.ma.app

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import projet.fst.ma.app.adapter.EtudiantAdapter
import projet.fst.ma.app.classes.Etudiant
import projet.fst.ma.app.databinding.ActivityMainBinding
import projet.fst.ma.app.service.EtudiantService

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var etudiantService: EtudiantService
    private lateinit var adapter: EtudiantAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        etudiantService = EtudiantService(this)
        
        setupToolbar()
        setupRecyclerView()
        setupListeners()
        refreshList()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = "Gestion Étudiants"
    }

    private fun setupRecyclerView() {
        adapter = EtudiantAdapter(emptyList()) { etudiant ->
            etudiantService.delete(etudiant.id)
            refreshList()
            Toast.makeText(this, "${etudiant.nom} supprimé", Toast.LENGTH_SHORT).show()
        }
        binding.rvEtudiants.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = this@MainActivity.adapter
        }
    }

    private fun setupListeners() {
        // Ajouter un étudiant
        binding.btnAdd.setOnClickListener {
            val nom = binding.etNom.text.toString().trim()
            val prenom = binding.etPrenom.text.toString().trim()

            if (nom.isNotEmpty() && prenom.isNotEmpty()) {
                etudiantService.create(Etudiant(nom, prenom))
                binding.etNom.text?.clear()
                binding.etPrenom.text?.clear()
                refreshList()
                Toast.makeText(this, "Étudiant ajouté avec succès", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show()
            }
        }

        // Chercher par ID
        binding.btnSearch.setOnClickListener {
            val idStr = binding.etId.text.toString().trim()
            if (idStr.isNotEmpty()) {
                val etudiant = etudiantService.findById(idStr.toInt())
                if (etudiant != null) {
                    binding.tvResult.visibility = View.VISIBLE
                    binding.tvResult.text = "Trouvé : ${etudiant.nom} ${etudiant.prenom}"
                    binding.tvResult.setTextColor(getColor(android.R.color.holo_green_dark))
                } else {
                    binding.tvResult.visibility = View.VISIBLE
                    binding.tvResult.text = "Aucun étudiant trouvé avec l'ID $idStr"
                    binding.tvResult.setTextColor(getColor(android.R.color.holo_red_dark))
                }
            } else {
                Toast.makeText(this, "Veuillez saisir un ID", Toast.LENGTH_SHORT).show()
            }
        }

        // Supprimer par ID
        binding.btnDelete.setOnClickListener {
            val idStr = binding.etId.text.toString().trim()
            if (idStr.isNotEmpty()) {
                val deletedCount = etudiantService.delete(idStr.toInt())
                if (deletedCount > 0) {
                    refreshList()
                    binding.etId.text?.clear()
                    binding.tvResult.text = "ID $idStr supprimé"
                    binding.tvResult.setTextColor(getColor(android.R.color.darker_gray))
                    Toast.makeText(this, "Supprimé avec succès", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "ID introuvable", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Veuillez saisir un ID", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun refreshList() {
        val students = etudiantService.findAll()
        adapter.updateData(students)
    }
}
