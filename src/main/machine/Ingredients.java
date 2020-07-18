package main.machine;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Ingredients {
    private Map<String , Integer> store;  // store stores the ingredients quantity in the machine


    public void addIngredient(String ingredient , int num) {
        store.put(ingredient , num);
    }

    public int check(String ingredient) {
        if(store.containsKey(ingredient) == false) return 0;
        return store.get(ingredient);
    }

    public void useIngredient(String ingredient , int num) {
        if(store.containsKey(ingredient) == false) throw new IllegalArgumentException("Ingredient not found");  // throw exception if the ingredient is not found in the machine
        store.put(ingredient , store.get(ingredient) - num);
    }

    public void setIngredientsMap(Map<String, Integer> ingredientsMap) {
        store = ingredientsMap;
    }

    public boolean hasSufficientForOrder(Map<String, Integer> ingredientsForOrder) { // checks whether the machine has sufficient ingredients for the order
        for(Map.Entry<String , Integer> requiredIngrident : ingredientsForOrder.entrySet()) {
            String ingredientName = requiredIngrident.getKey();
            int requiredQuantity = requiredIngrident.getValue();
            if(this.check(ingredientName) >= requiredQuantity) {
                // do nothing
            }
            else {
                return false;  // ingredient present in the machine is not sufficient
            }
        }
        return true;
    }

    public void reduceIngredientsForOrder(Map<String, Integer> ingredientsForOrder) {
        for(Map.Entry<String , Integer> requiredIngredient : ingredientsForOrder.entrySet()) {
            String ingredientName = requiredIngredient.getKey();
            int requiredQuantity = requiredIngredient.getValue();
            this.useIngredient(ingredientName , requiredQuantity);
        }
    }

    public Set<String> getMissingIngredients(Map<String, Integer> ingredientsForOrder) {
        Set<String> missingIngredients = new HashSet<>();
        for(Map.Entry<String , Integer> requiredIngredient : ingredientsForOrder.entrySet()) {
            String ingredientName = requiredIngredient.getKey();
            int quantityPresentInStore = this.check(ingredientName);
            if(quantityPresentInStore == 0)
                missingIngredients.add(ingredientName);
        }
        return missingIngredients;
    }

    public Set<String> getInsufficientIngredient(Map<String, Integer> ingredientsForOrder) {
        Set<String> insufficientIngredients =
        ingredientsForOrder.entrySet()
                .stream()
                .filter(ingredientsEntry -> {
                    final int requiredIngredientQuantity = ingredientsEntry.getValue();
                    final String ingredientName = ingredientsEntry.getKey();
                    if(this.check(ingredientName) < requiredIngredientQuantity && this.check(ingredientName) != 0)  // if quantity of ingredient is 0 its missing not insufficient
                        return true;
                    return false;
                })
                .map(ingredientsEntry -> ingredientsEntry.getKey())
                .collect(Collectors.toSet());
        return insufficientIngredients;
    }
}

