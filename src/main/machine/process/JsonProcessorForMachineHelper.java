package main.machine.process;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class JsonProcessorForMachineHelper {

    public Map<String , Integer> getIngredients(JsonObject fileObject) {  // get the ingredients present in the machine
        Set<Map .Entry<String , JsonElement>> entrySet =
                fileObject.get("total_items_quantity").getAsJsonObject().entrySet();
        Map<String , Integer> ingredients = new HashMap<>();
        for(Map.Entry<String , JsonElement> entry : entrySet) {
            ingredients.put(entry.getKey() , entry.getValue().getAsInt());
        }

        return ingredients;
    }

    public int getOutlets(JsonObject fileObject) {  // get outlet count from json
        return fileObject.getAsJsonObject()
                .get("outlets").getAsJsonObject()
                .get("count_n").getAsInt();
    }

    public Map<String, Map<String, Integer>> getOrders(JsonObject fileObject) {  // get all orders from json
        Set<Map.Entry<String , JsonElement>>  outerEntrySet =
                fileObject.get("beverages").getAsJsonObject().entrySet();

        Map<String , Map<String , Integer>> ordersMap = new HashMap<>();

        for(Map.Entry<String , JsonElement> entry : outerEntrySet) {
            ordersMap.put(entry.getKey() , getIngredientMapForOrder(entry.getValue()));
        }
        return ordersMap;
    }

    private Map<String, Integer> getIngredientMapForOrder(JsonElement value) {  // get ingredients with their required quantity for an order
        Set<Map.Entry<String , JsonElement>> entrySet =
                value.getAsJsonObject().entrySet();
        Map<String , Integer> ingredientsMap = new HashMap<>();

        for(Map.Entry<String , JsonElement> entry : entrySet) {
            ingredientsMap.put(entry.getKey() , entry.getValue().getAsInt());
        }

        return ingredientsMap;
    }
}
