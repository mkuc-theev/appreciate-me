package com.appreciateme.predefservice;

public class PredefMark {
    private String id;
    private String name;
    private String content;

    public PredefMark() {
        id = null;
    }

    public PredefMark(String name, String content) {
        this.name = name;
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "PredefMark{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
