package dam.pmdm.MAB.guia.pasos;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class GuiaAdapter extends FragmentStateAdapter {

    private static final int TOTAL_PASOS = 6;

    public GuiaAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        // Dependiendo de la posición, se crea el fragmento correspondiente
        switch (position) {
            case 0: return new GuiaPaso01Fragment();
            case 1: return new GuiaPaso02Fragment();
            case 2: return new GuiaPaso03Fragment();
            case 3: return new GuiaPaso04Fragment();
            case 4: return new GuiaPaso05Fragment();
            case 5: return new GuiaPaso06Fragment();
            default:
                return new GuiaPaso06Fragment();
        }
    }

    @Override
    public int getItemCount() {
        return TOTAL_PASOS;
    }
}
