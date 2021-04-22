package avanger.co.id

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TamuHolder(view: View) : RecyclerView.ViewHolder(view) {
    val tvNama: TextView = view.findViewById(R.id.namaTamu)
    val tvPlat: TextView = view.findViewById(R.id.platNomor)
    val dataHolder: View = view.findViewById(R.id.dataHolder)
}