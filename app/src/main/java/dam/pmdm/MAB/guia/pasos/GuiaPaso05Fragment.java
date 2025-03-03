package dam.pmdm.MAB.guia.pasos;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import dam.pmdm.MAB.guia.GuiaNavigationListener;
import dam.pmdm.spyrothedragon.R;
import dam.pmdm.spyrothedragon.databinding.FragmentGuiaPaso05Binding;

public class GuiaPaso05Fragment extends Fragment {
    private static final String TAG = "GuiaPaso05Fragment";
    private FragmentGuiaPaso05Binding binding;
    private GuiaNavigationListener navigationListener;
    private static final long SHOW_DELAY = 3000;
    private static final long HIDE_DELAY = 6000;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentGuiaPaso05Binding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findNavigationListener();
        setupButtons();
        startButtonAnimations();
        startSwipeAnimation();
        animateButtonMovement();  // Nueva animación de desplazamiento
    }

    @Override
    public void onResume() {
        super.onResume();
        Navigation.findNavController(requireActivity(), R.id.navHostFragment)
                .navigate(R.id.navigation_collectibles);
    }

    private void setupButtons() {
        binding.btnSkip.setOnClickListener(v -> {
            if (navigationListener != null) navigationListener.skipGuide();
        });
    }

    private void startSwipeAnimation() {
        Handler handler = new Handler();
        Runnable cycleAnimation = new Runnable() {
            @Override
            public void run() {
                binding.swipeLeft.setVisibility(View.VISIBLE);
                handler.postDelayed(() -> {
                    binding.swipeLeft.setVisibility(View.INVISIBLE);
                    handler.postDelayed(this, SHOW_DELAY);
                }, HIDE_DELAY);
            }
        };
        handler.postDelayed(cycleAnimation, SHOW_DELAY);
    }

    private void startButtonAnimations() {
        animateTextColor(binding.btnSkip);
        animateShadow(binding.btnSkip);
        animateTextSize(binding.btnSkip);
    }

    private void animateTextColor(View view) {
        Integer[] colors = {Color.RED, Color.MAGENTA, Color.BLUE, Color.CYAN, Color.GREEN, Color.YELLOW, Color.RED};
        createAnimator(view, "textColor", colors, 3000).start();
    }

    private void animateShadow(View view) {
        ValueAnimator animator = ValueAnimator.ofFloat(5f, 30f);
        animator.setDuration(1500);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setRepeatMode(ValueAnimator.REVERSE);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(animation -> ((TextView) view).setShadowLayer((float) animation.getAnimatedValue(), 0f, 0f, Color.WHITE));
        animator.start();
    }

    private void animateTextSize(View view) {
        ValueAnimator animator = ValueAnimator.ofFloat(18f, 25f);
        animator.setDuration(1500);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setRepeatMode(ValueAnimator.REVERSE);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(animation -> ((TextView) view).setTextSize((float) animation.getAnimatedValue()));
        animator.start();
    }

    private ValueAnimator createAnimator(View view, String property, Integer[] values, int duration) {
        ValueAnimator animator = ValueAnimator.ofObject(new ArgbEvaluator(), (Object[]) values);
        animator.setDuration(duration);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setRepeatMode(ValueAnimator.REVERSE);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(animation -> ((TextView) view).setTextColor((int) animation.getAnimatedValue()));
        return animator;
    }

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

    /**
     * Anima el desplazamiento del botón btnSkip al centro de la pantalla.
     */
    private void animateButtonMovement() {
        binding.btnSkip.post(() -> {
            int parentWidth = ((View) binding.btnSkip.getParent()).getWidth();
            int buttonWidth = binding.btnSkip.getWidth();

            int startX = binding.btnSkip.getLeft();
            int endX = (parentWidth - buttonWidth) / 2;

            ObjectAnimator animator = ObjectAnimator.ofFloat(binding.btnSkip, "x", startX, endX);
            animator.setDuration(2000);
            animator.setInterpolator(new LinearInterpolator());

            // Escuchar el final de la animación
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    binding.Fecha.setVisibility(View.VISIBLE); // Hacer visible la imagen al terminar
                    animateFecha(); // Iniciar la animación de tamaño y brillo
                }
            });

            animator.start();
        });

    }

    /**
     * Animación de tamaño y brillo para binding.Fecha.
     */
    private void animateFecha() {
        // Cambiar tamaño de la imagen
        ValueAnimator sizeAnimator = ValueAnimator.ofFloat(1.0f, 1.2f); // Cambia los valores según el tamaño deseado
        sizeAnimator.setDuration(1500);
        sizeAnimator.setRepeatCount(ValueAnimator.INFINITE);
        sizeAnimator.setRepeatMode(ValueAnimator.REVERSE);
        sizeAnimator.setInterpolator(new LinearInterpolator());
        sizeAnimator.addUpdateListener(animation -> binding.Fecha.setScaleX((float) animation.getAnimatedValue()));
        sizeAnimator.addUpdateListener(animation -> binding.Fecha.setScaleY((float) animation.getAnimatedValue()));
        sizeAnimator.start();

        // Efecto de brillo
        ObjectAnimator glowAnimator = ObjectAnimator.ofFloat(binding.Fecha, "alpha", 0.5f, 1f);
        glowAnimator.setDuration(1000);
        glowAnimator.setRepeatCount(ValueAnimator.INFINITE);
        glowAnimator.setRepeatMode(ValueAnimator.REVERSE);
        glowAnimator.setInterpolator(new LinearInterpolator());
        glowAnimator.start();
    }
}
