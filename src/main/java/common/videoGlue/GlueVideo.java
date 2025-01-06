package common.videoGlue;

import common.FileSelector.SelectorFiles;
import net.bramp.ffmpeg.FFmpeg;

import java.io.File;
import java.io.IOException;

public class GlueVideo {

    public static void Glue() throws IOException {


        try {
            FFmpeg ffmpeg = new FFmpeg("/usr/bin/ffmpeg");
            //File selectedVideo = SelectorFiles.getFileFromLastDirectory("");

            String baseName ="null"
                    .replaceAll("_part_\\d+\\.mp4$", "") // Elimina "_part_X" seguido de ".mp4"
                    + "_converted.mp4";

            String outputFile = "";


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
