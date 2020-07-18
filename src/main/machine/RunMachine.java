package main.machine;

import main.machine.process.InputProcessor;

import java.io.File;
import java.io.IOException;
/*
Class containing main function , it serves as the starting point
 */
public class RunMachine {
    public static void main(String args[]) throws IOException {
        File file = new File("src/resources/example1.json");
        InputProcessor inputProcessor = new InputProcessor();
        inputProcessor.process(file);
    }
}
