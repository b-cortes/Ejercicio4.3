import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import javax.swing.JFrame;

public class FicheroAleatorioVentana {

	public static void main(String[] args) throws IOException {
		
		JFrame f = new JFrame("DEPARTAMENTOS.");
		// Nos aseguramos que el fichero exista para evitar posibles errores
		File fichero = new File("AleatorioDep.dat");
		RandomAccessFile file;
		file = new RandomAccessFile(fichero, "rw");	
		file.close();
		
		VentanaDepart v = new VentanaDepart(f);
		v.setVisible(true);
	   
	}//fin main
	
	// Esta es la clase anidada
    public class claseAnidada {
        void entrada() {
            System.out.println("Método entrada.");
        }

        String salida(int d) {
            System.out.println("Salida.");
            return "Salida el " + d;
        }
    }

    // Método donde instanciamos y probamos la clase
    void verporconsola() {
        claseAnidada ej = new claseAnidada();
        ej.entrada();
        System.out.println("Llamo a Salida: " + ej.salida(10));
    }

	
}//fin class
