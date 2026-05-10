import javax.swing.JTextField;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class SuperclaseDepart {
    // Movemos los campos aquí para que los métodos puedan verlos (Solución error 1 y 2)
    public JTextField nombre = new JTextField(25);
    public JTextField loc = new JTextField(25);

    // Método Grabar extraído
    public void grabar(int dep, String nom, String loc) {
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
        } catch (IOException e) {
            System.out.println("ERROR AL GRABAR");
        }
    }

    // Método Visualizar extraído
    public void visualiza(int dep) {
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
        } catch (IOException e) {
            System.out.println("ERROR AL VISUALIZAR");
        }
    }
}