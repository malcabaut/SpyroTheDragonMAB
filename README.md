# TAREA 4 - PMDM 2024/25 - IES Aguadulce

---

Este repositorio contiene la tarea 4 del curso de **Programación Multimedia y Dispositivos Móviles (PMDM)** para el año académico 2024/25 realizado en **IES Aguadulce**.

---

## Introducción

Esta aplicación está inspirada en el universo de *Spyro the Dragon*. Su objetivo es ofrecer a los usuarios una experiencia interactiva y divertida mediante una guía de inicio visual y dinámica, junto con sorpresas ocultas (Easter Eggs). La app guía al usuario a través de sus principales funcionalidades y permite interactuar con los personajes, mundos y coleccionables del juego, utilizando animaciones, sonidos y efectos visuales.

---

## Características principales

- **Guía de inicio interactiva:**
  - **Pantalla de bienvenida:** Introduce al usuario a la app con un fondo inspirado en *Spyro the Dragon* y un botón para comenzar la guía.
  - **Pantallas de explicación de pestañas:** Se guía al usuario a través de las pestañas de *Personajes*, *Mundos* y *Coleccionables*, usando bocadillos informativos con animaciones.
  - **Pantalla final:** Resumen de los pasos completados en la guía y acceso a la app. Aquí, el usuario puede elegir si desea saltarse la guía o continuar explorando la app.

- **Animaciones y Sonidos:**
  - Efectos visuales y transiciones entre pantallas para hacer la experiencia más dinámica y fluida.
  - Sonidos temáticos de *Spyro the Dragon* durante la navegación y la interacción con los bocadillos informativos. Los sonidos ayudan a sumergir al usuario en el universo del juego.

- **Easter Eggs:**
  - **Easter Egg con video:** Al interactuar con las Gemas en la pestaña de *Coleccionables*, se activa un video temático de *Spyro* que muestra escenas especiales del juego.
  - **Easter Egg con animación:** Al mantener pulsado sobre el personaje Spyro en la pestaña de *Personajes*, se activa una animación de fuego que emula los poderes del dragón en el juego.

- **Interfaz de usuario:**
  - La app utiliza layouts XML personalizados con transparencia, de modo que la guía se sobrepone a la interfaz sin interrumpir la navegación principal de la app.
  - **Dialogs:** Para mostrar mensajes interactivos, se utilizan diálogos personalizados que le permiten al usuario elegir entre continuar con la guía o saltarla.
  - **RecyclerView:** Para mostrar los elementos de los mundos y personajes de manera eficiente.

---

## Tecnologías utilizadas

-Android SDK: Utiliza componentes como RecyclerView, TextView, ImageView para construir la interfaz y manejar listas de datos.
-ObjectAnimator & AnimatorSet: Se usan para crear animaciones de transformación, como escalado, rotación y cambio de opacidad en los elementos de la interfaz.
-MediaPlayer: Reproduce sonidos en respuesta a acciones del usuario, como la animación de un personaje.
-SoundPool: Usado en conjunto con MediaPlayer para manejar efectos de sonido y optimización de recursos, como los efectos de fuego o respiración de dragón.
-XML Layout: Se utiliza para definir la estructura visual de cada elemento en la lista, como las tarjetas con imagen y texto.
-Java: El código está escrito en Java, gestionando la lógica de la aplicación y la interacción con la interfaz.
-ViewPager2: Se emplea para la navegación horizontal entre fragmentos, proporcionando una experiencia de desplazamiento fluido.
-Fragmentos: Usados para modularizar la UI y gestionar diferentes pantallas o secciones de la aplicación.
-DialogFragment: Utilizado para mostrar cuadros de diálogo modales que permiten interacción sin abandonar la actividad actual.
-RecyclerView: Utilizado para mostrar listas de elementos de manera eficiente y con desplazamiento.
-ConstraintLayout: Se utiliza para crear diseños de interfaz flexibles y adaptables a diferentes tamaños de pantalla.
-Logcat: Usado para la depuración, proporcionando registros que ayudan a monitorear el estado de la aplicación durante el desarrollo.
-SharedPreferences: Se use en la app para almacenar configuraciones o preferencias del usuario de manera persistente.

---

## Instrucciones de uso

1. Clona este repositorio en tu máquina local.
2. Abre el proyecto en Android Studio.
3. Explora la aplicación en el emulador y verifica las funcionalidades de la guía de inicio y los Easter Eggs.
4. Para probar la guía de inicio, puedes volver a a verla desde la barra de navegación, en los 3 puntos.

---

## Conclusiones del desarrollador

Durante el desarrollo de este proyecto, se aprendió a integrar animaciones y sonidos en una app móvil, mejorando la experiencia del usuario. Se enfrentaron desafíos relacionados con la implementación de transiciones y la sincronización de efectos de sonido, pero finalmente se logró una experiencia fluida y atractiva. La creación de Easter Eggs añadió un toque de sorpresa y diversión que hace que la app sea más memorable para los usuarios.

---

## Capturas de pantalla (opcional)

![guia_ALL.gif](/master/README_IMG/guia_ALL.gif)
