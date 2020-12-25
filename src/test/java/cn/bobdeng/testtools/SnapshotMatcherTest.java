package cn.bobdeng.testtools;

import com.google.gson.Gson;
import org.junit.Test;

import java.io.IOException;

import static cn.bobdeng.testtools.SnapshotMatcher.snapshotMatch;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class SnapshotMatcherTest {
    @Test
    public void test_snapshot_string() throws IOException {
        TestForm form = new TestForm("hello");
        assertThat(new Gson().toJson(form), snapshotMatch(this, "hello"));
        TestResource testResource = new TestResource(this, "hello.snapshot");
        assertThat(testResource.readString(), is(new Gson().toJson(form)));
    }
}
