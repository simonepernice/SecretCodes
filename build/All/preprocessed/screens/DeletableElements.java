package screens;
/*
 * DeletableElements.java
 *
 * Created on 16 novembre 2007, 21.52
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 *
 * @author simone
 */
public interface DeletableElements {
    
    /**
     * Delete the element with given index 
     */
    public void deleteElementAt (int index);
    
    /**
     * Does not deleteElementAt anything!
     */
    public void showScreen ();
    
}
