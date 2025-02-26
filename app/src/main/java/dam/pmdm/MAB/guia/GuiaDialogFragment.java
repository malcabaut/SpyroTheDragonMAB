package dam.pmdm.MAB.guia;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.viewpager2.widget.ViewPager2;
import dam.pmdm.spyrothedragon.R;
import dam.pmdm.MAB.guia.pasos.GuiaAdapter;

public class GuiaDialogFragment extends DialogFragment implements GuiaNavigationListener {

    private static final String NOMBRE_PREFERENCIAS = "PreferenciasApp";
    private static final String PROGRESO_GUIA = "ProgresoGuia";
    private static final String GUIA_COMPLETADA = "GuiaCompletada";
    private ViewPager2 vistaPaginada;
    private SharedPreferences preferencias;
    private int pasoActual = 0;

    public GuiaDialogFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.FullScreenDialog);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_guia, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        preferencias = requireActivity().getSharedPreferences(NOMBRE_PREFERENCIAS, getContext().MODE_PRIVATE);
        pasoActual = preferencias.getInt(PROGRESO_GUIA, 0);

        vistaPaginada = view.findViewById(R.id.viewPager);
        GuiaAdapter adaptador = new GuiaAdapter(getChildFragmentManager(), getLifecycle());
        vistaPaginada.setAdapter(adaptador);
        vistaPaginada.setCurrentItem(pasoActual, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setLayout(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
            );
        }
    }

    @Override
    public void navigateToNextStep() {
        if (vistaPaginada.getCurrentItem() < vistaPaginada.getAdapter().getItemCount() - 1) {
            pasoActual++;
            guardarProgreso();
            vistaPaginada.setCurrentItem(pasoActual, true);
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

    private void guardarProgreso() {
        preferencias.edit().putInt(PROGRESO_GUIA, pasoActual).apply();
    }

    private void finalizarGuia() {
        preferencias.edit().putBoolean(GUIA_COMPLETADA, true).apply();
        dismiss();
    }
}

