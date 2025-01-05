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
        double cutposition = 0;

        for (int i = 0; i <numbersOfParts; i++) {

            try {
                //si la posicioon es el primer corte, el startoffset es 0
                cutposition = partDuration*i;
              //  cutposition = (i == 1) ? 0 : Math.round(partDuration * (i - 1));

                // FFmpegOutputBuilder out=new FFmpegOutputBuilder();
                String outputFilename = new File(selectedVideo.getParent(),
                        selectedVideo.getName()
                                .replace(".mp4", "")
                                + "_part_"
                                   + i
                                + ".mp4")
                        .getAbsolutePath();

                FFmpegBuilder builder = new FFmpegBuilder();
                builder

                        .setInput(selectedVideo.getAbsolutePath())
                        .overrideOutputFiles(true)

                        .addOutput(outputFilename) //establece el nombre del archivo y la salida


                        .setStartOffset((long) cutposition, TimeUnit.SECONDS)
                        .setDuration((long) partDuration,TimeUnit.SECONDS) //duracion de cada segmento(desde el corte hasta el tiemnpo definido)
                        .setFormat("mp4")
                        .addExtraArgs("-c", "copy")
                        .done()
                        .setVerbosity(FFmpegBuilder.Verbosity.DEBUG);
                FFmpegExecutor executor = new FFmpegExecutor(ffmpeg);
                System.out.println("Codificacion completada exitosamente");


                executor.createJob(builder).run();
                // executor.createTwoPassJob(builder).run();

                //.setAudioChannels(2)
                // .setAudioCodec("aac")
                //.setAudioSampleRate(48_000)
                //.setAudioBitRate(128 * 1024)
                //.setVideoCodec("libx264")
                //.setVideoBitRate(4000 * 1024)
                //.setVideoFrameRate(30, 1)
                // .setVideoResolution(1920, 1080)



            } catch (IOException e) {
                System.err.println("Error de " + e.getMessage());
                e.printStackTrace();

            }

        }


    }
}



