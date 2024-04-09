import gui.*;
import server.GetInfo;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        GUI.makeGUI();
        try {
            GetInfo.getData("http://145.24.223.74:8080");
        }catch (IOException e) {
            System.out.println("POTATOES");
        }
    }
}