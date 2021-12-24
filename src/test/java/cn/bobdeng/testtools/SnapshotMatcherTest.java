package cn.bobdeng.testtools;

import com.google.gson.Gson;
import org.junit.Test;

import java.io.IOException;

import static cn.bobdeng.testtools.SnapshotMatcher.snapshotMatch;
import static cn.bobdeng.testtools.SnapshotMatcher.snapshotMatchStrict;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class SnapshotMatcherTest {
    @Test
    public void test_snapshot_string() throws IOException {
        TestForm form = new TestForm("hello");
        assertThat(new Gson().toJson(form), snapshotMatch(this, "snapshot_match"));
        assertThat(form, snapshotMatch(this, "snapshot_match"));
        TestResource testResource = new TestResource(this, "_snapshots_/snapshot_match.snapshot");
        assertThat(testResource.readString(), is(new Gson().toJson(form)));
    }

    @Test
    public void test_snapshot_string_strict_true() throws IOException {
        TestForm form = new TestForm("hello");
        assertThat(form, snapshotMatchStrict(this, "snapshot_match_strict_true"));
    }

    @Test(expected = AssertionError.class)
    public void test_snapshot_string_strict_false() throws IOException {
        TestForm form = new TestForm("hello", 100);
        assertThat(form, snapshotMatchStrict(this, "snapshot_match_strict_false"));
    }

    @Test
    public void test_snapshot_with_object() throws Exception {
        TestForm form = new TestForm("hello");
        assertThat(form, snapshotMatch(this, "snapshot_match_with_object"));
        TestResource testResource = new TestResource(this, "_snapshots_/snapshot_match_with_object.snapshot");
        assertThat(testResource.readString(), is(new Gson().toJson(form)));
        testResource.delete();

    }

    @Test(expected = AssertionError.class)
    public void fail_when_snapshot_not_match() {
        TestForm form = new TestForm("hello");
        assertThat(new Gson().toJson(form), snapshotMatch(this, "snapshot_not_match"));
    }

    @Test
    public void create_snapshot_when_first_run() {
        TestForm form = new TestForm("hello");
        assertThat(new Gson().toJson(form), snapshotMatch(this, "snapshot_first"));
        new TestResource(this, "_snapshots_/snapshot_first.snapshot").delete();

    }

    @Test(expected = AssertionError.class)
    public void fail_when_json_parse_fail() {
        TestForm form = new TestForm("hello");
        assertThat(new Gson().toJson(form), snapshotMatch(this, "snapshot_json_fail"));
    }
}
