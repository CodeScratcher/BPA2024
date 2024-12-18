/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.keefeteam.atlantis.ui;

import org.keefeteam.atlantis.GameState;
import org.keefeteam.atlantis.entities.Player;
import org.keefeteam.atlantis.util.input.InputEvent;

import java.util.List;

/**
 *
 * @author afisher
 */
public class InventoryMenu implements Menu {
    private Player player;

    public InventoryMenu(Player player) {
        this.player = player;
    }

    @Override
    public void initialize(GameState gameState) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void update(List<InputEvent> inputEvents) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void dispose() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
