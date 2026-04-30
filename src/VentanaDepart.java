

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import javax.swing.*;

public class VentanaDepart extends JFrame implements ActionListener {
    
    private static final long serialVersionUID = 1L;
    
    // Atributos y constantes de la Actividad 4.6
    private String existedepart = "DEPARTAMENTO EXISTE.";
    private static final String NOEXISTEDEPART = "DEPARTAMENTO NO EXISTE.";
    private String depar_error = "DEPARTAMENTO ERRÓNEO";

    // Componentes de la interfaz
    JTextField num = new JTextField(10);
    JTextField nombre = new JTextField(25);
    JTextField loc = new JTextField(25);
    JLabel mensaje = new JLabel(" ----------------------------- ");
    JLabel titulo = new JLabel("GESTIÓN DE DEPARTAMENTOS.");
    JLabel lnum = new JLabel("NUMERO DEPARTAMENTO:");
    JLabel lnom = new JLabel("NOMBRE:");
    JLabel lloc = new JLabel("LOCALIDAD:");
    JButton balta = new JButton("Insertar Depar.t");
    JButton consu = new JButton("Consultar Depart.");
    JButton borra = new JButton("Borrar Depart.");
    JButton breset = new JButton("Limpiar datos.");
    JButton modif = new JButton("Modificar Departamento.");
    JButton ver = new JButton("Ver por consola.");
    JButton fin = new JButton("CERRAR");
    Color c;

