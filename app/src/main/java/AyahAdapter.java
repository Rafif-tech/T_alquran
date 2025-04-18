import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.al_quranapp.R;

import java.util.List;

public class AyahAdapter extends RecyclerView.Adapter<AyahAdapter.AyahViewHolder> {
    private List<AyahModel> ayahs;

    public AyahAdapter(List<AyahModel> ayahs) {
        this.ayahs = ayahs;
    }

    @Override
    public AyahViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ayah, parent, false);
        return new AyahViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AyahViewHolder holder, int position) {
        AyahModel ayah = ayahs.get(position);
        holder.textViewAyah.setText(ayah.getNumber() + ". " + ayah.getText());
    }

    @Override
    public int getItemCount() {
        return ayahs.size();
    }

    public static class AyahViewHolder extends RecyclerView.ViewHolder {
        TextView textViewAyah;
        public AyahViewHolder(View itemView) {
            super(itemView);
            textViewAyah = itemView.findViewById(R.id.textViewAyah);
        }
    }
}
