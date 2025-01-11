package common.FileSelector;

import common.MainMenu;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class SelectorFiles {
    private static String lastDirectory = null;
    private static File selectedFile;
    private static File[] listOffiles;
    private static File[] filteredFilesNames;
    private static JFileChooser fileChooser = new JFileChooser();


    //METODOS

    //file chooser
    public static File selectFiles() throws IOException {
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
            // lisfOffiles = fileChooser.getSelectedFiles();
            //retornamos el arhivo para cortar
            return selectedFile;

        } else if (result == JFileChooser.CANCEL_OPTION) {
            System.out.println("No se seleccionó ningun archivo");
            //return null;

            MainMenu.menuPrincipal();

        }

        return selectFiles();


    }


    public static File[] getFilesToUp() throws IOException {

        if (lastDirectory == null) {
            throw new IOException("No se ha seleccionado ningun directorio  previamente");
        }
        String regex = ".*_part_\\d+\\.mp4";
        ArrayList<File> listFiltered = new ArrayList<>();
        File directorio = new File(selectedFile.getParent());
        listOffiles = directorio.listFiles();
        String OriginalFileName = selectedFile.getName();
        Pattern pattern = Pattern.compile(regex);


        for (File f : listOffiles) {
            Matcher matcher = pattern.matcher(f.getName());

            if (matcher.find()) {
                listFiltered.add(f);
            }
        }
        System.out.println("-Lista de archivos para subir-");
        for (File f : listFiltered) {
            System.out.println(f.getName());
        }

        return filteredFilesNames = listFiltered.toArray(new File[0]);
    }


}






