package cn.bobdeng.testtools;

import com.google.common.io.ByteStreams;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class TestResource {
    private final Object testObject;
    private final String fileName;

    public TestResource(Object testObject, String fileName) {
        this.testObject = testObject;
        this.fileName = fileName;
    }

    public boolean exist() {
        return getFile().exists();
    }

    public File getFile() {
        File file = new File("src/test/java/" + testObject.getClass().getPackage().getName().replaceAll("\\.", File.separator) + "/" + fileName);
        return file;
    }

    private String getContent() throws IOException {
        FileInputStream fileInputStream = new FileInputStream(getFile());
        return new String(ByteStreams.toByteArray(fileInputStream), StandardCharsets.UTF_8);
    }

    public String readString() throws IOException {
        return this.getContent();
    }

    public void save(String content) throws IOException {
        FileOutputStream outputStream = new FileOutputStream(getFile());
        outputStream.write(content.getBytes(StandardCharsets.UTF_8));
    }

    public void saveObject(Object obj) throws IOException {
        FileOutputStream outputStream = new FileOutputStream(getFile());
        outputStream.write(new Gson().toJson(obj).getBytes(StandardCharsets.UTF_8));
    }

    public void save(byte[] newImage) throws IOException {
        FileOutputStream outputStream = new FileOutputStream(getFile());
        outputStream.write(newImage);
    }
}
