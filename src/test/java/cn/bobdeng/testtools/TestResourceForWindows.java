package cn.bobdeng.testtools;

public class TestResourceForWindows extends TestResource {
    public TestResourceForWindows(Object testObject, String fileName) {
        super(testObject, fileName);
    }

    @Override
    protected String fileSeparator() {
        return "\\";
    }
}
