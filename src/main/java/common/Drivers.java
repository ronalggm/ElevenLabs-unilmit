package common;

public enum Drivers {
    FFMPEG("/usr/bin/ffmpeg"),
    FFPROBE("/usr/bin/ffprobe");

    private final String COLOR;

    public String getPath() {
        return this.COLOR;
    }

    Drivers(String color) {
        this.COLOR = color;
    }

}
