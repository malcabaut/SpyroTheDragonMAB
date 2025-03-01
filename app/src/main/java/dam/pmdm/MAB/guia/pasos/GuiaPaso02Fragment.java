package dam.pmdm.MAB.guia.pasos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import dam.pmdm.MAB.guia.GuiaNavigationListener;
import dam.pmdm.spyrothedragon.databinding.FragmentGuiaPaso02Binding;

/**
 * Fragmento que representa el segundo paso de la guía de la aplicación.
 */
public class GuiaPaso02Fragment extends Fragment {

    private static final String TAG = "GuiaPaso02Fragment";
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
        dam.pmdm.spyrothedragon.databinding.FragmentGuiaPaso02Binding binding = FragmentGuiaPaso02Binding.inflate(inflater, container, false);
        return binding.getRoot();
    }

}
