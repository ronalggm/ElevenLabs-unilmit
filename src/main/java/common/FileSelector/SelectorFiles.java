package common.FileSelector;

import common.MainMenu;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import java.util.List;

public class SelectorFiles {
    private static String lastDirectory = null;
    private static File selectedFile;
    private static File[] lisfOffiles;
    private static String[] filteredFilesNames;
    //METODOS

    //file chooser
    public static File selectFiles() throws IOException {


        JFileChooser fileChooser = new JFileChooser();

        //SELECCIONA EL TIPO DE SELECCION, SI ARCHIVOS O CARPETAS
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        int result = fileChooser.showOpenDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            //seleccionamos el archivo y lo almacenamos en una variable File
            selectedFile = fileChooser.getSelectedFile();
            //Mostrar la ruta del archivo seleccionado
            System.out.println("Archivo seleccionado" + selectedFile.getAbsolutePath());
            lastDirectory = selectedFile.getPath();
            System.out.println("last DIRECTORTY: " + lastDirectory);
            //extraemos la lista de archivos dentro del directorio
            lisfOffiles = fileChooser.getSelectedFiles();
            //retornamos el arhivo para cortar
            return selectedFile;

        } else if (result == JFileChooser.CANCEL_OPTION) {
            System.out.println("No se seleccionó ningun archivo");
            //return null;

            MainMenu.menuPrincipal();

        }

        return selectFiles();
    }


    public static List<String> getFileFromLastDirectory() throws IOException {
        String regex = ".*_part_\\d+\\.mp4";
        ArrayList<String> listFiltered = new ArrayList<>();

        for (File lisfOffile : lisfOffiles) {
            if (lisfOffile.getName().matches(regex)) {
                listFiltered.add(lisfOffile.getName());
            }
        }


        if (lastDirectory == null) {
            throw new IOException("No se ha seleccionado ningun directorio  previamente");
        }


        return List.of();
    }
}

