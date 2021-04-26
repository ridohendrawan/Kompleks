package avanger.co.id

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.database.paging.DatabasePagingOptions
import com.firebase.ui.database.paging.FirebaseRecyclerPagingAdapter
import com.firebase.ui.database.paging.LoadingState
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_daftar_tamu_keluar.*

class DaftarTamuKeluar : AppCompatActivity() {
     private lateinit var adapter: FirebaseRecyclerPagingAdapter<Tamu, TamuHolder>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daftar_tamu_keluar)

        // RecyclerView.
        tamuRecyclerView.layoutManager = LinearLayoutManager(this)
        adapter = tamuAdapter(this)
        tamuRecyclerView.adapter = adapter

        // Snackbar.
        Snackbar.make(daftarTamu, getString(R.string.daftar_tamu_keluar_confirm), Snackbar.LENGTH_LONG).show()

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

    private fun tamuAdapter(ctx: Context): FirebaseRecyclerPagingAdapter<Tamu, TamuHolder> {
        // FirebaseUI preparations.
        val database = FirebaseUtils.getFirebaseInstance()
        val query = database.reference.child(getString(R.string.firebase_document))
        val config = PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPrefetchDistance(3)
                .setPageSize(10)
                .build()
        val options = DatabasePagingOptions.Builder<Tamu>()
                .setLifecycleOwner(this)
                .setQuery(query, config, Tamu::class.java)
                .build()

        // Returns a closure containing 'FirebaseRecyclerAdapter'.
        return object : FirebaseRecyclerPagingAdapter<Tamu, TamuHolder>(options) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TamuHolder {
                val view = LayoutInflater.from(parent.context)
                        .inflate(R.layout.item, parent, false)

                return TamuHolder(view)
            }

            override fun onBindViewHolder(holder: TamuHolder, position: Int, model: Tamu) {
                holder.tvNama.text = model.namaTamu
                holder.tvPlat.text = model.platTamu

                holder.dataHolder.setOnClickListener {
                    val intent = Intent(ctx, DetailTamuKeluar::class.java)
                    intent.putExtra("tamu", model)
                    ctx.startActivity(intent)
                }
            }

            override fun onError(error: DatabaseError) {
                super.onError(error)
                Snackbar.make(daftarTamu, getString(R.string.daftar_tamu_failed), Snackbar.LENGTH_LONG).show()
            }

            override fun onLoadingStateChanged(state: LoadingState) {
                when (state) {
                    LoadingState.LOADING_INITIAL -> progressBar.visibility = View.VISIBLE
                    LoadingState.LOADING_MORE -> progressBar.visibility = View.VISIBLE
                    LoadingState.FINISHED -> progressBar.visibility = View.GONE
                    LoadingState.LOADED -> progressBar.visibility = View.GONE
                    LoadingState.ERROR -> Snackbar.make(daftarTamu, getString(R.string.daftar_tamu_failed), Snackbar.LENGTH_LONG).show()
                }
            }
        }
    }
}