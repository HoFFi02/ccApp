package app;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class AutoCompleteTextField extends JTextField {
    private final List<String> suggestions;
    private final JList<String> suggestionsList;
    private final JScrollPane scrollPane;

    public AutoCompleteTextField(String[] suggestions) {
        super();
        this.suggestions = new ArrayList<>();
        this.suggestionsList = new JList<>(this.suggestions.toArray(new String[0]));

        setupAutoComplete(suggestions);

        // Ustaw preferowaną szerokość pola tekstowego
        setPreferredSize(new Dimension(200, getPreferredSize().height));

        this.scrollPane = new JScrollPane(suggestionsList);
        scrollPane.setPreferredSize(new Dimension(200, 100));  // Ustaw preferowaną szerokość i wysokość listy
        scrollPane.setVisible(false);

        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.SOUTH);

        getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateSuggestions();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateSuggestions();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateSuggestions();
            }
        });
    }

    private void setupAutoComplete(String[] suggestions) {
        for (String suggestion : suggestions) {
            this.suggestions.add(suggestion);
        }

        suggestionsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        suggestionsList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                setText(suggestionsList.getSelectedValue());
                scrollPane.setVisible(false);
            }
        });
    }

    private void updateSuggestions() {
        String input = getText().toLowerCase();
        suggestions.clear();

        for (String suggestion : suggestions) {
            if (suggestion.toLowerCase().contains(input)) {
                suggestions.add(suggestion);
            }
        }

        suggestionsList.setListData(suggestions.toArray(new String[0]));
        scrollPane.setVisible(!suggestions.isEmpty());
    }
}
