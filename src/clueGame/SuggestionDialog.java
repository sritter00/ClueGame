package clueGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Set;

public class SuggestionDialog extends JDialog {

    private JComboBox<String> weaponBox;
    private JComboBox<String> suspectBox;
    private JComboBox<String> roomBox;
    private JButton submitButton;
    private Solution suggestion;
    public Solution getSuggestion() {
    	return suggestion;
    }
    public SuggestionDialog(Component parent, Set<Card> cardList, String room) {
    	super((Frame) parent, "Make a Suggestion", true); // Modal dialog
        setLayout(new BorderLayout());
      
        ArrayList<String> weaponList = new ArrayList<>();
        ArrayList<String> personList = new ArrayList<>();
        // Panel for form fields
        JPanel formPanel = new JPanel(new GridLayout(3, 2));

        // Room (preselected and uneditable)
        formPanel.add(new JLabel("Room:"));
        roomBox = new JComboBox<>(new String[]{room});
        roomBox.setEnabled(false);
        formPanel.add(roomBox);
        for(Card card : cardList) {
        	if(card.getType() == CardType.WEAPON) {
        		weaponList.add(card.getCardName());
        	}
        	if(card.getType() == CardType.PERSON) {
        		personList.add(card.getCardName());
        	}
        }
        suspectBox = new JComboBox<>(personList.toArray(new String[0]));
        weaponBox = new JComboBox<>(weaponList.toArray(new String[0]));
        // Suspect selection
        formPanel.add(new JLabel("Suspect:"));
        formPanel.add(suspectBox);

        // Weapon selection
        formPanel.add(new JLabel("Weapon:"));
        formPanel.add(weaponBox);

        add(formPanel, BorderLayout.CENTER);

        // Submit button
        submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                suggestion = new Solution(Board.getInstance().getCard((String) suspectBox.getSelectedItem()),
                		Board.getInstance().getCard((String) weaponBox.getSelectedItem()),
                		Board.getInstance().getCard((String) roomBox.getSelectedItem()));
                dispose(); // Close the dialog
            }
        });
        add(submitButton, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(parent);
    }
    
}
