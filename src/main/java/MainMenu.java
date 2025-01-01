
import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class MainMenu {

    public static void main(String[] args) throws IOException {
        //creamos el file choser
        Scanner input = new Scanner(System.in);
        int cortarVideos;


        System.out.println("Menu principal");

        System.out.println("Cortar videos =1");
        cortarVideos(gestionarVideos());

    }


    //METODOS
    public static FFmpegExecutor gestionarVideos() throws IOException {
        JFileChooser fileChooser = new JFileChooser();

        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        int result = fileChooser.showOpenDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            //seleccionamos el archivo y lo almacenamos en una variable File
            File selectedFile = fileChooser.getSelectedFile();
            //Mostrar la ruta del archivo seleccionado
            System.out.println("Archivo seleccionado" + selectedFile.getAbsolutePath());

            //Ruta de salida del nuevo archivo
            String outputPath = selectedFile.getParent() + "/video_cortado.mp4";

            FFmpeg ffmpeg = new FFmpeg("/usr/bin/ffmpeg");
            FFmpegExecutor executor = new FFmpegExecutor(ffmpeg);

            return executor;

        } else {
            System.out.println("No se seleccionó ningun archivo");
            throw new IOException("Operacion cancelada por el usuario");
        }

    }


    public static void cortarVideos(FFmpegExecutor ejecutor) {

    }
}
