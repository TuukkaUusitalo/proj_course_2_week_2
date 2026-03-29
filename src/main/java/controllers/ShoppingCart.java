package controllers;

public class ShoppingCart {

    public double calculateTotal(double[] prices, int[] quantities) {
        if (prices.length != quantities.length)
            throw new IllegalArgumentException("Arrays must have same length");

        double total = 0;
        for (int i = 0; i < prices.length; i++) {
            if (prices[i] < 0 || quantities[i] < 0)
                throw new IllegalArgumentException("Negative values not allowed");

            total += prices[i] * quantities[i];
        }
        return total;
    }
}
