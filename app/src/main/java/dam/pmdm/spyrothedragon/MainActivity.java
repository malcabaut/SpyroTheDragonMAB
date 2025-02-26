package dam.pmdm.spyrothedragon;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import java.util.Objects;

import dam.pmdm.MAB.guia.GuiaDialogFragment;

import dam.pmdm.spyrothedragon.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    NavController navController = null;


    // Constantes para claves de SharedPreferences
    private static final String PROGRESO_GUIA = "ProgresoGuia";
    private static final String GUIA_COMPLETADA = "GuiaCompletada";
    private static final String NOMBRE_PREFERENCIAS = "PreferenciasApp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dam.pmdm.spyrothedragon.databinding.ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Fragment navHostFragment = getSupportFragmentManager().findFragmentById(R.id.navHostFragment);
        if (navHostFragment != null) {
            navController = NavHostFragment.findNavController(navHostFragment);
            NavigationUI.setupWithNavController(binding.navView, navController);
            NavigationUI.setupActionBarWithNavController(this, navController);
        }

        binding.navView.setOnItemSelectedListener(this::selectedBottomMenu);

        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            // Para las pantallas de los tabs, no queremos que aparezca la flecha de atrás
            // Si se navega a una pantalla donde se desea mostrar la flecha de atrás, habilítala
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(destination.getId() != R.id.navigation_characters && destination.getId() != R.id.navigation_worlds && destination.getId() != R.id.navigation_collectibles);
        });

        // Comprobar si la guía se completó previamente a través de SharedPreferences
        SharedPreferences prefs = getSharedPreferences(NOMBRE_PREFERENCIAS, MODE_PRIVATE);

        // Obtener el valor booleano almacenado bajo la clave "GuiaCompletada"
        // Si no existe, se asume el valor por defecto "false" (es decir, la guía no ha sido completada)
        boolean guideCompleted = prefs.getBoolean(GUIA_COMPLETADA, false);

        // Si la guía no se ha completado, se muestra la guía al usuario
        if (!guideCompleted) {
            showGuide(); // Muestra la guía
        }
    }

    private void showGuide() {

        GuiaDialogFragment dialog = new GuiaDialogFragment();
        dialog.show(getSupportFragmentManager(), "GuiaDialog");
    }


    private boolean selectedBottomMenu(@NonNull MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.nav_characters)
            navController.navigate(R.id.navigation_characters);
        else if (menuItem.getItemId() == R.id.nav_worlds)
            navController.navigate(R.id.navigation_worlds);
        else navController.navigate(R.id.navigation_collectibles);
        return true;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Infla el menú
        getMenuInflater().inflate(R.menu.about_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_info) {
            showInfoDialog();
            return true;
        } else if (item.getItemId() == R.id.action_reset_guide) {
            resetGuide();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showInfoDialog() {
        // Crear un diálogo de información
        new AlertDialog.Builder(this).setTitle(R.string.title_about).setMessage(R.string.text_about).setPositiveButton(R.string.accept, null).show();
    }

    /**
     * Reinicia la guía de usuario realizando los siguientes pasos:
     * 1. Restablece el estado en SharedPreferences marcando la guía como no completada.
     * 2. Vuelve a mostrar la guía desde el inicio.
     */
    private void resetGuide() {
        // Obtener el editor de SharedPreferences para modificar los valores guardados
        SharedPreferences.Editor editor = getSharedPreferences(NOMBRE_PREFERENCIAS, MODE_PRIVATE).edit();

        // Restablecer el estado de la guía: marcarla como no completada
        editor.putBoolean(GUIA_COMPLETADA, false);

        // Reiniciar el progreso de la guía a la primera etapa
        editor.putInt(PROGRESO_GUIA, 0);

        // Aplicar los cambios en SharedPreferences
        editor.apply();

        // Volver a mostrar la guía desde el principio
        showGuide();
    }


}