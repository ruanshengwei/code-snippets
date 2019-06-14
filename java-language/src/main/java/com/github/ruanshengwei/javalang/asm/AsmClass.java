package com.github.ruanshengwei.javalang.asm;

public class AsmClass {

    public String id;

    private String name;

    @Override
    public String toString() {
        return "AsmClass{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
