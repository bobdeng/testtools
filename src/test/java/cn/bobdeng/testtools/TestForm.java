package cn.bobdeng.testtools;

import lombok.Data;

@Data
public class TestForm {
    private String name;

    public TestForm(String name) {
        this.name = name;
    }
}
