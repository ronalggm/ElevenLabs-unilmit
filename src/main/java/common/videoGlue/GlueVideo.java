package common.videoGlue;

import common.Drivers;
import net.bramp.ffmpeg.FFmpeg;


import java.io.IOException;

public class GlueVideo {

    public static void Glue() throws IOException {


        try {
            FFmpeg ffmpeg = new FFmpeg(Drivers.FFMPEG.getPath());



        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
