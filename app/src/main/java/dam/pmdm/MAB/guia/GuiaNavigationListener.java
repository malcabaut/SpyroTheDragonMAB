package dam.pmdm.MAB.guia;

/**
 * Interfaz que define los métodos de navegación para la guía de la aplicación.
 * Esta interfaz permite la comunicación entre los fragmentos de pasos individuales
 * y el fragmento principal que controla la navegación.
 */
public interface GuiaNavigationListener {

    /**
     * Avanza al siguiente paso de la guía.
     * Este método debe ser invocado cuando el usuario desea continuar
     * al siguiente paso, por ejemplo, al pulsar un botón "Siguiente".
     */
    void navigateToNextStep();

    /**
     * Retrocede al paso anterior de la guía.
     * Este método debe ser invocado cuando el usuario desea volver
     * al paso previo, por ejemplo, al pulsar un botón "Atrás".
     */
    void navigateToPreviousStep();

    /**
     * Omite la guía completa.
     * Este método debe ser invocado cuando el usuario desea saltar
     * toda la guía, por ejemplo, al pulsar un botón "Omitir".
     */
    void skipGuide();
}