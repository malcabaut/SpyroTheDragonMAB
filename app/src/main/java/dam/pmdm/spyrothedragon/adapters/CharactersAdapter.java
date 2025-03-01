package dam.pmdm.spyrothedragon.adapters;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import dam.pmdm.spyrothedragon.R;
import dam.pmdm.spyrothedragon.models.Character;

import java.util.List;

public class CharactersAdapter extends RecyclerView.Adapter<CharactersAdapter.CharactersViewHolder> {

    private final List<Character> list;

    public CharactersAdapter(List<Character> charactersList) {
        this.list = charactersList;
    }

    @NonNull
    @Override
    public CharactersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview, parent, false);
        return new CharactersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CharactersViewHolder holder, int position) {
        Character character = list.get(position);
        holder.nameTextView.setText(character.getName());

        // Cargar la imagen (simulado con un recurso drawable)
        @SuppressLint("DiscouragedApi") int imageResId = holder.itemView.getContext().getResources().getIdentifier(character.getImage(), "drawable", holder.itemView.getContext().getPackageName());
        holder.imageImageView.setImageResource(imageResId);

        // Establecemos el listener para el evento de click largo sobre itemView
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                // Verificamos si el personaje es Spyro antes de ejecutar la animación
                if (character.getName().equals("Spyro")) {
                    // Obtenemos el contenedor de la vista
                    ViewGroup viewGroup = (ViewGroup) holder.itemView;
                    // Log para saber cuando se ejecuta la animación
                    Log.d("EasterEgg", "Easter egg activado para el personaje Spyro.");


                    // Reproducir el sonido
                    MediaPlayer mediaPlayer = MediaPlayer.create(v.getContext(), R.raw.dragon_fire_breath);
                    mediaPlayer.start();  // Iniciar la reproducción del sonido

                    // Crear el ImageView para el fuego
                    ImageView fireImageView = new ImageView(v.getContext());
                    fireImageView.setImageResource(R.drawable.fire); // El recurso SVG del fuego
                    ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(400, 400); // Tamaño del fuego
                    fireImageView.setLayoutParams(params);

            // Establecer la posición del fuego en el centro de la vista
                    fireImageView.setX((float) fireImageView.getWidth() / 2);  // Centrado respecto al punto X
                    fireImageView.setY((float) fireImageView.getHeight() / 2); // Centrado respecto al punto Y

                    // Añadir el ImageView al contenedor
                    viewGroup.addView(fireImageView);

// Animar el fuego (creciendo, rotando y cambiando su transparencia)

// Animación de escalado en X (crece en 2 segundos)
                    ObjectAnimator scaleX = ObjectAnimator.ofFloat(fireImageView, "scaleX", 0f, 2f);
                    scaleX.setDuration(2000); // Duración de 2 segundos

// Animación de escalado en Y (crece en 2 segundos)
                    ObjectAnimator scaleY = ObjectAnimator.ofFloat(fireImageView, "scaleY", 0f, 2f);
                    scaleY.setDuration(2000); // Duración de 2 segundos

// Animación de rotación (rota 5 veces en 4 segundos)
                    ObjectAnimator rotate = ObjectAnimator.ofFloat(fireImageView, "rotation", 0f, 3800f);
                    rotate.setDuration(5000); // Duración de 4 segundos

// Animación de opacidad (aparece en 2.5 segundos y desaparece en 2.5 segundos)
                    ObjectAnimator alphaIn = ObjectAnimator.ofFloat(fireImageView, "alpha", 0.1f, 1f);
                    alphaIn.setDuration(2500); // Duración de 2.5 segundos para que crezca de 0.1 a 1

                    ObjectAnimator alphaOut = ObjectAnimator.ofFloat(fireImageView, "alpha", 1f, 0.1f);
                    alphaOut.setDuration(2500); // Duración de 2.5 segundos para que disminuya de 1 a 0.1

// Crear un AnimatorSet para que todas las animaciones se ejecuten simultáneamente
                    AnimatorSet animatorSet = new AnimatorSet();
                    animatorSet.playTogether(scaleX, scaleY, rotate); // Todas las animaciones se ejecutan juntas
                    animatorSet.playSequentially(alphaIn, alphaOut); // Primero la entrada, luego la salida
                    animatorSet.start(); // Iniciar la animación

                    // Eliminar el ImageView después de la animación (si es necesario)
                    animatorSet.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            viewGroup.removeView(fireImageView); // Eliminar el fuego al final
                        }
                    });

                    return true;
                }
                return false;
            }
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class CharactersViewHolder extends RecyclerView.ViewHolder {

        TextView nameTextView;
        ImageView imageImageView;

        public CharactersViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.name);
            imageImageView = itemView.findViewById(R.id.image);
        }
    }
}
