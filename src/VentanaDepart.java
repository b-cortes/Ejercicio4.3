import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import javax.swing.*;

public class VentanaDepart extends JFrame implements ActionListener {

    private static final String NOEXISTEDEPART = "NUEVO DEPARTAMENTO.";
    private static final long serialVersionUID = 1L;
    
    // Atributos de la clase (Actividad 4.6)
    private String existedepart = "DEPARTAMENTO EXISTE.";
    private String depar_error = "DEPARTAMENTO ERRÓNEO.";

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

        JPanel p0 = new JPanel();
        c = Color.CYAN;
        p0.add(titulo);
        p0.setBackground(c);

        JPanel p1 = new JPanel();
        p1.setLayout(new FlowLayout());
        p1.add(lnum);
        p1.add(num);
        p1.add(consu);

        JPanel p2 = new JPanel();
        p2.setLayout(new FlowLayout());
        p2.add(lnom);
        p2.add(nombre);

        JPanel p3 = new JPanel();
        p3.setLayout(new FlowLayout());
        p3.add(lloc);
        p3.add(loc);

        JPanel p4 = new JPanel();
        p4.setLayout(new FlowLayout());
        c = Color.YELLOW;
        p4.add(balta);
        p4.add(borra);
        p4.add(modif);
        p4.setBackground(c);

        JPanel p5 = new JPanel();
        p5.setLayout(new FlowLayout());
        c = Color.PINK;
        p5.add(breset);
        p5.add(ver);
        p5.add(fin);
        p5.setBackground(c);

