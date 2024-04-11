package service;

import java.util.Scanner;

public class ScannerService {
    private static ScannerService instance = null;
    private final Scanner scanner;
    private ScannerService() {
        scanner = new Scanner(System.in);
    }
    public static ScannerService getInstance() {
        if (instance == null) {
            instance = new ScannerService();
        }
        return instance;
    }
    public String nextLine() {
        return scanner.nextLine();
    }
}
