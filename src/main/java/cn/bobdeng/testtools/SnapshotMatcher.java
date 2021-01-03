package cn.bobdeng.testtools;

import com.google.gson.Gson;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.json.JSONException;
import org.skyscreamer.jsonassert.JSONAssert;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class SnapshotMatcher extends BaseMatcher<Object> {
    private final Object testObject;
    private final String snapshotName;

    public static SnapshotMatcher snapshotMatch(Object testObject, String snapshotName) {
        return new SnapshotMatcher(testObject, snapshotName);
    }

    public static SnapshotMatcher matchSnapshot(Object testObject, String snapshotName) {
        return snapshotMatch(testObject, snapshotName);
    }

    public SnapshotMatcher(Object testObject, String snapshotName) {
        this.testObject = testObject;
        this.snapshotName = snapshotName + ".snapshot";
    }

    @Override
    public boolean matches(Object item) {
        TestResource testResource = getTestResource();
        if (testResource.exist()) {
            return compare(item, testResource);
        }
        trySaveSnapshot(item);
        return true;
    }

    private boolean compare(Object item, TestResource testResource) {
        try {
            if (item.getClass() == String.class) {
                JSONAssert.assertEquals(testResource.readString(), (String) item, false);
                return true;
            }
            JSONAssert.assertEquals(testResource.readString(), new Gson().toJson(item), false);
            return true;
        } catch (IOException | JSONException e) {
            return false;
        }
    }

    private void trySaveSnapshot(Object item) {
        try {
            saveJson(item);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void saveJson(Object item) throws IOException {
        File file = getTestResource().getFile();
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        if (item.getClass() == String.class) {
            fileOutputStream.write(((String) item).getBytes(StandardCharsets.UTF_8));
            return;
        }
        fileOutputStream.write((new Gson().toJson(item)).getBytes(StandardCharsets.UTF_8));
    }

    private TestResource getTestResource() {
        return new TestResource(testObject, snapshotName, "_snapshots_");
    }

    @Override
    public void describeTo(Description description) {
        TestResource testResource = getTestResource();
        try {
            description.appendText(testResource.readString());
        } catch (IOException e) {
            description.appendText(e.getMessage());
        }
    }
}
