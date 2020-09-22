package ui.panels;

import model.Archive;
import ui.Run;
import ui.buttonaction.NavigateAction;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

// REFERENCE: https://github.com/def-not-ys/BCS-Degree-Navigator
public class ContinueGamePanel extends GamePanel {

    protected JList<Archive> archiveList;
    protected JScrollPane curUserPanel;
    private JButton continueGame;
    private JButton toBattle;
    private JLabel label;
    private JLabel info;
    protected Archive selectedArchive;
    protected Archive curArchive;
    private SelectWizardsPanel selectWizardsPanel;

    // MODIFIES: this
    // EFFECTS: Construct the panel
    public ContinueGamePanel(Run run, Archive archive) {
        super(run, archive);

        initializeList();
        initializeContents();
        initializeInteraction();
        addToPanel();
    }

    // MODIFIES: this
    // EFFECTS: initialize the contents.
    @Override
    public void initializeContents() {
        continueGame = new JButton("Read Archive");
        toBattle = new JButton("Continue");
        curUserPanel = new JScrollPane(archiveList);
        curUserPanel.setPreferredSize(new Dimension(300, 600));
        curUserPanel.getHorizontalScrollBar().setPreferredSize(new Dimension(1, 1));
        label = new JLabel("Your existing archives: ");

        selectWizardsPanel = new SelectWizardsPanel(run, archive);
    }


    // EFFECTS: initialize the interactions
    @Override
    public void initializeInteraction() {
        initializeListInteraction();
        initializeContinueButton();
    }

    // MODIFIES: this
    // EFFECTS: update the slot
    public void updateSlot() {
        info = new JLabel("Please choose one archive to continue to play.");
        DefaultListModel<Archive> defaultListModel = new DefaultListModel<>();
        defaultListModel.removeAllElements();
        HashMap<String, Archive> archives = slot.getSlot();
        for (String str : archives.keySet()) {
            defaultListModel.addElement(archives.get(str));
        }
        try {
            archiveList.setModel(defaultListModel);
        } catch (NullPointerException e) {
            // throw exception
        }
        curArchive = archive;
    }

    // MODIFIES: this
    // EFFECTS: initialize the list
    public void initializeList() {
        archiveList = new JList<>();
        updateSlot();
    }

    // EFFECTS: this
    // EFFECTS: initialize the continue button
    private void initializeContinueButton() {
        NavigateAction action = new NavigateAction(run, this, selectWizardsPanel);
        continueGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedArchive != null) {
                    archive = selectedArchive;
                    readSuccessfullyDialog();
                }
            }
        });

        toBattle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (archive.checkPoint == 0) {
                    toBattle.addActionListener(action);
                } else {
                    goToBattle();
                }
            }
        });
    }

    // EFFECTS: initialize a dialog window
    private void readSuccessfullyDialog() {
        JOptionPane.showMessageDialog(this,
                "You have chosen " + archive.getArchiveName(),
                "Read Successfully", JOptionPane.PLAIN_MESSAGE);
    }

    // MODIFIES: this
    // EFFECTS: go to each battle regarding of the checkpoint of the archive
    private void goToBattle() {
        if (archive.getCheckPoint() == 1) {
            run.archive = archive;
            run.continueOrBackAfterChooseWizard();
        } else if (archive.getCheckPoint() == 2) {
            run.archive = archive;
            run.battle1ToBattle2();
        } else if (archive.getCheckPoint() == 3) {
            run.archive = archive;
            run.battle2ToBattle3();
        }
    }

    // MODIFIES: this
    // EFFECTS: initialize the list interaction
    protected void initializeListInteraction() {
        ListSelectionListener listener = new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                selectedArchive = archiveList.getSelectedValue();
            }
        };
        archiveList.addListSelectionListener(listener);
    }

    // MODIFIES: this and gbc
    // EFFECTS: add the components to the panel
    @Override
    public void addToPanel() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.insets = new Insets(20, 20, 0, 20);
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(label, gbc);

        gbc.insets = new Insets(0, 20, 20, 10);
        gbc.gridy = 1;
        gbc.gridheight = 3;
        add(curUserPanel, gbc);
        gbc.insets = new Insets(20, 40, 0, 20);
        gbc.gridx = 4;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        add(info, gbc);
        gbc.gridy = 2;
        add(continueGame, gbc);
        gbc.gridy = 3;
        add(toBattle, gbc);

    }

    // EFFECTS: update the panel
    @Override
    public void updatePanel() {
        updateSlot();
    }
}
