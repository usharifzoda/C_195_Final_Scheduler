/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.ZonedDateTime;

/**
 * @author usharifzoda
 */

public class LogSessions {
    private static final String FILENAME = "log.txt";
    
    public LogSessions() {}
    
    public static void log (String username, boolean success) {
        try (FileWriter newFile = new FileWriter(FILENAME, true);
             BufferedWriter writer = new BufferedWriter(newFile);
             PrintWriter print = new PrintWriter(writer)) {
            print.println(ZonedDateTime.now() + " " + username + (success ? " Success" : " Failure"));
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
