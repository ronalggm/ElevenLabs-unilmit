package common.videoSplitter;

import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.builder.FFmpegBuilder;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class VideoSplitter {

    //cortar videos
    public static void splitVideo(File selectedVideo, int numbersOfParts) throws IOException {
        FFmpeg ffmpeg = new FFmpeg("/usr/bin/ffmpeg"); //define la ubicacion de FFmpeg
        FFprobe ffprobe = new FFprobe("/usr/bin/ffprobe"); //define ubicacion de FFprobe


        double totalDuration =
                ffprobe.probe(selectedVideo.getAbsolutePath())
                        .getFormat().duration;//probe selecciona el video, toma el tiempo y luego lo formateamos a la duracion en  double
        System.out.println("Video duration: " + totalDuration);
        double partDuration = totalDuration / numbersOfParts;
        System.out.println("Part duration: " + partDuration);

        double cutposition;

        for (int i = 0; i < numbersOfParts; i++) {

            try {
                cutposition = partDuration * i;
                //define el archivo de salida y su nombre
                String outputFilename = new File(selectedVideo.getParent(),
                        selectedVideo.getName()
                                .replace(".mp4", "")
                                + "_part_"
                                + i
                                + ".mp4")
                        .getAbsolutePath();

                FFmpegBuilder builder = new FFmpegBuilder()
                        .setInput(selectedVideo.getAbsolutePath())
                        .overrideOutputFiles(true)
                        .addOutput(outputFilename) //establece el nombre del archivo y la salida
                        .setStartOffset((long) cutposition, TimeUnit.SECONDS)
                        .setDuration((long) partDuration, TimeUnit.SECONDS) //duracion de cada segmento(desde el corte hasta el tiemnpo definido)
                        .setFormat("mp4")
                        .addExtraArgs("-c", "copy")// copia los parametros del video, resolucion, bitrate etc
                        .done()
                        .setVerbosity(FFmpegBuilder.Verbosity.DEBUG);

                FFmpegExecutor executor = new FFmpegExecutor(ffmpeg);  //executor sirve para ejectuar el builder

                executor.createJob(builder).run();

                System.out.println("Codificacion completada exitosamente");
            } catch (IOException e) {
                System.err.println("Error de " + e.getMessage());
                e.printStackTrace();

            }

        }


    }


    public static void splitVideoIn270s(File selectedVideo) throws IOException {
        FFmpeg ffmpeg = new FFmpeg("/usr/bin/ffmpeg"); //define la ubicacion de FFmpeg
        FFprobe ffprobe = new FFprobe("/usr/bin/ffprobe"); //define ubicacion de FFprobe

//probe selecciona el video, toma el tiempo y luego lo formateamos a la duracion en  double
        double totalDuration =
                ffprobe.probe(selectedVideo.getAbsolutePath())
                        .getFormat().duration;
        System.out.println("Video duration: " + totalDuration);
        //Calcular la cantidad de segmentos completos y el tiempo restante
        int segmentDuration = 270; //4,30minutos
        int fullSegments = (int) (totalDuration / segmentDuration);
        double remainingTime = totalDuration % segmentDuration;
        int cutPosition;

        for (int i = 1; i <= fullSegments; i++) {
            cutPosition =fullSegments*i;
            if(i==fullSegments && remainingTime>0){


            }
            try {
                //define el archivo de salida y su nombre
                String outputFilename = new File(selectedVideo.getParent(),
                        selectedVideo.getName()
                                .replace(".mp4", "")
                                + "_part_"
                                + i
                                + ".mp4")
                        .getAbsolutePath();


                FFmpegBuilder builder = new FFmpegBuilder()
                        .setInput(selectedVideo.getAbsolutePath())
                        .overrideOutputFiles(true)
                        .addOutput(outputFilename) //establece el nombre del archivo y la salida
                        .setStartOffset((long) cutposition, TimeUnit.SECONDS)
                        .setDuration((long) partDuration, TimeUnit.SECONDS) //duracion de cada segmento(desde el corte hasta el tiemnpo definido)
                        .setFormat("mp4")
                        .addExtraArgs("-c", "copy")// copia los parametros del video, resolucion, bitrate etc
                        .done()
                        .setVerbosity(FFmpegBuilder.Verbosity.DEBUG);

                FFmpegExecutor executor = new FFmpegExecutor(ffmpeg);  //executor sirve para ejectuar el builder

                executor.createJob(builder).run();

                System.out.println("Codificacion completada exitosamente");
            } catch (IOException e) {
                System.err.println("Error de " + e.getMessage());
                e.printStackTrace();

            }

        }


    }


}



