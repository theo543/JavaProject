package model;

public record CourseStats(int gradeCount,
                          int examCount,
                          double averageGrade,
                          int fails) {}
