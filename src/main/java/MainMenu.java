
import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import net.bramp.ffmpeg.probe.FFmpegProbeResult;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class MainMenu {
    private final int CUT_IN_TWO = 2;
    private final int CUT_IN_THREE = 3;
    private Scanner input = new Scanner(System.in);
    private static String outputFilePath;

    public static void main(String[] args) throws IOException {
        //creamos el file choser


        System.out.println("Menu principal");

        System.out.println("Cortar videos =1");
        //  cortarVideos(selectVideos());

    }


    //METODOS
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

        } else {
            System.out.println("No se seleccionó ningun archivo");
            throw new IOException("Operacion cancelada por el usuario");

        }

    }


    public static void cortarVideos(File selectedVideo, int numbersOfParts) throws IOException {
        FFprobe ffprobe = new FFprobe("/usr/bin/ffprobe");
        //FFmpegProbeResult probeResult=ffprobe.probe(selectedVideo.getAbsolutePath());

        double totalDuration =
                ffprobe.probe(selectedVideo.getAbsolutePath())
                        .getFormat().duration;//probe selecciona el video, toma el tiempo y luego lo formateamos a la duracion en  double


        double durationPerPart = totalDuration / numbersOfParts;


        outputFilePath = selectedVideo.getParent().concat(selectedVideo.getName() + "part_" + numbersOfParts);
        //selecciona ruta donde esta ffmpeg
        FFmpeg ffmpeg = new FFmpeg("/usr/bin/ffmpeg");
        //ejecuta ffmpeg
        FFmpegExecutor executor = new FFmpegExecutor(ffmpeg);
//analiza la informacion multimedia

        //manipulacion del video
        FFmpegBuilder builder = new FFmpegBuilder()
                .setInput(selectedVideo.getAbsolutePath())//archivo de entrada
                .overrideOutputFiles(true)//Sobrescribir archivo de salida si existe
                .addOutput(outputFilePath)//archivo de salida
                .done();


    }
}
