package sharkitter.controller;

import sharkitter.model.FavouriteSharks;
import sharkitter.model.Konami;
import sharkitter.view.EasterEggFrame;
import sharkitter.view.MenuFrame;
import sharkitter.view.SearchFrame;
import sharkitter.view.StatisticsFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class FunctionalityController implements ActionListener, KeyListener {

    private MenuFrame menuFrame;
    private SearchFrame searchFrame;
    private StatisticsFrame statisticsFrame;
    private EasterEggFrame easterEggFrame;

    private FavouriteSharks favouriteSharks;

    private Konami konami;

    public FunctionalityController(MenuFrame menuFrame, FavouriteSharks favouriteSharks) {
        this.menuFrame = menuFrame;
        this.favouriteSharks = favouriteSharks;

        konami = new Konami();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String buttonName = ((JButton) e.getSource()).getText();

        switch(buttonName) {
            case "Search":
                menuFrame.setVisible(false);
                searchFrame = new SearchFrame(favouriteSharks);
                searchFrame.setVisible(true);
                break;

            case "Favourites":
                break;

            case "Statistics":
                menuFrame.setVisible(false);
                statisticsFrame = new StatisticsFrame();
                statisticsFrame.setVisible(true);
                break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        konami.registerPressedKey(e.getKeyCode());
        if(konami.checkKonamiCode()) {
            konami.reset();
            easterEggFrame = new EasterEggFrame();
            easterEggFrame.setVisible(true);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
