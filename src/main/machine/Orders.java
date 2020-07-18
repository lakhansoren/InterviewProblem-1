package main.machine;

import java.util.Map;

public class Orders {
    private Map<String , Map<String , Integer>> orders;

    public void addOrder(String order , Map<String , Integer> ingredients) {
        orders.put(order , ingredients);
    }

    public Map<String , Map<String , Integer>> getOrderMap() {
        return orders;
    }

    public void setOrdersMap(Map<String , Map<String, Integer>> map) {
        orders = map;
    }
}
