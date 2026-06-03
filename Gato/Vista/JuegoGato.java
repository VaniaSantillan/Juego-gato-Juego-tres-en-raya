package Vista;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

/* Clase del juego "Gato" Permite jugar contra la máquina 
 * mostrando íconos de X y O,
 * lleva un marcador de victorias, empates y derrotas,
 * y reinicia la partida según la elección del usuario.
 */

public class JuegoGato extends JFrame {
    private JButton[][] tablero = new JButton[3][3]; //// Matriz de botones que representan las 9 casillas del tablero
    private String jugador, maquina; // Símbolos de los jugadores
    private Random rand = new Random();
    private ImageIcon imgJugador, imgMaquina;// Íconos que muestran X u O con imágenes
    private JLayeredPane capa;// Capa para superponer elementos (tablero, líneas de victoria, marcador)
    private String nombreJugador; //Nombre del usuario

    // Variables del marcador
    private int victoriasJugador = 0;
    private int victoriasMaquina = 0;
    private int empates = 0;
    private JLabel marcador; //Etiqueta para visualizar el marcador 

    // Constructor del juego.
    public JuegoGato(String jugador, String nombreJugador) {
        this.jugador = jugador;
        // Si el jugador elige X, la máquina usa O, y viceversa
        this.maquina = jugador.equals("X") ? "O" : "X";
        this.nombreJugador = nombreJugador;

        // Carga las imágenes de los íconos de X y O
        imgJugador = crearIcono(jugador.equals("X") ? "/resources/Equis.png" : "/resources/Circulo.png", 100, 100);
        imgMaquina = crearIcono(jugador.equals("X") ? "/resources/Circulo.png" : "/resources/Equis.png", 100, 100);

        // Configuración de la ventana
        setTitle("Juego de Gato");
        setSize(420, 500);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Capa principal
        capa = new JLayeredPane();
        capa.setPreferredSize(new Dimension(420, 420));
        setContentPane(capa);

        // Estilo del marcador 
        marcador = new JLabel("", SwingConstants.CENTER);
        marcador.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 16));
        marcador.setOpaque(true);
        marcador.setBackground(new Color(255, 230, 245)); 
        marcador.setForeground(new Color(123, 31, 162));  
        marcador.setBounds(0, 420, 420, 40);
        capa.add(marcador, Integer.valueOf(2));
        actualizarMarcador();

        // Crear tablero y pone los botones 
        crearPanelBotones();

        setVisible(true);
    }

     /**
     * Crea el panel con los 9 botones del tablero.
     * Configura colores, bordes, efectos hover y eventos de clic.
     */
    private void crearPanelBotones() {
        JPanel panelBotones = new JPanel(new GridLayout(3, 3));
        panelBotones.setBounds(0, 0, 420, 420);
        panelBotones.setBackground(new Color(255, 230, 245)); // rosa claro

        // Recorre filas y columnas para crear los 9 botones
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                tablero[i][j] = new JButton();
                tablero[i][j].setFocusPainted(false);
                tablero[i][j].setBackground(new Color(255, 230, 245)); // base rosa
                tablero[i][j].setBorder(BorderFactory.createLineBorder(new Color(194, 123, 204), 2)); // borde morado
                final int fila = i, col = j;

                // Hover
                tablero[i][j].addMouseListener(new MouseAdapter() {
                    public void mouseEntered(MouseEvent e) {
                        if (tablero[fila][col].getIcon() == null)
                            tablero[fila][col].setBackground(new Color(255, 200, 230)); // rosa más fuerte
                    }

                    public void mouseExited(MouseEvent e) {
                        if (tablero[fila][col].getIcon() == null)
                            tablero[fila][col].setBackground(new Color(255, 230, 245)); // vuelve al rosa claro
                    }
                });
                
                // Acción al hacer clic: turno del jugador
                tablero[i][j].addActionListener(e -> turnoJugador(fila, col));
                panelBotones.add(tablero[i][j]);
            }
        }

        capa.add(panelBotones, Integer.valueOf(0));
    }

    /**
     * Maneja el turno del jugador.
     * Coloca su icono, verifica si ganó o si hay empate, y si no, deja jugar a la máquina.
     */
    private void turnoJugador(int fila, int col) {
        if (tablero[fila][col].getIcon() == null) {
            tablero[fila][col].setIcon(imgJugador);
            tablero[fila][col].putClientProperty("simbolo", jugador);
            if (verificarGanador(jugador)) return;
            if (!empate()) turnoMaquina();
        }
    }

    //Turno de la máquina: elige la mejor jugada posible y verifica resultado.
    private void turnoMaquina() {
        int[] jugada = mejorJugada();
        tablero[jugada[0]][jugada[1]].setIcon(imgMaquina);
        tablero[jugada[0]][jugada[1]].putClientProperty("simbolo", maquina);
        verificarGanador(maquina);
    }

    /**
     * Determina la mejor jugada para la máquina:
     * 1. Gana si puede.
     * 2. Bloquea al jugador si está por ganar.
     * 3. Toma el centro si está libre.
     * 4. Toma una esquina.
     * 5. Toma un lado.
     * 6. En último caso, elige una casilla libre al azar.
     */
    private int[] mejorJugada() {
        // Ganar si puede
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (tablero[i][j].getIcon() == null) {
                    tablero[i][j].putClientProperty("simbolo", maquina);
                    if (verificarGanadorSinMostrar(maquina)) {
                        tablero[i][j].putClientProperty("simbolo", null);
                        return new int[]{i, j};
                    }
                    tablero[i][j].putClientProperty("simbolo", null);
                }

        // Bloquear jugador
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (tablero[i][j].getIcon() == null) {
                    tablero[i][j].putClientProperty("simbolo", jugador);
                    if (verificarGanadorSinMostrar(jugador)) {
                        tablero[i][j].putClientProperty("simbolo", null);
                        return new int[]{i, j};
                    }
                    tablero[i][j].putClientProperty("simbolo", null);
                }

        // Centro
        if (tablero[1][1].getIcon() == null) return new int[]{1, 1};

        // Esquinas
        int[][] esquinas = {{0, 0}, {0, 2}, {2, 0}, {2, 2}};
        for (int[] esquina : esquinas)
            if (tablero[esquina[0]][esquina[1]].getIcon() == null)
                return esquina;

        // Lados
        int[][] lados = {{0, 1}, {1, 0}, {1, 2}, {2, 1}};
        for (int[] lado : lados)
            if (tablero[lado[0]][lado[1]].getIcon() == null)
                return lado;

        // Último recurso
        int fila, col;
        do {
            fila = rand.nextInt(3);
            col = rand.nextInt(3);
        } while (tablero[fila][col].getIcon() != null);
        return new int[]{fila, col};
    }

    //Verifica si hay un ganador o empate, actualiza el marcador y reinicia el juego.
    private boolean verificarGanador(String simbolo) {
        int linea = encontrarLineaGanadora(simbolo);
        if (linea != -1) {
            mostrarLinea(linea, simbolo);
            if (simbolo.equals(jugador)) {
                victoriasJugador++;
                JOptionPane.showMessageDialog(this, "¡Ganaste " + nombreJugador + "!");
            } else {
                victoriasMaquina++;
                JOptionPane.showMessageDialog(this, "Perdiste contra la máquina");
            }
            actualizarMarcador();
            reiniciarJuego();
            return true;
        }

        if (empate()) {
            empates++;
            JOptionPane.showMessageDialog(this, "¡Empate!");
            actualizarMarcador();
            reiniciarJuego();
            return true;
        }

        return false;
    }

    // Versión de verificación sin mostrar mensajes ni reiniciar
    private boolean verificarGanadorSinMostrar(String simbolo) {
        return encontrarLineaGanadora(simbolo) != -1;
    }

    /**
     * Revisa todas las filas, columnas y diagonales para encontrar
     * si hay una línea ganadora del símbolo indicado.
     */
    private int encontrarLineaGanadora(String simbolo) {
        //Filas
        for (int i = 0; i < 3; i++)
            if (simbolo.equals(tablero[i][0].getClientProperty("simbolo")) &&
                simbolo.equals(tablero[i][1].getClientProperty("simbolo")) &&
                simbolo.equals(tablero[i][2].getClientProperty("simbolo"))) return i + 1;

        //Columnas
        for (int j = 0; j < 3; j++)
            if (simbolo.equals(tablero[0][j].getClientProperty("simbolo")) &&
                simbolo.equals(tablero[1][j].getClientProperty("simbolo")) &&
                simbolo.equals(tablero[2][j].getClientProperty("simbolo"))) return j + 4;

        //Diagonales 
        if (simbolo.equals(tablero[0][0].getClientProperty("simbolo")) &&
            simbolo.equals(tablero[1][1].getClientProperty("simbolo")) &&
            simbolo.equals(tablero[2][2].getClientProperty("simbolo"))) return 7;

        if (simbolo.equals(tablero[0][2].getClientProperty("simbolo")) &&
            simbolo.equals(tablero[1][1].getClientProperty("simbolo")) &&
            simbolo.equals(tablero[2][0].getClientProperty("simbolo"))) return 8;

        return -1;
    }

    //Para mostrar la linea visual sobre la jugada ganadora 
    private void mostrarLinea(int linea, String simbolo) {
        String nombreArchivo = simbolo.equals("X") ? "EquisLinea" : "CirculoLinea";
        nombreArchivo += linea + ".png";
        ImageIcon iconLinea = new ImageIcon(getClass().getResource("/resources/" + nombreArchivo));
        Image img = iconLinea.getImage().getScaledInstance(420, 420, Image.SCALE_SMOOTH);
        JLabel labelLinea = new JLabel(new ImageIcon(img));
        labelLinea.setBounds(0, 0, 420, 420);
        capa.add(labelLinea, Integer.valueOf(1));
        capa.repaint();
    }

    //Determina si todas las casillas estan llenas sin ganador 
    private boolean empate() {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (tablero[i][j].getIcon() == null) return false;
        return true;
    }

    //Reinicia el tablero o muestra los resultados finales si el jugador no desea continuar.
    private void reiniciarJuego() {
        int opcion = JOptionPane.showConfirmDialog(this, "¿Quieres jugar otra vez?", "Reiniciar", JOptionPane.YES_NO_OPTION);
        if (opcion == JOptionPane.YES_OPTION) {
            // Limpia el tablero y recrea los botones
            Component[] comps = capa.getComponents();
            for (Component c : comps)
                if ((c instanceof JPanel || c instanceof JLabel) && c != marcador)
                    capa.remove(c);

            crearPanelBotones();
            capa.repaint();
        } else {
            // Muestra el resumen final del marcador
            String resumen = "Resultados finales \n\n" +
                    nombreJugador + ": " + victoriasJugador + " victorias\n" +
                    "Máquina: " + victoriasMaquina + " victorias\n" +
                    "Empates: " + empates + "\n\n";

            if (victoriasJugador > victoriasMaquina)
                resumen += "¡Ganó " + nombreJugador + "!";
            else if (victoriasMaquina > victoriasJugador)
                resumen += "¡Ganó la Máquina!";
            else resumen += "¡Empate!";

            JOptionPane.showMessageDialog(this, resumen, "Resultados finales", JOptionPane.INFORMATION_MESSAGE);
            dispose(); //Cierra la ventana
        }
    }
    
    //Crea un icono escalado desde una ruta del proyecto.
    private ImageIcon crearIcono(String ruta, int ancho, int alto) {
        ImageIcon icon = new ImageIcon(getClass().getResource(ruta));
        Image img = icon.getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }

    //Muestra el marcador con la estructura 
    private void actualizarMarcador() {
        marcador.setText("<html><table width='100%'><tr>" +
                "<td align='left'> Máquina: " + victoriasMaquina + "</td>" +
                "<td align='center'> Empates: " + empates + "</td>" +
                "<td align='right'> " + nombreJugador + ": " + victoriasJugador + "</td>" +
                "</tr></table></html>");
    }
}
