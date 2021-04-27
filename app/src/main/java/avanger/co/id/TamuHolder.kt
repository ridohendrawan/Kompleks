package avanger.co.id

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TamuHolder(view: View) : RecyclerView.ViewHolder(view) {
    val tvNama: TextView = view.findViewById(R.id.namaTamu)
    val tvPlat: TextView = view.findViewById(R.id.platNomor)
    val dataHolder: View = view.findViewById(R.id.dataHolder)

    fun bind(tamu: Tamu, context: Context) {
        tvNama.text = tamu.namaTamu
        tvPlat.text = tamu.platTamu

        itemView.setOnClickListener {
            val intent = Intent(itemView.context, DetailDaftarTamu::class.java)
            intent.putExtra("tamu", tamu)
            itemView.context.startActivity(intent)
        }
    }
}