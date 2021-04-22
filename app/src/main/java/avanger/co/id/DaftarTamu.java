package avanger.co.id;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class DaftarTamu extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Query query;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_tamu);

        // Layout.
        progressBar = findViewById(R.id.progressBar);

        // Firebase preparations.
        query = FirebaseDatabase.getInstance().getReference().child("tamu").child("tamu");

        // RecyclerView.
        recyclerView = findViewById(R.id.tamuRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(tamuAdapter(this));

        // Delegasi listeners.
        Button btnKembaliMenu = findViewById(R.id.KembaliMenu);
        btnKembaliMenu.setOnClickListener((v) -> finish());
    }

    @NonNull
    protected FirebaseRecyclerAdapter<Tamu, TamuHolder> tamuAdapter(Context ctx) {
        // FirebaseUI preparations.
        FirebaseRecyclerOptions<Tamu> options = new FirebaseRecyclerOptions.Builder<Tamu>()
                .setLifecycleOwner(this)
                .setQuery(query, Tamu.class)
                .build();

        return new FirebaseRecyclerAdapter<Tamu, TamuHolder>(options) {
            @NonNull
            @Override
            public TamuHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                progressBar.setVisibility(View.GONE);
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item, parent, false);

                return new TamuHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull TamuHolder holder, int position, @NonNull Tamu model) {
                holder.tvNama.setText(model.getNamaTamu());
                holder.tvPlat.setText(model.getPlatTamu());

                holder.dataHolder.setOnClickListener((v) -> {
                    Intent intent = new Intent(ctx, DetailDaftarTamu.class);
                    ctx.startActivity(intent);
                });
            }
        };
    }
}
