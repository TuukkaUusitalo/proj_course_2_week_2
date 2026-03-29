import static org.junit.jupiter.api.Assertions.*;

import controllers.ShoppingCart;
import org.junit.jupiter.api.Test;

public class ShoppingCartTest {

    @Test
    public void testSimpleCalculation() {
        ShoppingCart cart = new ShoppingCart();
        double[] prices = { 10.0, 5.5 };
        int[] quantities = { 2, 4 };

        double total = cart.calculateTotal(prices, quantities);
        assertEquals(10*2 + 5.5*4, total);
    }

    @Test
    public void testZeroQuantities() {
        ShoppingCart cart = new ShoppingCart();
        double[] prices = { 10.0, 5.0 };
        int[] quantities = { 0, 0 };

        assertEquals(0, cart.calculateTotal(prices, quantities));
    }

    @Test
    public void testNegativePriceThrowsException() {
        ShoppingCart cart = new ShoppingCart();
        double[] prices = { -1.0 };
        int[] quantities = { 1 };

        assertThrows(IllegalArgumentException.class,
                () -> cart.calculateTotal(prices, quantities));
    }

    @Test
    public void testNegativeQuantityThrowsException() {
        ShoppingCart cart = new ShoppingCart();
        double[] prices = { 5.0 };
        int[] quantities = { -2 };

        assertThrows(IllegalArgumentException.class,
                () -> cart.calculateTotal(prices, quantities));
    }

    @Test
    public void testDifferentLengthsThrowsException() {
        ShoppingCart cart = new ShoppingCart();
        double[] prices = { 5.0, 2.0 };
        int[] quantities = { 1 };

        assertThrows(IllegalArgumentException.class,
                () -> cart.calculateTotal(prices, quantities));
    }
}