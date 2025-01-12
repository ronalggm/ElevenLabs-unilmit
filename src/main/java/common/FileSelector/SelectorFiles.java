package common.FileSelector;

import common.MainMenu;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class SelectorFiles {
    private static String lastDirectory = null;
    private static File selectedFile;
    private static File[] filteredFilesNames;
    private static JFileChooser fileChooser = new JFileChooser();
    private static File[] listOfFiles;//lista de todos los archivos del directorio
    //METODOS

    //file chooser
    public static File selectFiles() throws IOException {
        //SELECCIONA EL TIPO DE SELECCION, SI ARCHIVOS O CARPETAS
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int result = fileChooser.showOpenDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            selectedFile = fileChooser.getSelectedFile();
            System.out.println("Archivo seleccionado: " + selectedFile.getName());
            lastDirectory = selectedFile.getPath();
            System.out.println("Last Directory: " + lastDirectory);
            //obtenemos la lista de archivos dentro del directorio
            return selectedFile;  //retornamos el arhivo para cortar

        } else if (result == JFileChooser.CANCEL_OPTION) {
            System.out.println("No se seleccionó ningun archivo");
            MainMenu.menuPrincipal();
        }
//MainMenu.menuPrincipal();
        return selectFiles();
    }

    //Obtener la lista de archivos para subir
    public static File[] getFilesToUp() throws IOException {
        if (lastDirectory == null) {
            MainMenu.menuPrincipal();
            throw new IOException("No se ha seleccionado ningun directorio  previamente");
        }
        //parametros
        String regex = ".*_part_(\\d+)\\.mp4";// para ordenar en el comparator por numero
        ArrayList<File> listFiltered = new ArrayList<>();
        String originalFileName = selectedFile.getName();
        String regex_mp4 = "\\.mp4";// regex para quitar .mp4
        String secuencia = originalFileName.replaceAll(regex_mp4, "");//le qitamos al archivo original el .mp4

        //1 Filtrar archivos que coinciden con la secuencia y el patron
        getListOfFiles();
        for (File f : listOfFiles) {
            if (f.getName().contains(secuencia) && f.getName().matches(regex)) {
                listFiltered.add(f);
            }
        }
        //2 Convertimos el arraylist a array de los archivos filtrados
        filteredFilesNames = listFiltered.toArray(new File[0]);
        //3 Ordenamos los archivos cortados
        Arrays.sort(filteredFilesNames, new Comparator<File>() {
            @Override
            public int compare(File f1, File f2) {
                int part1 = extractPartNumber(f1.getName(), regex);
                int part2 = extractPartNumber(f2.getName(), regex);
                return Integer.compare(part1, part2);
            }
        });
        System.out.println("-Lista de archivos para subir-");
        for (File f : filteredFilesNames) {
            System.out.println(f.getName());
        }
        return filteredFilesNames;
    }


    //Extrae el numero de parte de los archivos filtrados-> Se utiliza en el comparator de arriba;
    private static int extractPartNumber(String filename, String regex) {
        try {
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(filename);
            if (matcher.find()) {
                return Integer.parseInt(matcher.group(1));
            }
        } catch (NumberFormatException e) {
            System.err.println("Error al convertir el numero en archivo " + filename);
        }
        return -1;
    }

    //obtener la lista de todos los archivos del directorio
    public static File[] getListOfFiles() {
        try {
            // Validamos si selectedFile tiene un directorio padre
            File parentDirectory = selectedFile != null ? selectedFile.getParentFile() : null;
            if (parentDirectory != null && parentDirectory.isDirectory()) {
                listOfFiles = parentDirectory.listFiles();
                if (listOfFiles == null) {
                    throw new IOException("No se pudieron listar los archivos del directorio.");
                }
                return listOfFiles;
            } else {
                throw new NullPointerException("El archivo seleccionado no tiene un directorio válido.");
            }
        } catch (NullPointerException e) {
            System.out.println("Error: " + e.getMessage());
            return new File[0]; // Retorna un arreglo vacío en caso de error
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
            return new File[0]; // Retorna un arreglo vacío en caso de error
        }
    }

    public static File getSelectedFile() {
        return selectedFile;
    }


}






