import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import net.bramp.ffmpeg.builder.FFmpegOutputBuilder;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class VideoSplitter {

    //cortar videos
    public static void splitVideo(File selectedVideo, int numbersOfParts) throws IOException {
        FFmpeg ffmpeg = new FFmpeg("/usr/bin/ffmpeg");
        FFprobe ffprobe = new FFprobe("/usr/bin/ffprobe");


        double totalDuration =
                ffprobe.probe(selectedVideo.getAbsolutePath())
                        .getFormat().duration;//probe selecciona el video, toma el tiempo y luego lo formateamos a la duracion en  double
        System.out.println("durcaicon del video: " + totalDuration);

        double partDuration = totalDuration / numbersOfParts;


        System.out.println("duracion por parte: " + partDuration);
        double cutposition = 0.0;

        for (int i = 1; i <= numbersOfParts; i++) {

            //si la posicioon es el primer corte, el startoffset es 0
            cutposition = (i == 1) ? cutposition : cutposition + partDuration;

            // FFmpegOutputBuilder out=new FFmpegOutputBuilder();

            FFmpegBuilder builder = new FFmpegBuilder();
            //.setInput(selectedVideo.getAbsolutePath())//archivo de entrada
            builder
                    .setInput("archivo.mp4")
                    .overrideOutputFiles(true)

                    .addOutput(selectedVideo.getParent()
                            + selectedVideo.getName()
                            + "_part_" + (i + 1)) //establece el nombre del archivo y la salida
                    .setStartOffset((long) cutposition, TimeUnit.SECONDS)

                    .setAudioChannels(1)
                    .setAudioCodec("aac")
                    .setAudioSampleRate(48_000)
                    .setAudioBitRate(128 * 1014)
                    .setVideoCodec("libx264")
                    .setVideoBitRate(100 * 1024)
                    .setVideoFrameRate(30, 1)
                    .setVideoResolution(1920, 1080)
                    .setFormat()
                    .done();
            FFmpegExecutor executor = new FFmpegExecutor(ffmpeg);
            // executor.createJob(builder).run();
            executor.createTwoPassJob(builder).run();


        }


    }


}
