package common.FileSelector;

import common.MainMenu;

import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class SelectorFiles {
    private static String lastDirectory = null;
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

            lastDirectory = selectedFile.getPath();
            System.out.println("last DIRECTORTY: " + lastDirectory);
            return selectedFile;

        } else if (result == JFileChooser.CANCEL_OPTION) {
            System.out.println("No se seleccionó ningun archivo");
            //return null;

            MainMenu.menuPrincipal();


        }

        return selectFiles();
    }


    public static File getFileFromLastDirectory(String filename) throws IOException {
        if (lastDirectory == null) {
            throw new IOException("No se ha seleccionado ningun directorio  previamente");
        }
        String regex = ".*_part_\\d+\\.mp4";
        if (filename.matches(regex)) {

        }

        File file = new File(lastDirectory, filename);

        if (file.exists()) {
            System.out.println("Archivo encontrado: " + file.getAbsolutePath());
            return file;
        } else {
            MainMenu.menuPrincipal();
            throw new IOException("El archivo no existe en el último directorio seleccionado");
        }
    }
}

