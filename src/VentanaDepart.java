import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;

// Ahora hereda de SuperclaseDepart
public class VentanaDepart extends SuperClasedepart implements ActionListener, InterfaceVentanaDepart {
    
    private static final long serialVersionUID = 1L;
    private String existedepart = "DEPARTAMENTO EXISTE.";
    private static final String NOEXISTEDEPART = "DEPARTAMENTO NO EXISTE.";
    private String depar_error = "DEPARTAMENTO ERRÓNEO";

    JTextField num = new JTextField(10);
    JLabel mensaje = new JLabel(" ----------------------------- ");
    JButton balta = new JButton("Insertar Depar.t");
    JButton consu = new JButton("Consultar Depart.");
    // ... resto de botones

    public VentanaDepart(JFrame f) {
        // Solución error 3: Si usas la clase anidada, castea 'this' a la interfaz
        // claseAnidada ej = new claseAnidada((InterfaceVentanaDepart) this);
        
        configurarVentana();
        // Listeners...
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Tu lógica de botones llamando a los métodos de la superclase
        if (e.getSource() == balta) altadepart("PRUEBA");
        // ... resto de eventos
    }

    // Implementación de los métodos de la interfaz
    @Override
    public int altadepart(String p) {
        try {
            int dep = Integer.parseInt(num.getText());
            if (dep > 0) {
                if (consultar(dep)) {
                    mensaje.setText(existedepart + " (" + p + ")");
                    return 0;
                } else {
                    // Usamos el método que ahora está en la Superclase
                    grabar(dep, nombre.getText(), loc.getText());
                    mensaje.setText("NUEVO DEPARTAMENTO GRABADO.");
                    return 1;
                }
            }
        } catch (Exception ex) { mensaje.setText(depar_error); }
        return -1;
    }

    public boolean consultar(int dep) throws IOException {
        try (RandomAccessFile file = new RandomAccessFile(fichero, "r")) {
            long pos = 44 * (dep - 1);
            if (file.length() == 0 || pos >= file.length()) return false;
            file.seek(pos);
            return file.readInt() > 0;
        }
    }

    // Los métodos consuldepart, borradepart, etc., usarían visualiza() de la superclase
}