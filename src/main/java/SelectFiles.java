import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class SelectFiles {

    //METODOS

    //file chooser
    public static File selectFiles() throws IOException {


        JFileChooser fileChooser = new JFileChooser();

        //SELECCIONA EL TIPO DE SELECCION, SI ARCHIVOS O CARPETAS
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        int result = fileChooser.showOpenDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            //seleccionamos el archivo y lo almacenamos en una variable File
            File selectedFile = fileChooser.getSelectedFile();
            //Mostrar la ruta del archivo seleccionado
            System.out.println("Archivo seleccionado" + selectedFile.getAbsolutePath());
            return selectedFile;

        } else if (result == JFileChooser.CANCEL_OPTION) {
            System.out.println("No se seleccionó ningun archivo, intentelo de nuevo");
            //return null;
            MainMenu.menuPrincipal();

        }

        return selectFiles();
    }
}

