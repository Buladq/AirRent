package ru.bul.springs.AirRent.models;


import javax.persistence.*;

@Entity
@Table(name = "image")
public class Image {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;
    @Column(name = "originalFileName")
    private String originalFileName;
    @Column(name = "size")
    private Long size;
    @Column(name = "contentType")
    private String contentType;

    @Lob
    @Column(name = "bytes")
    private byte[] bytes;

    @OneToOne(mappedBy = "avatar_id")
    private Pilot pilot;


    public Image() {
    }

    public Image(String name, String originalFileName, Long size, String contentType, byte[] bytes, Pilot pilot) {
        this.name = name;
        this.originalFileName = originalFileName;
        this.size = size;
        this.contentType = contentType;
        this.bytes = bytes;
        this.pilot = pilot;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOriginalFileName() {
        return originalFileName;
    }

    public void setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    public Pilot getPilot() {
        return pilot;
    }

    public void setPilot(Pilot pilot) {
        this.pilot = pilot;
    }
}
