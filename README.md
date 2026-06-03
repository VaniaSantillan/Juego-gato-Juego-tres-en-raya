# Juego del Gato (Tres en Raya) con IA

## Descripción

Juego del Gato (Tres en Raya) desarrollado en Java con interfaz gráfica. El jugador compite contra una Inteligencia Artificial (IA) en partidas individuales, con la posibilidad de personalizar su experiencia antes de comenzar.

## Características

* Juego Usuario vs IA.
* Registro del nombre del jugador.
* Selección de figura:

  * ❌ Equis
  * ⭕ Círculo
* Turnos automáticos entre el usuario y la IA.
* Detección automática de victorias y empates.
* Contador de estadísticas en tiempo real:

  * Victorias del usuario.
  * Victorias de la IA.
  * Empates.
* Interfaz gráfica amigable e intuitiva.
* Recursos visuales personalizados para cada jugada.

## Funcionamiento

1. Al iniciar la aplicación, el usuario ingresa su nombre.
2. Selecciona la figura con la que desea jugar (Equis o Círculo).
3. Comienza la partida contra la Inteligencia Artificial.
4. Los jugadores realizan movimientos alternados sobre el tablero.
5. El sistema verifica después de cada movimiento si existe un ganador o si la partida termina en empate.
6. Al finalizar una partida, las estadísticas se actualizan automáticamente.

## Tecnologías Utilizadas

* Java
* Java Swing
* Programación Orientada a Objetos (POO)

## Estructura del Proyecto

```text
Gato/
│
├── Principal/
│   └── Principal.java
│
├── Vista/
│   ├── VentanaInicio.java
│   └── JuegoGato.java
│
└── Resources/
    ├── Equis.png
    ├── Circulo.png
    ├── UsuarioEquis.png
    ├── UsuarioCirculo.png
    └── demás recursos gráficos...
```

## Requisitos

* Java JDK 8 o superior.
* Sistema operativo compatible con Java.

## Ejecución

1. Clonar el repositorio:

```bash
git clone https://github.com/VaniaSantillan/Juego-gato-Juego-tres-en-raya.git
```

2. Abrir el proyecto en su IDE de preferencia.

3. Compilar y ejecutar:

```bash
Principal.java
```

## Objetivo del Proyecto

Este proyecto fue desarrollado con fines académicos para practicar conceptos de:

* Programación Orientada a Objetos.
* Desarrollo de interfaces gráficas.
* Manejo de eventos.
* Implementación de lógica de juego.
* Diseño de una Inteligencia Artificial básica para toma de decisiones.

## Autor

**Vania Santillan Aguilar**

Estudiante de Ingeniería en Computación.
