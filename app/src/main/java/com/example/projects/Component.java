package com.example.projects;

public class Component{
    private int id;
    private String nameComponent;
    private int number;
    private int useNumber = 0;
    private String image;
    private String description;
    private String characteristics;
    private String documentation;
    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }
    public void setNameComponent(String n) {
        nameComponent = n;
    }
    public String getNameComponent() {
        return nameComponent;
    }
    public void setNumber(int num) {
        number = num;
    }
    public  int getNumber() {
        return number;
    }
    public void setUseNumber(int useNumber) {
        this.useNumber = useNumber;
    }
    public int getUseNumber() {
        return useNumber;
    }
    public void setImage(String i) { image = i; }
    public String getImage() { return image; }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getDescription() {
        return description;
    }
    public void setCharacteristics(String characteristics) {
        this.characteristics = characteristics;
    }
    public String getCharacteristics() {
        return characteristics;
    }
    public void setDocumentation(String documentation) {
        this.documentation = documentation;
    }
    public String getDocumentation() {
        return documentation;
    }
}
