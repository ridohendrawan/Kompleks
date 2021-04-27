package avanger.co.id

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_daftar_tamu_masuk.*

class DaftarTamuMasuk : AppCompatActivity() {
    private lateinit var adapter: FirestoreRecyclerAdapter<Tamu, TamuHolder>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daftar_tamu_masuk)

        // Initialize adapter.
        adapter = tamuAdapter()

        // RecyclerView.
        tamuRecyclerView.layoutManager = LinearLayoutManager(this)
        tamuRecyclerView.adapter = adapter

        // Snackbar.
        Snackbar.make(daftarTamu, getString(R.string.daftar_tamu_confirm), Snackbar.LENGTH_LONG).show()

        // Delegasi listeners.
        returnBtn.setOnClickListener { finish() }
    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }

    private fun tamuAdapter(): FirestoreRecyclerAdapter<Tamu, TamuHolder> {
        val db = Firebase.firestore.collection(getString(R.string.firebase_document))
        val query = db.orderBy("jamMasuk", Query.Direction.DESCENDING)
        val options = FirestoreRecyclerOptions.Builder<Tamu>()
                .setLifecycleOwner(this)
                .setQuery(query, Tamu::class.java)
                .build()

        // Returns a closure containing 'FirebaseRecyclerAdapter'.
        return object : FirestoreRecyclerAdapter<Tamu, TamuHolder>(options) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TamuHolder {
                val view = LayoutInflater.from(parent.context)
                        .inflate(R.layout.item, parent, false)

                return TamuHolder(view)
            }

            override fun onBindViewHolder(holder: TamuHolder, position: Int, model: Tamu) {
                holder.bindTamuMasuk(model)
            }

            override fun onDataChanged() {
                super.onDataChanged()
                progressBar.visibility = View.GONE
            }

            override fun onError(e: FirebaseFirestoreException) {
                super.onError(e)
                Snackbar.make(daftarTamu, getString(R.string.daftar_tamu_failed), Snackbar.LENGTH_LONG).show()
            }
        }
    }
}