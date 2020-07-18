package main.machine.process;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Set;

public class InputProcessorHelper {

    public  JsonObject convertToJsonObject(String fileContent) {
        JsonObject jsonObject = new Gson().fromJson(fileContent , JsonObject.class);
        return jsonObject;
    }

    // get the contents of the file in a single string
    public String getFileContent(File file) throws IOException {
        FileReader fileReader = new FileReader(file);
        BufferedReader br = new BufferedReader(fileReader);
        String fileContent = new String();
        String line = new String();
        while((line = br.readLine()) != null) {  // reading line by line
            fileContent += line;  // appendning one line to the result string
        }
        return fileContent;
    }

    public void printFailureOrderStatus(String orderName, Set<String> missingIngredients, Set<String> insufficientIngredients) {
        StringBuilder failureStatus =
                new StringBuilder();
        failureStatus.append(orderName + " cannot be prepared because ");
        missingIngredients.stream().forEach(it -> failureStatus.append(it + " is not available  "));
        insufficientIngredients.stream().forEach(it -> failureStatus.append(it + " is not sufficient  "));
        String failureStatusString = failureStatus.toString();
        System.out.println(failureStatusString);
    }
}
