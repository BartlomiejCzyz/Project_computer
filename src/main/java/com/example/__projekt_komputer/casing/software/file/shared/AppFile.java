package com.example.__projekt_komputer.casing.software.file.shared;

import jakarta.persistence.*;

@Entity
public class AppFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String content;
    private String type;
    private long size;

    public AppFile() {
    }

    public AppFile(String name, String content, String type, long size) {
        this.name = name;
        this.content = content;
        this.type = type;
        this.size = size;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }
}
