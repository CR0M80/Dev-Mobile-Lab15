package projet.fst.ma.app.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import projet.fst.ma.app.classes.Etudiant
import projet.fst.ma.app.databinding.ItemEtudiantBinding

class EtudiantAdapter(
    private var students: List<Etudiant>,
    private val onDeleteClick: (Etudiant) -> Unit
) : RecyclerView.Adapter<EtudiantAdapter.EtudiantViewHolder>() {

    inner class EtudiantViewHolder(val binding: ItemEtudiantBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EtudiantViewHolder {
        val binding = ItemEtudiantBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EtudiantViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EtudiantViewHolder, position: Int) {
        val student = students[position]
        with(holder.binding) {
            tvFullName.text = "${student.nom} ${student.prenom}"
            tvId.text = "ID: ${student.id}"
            
            // Initiales créatives pour l'avatar
            val initials = (student.nom.take(1) + student.prenom.take(1)).uppercase()
            tvInitials.text = if (initials.isNotEmpty()) initials else "?"

            btnItemDelete.setOnClickListener { onDeleteClick(student) }
        }
    }

    override fun getItemCount(): Int = students.size

    fun updateData(newStudents: List<Etudiant>) {
        this.students = newStudents
        notifyDataSetChanged()
    }
}
