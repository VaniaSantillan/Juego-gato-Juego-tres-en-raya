package Vista;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.PlainDocument;

/**
 * CLASE VentanaInicio
   Esta es la primer ventana que va a emerger en el programa. 
 * Esta permite que el usuario ingrese su nombre, elija la  figura 
 * (Equis o Circulo) para poder comenzar el juego
 */

public class VentanaInicio extends JFrame {
    private String figuraSeleccionada = ""; //Guarda la figura que selecciono el usuaio
    private JTextField nombreJugador; //Campo donde el jugador escribe su nombre 

    //Constructor, en este vamos a configurar y mostrar la ventana de inicio 
    public VentanaInicio() {
        setUndecorated(true);//Quitamos la barra que tiene por default la ventana 
        setResizable(false);
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel principal
        JPanel panel = new JPanel(null); // Posición absoluta
        panel.setBackground(new Color(255, 230, 245));
        add(panel);

        // JLabel para cerrar
        JLabel cerrar = new JLabel("X");
        cerrar.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
        cerrar.setBounds(470, 0, 30, 30); 
        cerrar.setOpaque(true);
        cerrar.setBackground(new Color(255, 230, 245));
        cerrar.setForeground(new Color(123, 31, 162));
        cerrar.setHorizontalAlignment(SwingConstants.CENTER);

        // Evento para cerrar y animación
        cerrar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();//Cierra ventana
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                cerrar.setBackground(new Color(186, 104, 200));
                cerrar.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                cerrar.setBackground(new Color(255, 230, 245));
                cerrar.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });

        // JLabel para el título
        JLabel titulo = new JLabel("Juego de Gato");
        titulo.setFont(new Font("Showcard Gothic", Font.BOLD, 40));
        titulo.setBounds(0, 40, 500, 100); 
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        titulo.setForeground(new Color(123, 31, 162));

        // Campo de nombre del usuario
        JTextField nombreJugador = new JTextField("Tu nombre") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.WHITE);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                super.paintComponent(g); 
                g2.dispose();
            }
        };
        
        //Ajustamos posicion, fuente, colores y el estilo del cursor 
        nombreJugador.setBounds(125, 140, 250, 40);
        nombreJugador.setHorizontalAlignment(SwingConstants.CENTER);
        nombreJugador.setForeground(new Color(194, 123, 204));
        nombreJugador.setFont(new Font("Showcard Gothic", Font.BOLD, 20));
        nombreJugador.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        nombreJugador.setCaretColor(new Color(194, 123, 204));
        nombreJugador.setOpaque(false);

        // Para limpiar texto 
        nombreJugador.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (nombreJugador.getText().equals("Tu nombre")) {
                    nombreJugador.setText("");
                }
            }
        });
        nombreJugador.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                if (nombreJugador.getText().isEmpty()) {
                    nombreJugador.setText("Tu nombre");
                }
            }
        });

        // Limitar a 10 caracteres
        PlainDocument doc = new PlainDocument() {
            @Override
            public void insertString(int offset, String str, javax.swing.text.AttributeSet attr) throws javax.swing.text.BadLocationException {
                if (str == null) return;
                if ((getLength() + str.length()) <= 10) {
                    super.insertString(offset, str, attr);
                }
            }
        };

        nombreJugador.setDocument(doc);

        // Texto inicial del TextField
        nombreJugador.setText("Tu nombre");
        nombreJugador.setCaretPosition(nombreJugador.getText().length());

        // Botones para elegir figura
        // Botón Equis
        JButton btnEquis = new JButton(getScaledIcon("/resources/Equis.png", 80, 80));
        btnEquis.setBounds(125, 200, 90, 90);
        btnEquis.setFocusPainted(false);
        btnEquis.setContentAreaFilled(false);
        btnEquis.setBorderPainted(false);
        btnEquis.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Botón Círculo
        JButton btnCirculo = new JButton(getScaledIcon("/resources/Circulo.png", 80, 80));
        btnCirculo.setBounds(295, 200, 90, 90);
        btnCirculo.setFocusPainted(false);
        btnCirculo.setContentAreaFilled(false);
        btnCirculo.setBorderPainted(false);
        btnCirculo.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Selección de figura
        btnEquis.addActionListener(e -> figuraSeleccionada = "Equis");
        btnCirculo.addActionListener(e -> figuraSeleccionada = "Círculo");


        //Boton inicio de Juego
        JButton btnInicio = new JButton("Iniciar el juego") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);
                super.paintComponent(g);
                g2.dispose();
            }

            @Override
            protected void paintBorder(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(123, 31, 162));
                g2.setStroke(new BasicStroke(2));
                g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 25, 25);
                g2.dispose();
            }
        };

        //Caracteristicas del boton para empezar el juego
        btnInicio.setName("btnInicio"); 
        btnInicio.setBounds(165, 310, 170, 45);
        btnInicio.setFont(new Font("Showcard Gothic", Font.BOLD, 15));
        btnInicio.setForeground(new Color(123, 31, 162));
        btnInicio.setBackground(new Color(255, 123, 182));
        btnInicio.setFocusPainted(false);
        btnInicio.setContentAreaFilled(false);
        btnInicio.setOpaque(false);
        btnInicio.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Animación para el boton de Empezar el juego
        btnInicio.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnInicio.setBackground(new Color(186, 104, 200)); // más oscuro al pasar
                btnInicio.setForeground(Color.WHITE); // texto blanco al pasar
                btnInicio.repaint();
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnInicio.setBackground(new Color(255, 123, 182)); // vuelve al original
                btnInicio.setForeground(new Color(123, 31, 162));
                btnInicio.repaint();
            }
        });

        //Animacion para las figuras
        // Animación hover y selección para EQUIS
        btnEquis.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnEquis.setIcon(getScaledIcon("/resources/Equis.png", 90, 90)); // agranda
            }
            @Override
            public void mouseExited(MouseEvent e) {
                if (!figuraSeleccionada.equals("Equis")) {
                    btnEquis.setIcon(getScaledIcon("/resources/Equis.png", 80, 80)); // vuelve
                }
            }
        });

        btnEquis.addActionListener(e -> {
            figuraSeleccionada = "Equis";
            btnEquis.setIcon(getScaledIcon("/resources/Equis.png", 90, 90)); // resaltado permanente
            btnCirculo.setIcon(getScaledIcon("/resources/Circulo.png", 80, 80)); // desactiva círculo
        });

        // Animación hover y selección para CIRCULO
        btnCirculo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnCirculo.setIcon(getScaledIcon("/resources/Circulo.png", 90, 90)); // agranda
            }
            @Override
            public void mouseExited(MouseEvent e) {
                if (!figuraSeleccionada.equals("Círculo")) {
                    btnCirculo.setIcon(getScaledIcon("/resources/Circulo.png", 80, 80)); // vuelve
                }
            }
        });

        btnCirculo.addActionListener(e -> {
            figuraSeleccionada = "Círculo";
            btnCirculo.setIcon(getScaledIcon("/resources/Circulo.png", 90, 90)); // resaltado permanente
            btnEquis.setIcon(getScaledIcon("/resources/Equis.png", 80, 80)); // desactiva equis
        });

  
        // Acción al iniciar juego
        btnInicio.addActionListener(e -> {
            String nombre = nombreJugador.getText().trim();
            if (nombre.equals("Tu nombre") || nombre.isEmpty()) {// Verifica que se seleccione una figura
                JOptionPane.showMessageDialog(this, "Por favor ingresa tu nombre.");
            } else if (figuraSeleccionada.isEmpty()) {// Verifica que se seleccione una figura
                JOptionPane.showMessageDialog(this, "Debes escoger una figura (Equis o Círculo)."); 
            } else {
                // Mensaje de bienvenida
                JOptionPane.showMessageDialog(this,
                        "¡Hola " + nombre + "! Has escogido: " + figuraSeleccionada);
                // Abrir juego pasando la figura y el nombre
                String simbolo = figuraSeleccionada.equals("Equis") ? "X" : "O";
                new JuegoGato(simbolo, nombre);  // <- pasamos el nombre

                dispose(); // cerrar ventana de inicio
            }
        });

        //Componentes del panel
        panel.add(cerrar);
        panel.add(titulo);
        panel.add(nombreJugador);
        panel.add(btnEquis);
        panel.add(btnCirculo);
        panel.add(btnInicio);

        setVisible(true);//Muestra la ventana 
    }
    

    // Clase auxiliar para crear un borde redondeado
    class RoundedBorder extends javax.swing.border.AbstractBorder {
        private int radius;
        public RoundedBorder(int radius) {
            this.radius = radius;
        }
        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setColor(Color.BLACK);
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.drawRoundRect(x, y, width-1, height-1, radius, radius);
        }
    }

    //Para ajustar las imagenes a el tamaño del Icon
    private ImageIcon getScaledIcon(String path, int width, int height) {
        ImageIcon icon = new ImageIcon(getClass().getResource(path));
        Image img = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }
}
