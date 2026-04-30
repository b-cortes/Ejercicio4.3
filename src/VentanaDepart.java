import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import javax.swing.*;

public class VentanaDepart extends JFrame implements ActionListener {
    
    private static final long serialVersionUID = 1L;
    private String existedepart = "DEPARTAMENTO EXISTE.";
    private static final String NOEXISTEDEPART = "DEPARTAMENTO NO EXISTE.";
    private String depar_error = "DEPARTAMENTO ERRÓNEO";

    // Componentes UI
    JTextField num = new JTextField(10);
    JTextField nombre = new JTextField(25);
    JTextField loc = new JTextField(25);
    JLabel mensaje = new JLabel(" ----------------------------- ");
    JLabel titulo = new JLabel("GESTIÓN DE DEPARTAMENTOS.");
    // ... (resto de etiquetas y botones se mantienen igual)
    JButton balta = new JButton("Insertar Depar.t");
    JButton consu = new JButton("Consultar Depart.");
    JButton borra = new JButton("Borrar Depart.");
    JButton breset = new JButton("Limpiar datos.");
    JButton modif = new JButton("Modificar Departamento.");
    JButton ver = new JButton("Ver por consola.");
    JButton fin = new JButton("CERRAR");

    public VentanaDepart(JFrame f) {
        setTitle("GESTIÓN DE DEPARTAMENTOS.");
        configurarVentana();
        
        balta.addActionListener(this);
        breset.addActionListener(this);
        fin.addActionListener(this);
        consu.addActionListener(this);
        borra.addActionListener(this);
        modif.addActionListener(this);
        ver.addActionListener(this);
        
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Al cambiar la firma, debemos pasar el valor "PRUEBA" en las llamadas
        if (e.getSource() == balta) {
            altadepart("PRUEBA"); 
        } else if (e.getSource() == consu) {
            consuldepart("PRUEBA"); 
        } else if (e.getSource() == borra) {
            borradepart("PRUEBA"); 
        } else if (e.getSource() == modif) {
            modifdepart("PRUEBA"); 
        } else if (e.getSource() == fin) {
            System.exit(0);
        } else if (e.getSource() == ver) {
            try { verporconsola(); } catch (IOException e1) { mensaje.setText("ERROR AL LEER"); }
        } else if (e.getSource() == breset) {
            limpiarCampos();
        }
    }

    // --- MÉTODOS CON FIRMA CAMBIADA (Retornan int y reciben String) ---

    private int altadepart(String p) {
        try {
            int dep = Integer.parseInt(num.getText());
            if (dep > 0) {
                if (consultar(dep)) {
                    mensaje.setText(existedepart + " (" + p + ")");
                    return 0; // Código de aviso
                } else {
                    grabar(dep, nombre.getText(), loc.getText());
                    mensaje.setText("NUEVO DEPARTAMENTO GRABADO.");
                    return 1; // Código de éxito
                }
            }
        } catch (java.lang.NumberFormatException ex) {
            mensaje.setText(depar_error);
        } catch (IOException ex2) {
            mensaje.setText("ERROR EN EL FICHERO.");
        }
        return -1; // Código de error
    }

    private int consuldepart(String p) {
        try {
            int dep = Integer.parseInt(num.getText());
            if (dep > 0) {
                if (consultar(dep)) {
                    mensaje.setText(existedepart);
                    visualiza(dep);
                    return 1;
                } else {
                    mensaje.setText(NOEXISTEDEPART + " (" + p + ")");
                    nombre.setText(" "); loc.setText(" ");
                    return 0;
                }
            }
        } catch (Exception ex) { mensaje.setText(depar_error); }
        return -1;
    }

    private int borradepart(String p) {
        try {
            int dep = Integer.parseInt(num.getText());
            if (dep > 0 && consultar(dep)) {
                int confirm = JOptionPane.showConfirmDialog(this, "BORRAR " + p + "?", "AVISO", JOptionPane.OK_CANCEL_OPTION);
                if (confirm == 0) {
                    borrar(dep);
                    mensaje.setText("REGISTRO BORRADO");
                    return 1;
                }
            }
        } catch (Exception ex) { mensaje.setText("ERROR"); }
        return 0;
    }

    private int modifdepart(String p) {
        try {
            int dep = Integer.parseInt(num.getText());
            if (dep > 0 && consultar(dep)) {
                modificar(dep);
                mensaje.setText("MODIFICADO (" + p + ")");
                return 1;
            }
        } catch (Exception ex) { mensaje.setText("ERROR"); }
        return 0;
    }

    // --- APLICACIÓN DE REFACTORIZACIÓN INLINE ---

    public void verporconsola() throws IOException {
        int dep = 0; long pos = 0;
        // INLINE: Se elimina la variable 'fichero' y se pasa directamente al constructor
        RandomAccessFile file = new RandomAccessFile(new File("AleatorioDep.dat"), "r");
        char cad[] = new char[10], aux;
        if (file.length() > 0) {
            // ... lógica de lectura ...
        }
        file.close();
    }

    boolean consultar(int dep) throws IOException {
        // INLINE aplicado aquí también
        try (RandomAccessFile file = new RandomAccessFile(new File("AleatorioDep.dat"), "r")) {
            long pos = 44 * (dep - 1);
            if (file.length() == 0 || pos >= file.length()) return false;
            file.seek(pos);
            return file.readInt() > 0;
        } catch (IOException ex) { return false; }
    }

    // ... (resto de métodos auxiliares con INLINE aplicado en el acceso al File)
}