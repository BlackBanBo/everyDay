package day.crease.day.bean;

public enum  FileType {
    PICTURE("图片格式","webp,bmp,pcx,tif,gif,jpeg"),
    VIDEO("视频格式","avi,rmvb,rm,asf"),
    AUDIO("音频格式","wav,aif,au"),
    LOGS("日志文件","log");

    private String value = "";
    private String ext = "";

    FileType(String value, String ext) {
        this.ext = ext;
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }
}
