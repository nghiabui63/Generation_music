package com.pbl6.music.dto.request;

public class GenerateMusicRequest {
    private String seed;
    private int num_steps;
    private int max_sequence_length;
    private double temperature;

    // Getter and Setter methods
    public String getSeed() {
        return seed;
    }

    public void setSeed(String seed) {
        this.seed = seed;
    }

    public int getNum_steps() {
        return num_steps;
    }

    public void setNum_steps(int num_steps) {
        this.num_steps = num_steps;
    }

    public int getMax_sequence_length() {
        return max_sequence_length;
    }

    public void setMax_sequence_length(int max_sequence_length) {
        this.max_sequence_length = max_sequence_length;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    // Constructor for convenience
    public GenerateMusicRequest(String seed, int num_steps, int max_sequence_length, double temperature) {
        this.seed = seed;
        this.num_steps = num_steps;
        this.max_sequence_length = max_sequence_length;
        this.temperature = temperature;
    }

    // Default constructor
    public GenerateMusicRequest() {}
}
