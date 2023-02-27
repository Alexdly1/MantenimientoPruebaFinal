package com.myproject.controller;

import java.awt.Component;
import java.awt.event.*;
import javax.swing.*;
// -------------------------------------------------------------------------
/**
 * Represents the north menu-bar that contains various controls for the game.
 *
 * @author Ben Katz (bakatz)
 * @author Myles David II (davidmm2)
 * @author Danielle Bushrow (dbushrow)
 * @version 2010.11.17
 * Este código utiliza el patrón de programación de "Listener". En particular, 
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
            frame.setVisible( false );
            frame.dispose();
        }
    }
}
