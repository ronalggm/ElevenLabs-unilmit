
import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import net.bramp.ffmpeg.probe.FFmpegProbeResult;
import org.checkerframework.checker.units.qual.N;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class MainMenu {
    private final int CUT_IN_TWO = 2;
    private final int CUT_IN_THREE = 3;
    private Scanner input = new Scanner(System.in);
    private static String outputFilePath;

    public static void main(String[] args) throws IOException {
        menuPrincipal();

    }


    public static void menuPrincipal() throws IOException {
        Scanner input = new Scanner(System.in);

        System.out.println("Main menu");
        System.out.println("Press the number option to start");
        System.out.println("1 Cut a video");
        int option = input.nextInt();

        switch (option) {
            case 1:
                Scanner parts = new Scanner(System.in);
                File selectedFile = SelectFiles.selectFiles();

                System.out.println("ingrese la cantidad de partes en que desa cortar 2, 3, 4");

                VideoSplitter.splitVideo(selectedFile, parts.nextInt());

        }

    }
}




