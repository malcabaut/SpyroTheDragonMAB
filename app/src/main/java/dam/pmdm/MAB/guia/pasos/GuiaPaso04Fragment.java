package dam.pmdm.MAB.guia.pasos;

import android.animation.ArgbEvaluator;
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
import dam.pmdm.spyrothedragon.databinding.FragmentGuiaPaso04Binding;

public class GuiaPaso04Fragment extends Fragment {
    private static final String TAG = "GuiaPaso04Fragment";
    private FragmentGuiaPaso04Binding binding;
    private GuiaNavigationListener navigationListener;
    private static final long SHOW_DELAY = 3000;
    private static final long HIDE_DELAY = 6000;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentGuiaPaso04Binding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findNavigationListener();
        setupButtons();
        startButtonAnimations();
        startSwipeAnimation();
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
}
