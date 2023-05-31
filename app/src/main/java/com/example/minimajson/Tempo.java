package com.example.minimajson;

public class Tempo {
    private int id;

    public Tempo() {
    }

    private int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;

        switch(id){
            case 200:
                setDescription("trovoada com chuviscos");
                break;
            case 201:
                setDescription("trovoada com chuva");
                break;
                // ...
            case 800:
                setDescription("céu limpo");
                break;
            case 801:
                setDescription("algumas nuvens");
                break;
            case 802:
                setDescription("nuvens dispersas");
                break;
            case 803:
                setDescription("nuvens quebradas");
                break;
            case 804:
                setDescription("nuvens nubladas");
                break;
                // ...
            default:
                setDescription("desconhecido");
                break;
        }
    }
    private String description;
    String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private String icon;
    private float temp;
    private float humidade;
    private float temp_min;
    private float temp_max;

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public float getTemp() {
        return temp;
    }

    public void setTemp(float temp) {
        this.temp = temp;
    }

    public float getHumidade() {
        return humidade;
    }

    public void setHumidade(float humidade) {
        this.humidade = humidade;
    }

    public float getTemp_min() {
        return temp_min;
    }

    public void setTemp_min(float temp_min) {
        this.temp_min = temp_min;
    }

    public float getTemp_max() {
        return temp_max;
    }

    public void setTemp_max(float temp_max) {
        this.temp_max = temp_max;
    }

    public byte[] iconData;

}
