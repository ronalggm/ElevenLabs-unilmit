package common.videoSplitter;

import common.Drivers;
import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.builder.FFmpegBuilder;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class VideoSplitter {
    private static final FFmpeg ffmpeg; //define la ubicacion de FFmpeg
    private static final FFprobe ffprobe;
    private static String verde = "\u001B[32m";
    private static String reset = "\u001B[0m";

    static {
        try {
            ffmpeg = new FFmpeg(Drivers.FFMPEG.getPath());
            ffprobe = new FFprobe(Drivers.FFPROBE.getPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static void splitVideo(File selectedVideo, int numbersOfParts) throws IOException {
        double totalDuration =
                ffprobe.probe(selectedVideo.getAbsolutePath())
                        .getFormat().duration;//probe selecciona el video, toma el tiempo y luego lo formateamos a la duracion en  double
        double segmentDuration = totalDuration / numbersOfParts;
        double cutPosition = 0;
        System.out.println(verde + "Video duration: " + totalDuration + reset);
        System.out.println(verde + "Part duration: " + segmentDuration + reset);
        for (int i = 0; i < numbersOfParts; i++) {
            cutPosition = segmentDuration * i;
            ffmpegBuilder(selectedVideo, cutPosition, segmentDuration, i);
        }
    }


    public static void splitVideoIn270s(File selectedVideo) throws IOException {
        //define ubicacion de FFprobe

//probe selecciona el video, toma el tiempo y luego lo formateamos a la duracion en  double
        double totalDuration =
                ffprobe.probe(selectedVideo.getAbsolutePath())
                        .getFormat().duration;
        System.out.println(verde + "Video duration: " + totalDuration + reset);
        double segmentDuration = 270; //4,30minutos
        double fullSegments = totalDuration / segmentDuration;
        double fullSegmentRounded = Math.floor(fullSegments);//Redondeo hacia abajo
        double remainingTime = totalDuration % segmentDuration;
        System.out.println(verde + "fullSegments duration: " + fullSegments + reset);
        System.out.println(verde + "remainingTIme: " + remainingTime + reset);
        double cutPosition = 0;
        if (remainingTime > 0) {
            fullSegmentRounded += 1;
        }

        for (int i = 0; i < fullSegmentRounded; i++) {
            if (i==fullSegmentRounded &&remainingTime > 0) {
                ffmpegBuilder(selectedVideo, cutPosition, remainingTime, i);

            }
            cutPosition = segmentDuration * i;
            ffmpegBuilder(selectedVideo, cutPosition, segmentDuration, i);
        }


    }

    //configura el archivo de salida
    private static void ffmpegBuilder(File selectedVideo, double cutPosition, double segmentDuration, int numberPart) {
        try {

            String outputFilename = new File(selectedVideo.getParent(),
                    selectedVideo.getName()
                            .replace(".mp4", "")
                            + "_part_"
                            + numberPart
                            + ".mp4")
                    .getAbsolutePath();//metodo del objeto file para obtener la ruta completa

            FFmpegBuilder builder = new FFmpegBuilder()
                    .setInput(selectedVideo.getAbsolutePath())
                    .overrideOutputFiles(true)
                    .addOutput(outputFilename) //establece el nombre del archivo y la salida
                    .setStartOffset((long) cutPosition, TimeUnit.SECONDS)
                    .setDuration((long) segmentDuration, TimeUnit.SECONDS) //duracion de cada segmento(desde el corte hasta el tiemnpo definido)
                    .setFormat("mp4")
                    .addExtraArgs("-c", "copy")// copia los parametros del video, resolucion, bitrate etc
                    .done()
                    .setVerbosity(FFmpegBuilder.Verbosity.DEBUG);

            FFmpegExecutor executor = new FFmpegExecutor(ffmpeg);  //executor sirve para ejectuar el builder
            executor.createJob(builder).run();

            System.out.println(verde+"Codificacion completada exitosamente"+reset);
        } catch (IOException e) {
            System.err.println("Error de " + e.getMessage());
            e.printStackTrace();

        }
    }

}










