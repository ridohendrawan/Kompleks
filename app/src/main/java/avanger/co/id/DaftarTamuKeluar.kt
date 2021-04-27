package avanger.co.id

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.firestore.paging.FirestorePagingAdapter
import com.firebase.ui.firestore.paging.FirestorePagingOptions
import com.firebase.ui.firestore.paging.LoadingState
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_daftar_tamu_keluar.*
import java.lang.Exception

class DaftarTamuKeluar : AppCompatActivity() {
    private lateinit var adapter: FirestorePagingAdapter<Tamu, TamuHolder>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daftar_tamu_keluar)

        // Initialize adapter.
        adapter = tamuAdapter()

        // RecyclerView.
        tamuRecyclerView.layoutManager = LinearLayoutManager(this)
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

    private fun tamuAdapter(): FirestorePagingAdapter<Tamu, TamuHolder> {
        // FirebaseUI preparations.
        val db = Firebase.firestore.collection(getString(R.string.firebase_document))
        val query = db.orderBy("jamKeluar", Query.Direction.DESCENDING).whereEqualTo("didalamKompleks", false)
        val config = PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPrefetchDistance(5)
                .setPageSize(10)
                .build()
        val options = FirestorePagingOptions.Builder<Tamu>()
                .setLifecycleOwner(this)
                .setQuery(query, config, Tamu::class.java)
                .build()

        // Returns a closure containing 'FirebaseRecyclerAdapter'.
        return object : FirestorePagingAdapter<Tamu, TamuHolder>(options) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TamuHolder {
                val view = LayoutInflater.from(parent.context)
                        .inflate(R.layout.item, parent, false)

                return TamuHolder(view)
            }

            override fun onBindViewHolder(holder: TamuHolder, position: Int, model: Tamu) {
                holder.bindTamuKeluar(model)
            }

            override fun onError(e: Exception) {
                super.onError(e)
                Snackbar.make(daftarTamu, getString(R.string.daftar_tamu_failed), Snackbar.LENGTH_LONG).show()
            }

            override fun onLoadingStateChanged(state: LoadingState) {
                super.onLoadingStateChanged(state)

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