# 🎮 Proyecto Integrador Java: JuegoAhorjado
## [**ByteNine**]

Este repositorio contiene el proyecto integrador del JuegoAhorjado, desarrollado en Java con una arquitectura de software completa (Consola) y una interfaz gráfica de usuario (GUI) funcional.

---

## 👥 Nuestro Equipo

| Integrantes |Perfil de GitHub
| :--- | :--- |
| Valeria Elena Lardín | [@Vale10Lar](https://github.com/Vale10Lar) |
| Maximiliano Ariel Morales | [@MaxiMorales96](https://github.com/MaxiMorales96) |
| Agustín Santarena | [@GabrielSantarena](https://github.com/GabrielSantarena/) |
| Gabriel Santarena | [@AgusSantarena](https://github.com/AgusSantarena/) |
| Gustavo Ariel Rodriguez | [@GustavoRodriguez79](https://github.com/GustavoRodriguez79) |
| Santiago Nicolas Martin | [@santy171004](https://github.com/santy171004) |
| Nahuel Spikerman | [@nawe2](https://github.com/nawe2) |

---

## 📖 Sobre el Proyecto

El proyecto implementa una aplicación de escritorio completa que permite a los usuarios registrarse, iniciar sesión y jugar al clásico juego del Ahorcado. El sistema distingue entre jugadores y administradores, ofreciendo un panel de control especial para la gestión del juego.

### Funcionalidades Clave

* **Modo de Juego Dual:** Al iniciar la aplicación, el usuario puede elegir entre jugar en la **interfaz gráfica (GUI)** moderna o en la **consola tradicional**.
* **Gestión de Cuentas:** Sistema completo para el **Registro de nuevos jugadores** y un **Inicio de Sesión** seguro.
* **Sistema de Roles:** El sistema diferencia los permisos entre un `JUGADOR` (accede al juego) y un `ADMIN` (accede al panel de gestión).
* **Panel de Administración:** Los usuarios con rol `ADMIN` pueden:
    * Ver un dashboard de estadísticas.
    * Gestionar el diccionario de palabras (añadir o eliminar palabras y pistas).
    * Gestionar la lista de usuarios (crear nuevos administradores o eliminar usuarios existentes).
* **Ranking y Estadísticas:** Los jugadores pueden competir por puntos y consultar un **Ranking Global** (`VentanaRankingGUI`), además de revisar su **Historial de Partidas** personal (`VentanaHistorialGUI`).

---

## 🛠️ Tecnologías Utilizadas

Este proyecto fue construido 100% en **Java**, aplicando los principios fundamentales de la Programación Orientada a Objetos (Herencia, Polimorfismo, Encapsulación) y utilizando la biblioteca **Java Swing** para la interfaz gráfica.



<p align="left">
  <img src="https://upload.wikimedia.org/wikipedia/en/3/30/Java_programming_language_logo.svg" alt="Logo de Java" width="60" />
</p>

Todo el desarrollo, compilación y empaquetado del proyecto se gestionó utilizando el IDE **Apache NetBeans**.

<p align="left">
  <img src="https://upload.wikimedia.org/wikipedia/commons/9/98/Apache_NetBeans_Logo.svg" alt="Logo de Apache NetBeans" width="70" />
</p>

---

## ✨ Características Principales

Este proyecto va más allá de un simple juego y funciona como una aplicación de escritorio completa:

* **🕹️ Modo de Juego Dual:** El usuario puede elegir entre jugar en la **GUI moderna de Swing** o en la **consola tradicional** al iniciar la aplicación.
* **👤 Gestión de Cuentas:** Sistema completo de **Inicio de Sesión** y **Registro de Usuarios**.
* **🔒 Sistema de Roles:** Diferenciación clara entre `JUGADOR` y `ADMIN`, cada uno con su propia interfaz y permisos.
* **⚙️ Panel de Administración:** Los usuarios `ADMIN` tienen acceso a un dashboard (`VentanaAdministracionGUI`) para:
    * Gestionar usuarios (Crear nuevos administradores, ver y eliminar usuarios existentes).
    * Gestionar el diccionario de palabras (Añadir y eliminar palabras y pistas).
* **🎮 Lógica de Juego Completa:**
    * Motor de juego (`JuegoAhorcado`) que maneja los intentos, procesa letras y palabras.
    * Dibujo personalizado del ahorcado que progresa con cada error (`PanelDibujoAhorcado`).
* **🏆 Estadísticas del Jugador:**
    * **Ranking Global:** Los jugadores pueden ver un ranking con las puntuaciones más altas (`VentanaRankingGUI`).
    * **Historial de Partidas:** Cada jugador puede revisar su propio historial de partidas jugadas (`VentanaHistorialGUI`).

---

## 🏗️ Arquitectura del Software

El proyecto está estructurado en cuatro capas principales para una correcta separación de intereses:

1.  **`domain` (Dominio):**
    * Contiene las entidades puras del negocio: `Usuario`, `Palabra`, `Partida`, y el enum `Rol`.
2.  **`repository` (Repositorio):**
    * Actúa como la capa de acceso a datos (simulada en memoria).
    * `GestorUsuarios`: Maneja el CRUD y la autenticación de usuarios.
    * `Diccionario`: Maneja la colección de palabras disponibles.
3.  **`logic` (Lógica):**
    * Contiene el "motor" del juego.
    * `JuegoBaseAbstracto`: Define el esqueleto de un juego (Patrón Template Method).
    * `JuegoAhorcado`: Implementación concreta de la lógica del ahorcado.
4.  **`app` y `gui.dominio` (Presentación):**
    * `app`: Contiene el lanzador principal (`AhorcadoApp`) y la lógica de la consola.
    * `gui.dominio`: Contiene todas las ventanas (`JFrame`) y paneles (`JPanel`) de la interfaz gráfica de Swing.

---

## 🚀 Cómo Ejecutar el Proyecto

1.  Asegúrate de tener un JDK (Java Development Kit) instalado.
2.  Importa el proyecto en tu IDE preferido (NetBeans, IntelliJ, VS Code).
3.  Establece el punto de entrada principal (Main Class) como:
    `ar.com.juegoahorcado.app.AhorcadoApp`
4.  Ejecuta el proyecto.

Al iniciar, un diálogo te preguntará si deseas usar:
* **Opción 1: Consola**
* **Opción 2: Interfaz Gráfica (GUI)**

### Credenciales de Administrador

Para probar el panel de administración, puedes usar las credenciales por defecto cargadas en el `GestorUsuarios`:

* **Usuario:** `GabiAdmin`
* **Contraseña:** `1234`