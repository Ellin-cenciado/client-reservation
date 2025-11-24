package com.reservation.admin.gui;

import com.reservation.admin.service.IReservationService;
import com.reservation.admin.service.ReservationService;
import com.reservation.common.model.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import raven.datetime.DatePicker;
import raven.datetime.TimePicker;

import java.text.SimpleDateFormat;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.*;
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
    private JTextField emailField;
    private JButton submit;
    private TimePicker timePicker;
    private JLabel confirmationLabel;
    private JCheckBox confirmedAssistanceCheckBox;
    private IReservationService reservationService;
    private List<WorkSelectorRow> workSelectorRows;

    private Integer idReservation;

    @Autowired
    public Form(ReservationService reservationService){
        System.out.println("Form constructor called at: " + System.currentTimeMillis());
        this.reservationService = reservationService;
        initializeForm();
        listReservations();

        addWorkButton.addActionListener(e -> addWorkSelector());
        submit.addActionListener(e -> handleSubmit());

        tableReservation.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                loadSelectedReservation();
            }
        });
    }

    private void initializeForm(){
        setContentPane(Container);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 700);
        setLocationRelativeTo(null);
    }


    private void createUIComponents() {
        this.workSelectorRows = new ArrayList<>();
        // Table setup
        this.reservationTableModel = new DefaultTableModel(0, 6){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }

            @Override
            public Class<?> getColumnClass(int columnIndex){
                switch (columnIndex){
                    case 0:
                        return Integer.class;
                    case 3:
                        return java.util.ArrayList.class;
                    case 5:
                        return java.sql.Timestamp.class;
                    default:
                        return String.class;
                }
            }
        };

        String[] headers = {"Id", "Name", "Surname", "Work List","Email","Reservation Date","Confirmation"};
        this.reservationTableModel.setColumnIdentifiers(headers);
        this.tableReservation = new JTable(reservationTableModel);
        this.tableReservation.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.tableReservation.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        TableCellRenderer timestampRenderer = getTableCellRenderer();

        // Apply the renderer to both date columns
        this.tableReservation.getColumnModel().getColumn(4).setCellRenderer(timestampRenderer);
        this.tableReservation.getColumnModel().getColumn(5).setCellRenderer(timestampRenderer);



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

    }

    private static TableCellRenderer getTableCellRenderer() {
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd MMM yy HH:mm");
        return new DefaultTableCellRenderer() {
            @Override
            public java.awt.Component getTableCellRendererComponent(JTable table, Object value,
                                                                    boolean isSelected, boolean hasFocus, int row, int column) {
                if (value instanceof java.sql.Timestamp) {
                    value = dateTimeFormat.format((java.sql.Timestamp) value);
                } else if (value instanceof Date) {
                    value = dateTimeFormat.format((Date) value);
                }
                return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            }
        };
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
        this.reservationTableModel.setRowCount(0);
        var reservations = this.reservationService.listReservations();
        reservations.sort(Comparator.comparing(Reservation::getId));
        reservations.forEach(reservation -> {
            Object[] reservationRow = {
                    reservation.getId(),
                    reservation.getName(),
                    reservation.getSurname(),
                    reservation.getWorks(),
                    reservation.getEmail(),
                    reservation.getDateDay(),
                    (reservation.getAssistanceConfirmation() ? "Si" : "No")
            };
            System.out.println(Arrays.toString(reservationRow));
            this.reservationTableModel.addRow(reservationRow);
        });
        fitColumnsToHeader(this.tableReservation);
    }

    private void fitColumnsToHeader(JTable table) {
        for (int column = 0; column < table.getColumnCount(); column++) {
            TableColumn tableColumn = table.getColumnModel().getColumn(column);

            // Get header renderer and component
            TableCellRenderer headerRenderer = table.getTableHeader().getDefaultRenderer();
            java.awt.Component comp = headerRenderer.getTableCellRendererComponent(
                    table, tableColumn.getHeaderValue(), false, false, 0, column);

            int headerWidth = comp.getPreferredSize().width + 20; // +20 for padding
            tableColumn.setPreferredWidth(headerWidth);
        }
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

    private void loadSelectedReservation(){
        var viewRow = tableReservation.getSelectedRow();
        if(viewRow != -1){//-1 means that there is no entry selected
            // Convert view row index to model row index
            int modelRow = tableReservation.convertRowIndexToModel(viewRow);

            //Get id from the table displayed and find the reservation
            Reservation reservationFound = reservationService.findReservationById(Integer.parseInt(tableReservation
                    .getModel()
                    .getValueAt(modelRow,0)
                    .toString()));

            //Fill the fields on the form
            this.idReservation = reservationFound.getId();
            nameField.setText(reservationFound.getName());
            surnameField.setText(reservationFound.getSurname());
            emailField.setText(reservationFound.getEmail());
            confirmedAssistanceCheckBox.setSelected(reservationFound.getAssistanceConfirmation());
            handleCustomAddWorkSelector(reservationFound.getWorks());
            datePicker.setSelectedDate(
                    reservationFound.getDateDay().toInstant()
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate()
            );
            timePicker.setSelectedTime(
                    reservationFound.getDateDay().toInstant()
                            .atZone(ZoneId.systemDefault())
                            .toLocalTime()
            );
        }
    }

    private void handleCustomAddWorkSelector(List<Reservation.Work> works){
        // Clear previous combo boxes from the panel and list
        worksPanel.removeAll();
        workSelectorRows.clear();

        // Add a combo box for each work in the list
        for (Reservation.Work work : works) {
            JPanel rowPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
            rowPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

            // Create new combo box
            JComboBox<Reservation.Work> combo = new JComboBox<>();
            Arrays.stream(Reservation.Work.values()).forEach(combo::addItem);
            combo.setPreferredSize(new Dimension(200, 30));
            combo.setSelectedItem(work);  // Set the selected item to this work

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
        }

        // Update the workSelector reference to the first combo box (if needed elsewhere)
        if (!workSelectorRows.isEmpty()) {
            this.workSelector = workSelectorRows.getFirst().comboBox;
        }

        // Refresh the panel
        worksPanel.revalidate();
        worksPanel.repaint();
    }

    private void handleSubmit(){
//        try{
//            if(!validateInputs()){
//                return;
//            }

        LocalDate selectedDate = datePicker.getSelectedDate();
        LocalTime selectedTime = timePicker.getSelectedTime();
        LocalDateTime reservationDateTime = LocalDateTime.of(selectedDate, selectedTime);

        Date reservationDate = Date.from(
                reservationDateTime.atZone(ZoneId.systemDefault()).toInstant()
        );

            Reservation reservation = new Reservation();
            reservation.setId(this.idReservation);
            reservation.setName(nameField.getText());
            reservation.setSurname(surnameField.getText());
            reservation.setEmail(emailField.getText());
            reservation.setWorks(getSelectedWorks());
            reservation.setCreationDate(new Date());
            reservation.setDateDay(reservationDate);
            reservation.setUuid(UUID.randomUUID());
            reservation.setAssistanceConfirmation(confirmedAssistanceCheckBox.isSelected());
        String message = String.format("""
        New reservation for: %s, %s
        Email: %s
        Works to get done: %s
        Created at: %tF at %tT
        Reservation date: %tF at %tT
        """,
                reservation.getName(),
                reservation.getSurname(),
                reservation.getEmail(),
                reservation.getWorks(),
                reservation.getCreationDate(),
                reservation.getCreationDate(),
                reservationDate,
                reservationDate);

        if (this.idReservation == null){
            showMessage("New reservation entry added");
        }else{
            showMessage("Reservation entry updated");
        }

        System.out.println(message);
        this.reservationService.saveReservation(reservation);
        clearForm();
        listReservations();
    }

    private void clearForm(){
        this.idReservation = null;
        nameField.setText("");
        surnameField.setText("");
        emailField.setText("");
        confirmedAssistanceCheckBox.setSelected(false);
        addInitialWorkSelector();
        datePicker.setSelectedDate(LocalDate.now());
        timePicker.setSelectedTime(LocalTime.now());
    }

    private void showMessage(String message){
        JOptionPane.showMessageDialog(this,message);
    }
}
