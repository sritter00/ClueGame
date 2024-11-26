package clueGame;

/**
 * AccusationDialog: Handles the accusation for the Clue Game.
 * 
 * author: Shane Ritter
 * author: Carter Gorling
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Set;

public class AccusationDialog extends JDialog {

    private JComboBox<String> weaponBox;
    private JComboBox<String> suspectBox;
    private JComboBox<String> roomBox;
    private JButton submitButton;
    private Solution accusation;
    public Solution getAccusation() {
    	return accusation;
    }
    public AccusationDialog(Component parent, Set<Card> cardList) {
    	super((Frame) parent, "Make a Accusation", true); // Modal dialog
        setLayout(new BorderLayout());
      
        ArrayList<String> weaponList = new ArrayList<>();
        ArrayList<String> personList = new ArrayList<>();
        ArrayList<String> roomList = new ArrayList<>();
        // Panel for form fields
        JPanel formPanel = new JPanel(new GridLayout(3, 2));

       
        for(Card card : cardList) {
        	if(card.getType() == CardType.WEAPON) {
        		weaponList.add(card.getCardName());
        	}
        	if(card.getType() == CardType.PERSON) {
        		personList.add(card.getCardName());
        	}
        	if(card.getType() == CardType.ROOM) {
        		roomList.add(card.getCardName());
        	}
        	
        }
        suspectBox = new JComboBox<>(personList.toArray(new String[0]));
        weaponBox = new JComboBox<>(weaponList.toArray(new String[0]));
        roomBox = new JComboBox<>(roomList.toArray(new String[0]));
        
        formPanel.add(new JLabel("Room:"));
        formPanel.add(roomBox);
        add(formPanel, BorderLayout.CENTER);
        
        // Suspect selection
        formPanel.add(new JLabel("Suspect:"));
        formPanel.add(suspectBox);

        // Weapon selection
        formPanel.add(new JLabel("Weapon:"));
        formPanel.add(weaponBox);
        // Room (preselected and uneditable)
        

        // Submit button
        submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	accusation = new Solution(Board.getInstance().getCard((String) roomBox.getSelectedItem()),
                		Board.getInstance().getCard((String) suspectBox.getSelectedItem()),
                		Board.getInstance().getCard((String) weaponBox.getSelectedItem()));
                dispose(); // Close the dialog
            }
        });
        add(submitButton, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(parent);
    }
    
}
