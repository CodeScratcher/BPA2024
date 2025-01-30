package org.keefeteam.atlantis;

import org.junit.jupiter.api.Test;
import org.keefeteam.atlantis.util.Item;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SQLTest {
    @Test
    public void loadItem() {
        Item item = new Item("Cloth");
        assertEquals(item.getName(), "Cloth");
    }
}
