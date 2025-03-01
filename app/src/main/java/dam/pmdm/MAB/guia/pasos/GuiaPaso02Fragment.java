package dam.pmdm.MAB.guia.pasos;


import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import dam.pmdm.MAB.guia.GuiaNavigationListener;
import dam.pmdm.spyrothedragon.databinding.FragmentGuiaPaso02Binding;

public class GuiaPaso02Fragment extends Fragment {

    private static final String TAG = "GuiaPaso02Fragment";
    private FragmentGuiaPaso02Binding binding;
    private GuiaNavigationListener navigationListener;

    public GuiaPaso02Fragment() {
        // Constructor vacío requerido
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentGuiaPaso02Binding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findNavigationListener();
        setupButtons();
        startAnimations();
    }

    /**
     * Configura el clic para saltar la guía.
     */
    private void setupButtons() {
        // Se utiliza el botón base (btnSkip) para la interacción
        binding.btnSkip.setOnClickListener(v -> {
            if (navigationListener != null) navigationListener.skipGuide();
        });
    }

    /**
     * Inicia las animaciones sobre el TextView que muestra el texto del botón.
     */
    private void startAnimations() {
        // Animamos únicamente el TextView (tvSkipText) en lugar del botón completo.
        animateRainbowText(binding.btnSkip);
        animateShadow(binding.btnSkip);
        animateTextSize(binding.btnSkip);
    }

    /**
     * Anima el cambio de color en un ciclo arcoíris en el TextView.
     */
    private void animateRainbowText(View... views) {
        Integer[] rainbowColors = new Integer[]{
                Color.RED, Color.MAGENTA, Color.BLUE, Color.CYAN, Color.GREEN, Color.YELLOW, Color.RED
        };

        ValueAnimator colorAnimator = ValueAnimator.ofObject(new ArgbEvaluator(), (Object[]) rainbowColors);
        colorAnimator.setDuration(3000);
        colorAnimator.setRepeatCount(ValueAnimator.INFINITE);
        colorAnimator.setRepeatMode(ValueAnimator.REVERSE);
        colorAnimator.setInterpolator(new LinearInterpolator());

        colorAnimator.addUpdateListener(animation -> {
            int color = (int) animation.getAnimatedValue();
            for (View view : views) {
                if (view instanceof TextView) {
                    ((TextView) view).setTextColor(color);
                }
            }
        });

        colorAnimator.start();
    }

    /**
     * Anima una sombra pulsante en el TextView.
     */
    private void animateShadow(View... views) {
        ValueAnimator shadowAnimator = ValueAnimator.ofFloat(5f, 30f);
        shadowAnimator.setDuration(1500);
        shadowAnimator.setRepeatCount(ValueAnimator.INFINITE);
        shadowAnimator.setRepeatMode(ValueAnimator.REVERSE);
        shadowAnimator.setInterpolator(new LinearInterpolator());

        shadowAnimator.addUpdateListener(animation -> {
            float radius = (float) animation.getAnimatedValue();
            for (View view : views) {
                if (view instanceof TextView) {
                    ((TextView) view).setShadowLayer(radius, 0f, 0f, Color.WHITE);
                }
            }
        });

        shadowAnimator.start();
    }

    /**
     * Anima el cambio de tamaño del texto en el TextView.
     */
    private void animateTextSize(View... views) {
        ValueAnimator textSizeAnimator = ValueAnimator.ofFloat(18f, 25f);
        textSizeAnimator.setDuration(1500);
        textSizeAnimator.setRepeatCount(ValueAnimator.INFINITE);
        textSizeAnimator.setRepeatMode(ValueAnimator.REVERSE);
        textSizeAnimator.setInterpolator(new LinearInterpolator());

        textSizeAnimator.addUpdateListener(animation -> {
            float size = (float) animation.getAnimatedValue();
            for (View view : views) {
                if (view instanceof TextView) {
                    ((TextView) view).setTextSize(size);
                }
            }
        });

        textSizeAnimator.start();
    }

    /**
     * Busca y asigna el listener de navegación.
     */
    private void findNavigationListener() {
        if (getParentFragment() instanceof GuiaNavigationListener) {
            navigationListener = (GuiaNavigationListener) getParentFragment();
        } else if (getActivity() instanceof GuiaNavigationListener) {
            navigationListener = (GuiaNavigationListener) getActivity();
        }

        if (navigationListener == null) {
            Log.e(TAG, "ERROR: No se encontró el listener.");
            binding.btnSkip.setEnabled(false);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null; // Evitar fugas de memoria
    }
}
