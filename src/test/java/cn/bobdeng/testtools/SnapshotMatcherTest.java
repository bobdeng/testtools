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
        assertThat(new Gson().toJson(form), snapshotMatch(this, "snapshot_match"));
        TestResource testResource = new TestResource(this, "snapshot_match.snapshot");
        assertThat(testResource.readString(), is(new Gson().toJson(form)));
    }

    @Test(expected = AssertionError.class)
    public void fail_when_snapshot_not_match() {
        TestForm form = new TestForm("hello");
        assertThat(new Gson().toJson(form), snapshotMatch(this, "snapshot_not_match"));
    }
    @Test
    public void create_snapshot_when_first_run(){
        TestForm form = new TestForm("hello");
        assertThat(new Gson().toJson(form), snapshotMatch(this, "snapshot_first"));
        new TestResource(this,"snapshot_first.snapshot").delete();

    }
    @Test(expected = AssertionError.class)
    public void fail_when_json_parse_fail(){
        TestForm form = new TestForm("hello");
        assertThat(new Gson().toJson(form), snapshotMatch(this, "snapshot_json_fail"));

    }
}
