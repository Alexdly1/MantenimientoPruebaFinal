package com.myproject.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.awt.Component;
import java.awt.event.*;
import javax.swing.*;

import org.junit.Test;
// -------------------------------------------------------------------------
/**
 * Represents the north menu-bar that contains various controls for the game.
 *
 * @author Ben Katz (bakatz)
 * @author Myles David II (davidmm2)
 * @author Danielle Bushrow (dbushrow)
 * @version 2010.11.17
 * Este código utiliza el patrón de programación de "Listener". 
 * utiliza el patrón de diseño "Observer" o "Listener" para manejar los eventos 
 * generados por los elementos de la GUI.
 * 
 * Ademas este código sigue el patrón de diseño de "Factory Method" para crear menús 
 * y elementos de menú en bucles for, lo que permite una creación de menús y 
 * elementos de menú más eficiente y escalable.
 */
public class ChessMenuBar
    extends JMenuBar{
    // ----------------------------------------------------------
    /**
     * Create a new ChessMenuBar object.
     */
    public ChessMenuBar(){
        String[] menuCategories = { "File", "Options", "Help" };
        String[] menuItemLists =
        { "New game/restart,Exit", "Toggle graveyard,Toggle game log",
          "About" };
        for ( int i = 0; i < menuCategories.length; i++ ){
            JMenu currMenu = new JMenu( menuCategories[i] );
            String[] currMenuItemList = menuItemLists[i].split( "," );
            for ( int j = 0; j < currMenuItemList.length; j++ ){
                JMenuItem currItem = new JMenuItem( currMenuItemList[j] );
                currItem.addActionListener( new MenuListener() );
                currMenu.add( currItem );
            }
            this.add( currMenu );
        }
    }
    /**
     * Listener for the north menu bar.
     *
     * @author Ben Katz (bakatz)
     * @author Myles David II (davidmm2)
     * @author Danielle Bushrow (dbushrow)
     * @version 2010.11.17
     */
    private class MenuListener
        implements ActionListener
    {
        /**
         * Takes an appropriate action based on which menu button is clicked
         *
         * @param event
         *            the mouse event from the source
         */
        @Override
        public void actionPerformed( ActionEvent event ){
            
            String buttonName = ( (JMenuItem)event.getSource() ).getText();
            if ( buttonName.equals( "About" ) ){
                aboutHandler();
            }
            else if ( buttonName.equals( "New game/restart" ) ){
                restartHandler();
            }
            else if ( buttonName.equals( "Toggle game log" ) ){
                toggleGameLogHandler();
            }
            else if ( buttonName.equals( "Exit" ) ){
                exitHandler();
            }
            else
            {
                toggleGraveyardHandler();
            }
        }
        private void aboutHandler(){
        JOptionPane.showMessageDialog(
            getParent(),
            "YetAnotherChessGame v1.0 by:\nBen Katz\nMyles David\n"
                + "Danielle Bushrow\n\nFinal Project for CS2114 @ VT" 
            );
        }
        
        private void restartHandler(){
            ( (ChessPanel)getParent() ).getGameEngine().reset();
        }
    
        private void toggleGraveyardHandler(){
            ( (ChessPanel)getParent() ).getGraveyard( 1 ).setVisible(
                !( (ChessPanel)getParent() ).getGraveyard( 1 ).isVisible() );
            ( (ChessPanel)getParent() ).getGraveyard( 2 ).setVisible(
                !( (ChessPanel)getParent() ).getGraveyard( 2 ).isVisible() );
        }
        
        private void toggleGameLogHandler(){
            ( (ChessPanel)getParent() ).getGameLog().setVisible(
                !( (ChessPanel)getParent() ).getGameLog().isVisible() );
            ( (ChessPanel)getParent() ).revalidate();
        }
        
        private void exitHandler(){
            JOptionPane.showMessageDialog(getParent(), "Thanks for leaving"
                + ", quitter! >:(" );
            Component possibleFrame = getParent();
            while ( possibleFrame != null && !( possibleFrame instanceof JFrame ) ){
                possibleFrame = possibleFrame.getParent();
            }
            JFrame frame = (JFrame)possibleFrame;
            if (frame != null) {
                frame.setVisible(false);
                frame.dispose();
            }
        }
    }
    // Este método de prueba unitaria prueba la creación de los menús de la aplicación.
    @Test
    public void testCreateMenus(){
        // Primero se crea una instancia de ChessMenuBar.
        ChessMenuBar menuBar = new ChessMenuBar();
        // Luego se obtienen los menús "File", "Options" y "Help" de la barra de menú.
        JMenu fileMenu = menuBar.getMenu(0);
        JMenu optionsMenu = menuBar.getMenu(1);
        JMenu helpMenu = menuBar.getMenu(2);
        // Se comprueba que los menús no sean nulos y que tengan el texto esperado.
        assertNotNull(fileMenu);
        assertNotNull(optionsMenu);
        assertNotNull(helpMenu);
        assertEquals("File", fileMenu.getText());
        assertEquals("Options", optionsMenu.getText());
        assertEquals("Help", helpMenu.getText());
        // Si se cumple todo lo anterior, la prueba pasa satisfactoriamente.
    }

    @Test
    public void testCreateMenuItems() {
        // Crear una nueva ChessMenuBar y obtener los menús correspondientes
        ChessMenuBar menuBar = new ChessMenuBar();
        JMenu fileMenu = menuBar.getMenu(0);
        JMenu optionsMenu = menuBar.getMenu(1);

        // Obtener los JMenuItem correspondientes a cada menú
        JMenuItem newItem = fileMenu.getItem(0);
        JMenuItem exitItem = fileMenu.getItem(1);
        JMenuItem undoItem = optionsMenu.getItem(0);
        JMenuItem redoItem = optionsMenu.getItem(1);

        // Asegurarse de que los JMenuItem no sean nulos y tengan los textos correctos
        assertNotNull(newItem);
        assertNotNull(exitItem);
        assertNotNull(undoItem);
        assertNotNull(redoItem);
        assertEquals("New game/restart", newItem.getText());
        assertEquals("Exit", exitItem.getText());
        assertEquals("Toggle graveyard", undoItem.getText());
        assertEquals("Toggle game log", redoItem.getText());
    }

    @Test
    public void testExitHandler() {
        // Crear una nueva ChessMenuBar y un JFrame para usar como frame padre
        ChessMenuBar menuBar = new ChessMenuBar();
        JFrame parentFrame = new JFrame();
        parentFrame.setJMenuBar(menuBar);
        parentFrame.pack();
        parentFrame.setVisible(true);
    
        // Simular un clic en el botón "Exit" para invocar el método exitHandler()
        JMenuItem exitItem = (JMenuItem) ((JMenu) menuBar.getComponents()[0]).getPopupMenu().getComponent(1);
        exitItem.doClick();
    
        // Se busca JFrame que contiene el ChessMenuBar y se cierra 
        Component possibleFrame = menuBar.getParent();
        JFrame frame = null;
        // Se busca el componente padre de la barra de menú a través del método getParent()
        while (possibleFrame != null && !(possibleFrame instanceof JFrame)) {
            possibleFrame = possibleFrame.getParent();
        }
        // Si el componente padre es un JFrame, se almacena en la variable frame
        if (possibleFrame instanceof JFrame) {
            frame = (JFrame) possibleFrame;
        }
        // Se visible en false y se invoca el método dispose() para cerrar el JFrame.
        if (frame != null) {
            frame.setVisible(false);
            frame.dispose();
        }
    }

}
