package cn.bobdeng.testtools;

import com.google.gson.Gson;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestResourceTest {

    @Test
    public void should_read_json() throws IOException {
        TestResource testResource = new TestResource(this, "test_read.json");
        String content = testResource.readString();
        assertThat(content, is("{\n  \"name\": \"hello\"\n}\n"));
    }

    @Test
    public void should_get_json_when_write() throws Exception {
        TestResource testResource = new TestResource(this, "test_write_object.json");
        TestForm testForm = new TestForm("hello world!");
        testResource.saveObject(testForm);
        assertThat(testResource.readString(), is(new Gson().toJson(testForm)));
    }
}
