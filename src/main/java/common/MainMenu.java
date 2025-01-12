package common;

import common.FileSelector.SelectorFiles;
import common.videoGlue.GlueVideo;
import common.videoSplitter.VideoSplitter;

import java.io.File;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class MainMenu {
    private final int CUT_IN_TWO = 2;
    private final int CUT_IN_THREE = 3;
    private Scanner input = new Scanner(System.in);
    private static String outputFilePath;
    private static File selectedFile;

    public static void main(String[] args) throws IOException {
        System.out.println("Main menu");
        System.out.println("Press the number option to start");
        System.out.println("1-Cut a video");
        System.out.println("Crear un correo temporal");
        menuPrincipal();

    }

    public static void menuPrincipal() throws IOException {
        Scanner input = new Scanner(System.in);
        try {
            int option = input.nextInt();
            switch (option) {
                case 1:
                    Scanner parts = new Scanner(System.in);
                /*se lo tengo que pasar a una nueva variable porque el metodo
                 retorna un tipo File y se retorna a si mismo
                 --> */
                    SelectorFiles.selectFiles();
                    System.out.println("ingrese la cantidad de partes en que desa cortar 2, 3, 4");
                    VideoSplitter.splitVideo(SelectorFiles.getSelectedFile(), parts.nextInt());

                case 2:
                    // selectedFile = SelectorFiles.selectFiles();
                    VideoSplitter.splitVideoIn270s(SelectorFiles.getSelectedFile());
                    menuPrincipal();
                    break;
                case 3:
                    SelectorFiles.getFilesToUp();
            }

        } catch (InputMismatchException e) {
            System.err.println("Selecione una de las opciones");
            MainMenu.menuPrincipal();
        }
    }
}




