

//Este es la clase principal para la ejecucion del programa.
package Principal; //Paquete al que pertenece la clase 

import Vista.VentanaInicio; // Importacion de la clase "VentanaInicio" desde el paquete "Vista".

//Esta clase contiene el método main(), para ejecucion de la primer ventana gráfica de la aplicación
public class Principal {

    //Método main
    public static void main(String[] args) {
        /*Se crea una instancia de la clase VentanaInicio.
        Esto inicializa y muestra la ventana principal de la interfaz gráfica.*/
        VentanaInicio ventanainicio = new VentanaInicio();
    }
}
