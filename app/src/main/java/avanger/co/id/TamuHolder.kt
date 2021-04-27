package avanger.co.id

import android.content.Intent
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TamuHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val tvNama: TextView = view.findViewById(R.id.namaTamu)
    private val tvPlat: TextView = view.findViewById(R.id.platNomor)

    fun bindTamuMasuk(tamu: Tamu) {
        tvNama.text = tamu.namaTamu
        tvPlat.text = tamu.platTamu

        itemView.setOnClickListener {
            val intent = Intent(itemView.context, DetailTamuMasuk::class.java)
            intent.putExtra("tamu", tamu)
            itemView.context.startActivity(intent)
        }
    }

    fun bindTamuKeluar(tamu: Tamu) {
        tvNama.text = tamu.namaTamu
        tvPlat.text = tamu.platTamu

        itemView.setOnClickListener {
            val intent = Intent(itemView.context, DetailTamuKeluar::class.java)
            intent.putExtra("tamu", tamu)
            itemView.context.startActivity(intent)
        }
    }
}