        JPanel p7 = new JPanel();
        p7.setLayout(new FlowLayout());
        p7.add(mensaje);

        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        add(p0); add(p1); add(p2); add(p3); add(p4); add(p5); add(p7);
        pack();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        balta.addActionListener(this);
        breset.addActionListener(this);
        fin.addActionListener(this);
        consu.addActionListener(this);
        borra.addActionListener(this);
        modif.addActionListener(this);
        ver.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
   
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
            try {
                mensaje.setText("Visualizando el fichero por la consolaa.....");
                verporconsola();
            } catch (IOException e1) {
                System.out.println("ERRROR AL LEEERRRRRR AleatorioDep.dat");
            }
        } else if (e.getSource() == breset) {
            mensaje.setText(" has pulsado el boton limpiar..");
            num.setText(" "); nombre.setText(" "); loc.setText(" ");
        }
    }


    private int altadepart(String prueba) {
        int dep;
        mensaje.setText(" has pulsado el boton alta. Modo: " + prueba);
        try {
            dep = Integer.parseInt(num.getText());
            if (dep > 0) {
                if (consultar(dep)) {
                    mensaje.setText(existedepart);
                } else {
                    mensaje.setText(NOEXISTEDEPART);
                    grabar(dep, nombre.getText(), loc.getText());
                    mensaje.setText("NUEVO DEPARTAMENTO GRABADO.");
                }
            } else {
                mensaje.setText("DEPARTAMENTO DEBE SER MAYOR QUE 0");
            }
        } catch (java.lang.NumberFormatException ex) {
            mensaje.setText(depar_error);
        } catch (IOException ex2) {
            mensaje.setText("ERRORRR EN EL FICHERO. Fichero no existe. (ALTA)");
        }
        return 0;
    }

    private int consuldepart(String prueba) {
        int dep;
        try {
            dep = Integer.parseInt(num.getText());
            if (dep > 0) {
                if (consultar(dep)) {
                    mensaje.setText(existedepart);
                    visualiza(dep);
                } else {
                    mensaje.setText("DEPARTAMENTO NO EXISTE.");
                    nombre.setText(" "); loc.setText(" ");
                }
            } else {
                mensaje.setText("DEPARTAMENTO DEBE SER MAYOR QUE 0");
            }
        } catch (Exception ex) {
            mensaje.setText("DEPARTAMENTO ERRÓNEO");
        }
        return 0;
    }

    private int borradepart(String prueba) {
        int dep, confirm;
        try {
            dep = Integer.parseInt(num.getText());
            if (dep > 0) {
                if (consultar(dep)) {
                    visualiza(dep);
                    confirm = JOptionPane.showConfirmDialog(this, "ESTAS SEGURO DE BORRAR...", "AVISO BORRADO.", JOptionPane.OK_CANCEL_OPTION);
                    if (confirm == 0) {
                        borrar(dep);
                        mensaje.setText(" REGISTRO BORRADOO: " + dep);
                        nombre.setText(" "); loc.setText(" ");
                    }
                } else {
                    mensaje.setText("DEPARTAMENTO NO EXISTE.");
                }
            }
        } catch (Exception ex) {
            mensaje.setText("ERROR EN BORRADO");
        }
        return 0;
    }

    private int modifdepart(String prueba) {
        int dep, confirm;
        try {
            dep = Integer.parseInt(num.getText());
            if (dep > 0) {
                if (consultar(dep)) {
                    confirm = JOptionPane.showConfirmDialog(this, "ESTAS SEGURO DE MODIFICAR...", "AVISO MODIFICACIÓN.", JOptionPane.OK_CANCEL_OPTION);
                    if (confirm == 0) {
                        modificar(dep);
                        mensaje.setText(" REGISTRO MODIFICADO: " + dep);
                    }
                } else {
                    mensaje.setText("DEPARTAMENTO NO EXISTE.");
                }
            }
        } catch (Exception ex) {
            mensaje.setText("ERROR EN MODIFICACIÓN");
        }
        return 0;
    }


    public void verporconsola() throws IOException {
        File fichero = new File("AleatorioDep.dat");
        if (!fichero.exists()) return;
        RandomAccessFile file = new RandomAccessFile(fichero, "r");
        if (file.length() > 0) {
            for (long pos = 0; pos < file.length(); pos += 44) {
                file.seek(pos);
                int dep = file.readInt();
                char cad[] = new char[10];
                for (int i = 0; i < 10; i++) cad[i] = file.readChar();
                String nom = new String(cad);
                for (int i = 0; i < 10; i++) cad[i] = file.readChar();
                String locStr = new String(cad);
                System.out.println("DEP: " + dep + ", Nombre: " + nom + ", Localidad: " + locStr);
            }
        }
        file.close();
    }

    boolean consultar(int dep) throws IOException {
        File fichero = new File("AleatorioDep.dat");
        if (!fichero.exists()) return false;
        RandomAccessFile file = new RandomAccessFile(fichero, "r");
        long pos = 44 * (dep - 1);
        if (pos >= file.length()) { file.close(); return false; }
        file.seek(pos);
        int depa = file.readInt();
        file.close();
        return depa > 0;
    }

    void visualiza(int dep) {
        try {
            RandomAccessFile file = new RandomAccessFile(new File("AleatorioDep.dat"), "r");
            file.seek(44 * (dep - 1));
            file.readInt();
            char nom[] = new char[10], loc1[] = new char[10];
            for (int i = 0; i < 10; i++) nom[i] = file.readChar();
            for (int i = 0; i < 10; i++) loc1[i] = file.readChar();
            nombre.setText(new String(nom));
            loc.setText(new String(loc1));
            file.close();
        } catch (IOException e) {}
    }

    void borrar(int dep) {
        try {
            RandomAccessFile file = new RandomAccessFile(new File("AleatorioDep.dat"), "rw");
            file.seek(44 * (dep - 1));
            file.writeInt(0);
            StringBuffer buffer = new StringBuffer("");
            buffer.setLength(10);
            file.writeChars(buffer.toString());
            file.writeChars(buffer.toString());
            file.close();
        } catch (IOException e) {}
    }

    void modificar(int dep) {
        try {
            RandomAccessFile file = new RandomAccessFile(new File("AleatorioDep.dat"), "rw");
            file.seek(44 * (dep - 1));
            file.writeInt(dep);
            StringBuffer buffer = new StringBuffer(nombre.getText());
            buffer.setLength(10);
            file.writeChars(buffer.toString());
            buffer = new StringBuffer(loc.getText());
            buffer.setLength(10);
            file.writeChars(buffer.toString());
            file.close();
        } catch (IOException e) {}
    }

    void grabar(int dep, String nom, String loc) {
        try {
            RandomAccessFile file = new RandomAccessFile(new File("AleatorioDep.dat"), "rw");
            file.seek(44 * (dep - 1));
            file.writeInt(dep);
            StringBuffer buffer = new StringBuffer(nom);
            buffer.setLength(10);
            file.writeChars(buffer.toString());
            buffer = new StringBuffer(loc);
            buffer.setLength(10);
            file.writeChars(buffer.toString());
            file.close();
        } catch (IOException e) {}
    }
}