    public VentanaDepart(JFrame f) {
        setTitle("GESTIÓN DE DEPARTAMENTOS.");
        configurarVentana(); // Opcional: podrías extraer también la configuración UI
        
        // Registro de Listeners
        balta.addActionListener(this);
        breset.addActionListener(this);
        fin.addActionListener(this);
        consu.addActionListener(this);
        borra.addActionListener(this);
        modif.addActionListener(this);
        ver.addActionListener(this);
        
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    // --- MÉTODO ACTIONPERFORMED REFACTORIZADO ---
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == balta) {
            altadepart(); //
        } else if (e.getSource() == consu) {
            consuldepart(); //
        } else if (e.getSource() == borra) {
            borradepart(); //
        } else if (e.getSource() == modif) {
            modifdepart(); //
        } else if (e.getSource() == fin) {
            System.exit(0);
        } else if (e.getSource() == ver) {
            try { 
                verporconsola(); 
            } catch (IOException e1) { 
                mensaje.setText("ERROR AL LEER"); 
            }
        } else if (e.getSource() == breset) {
            limpiarCampos();
        }
    }

    // --- MÉTODOS EXTRAÍDOS (Actividad 4.7) ---

    /** Extrae la lógica de inserción de departamentos */
    private void altadepart() {
        try {
            int dep = Integer.parseInt(num.getText());
            if (dep > 0) {
                if (consultar(dep)) {
                    mensaje.setText(existedepart);
                } else {
                    grabar(dep, nombre.getText(), loc.getText());
                    mensaje.setText("NUEVO DEPARTAMENTO GRABADO.");
                }
            } else {
                mensaje.setText("DEPARTAMENTO DEBE SER MAYOR QUE 0");
            }
        } catch (java.lang.NumberFormatException ex) {
            mensaje.setText(depar_error);
        } catch (IOException ex2) {
            mensaje.setText("ERROR EN EL FICHERO.");
        }
    }

    /** Extrae la lógica de consulta de departamentos */
    private void consuldepart() {
        try {
            int dep = Integer.parseInt(num.getText());
            if (dep > 0) {
                if (consultar(dep)) {
                    mensaje.setText(existedepart);
                    visualiza(dep);
                } else {
                    mensaje.setText(NOEXISTEDEPART);
                    nombre.setText(" "); loc.setText(" ");
                }
            } else {
                mensaje.setText("DEPARTAMENTO DEBE SER MAYOR QUE 0");
            }
        } catch (java.lang.NumberFormatException ex) {
            mensaje.setText(depar_error);
        } catch (IOException ex2) {
            mensaje.setText("ERROR EN EL FICHERO.");
        }
    }

    /** Extrae la lógica de borrado de departamentos */
    private void borradepart() {
        try {
            int dep = Integer.parseInt(num.getText());
            if (dep > 0) {
                if (consultar(dep)) {
                    mensaje.setText(existedepart);
                    visualiza(dep);
                    int confirm = JOptionPane.showConfirmDialog(this, "ESTAS SEGURO DE BORRAR...", "AVISO BORRADO.", JOptionPane.OK_CANCEL_OPTION);
                    if (confirm == 0) {
                        borrar(dep);
                        mensaje.setText(" REGISTRO BORRADO: " + dep);
                        nombre.setText(" "); loc.setText(" ");
                    }
                } else {
                    mensaje.setText(NOEXISTEDEPART);
                    nombre.setText(" "); loc.setText(" ");
                }
            }
        } catch (java.lang.NumberFormatException ex) {
            mensaje.setText(depar_error);
        } catch (IOException ex2) {
            mensaje.setText("ERROR EN EL FICHERO.");
        }
    }

    /** Extrae la lógica de modificación de departamentos */
    private void modifdepart() {
        try {
            int dep = Integer.parseInt(num.getText());
            if (dep > 0) {
                if (consultar(dep)) {
                    mensaje.setText(existedepart);
                    int confirm = JOptionPane.showConfirmDialog(this, "ESTAS SEGURO DE MODIFICAR...", "AVISO MODIFICACIÓN.", JOptionPane.OK_CANCEL_OPTION);
                    if (confirm == 0) {
                        modificar(dep);
                        mensaje.setText(" REGISTRO MODIFICADO: " + dep);
                    }
                } else {
                    mensaje.setText(NOEXISTEDEPART);
                    nombre.setText(" "); loc.setText(" ");
                }
            }
        } catch (java.lang.NumberFormatException ex) {
            mensaje.setText(depar_error);
        } catch (IOException ex2) {
            mensaje.setText("ERROR EN EL FICHERO.");
        }
    }

    // --- MÉTODOS AUXILIARES ---

    private void limpiarCampos() {
        num.setText(" "); nombre.setText(" "); loc.setText(" ");
        mensaje.setText(" has pulsado el boton limpiar..");
    }

    private void configurarVentana() {
        JPanel p0 = new JPanel(); c = Color.CYAN; p0.add(titulo); p0.setBackground(c);
        JPanel p1 = new JPanel(); p1.setLayout(new FlowLayout()); p1.add(lnum); p1.add(num); p1.add(consu);
        JPanel p2 = new JPanel(); p2.setLayout(new FlowLayout()); p2.add(lnom); p2.add(nombre);
        JPanel p3 = new JPanel(); p3.setLayout(new FlowLayout()); p3.add(lloc); p3.add(loc);
        JPanel p4 = new JPanel(); p4.setLayout(new FlowLayout()); c = Color.YELLOW; p4.add(balta); p4.add(borra); p4.add(modif); p4.setBackground(c);
        JPanel p5 = new JPanel(); p5.setLayout(new FlowLayout()); c = Color.PINK; p5.add(breset); p5.add(ver); p5.add(fin); p5.setBackground(c);
        JPanel p7 = new JPanel(); p7.setLayout(new FlowLayout()); p7.add(mensaje);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        add(p0); add(p1); add(p2); add(p3); add(p4); add(p5); add(p7);
        pack();
    }

    // Métodos de acceso a datos (grabar, consultar, borrar, etc.) se mantienen igual...
    public void verporconsola() throws IOException { /* ... */ }
    boolean consultar(int dep) throws IOException { /* ... */ return false; }
    void visualiza(int dep) { /* ... */ }
    void borrar(int dep) { /* ... */ }
    void modificar(int dep) { /* ... */ }
    void grabar(int dep, String nom, String loc) { /* ... */ }
}}