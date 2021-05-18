package avanger.co.id

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import avanger.co.id.databinding.ActivityDaftarTamuKeluarBinding
import com.firebase.ui.firestore.paging.FirestorePagingAdapter
import com.firebase.ui.firestore.paging.FirestorePagingOptions
import com.firebase.ui.firestore.paging.LoadingState
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.lang.Exception

class DaftarTamuKeluar : AppCompatActivity() {
    private lateinit var adapter: FirestorePagingAdapter<Tamu, TamuHolder>
    private lateinit var binding: ActivityDaftarTamuKeluarBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Binding.
        binding = ActivityDaftarTamuKeluarBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Initialize adapter.
        adapter = tamuAdapter()

        // RecyclerView.
        binding.tamuRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.tamuRecyclerView.adapter = adapter

        // Snackbar.
        Snackbar.make(binding.daftarTamu, getString(R.string.daftar_tamu_keluar_confirm), Snackbar.LENGTH_LONG).show()

        // Delegasi listeners.
        binding.returnBtn.setOnClickListener { finish() }
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
        val query = db.whereEqualTo("owner", Firebase.auth.uid)
                .whereEqualTo("didalamKompleks", false)
                .orderBy("jamKeluar", Query.Direction.DESCENDING)
        val config = PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPrefetchDistance(3)
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
                Snackbar.make(binding.daftarTamu, getString(R.string.daftar_tamu_failed), Snackbar.LENGTH_LONG).show()
            }

            override fun onLoadingStateChanged(state: LoadingState) {
                super.onLoadingStateChanged(state)

                when (state) {
                    LoadingState.LOADING_INITIAL -> binding.progressBar.visibility = View.VISIBLE
                    LoadingState.LOADING_MORE -> binding.progressBar.visibility = View.VISIBLE
                    LoadingState.FINISHED -> binding.progressBar.visibility = View.GONE
                    LoadingState.LOADED -> binding.progressBar.visibility = View.GONE
                    LoadingState.ERROR -> Snackbar.make(binding.progressBar, getString(R.string.daftar_tamu_failed), Snackbar.LENGTH_LONG).show()
                }
            }
        }
    }
}