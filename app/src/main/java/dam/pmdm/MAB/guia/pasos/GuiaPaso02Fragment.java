package dam.pmdm.MAB.guia.pasos;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import dam.pmdm.MAB.guia.GuiaNavigationListener;
import dam.pmdm.spyrothedragon.databinding.FragmentGuiaPaso02Binding;

/**
 * Fragmento que representa el primer paso de la guía de la aplicación.
 */
public class GuiaPaso02Fragment extends Fragment {

    private static final String TAG = "GuiaPaso01Fragment";
    private FragmentGuiaPaso02Binding binding;
    private GuiaNavigationListener navigationListener;

    /**
     * Constructor vacío recomendado para fragmentos.
     */
    public GuiaPaso02Fragment() {

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

        // Llamada a findNavigationListener para asignar el listener correctamente
        findNavigationListener();

        // Configurar botones con animaciones
        setupButtonsWithAnimations();
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
            Log.e(TAG, "GuiaPaso01Fragment ERROR: No se encontró el listener.");
            binding.btnNext.setEnabled(false); // Desactivar botón si no hay listener
        }
    }


    /**
     * Configura los botones con animaciones y evita clics rápidos.
     */
    private void setupButtonsWithAnimations() {
        Animation fadeIn = AnimationUtils.loadAnimation(getContext(), android.R.anim.fade_in);

        binding.btnNext.setOnClickListener(v -> {
            Log.d(TAG, "GuiaPaso01Fragment Botón 'Siguiente' presionado.");
            v.startAnimation(fadeIn);
            v.setEnabled(true); // Evita doble clic
            if (navigationListener != null) {
                Log.d(TAG, "GuiaPaso01Fragment Navegando al siguiente paso.");
                navigationListener.navigateToNextStep();
            }
        });

        binding.btnSkip.setOnClickListener(v -> {
            Log.d(TAG, "GuiaPaso01Fragment Botón 'Saltar' presionado.");
            v.startAnimation(fadeIn);
            v.setEnabled(true);
            if (navigationListener != null) {
                Log.d(TAG, "GuiaPaso01Fragment Saltando la guía.");
                navigationListener.skipGuide();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null; // Evitar fugas de memoria
    }
}
