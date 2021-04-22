package avanger.co.id;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class TamuHolder extends RecyclerView.ViewHolder {
    public final TextView tvNama, tvPlat;
    public final View dataHolder;

    public TamuHolder(View view) {
        super(view);

        tvNama = view.findViewById(R.id.namaTamu);
        tvPlat = view.findViewById(R.id.platNomor);
        dataHolder = view.findViewById(R.id.dataHolder);
    }
}
