package cn.bobdeng.testtools;

import lombok.Data;

@Data
public class TestForm {
    private String name;
    private int index;

    public TestForm(String name, int index) {
        this.name = name;
        this.index = index;
    }

    public TestForm(String name) {
        this.name = name;
    }
}
