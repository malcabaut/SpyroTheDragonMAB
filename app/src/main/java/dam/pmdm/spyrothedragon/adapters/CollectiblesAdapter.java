package dam.pmdm.spyrothedragon.adapters;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import dam.pmdm.spyrothedragon.R;
import dam.pmdm.spyrothedragon.databinding.FragmentCollectiblesBinding;
import dam.pmdm.spyrothedragon.models.Collectible;

public class CollectiblesAdapter extends RecyclerView.Adapter<CollectiblesAdapter.CollectiblesViewHolder> {

    private final List<Collectible> list;
    private final Context context;
    private final FragmentCollectiblesBinding binding; // Binding agregado
    private int touchCount = 0; // Contador para contar los toques consecutivos

    public CollectiblesAdapter(Context context, List<Collectible> collectibleList, FragmentCollectiblesBinding binding) {
        this.context = context;
        this.list = collectibleList;
        this.binding = binding;
    }

    @NonNull
    @Override
    public CollectiblesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview, parent, false);
        return new CollectiblesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CollectiblesViewHolder holder, int position) {
        Collectible collectible = list.get(position);
        holder.nameTextView.setText(collectible.getName());

        // Cargar la imagen (simulator con un recurso drawable)
        int imageResId = holder.itemView.getContext().getResources().getIdentifier(
                collectible.getImage(), "drawable", holder.itemView.getContext().getPackageName());
        holder.imageImageView.setImageResource(imageResId);

        // Maneja el toque sobre las Gemas
        holder.itemView.setOnClickListener(v -> handleGemTouch(collectible));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    /**
     * Maneja el toque sobre las Gemas. Si el usuario toca cuatro veces consecutivas,
     * se reproduce un video de Easter Egg.
     */
    private void handleGemTouch(Collectible collectible) {
        if (!collectible.getName().equals("Gemas")) {
            return; // Ignora el toque si no es "Gemas"
        }
        touchCount++;
        if (touchCount == 4) {
            playEasterEggVideo(); // Reproducir el video cuando el contador llega a 4
            touchCount = 0; // Reiniciar el contador después de activar el Easter Egg
            Log.d("CollectiblesTab", "Easter Egg activado. Reproduciendo video temático.");
        } else {
            Log.d("CollectiblesTab", "Toque " + touchCount + " sobre las Gemas.");
        }
    }

    /**
     * Reproduce el video de Easter Egg.
     */
    private void playEasterEggVideo() {
        Log.d("CollectiblesAdapter", "Reproduciendo video del Easter Egg.");

        binding.videoView.setVisibility(View.VISIBLE); // Hacer visible el VideoView
        Uri videoUri = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.easter_egg_01);
        binding.videoView.setVideoURI(videoUri);

        binding.videoView.setOnPreparedListener(MediaPlayer::start);
        binding.videoView.setOnCompletionListener(mp -> {
            binding.videoView.setVisibility(View.GONE); // Ocultar el VideoView después de terminar
        });
    }

    public static class CollectiblesViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        ImageView imageImageView;

        public CollectiblesViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.name);
            imageImageView = itemView.findViewById(R.id.image);
        }
    }
}
