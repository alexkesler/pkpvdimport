package org.kesler.pkpvdimport;

import org.kesler.pkpvdimport.gui.MainViewController;

import javax.swing.*;


public class PKPVDImport
{
    public static void main( String[] args )
    {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MainViewController.getInstance().showView();
            }
        });
    }
}
