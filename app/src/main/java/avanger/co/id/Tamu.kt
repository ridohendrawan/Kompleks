package avanger.co.id

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Tamu(val idTamu: String? = null,
                val namaTamu: String? = null,
                val tujuanKunjungan: String? = null,
                val platTamu: String? = null,
                val jamMasuk: Long? = null,
                val jamKeluar: Long? = null,
                val photo: String? = null,
                val didalamKompleks: Boolean? = null,
                val owner: String? = null) : Parcelable
