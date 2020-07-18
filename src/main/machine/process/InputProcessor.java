package main.machine.process;

import com.google.gson.JsonObject;
import main.machine.Ingredients;
import main.machine.Orders;
import main.machine.Outlets;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
/*
Class which processes the file and inputs.
 */
public class InputProcessor {

    Outlets outlets = new Outlets();
    Orders orders = new Orders();
    Ingredients ingredients = new Ingredients();

    InputProcessorHelper inputProcessorHelper = new InputProcessorHelper();
    JsonProcessorForMachineHelper jsonProcessorForMachineHelper = new JsonProcessorForMachineHelper();

    // processes the file and shows output of the problem statement
    public void process(File file) throws IOException {
        String fileContent = inputProcessorHelper.getFileContent(file);
        JsonObject fileObject = inputProcessorHelper.convertToJsonObject(fileContent);
        processJson(fileObject.get("machine").getAsJsonObject());
        processOrders();
    }

    private void processOrders() {
        boolean flag = true;  // its true when we got an order processed successfully.
        Set<String> processed = new HashSet<>();  // set to store processed order
        while(flag) {
            flag = false;  // setting flag as false
            for (Map.Entry<String, Map<String, Integer>> ordersEntry : orders.getOrderMap().entrySet()) {
                String orderName = ordersEntry.getKey();
                Map<String , Integer> ingredientsForOrder = ordersEntry.getValue();

                if(processed.contains(orderName))  continue;

                if(ingredients.hasSufficientForOrder(ingredientsForOrder)) {  // if true order can be processed successfully
                    processSuccessOrder(ordersEntry);
                    processed.add(orderName);
                    flag = true;  // order is processed successfully
                } else {
                    processFailureOrder(ordersEntry);
                    processed.add(orderName);
                }
            }
        }
    }

    private void processFailureOrder(Map.Entry<String, Map<String, Integer>> ordersEntry) {
        String orderName = ordersEntry.getKey();
        Map<String ,Integer> ingredientsForOrder = ordersEntry.getValue();
        // getting missing and insufficient orders
        Set<String> missingIngredients = ingredients.getMissingIngredients(ingredientsForOrder);
        Set<String> insufficientIngredients = ingredients.getInsufficientIngredient(ingredientsForOrder);
        // printing why order was not prepared
        inputProcessorHelper.printFailureOrderStatus(orderName , missingIngredients , insufficientIngredients);
    }


    private void processSuccessOrder(Map.Entry<String, Map<String, Integer>> ordersEntry) {
        String orderName = ordersEntry.getKey();
        Map<String ,Integer> ingredientsForOrder = ordersEntry.getValue();
        ingredients.reduceIngredientsForOrder(ingredientsForOrder); // reduce ingredients from the machine
        System.out.println(orderName + " is prepared");

    }

    // extract information from json and set the values of ingredients and orders
    private void processJson(JsonObject fileObject) {
        int outletCount = jsonProcessorForMachineHelper.getOutlets(fileObject);
        Map<String, Integer> ingredientsMap = jsonProcessorForMachineHelper.getIngredients(fileObject);
        Map<String , Map<String, Integer>> ordersMap  = jsonProcessorForMachineHelper.getOrders(fileObject);
        outlets.setNumber(outletCount);
        orders.setOrdersMap(ordersMap);
        ingredients.setIngredientsMap(ingredientsMap);
    }



}
