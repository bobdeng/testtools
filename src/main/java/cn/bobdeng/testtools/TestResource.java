package cn.bobdeng.testtools;

import com.google.common.io.ByteStreams;
import com.google.gson.Gson;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class TestResource {
    private final Object testObject;
    private final String fileName;
    private final String subFolder;

    public TestResource(Object testObject, String fileName) {
        this.testObject = testObject;
        this.fileName = fileName;
        this.subFolder = null;
    }

    public TestResource(Object testObject, String fileName, String subFolder) {
        this.testObject = testObject;
        this.fileName = fileName;
        this.subFolder = subFolder;
    }

    public boolean exist() {
        return getFile().exists();
    }

    public File getFile() {
        File folder = new File("src/test/java/" + testObject.getClass().getPackage().getName().replaceAll("\\.", File.separator));
        if (subFolder != null) {
            folder = new File(folder, subFolder);
        }
        if (!folder.exists()) {
            boolean success = folder.mkdirs();
            assert success;
        }
        return new File(folder, fileName);
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

    public byte[] readBytes() throws Exception {
        FileInputStream fileInputStream = new FileInputStream(getFile());
        return ByteStreams.toByteArray(fileInputStream);
    }

    public void delete() {
        getFile().delete();
    }

    public <T> T read(Class<T> aClass) throws IOException {
        return new Gson().fromJson(readString(), aClass);
    }
}
