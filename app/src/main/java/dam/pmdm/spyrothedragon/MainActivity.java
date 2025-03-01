package dam.pmdm.spyrothedragon;

import android.content.SharedPreferences;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.os.VibratorManager;
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
    private static final String GUIA_COMPLETADA = "GuiaCompleta";
    private static final String NOMBRE_PREFERENCIAS = "PreferenciasApp";

    // Para sonido y vibración
    private SoundPool soundPool;
    private int soundId;
    private int upSoundId; // Sonido para "up_1"
    private Vibrator vibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Inicializar SoundPool de forma moderna
        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build();
        soundPool = new SoundPool.Builder()
                .setMaxStreams(10)
                .setAudioAttributes(audioAttributes)
                .build();

        // Cargar los sonidos
        soundId = soundPool.load(this, R.raw.clip_clop1, 1);
        upSoundId = soundPool.load(this, R.raw.up_1, 1); // Cargar el sonido up_1

        // Inicializar Vibrator utilizando VibratorManager en API 31 o superior
        VibratorManager vibratorManager = (VibratorManager) getSystemService(VIBRATOR_MANAGER_SERVICE);
        if (vibratorManager != null) {
            vibrator = vibratorManager.getDefaultVibrator();
        }

        // Configuración de navegación
        Fragment navHostFragment = getSupportFragmentManager().findFragmentById(R.id.navHostFragment);
        if (navHostFragment != null) {
            navController = NavHostFragment.findNavController(navHostFragment);
            NavigationUI.setupWithNavController(binding.navView, navController);
            NavigationUI.setupActionBarWithNavController(this, navController);
        }

        binding.navView.setOnItemSelectedListener(this::selectedBottomMenu);

        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            // Reproducir sonido y vibrar al cambiar de fragmento
            playSound();
            vibrate();

            // Para las pantallas de los tabs, no mostrar la flecha de atrás
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(
                    destination.getId() != R.id.navigation_characters &&
                            destination.getId() != R.id.navigation_worlds &&
                            destination.getId() != R.id.navigation_collectibles);
        });

        // Comprobar si la guía se completó previamente a través de SharedPreferences
        SharedPreferences prefs = getSharedPreferences(NOMBRE_PREFERENCIAS, MODE_PRIVATE);
        boolean guideCompleted = prefs.getBoolean(GUIA_COMPLETADA, false);

        // Si la guía no se ha completado, se muestra la guía al usuario
        if (!guideCompleted) {
            showGuide();
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
        else
            navController.navigate(R.id.navigation_collectibles);
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
            playUpSound(); // Reproducir el sonido up_1
            showInfoDialog();
            return true;
        } else if (item.getItemId() == R.id.action_reset_guide) {
            resetGuide();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void playUpSound() {
        soundPool.play(upSoundId, 1f, 1f, 0, 0, 1f);
    }

    private void showInfoDialog() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.title_about)
                .setMessage(R.string.text_about)
                .setPositiveButton(R.string.accept, null)
                .show();
    }

    private void resetGuide() {
        SharedPreferences.Editor editor = getSharedPreferences(NOMBRE_PREFERENCIAS, MODE_PRIVATE).edit();
        editor.putBoolean(GUIA_COMPLETADA, false);
        editor.putInt(PROGRESO_GUIA, 0);
        editor.apply();
        showGuide();
    }

    // Función para reproducir el sonido
    private void playSound() {
        soundPool.play(soundId, 1f, 1f, 0, 0, 1f);
    }

    // Función para hacer vibrar el dispositivo
    private void vibrate() {
        if (vibrator != null) {
            VibrationEffect vibrationEffect = VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE);
            vibrator.vibrate(vibrationEffect);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (soundPool != null) {
            soundPool.release();
        }
        if (vibrator != null) {
            vibrator.cancel();
        }
    }
}
