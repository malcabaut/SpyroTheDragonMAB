package dam.pmdm.MAB.guia;


import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.viewpager2.widget.ViewPager2;

import java.util.Objects;

import dam.pmdm.spyrothedragon.R;
import dam.pmdm.MAB.guia.pasos.GuiaAdapter;

public class GuiaDialogFragment extends DialogFragment implements GuiaNavigationListener {

    private static final String NOMBRE_PREFERENCIAS = "PreferenciasApp";
    private static final String PROGRESO_GUIA = "ProgresoGuia";
    private static final String GUIA_COMPLETADA = "GuiaCompleta";

    private ViewPager2 vistaPaginada;
    private SharedPreferences preferencias;
    private int pasoActual = 0;

    private MediaPlayer mediaPlayer;
    private SoundPool soundPool;
    private int soundId;

    public GuiaDialogFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.FullScreenDialog);

        // Configuración de SoundPool para efectos de sonido
        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();

        soundPool = new SoundPool.Builder()
                .setMaxStreams(1)
                .setAudioAttributes(audioAttributes)
                .build();

        // Cargar sonido de paso de página
        soundId = soundPool.load(requireContext(), R.raw.sfx_paperflip3, 1);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_guia, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        preferencias = getActivity() != null ? getActivity().getSharedPreferences(NOMBRE_PREFERENCIAS, Context.MODE_PRIVATE) : null;
        if (preferencias != null) {
            pasoActual = preferencias.getInt(PROGRESO_GUIA, 0); // Restaurar progreso guardado
        }

        vistaPaginada = view.findViewById(R.id.viewPager);
        GuiaAdapter adaptador = new GuiaAdapter(getChildFragmentManager(), getLifecycle());
        vistaPaginada.setAdapter(adaptador);
        vistaPaginada.setCurrentItem(pasoActual, false);

        // Reproducir sonido al cambiar de fragmento
        vistaPaginada.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                Log.d("GuiaDialogFragment", "Página seleccionada: " + position);

                // Llamar a navigateToNextStep cuando se cambia a un nuevo paso
                pasoActual = position;
                guardarProgreso();
                playPageFlipSound();

                // Comprobar si es el último paso
                if (position == Objects.requireNonNull(vistaPaginada.getAdapter()).getItemCount()) {
                    finalizarGuia();
                }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        }
        startBackgroundMusic(); // Iniciar música de fondo
    }

    private void startBackgroundMusic() {
        mediaPlayer = MediaPlayer.create(getContext(), R.raw.spyro_guia01);
        if (mediaPlayer != null) {
            mediaPlayer.setVolume(0.8f, 0.5f);
            mediaPlayer.setPlaybackParams(mediaPlayer.getPlaybackParams().setSpeed(0.7f));
            mediaPlayer.start();
            mediaPlayer.setOnCompletionListener(mp -> {
                mp.release();
                mediaPlayer = null;
            });
        }
    }

    private void playPageFlipSound() {
        if (soundPool != null) {
            soundPool.play(soundId, 1, 1, 1, 0, 1f);
        }
    }

    @Override
    public void onDestroy() {
        guardarProgreso(); // Guardar progreso cuando el fragmento es destruido
        releaseResources();
        super.onDestroy();
    }

    private void releaseResources() {
        // Liberar recursos de SoundPool y MediaPlayer
        if (soundPool != null) {
            soundPool.release();
            soundPool = null;
        }
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    private void guardarProgreso() {
        if (preferencias != null) {
            preferencias.edit().putInt(PROGRESO_GUIA, pasoActual).apply();
        }
    }

    private void finalizarGuia() {
        releaseResources();
        if (preferencias != null) {
            preferencias.edit().putBoolean(GUIA_COMPLETADA, true).apply();
        }
        dismiss();
    }

    @Override
    public void navigateToNextStep() {
        if (vistaPaginada.getCurrentItem() < Objects.requireNonNull(vistaPaginada.getAdapter()).getItemCount() - 1) {
            pasoActual++;
            guardarProgreso();
            vistaPaginada.setCurrentItem(pasoActual, true);
            Log.d("GuiaDialogFragment", "Paso actual: " + pasoActual);
        } else {
            finalizarGuia();
        }
    }

    @Override
    public void navigateToPreviousStep() {
        if (vistaPaginada.getCurrentItem() > 0) {
            pasoActual--;
            guardarProgreso();
            vistaPaginada.setCurrentItem(pasoActual, true);
        }
    }

    @Override
    public void skipGuide() {
        finalizarGuia();
    }
}
