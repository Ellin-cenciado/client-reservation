package com.reservation.admin.gui;

import com.reservation.admin.service.IReservationService;
import com.reservation.admin.service.ReservationService;
import com.reservation.common.model.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import raven.datetime.DatePicker;
import raven.datetime.TimePicker;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class Form extends JFrame {
    private JPanel Container;
    private JTable tableReservation;
    private DefaultTableModel reservationTableModel;
    private JPanel tableWrapper;
    private JPanel formWrapper;
    private JLabel nameLabel;
    private JTextField nameField;
    private JLabel surnameLabel;
    private JTextField surnameField;
    private JComboBox<Reservation.Work> workSelector;
    private JButton addWorkButton;
    private JLabel worksLabel;
    private DatePicker datePicker;
    private JScrollPane tableScrollPane;
    private JScrollPane workScrollPane;
    private JPanel worksPanel;
    private JPanel datePickerWrapper;
    private JLabel emailLabel;
    private JTextField textField1;
    private JButton submit;
    private TimePicker timePicker;
    private IReservationService reservationService;

    private List<WorkSelectorRow> workSelectorRows;

    @Autowired
    public Form(ReservationService reservationService){
        this.reservationService = reservationService;
        initializeForm();

        addWorkButton.addActionListener(e -> addWorkSelector());
    }

    private void initializeForm(){

        setContentPane(Container);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
    }

    private void createUIComponents() {
        this.workSelectorRows = new ArrayList<>();
        // Table setup
        this.reservationTableModel = new DefaultTableModel(0, 4){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };

        String[] headers = {"Id", "Name", "Surname", "Work"};
        this.reservationTableModel.setColumnIdentifiers(headers);
        this.tableReservation = new JTable(reservationTableModel);
        this.tableReservation.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Works panel setup
        this.worksPanel = new JPanel();
        this.worksPanel.setLayout(new BoxLayout(worksPanel, BoxLayout.Y_AXIS));

        // Add initial work selector
        addInitialWorkSelector();

        // Create the works scroll pane
        this.workScrollPane = new JScrollPane(this.worksPanel);
        this.workScrollPane.setPreferredSize(new Dimension(300, 150));
        this.workScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        this.workScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        listReservations();
    }

    private void addInitialWorkSelector() {
        JPanel rowPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        rowPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

        JComboBox<Reservation.Work> combo = new JComboBox<>();
        Arrays.stream(Reservation.Work.values()).forEach(combo::addItem);
        combo.setPreferredSize(new Dimension(200, 30));

        rowPanel.add(combo);

        // Store reference
        this.workSelector = combo;
        workSelectorRows.add(new WorkSelectorRow(rowPanel, combo, null));

        worksPanel.add(rowPanel);
    }

    private void addWorkSelector() {
        JPanel rowPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        rowPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

        // Create new combo box
        JComboBox<Reservation.Work> combo = new JComboBox<>();
        Arrays.stream(Reservation.Work.values()).forEach(combo::addItem);
        combo.setPreferredSize(new Dimension(200, 30));

        // Create remove button
        JButton removeButton = new JButton("-");
        removeButton.setPreferredSize(new Dimension(45, 30));

        WorkSelectorRow row = new WorkSelectorRow(rowPanel, combo, removeButton);

        // Add remove action
        removeButton.addActionListener(e -> removeWorkSelector(row));

        rowPanel.add(combo);
        rowPanel.add(removeButton);

        // Add to list and panel
        workSelectorRows.add(row);
        worksPanel.add(rowPanel);

        // Refresh the panel
        worksPanel.revalidate();
        worksPanel.repaint();
    }

    private void removeWorkSelector(WorkSelectorRow row) {
        // Don't allow removing the last work selector
        if (workSelectorRows.size() <= 1) {
            JOptionPane.showMessageDialog(this,
                    "At least one work must be selected.",
                    "Cannot Remove",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        workSelectorRows.remove(row);
        worksPanel.remove(row.panel);

        // Refresh the panel
        worksPanel.revalidate();
        worksPanel.repaint();
    }

    public List<Reservation.Work> getSelectedWorks() {
        List<Reservation.Work> works = new ArrayList<>();
        for (WorkSelectorRow row : workSelectorRows) {
            Reservation.Work selected = (Reservation.Work) row.comboBox.getSelectedItem();
            if (selected != null) {
                works.add(selected);
            }
        }
        return works;
    }

    private void listReservations(){
        var reservations = this.reservationService.listReservations();
        reservations.forEach(reservation -> {
            Object[] reservationRow = {
                    reservation.getId(),
                    reservation.getName(),
                    reservation.getSurname(),
                    reservation.getWorks()
            };
            System.out.println(Arrays.toString(reservationRow));
            this.reservationTableModel.addRow(reservationRow);
        });
    }

    private static class WorkSelectorRow {
        JPanel panel;
        JComboBox<Reservation.Work> comboBox;
        JButton removeButton;

        WorkSelectorRow(JPanel panel, JComboBox<Reservation.Work> comboBox, JButton removeButton) {
            this.panel = panel;
            this.comboBox = comboBox;
            this.removeButton = removeButton;
        }
    }
}