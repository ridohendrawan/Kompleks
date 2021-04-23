package avanger.co.id

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_daftar_tamu.*
import kotlinx.android.synthetic.main.item.*

class DaftarTamu : AppCompatActivity() {
    private lateinit var adapter: FirebaseRecyclerAdapter<Tamu, TamuHolder>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daftar_tamu)

        // RecyclerView.
        tamuRecyclerView.layoutManager = LinearLayoutManager(this)
        adapter = tamuAdapter(this)
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

    private fun tamuAdapter(ctx: Context): FirebaseRecyclerAdapter<Tamu, TamuHolder> {
        // FirebaseUI preparations.
        val database = FirebaseDatabase.getInstance().reference
        val query = database
                .child(getString(R.string.firebase_document))
                .child(getString(R.string.firebase_document))
                .orderByChild("didalamKompleks")
                .equalTo(true)
        val options = FirebaseRecyclerOptions.Builder<Tamu>()
                .setLifecycleOwner(this)
                .setQuery(query, Tamu::class.java)
                .build()

        // Returns a closure containing 'FirebaseRecyclerAdapter'.
        return object : FirebaseRecyclerAdapter<Tamu, TamuHolder>(options) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TamuHolder {
                val view = LayoutInflater.from(parent.context)
                        .inflate(R.layout.item, parent, false)

                return TamuHolder(view)
            }

            override fun onBindViewHolder(holder: TamuHolder, position: Int, model: Tamu) {
                holder.tvNama.text = model.namaTamu
                holder.tvPlat.text = model.platTamu

                holder.dataHolder.setOnClickListener {
                    val intent = Intent(ctx, DetailDaftarTamu::class.java)
                    intent.putExtra("tamu", model)
                    ctx.startActivity(intent)
                }
            }

            override fun onDataChanged() {
                super.onDataChanged()
                progressBar.visibility = View.GONE
            }

            override fun onError(error: DatabaseError) {
                super.onError(error)
                Snackbar.make(daftarTamu, getString(R.string.daftar_tamu_failed), Snackbar.LENGTH_LONG).show()
            }
        }
    }
}