package com.scoreanalyzer;

public record Student(String name, int math, int chinese, int english) {

    public double average() {
        return (math + chinese + english) / 3.0;
    }

    public int total() {
        return math + chinese + english;
    }
}
