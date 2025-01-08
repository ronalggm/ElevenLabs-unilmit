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
        double partDuration = totalDuration / numbersOfParts;
        double cutPosition = 0;
        System.out.println("Video duration: " + totalDuration);
        System.out.println("Part duration: " + partDuration);
        for (int i = 0; i < numbersOfParts; i++) {
            cutPosition = partDuration * i;
            ffmpegBuilder(selectedVideo, cutPosition, partDuration, i);
        }
    }


    public static void splitVideoIn270s(File selectedVideo) throws IOException {


        //define ubicacion de FFprobe

//probe selecciona el video, toma el tiempo y luego lo formateamos a la duracion en  double
        double totalDuration =
                ffprobe.probe(selectedVideo.getAbsolutePath())
                        .getFormat().duration;
        System.out.println("Video duration: " + totalDuration);
        //Calcular la cantidad de segmentos completos y el tiempo restante
        double segmentDuration = 270; //4,30minutos
        int fullSegments = (int) (totalDuration / segmentDuration);
        double remainingTime = totalDuration % segmentDuration;
        double cutPosition;
        for (int i = 1; i <= fullSegments; i++) {
            if (i == fullSegments && remainingTime > 0) {
                segmentDuration = remainingTime;
            }
            cutPosition = fullSegments * i;
            ffmpegBuilder(selectedVideo, cutPosition, segmentDuration, i);
        }


    }

    //configura el archivo de salida
    private static void ffmpegBuilder(File selectedVideo, double cutPosition, double partDuration, int numberPart) {
        try {

            String outputFilename = new File(selectedVideo.getParent(),
                    selectedVideo.getName()
                            .replace(".mp4", "")
                            + "_part_"
                            + numberPart
                            + ".mp4")
                    .getAbsolutePath();

            FFmpegBuilder builder = new FFmpegBuilder()
                    .setInput(selectedVideo.getAbsolutePath())
                    .overrideOutputFiles(true)
                    .addOutput(outputFilename) //establece el nombre del archivo y la salida
                    .setStartOffset((long) cutPosition, TimeUnit.SECONDS)
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










