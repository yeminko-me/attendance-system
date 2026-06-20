package attendance.ui;

import attendance.dao.model.BreakRecord;
import attendance.service.App;
import attendance.dao.model.Attendance;
import attendance.dao.model.Employee;
import attendance.service.SalaryService;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class AttendanceScreenUI extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(AttendanceScreenUI.class.getName());

    public AttendanceScreenUI() {
        initComponents();
        setLocationRelativeTo(null);
        initFilterCombos();
        loadTableData();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        cmbYear = new javax.swing.JComboBox<>();
        cmbMonth = new javax.swing.JComboBox<>();
        btnFilter = new javax.swing.JButton();
        btnShowAll = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblRecords = new javax.swing.JTable();
        btnEdit = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnBreaks = new javax.swing.JButton();
        btnBack = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Attendance Records");

        jLabel1.setText("Filter:");

        btnFilter.setText("Filter");
        btnFilter.addActionListener(this::btnFilterActionPerformed);

        btnShowAll.setText("Show All");
        btnShowAll.addActionListener(this::btnShowAllActionPerformed);

        tblRecords.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] { "ID", "Date", "Check In", "Check Out", "Net Hours", "Note", "Employee" }
        ) {
            Class[] types = new Class [] { Integer.class, String.class, String.class, String.class, String.class, String.class, String.class };
            boolean[] canEdit = new boolean [] { false, false, false, false, false, true, false };
            @Override
            public Class getColumnClass(int columnIndex) { return types[columnIndex]; }
            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) { return canEdit[columnIndex]; }
        });
        jScrollPane1.setViewportView(tblRecords);

        btnEdit.setText("Edit");
        btnEdit.addActionListener(this::btnEditActionPerformed);

        btnDelete.setText("Delete");
        btnDelete.addActionListener(this::btnDeleteActionPerformed);

        btnBreaks.setText("Breaks");
        btnBreaks.addActionListener(this::btnBreaksActionPerformed);

        btnBack.setText("Back");
        btnBack.addActionListener(this::btnBackActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(10, 10, 10)
                        .addComponent(cmbYear, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(cmbMonth, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addComponent(btnFilter, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 136, Short.MAX_VALUE)
                        .addComponent(btnShowAll, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 780, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(btnBreaks, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 230, Short.MAX_VALUE)
                        .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(20, 20, 20))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(cmbYear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbMonth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnFilter)
                    .addComponent(btnShowAll))
                .addGap(15, 15, 15)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEdit)
                    .addComponent(btnDelete)
                    .addComponent(btnBreaks)
                    .addComponent(btnBack))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        pack();
    }

    private void initFilterCombos() {
        int currentYear = LocalDate.now().getYear();
        for (int y = currentYear - 5; y <= currentYear + 1; y++) {
            cmbYear.addItem(y);
        }
        cmbYear.setSelectedItem(currentYear);

        String[] months = { "January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December" };
        for (String m : months) {
            cmbMonth.addItem(m);
        }
        cmbMonth.setSelectedIndex(LocalDate.now().getMonthValue() - 1);
    }

    private void loadTableData() {
        loadTableData(null, 0, 0);
    }

    private void loadTableData(ArrayList<Attendance> records) {
        DefaultTableModel model = (DefaultTableModel) tblRecords.getModel();
        model.setRowCount(0);

        App app = App.getSingleton();
        boolean isAdmin = app.isAdmin();
        SalaryService salaryService = new SalaryService();

        for (Attendance a : records) {
            String empName = "";
            if (isAdmin) {
                Employee emp = app.getEmployeeService().getById(a.employeeId());
                empName = emp != null ? emp.fullName() : "Unknown";
            }
            String dateStr = a.date() != null ? a.date().toLocalDate().format(DateTimeFormatter.ofPattern("yyyy/MM/dd")) : "";
            String inStr = a.clockIn() != null ? a.clockIn().toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm:ss")) : "";
            String outStr = a.clockOut() != null ? a.clockOut().toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm:ss")) : "";
            String netHours = a.clockIn() != null && a.clockOut() != null
                ? salaryService.calculateNetWorkingHoursFromDb(a).formatted() : "";
            String note = a.note() != null ? a.note() : "";
            model.addRow(new Object[]{ a.id(), dateStr, inStr, outStr, netHours, note, empName });
        }
    }

    private void loadTableData(String filter, int year, int month) {
        App app = App.getSingleton();
        ArrayList<Attendance> records;

        if (app.isAdmin()) {
            if (filter == null) {
                records = app.getAttendanceService().getAll();
            } else {
                records = new ArrayList<>();
                for (Employee emp : app.getEmployeeService().getAll()) {
                    records.addAll(app.getAttendanceService().getByEmployeeAndMonth(emp.id(), year, month));
                }
            }
        } else {
            int empId = app.getCurrentUser().id();
            if (filter == null) {
                records = app.getAttendanceService().getByEmployee(empId);
            } else {
                records = app.getAttendanceService().getByEmployeeAndMonth(empId, year, month);
            }
        }

        loadTableData(records);
    }

    private void btnFilterActionPerformed(java.awt.event.ActionEvent evt) {
        int year = (int) cmbYear.getSelectedItem();
        int month = cmbMonth.getSelectedIndex() + 1;
        loadTableData("filter", year, month);
    }

    private void btnShowAllActionPerformed(java.awt.event.ActionEvent evt) {
        loadTableData();
    }

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {
        if (!App.getSingleton().isAdmin()) {
            JOptionPane.showMessageDialog(this, "Only admin can edit records.");
            return;
        }
        int row = tblRecords.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Please select a record to edit.");
            return;
        }
        DefaultTableModel model = (DefaultTableModel) tblRecords.getModel();
        int id = (int) model.getValueAt(row, 0);
        Attendance a = App.getSingleton().getAttendanceService().getById(id);
        if (a == null) return;

        var timeFmt = DateTimeFormatter.ofPattern("HH:mm");
        var inField = new JTextField(a.clockIn() != null ? a.clockIn().toLocalTime().format(timeFmt) : "09:00", 10);
        var outField = new JTextField(a.clockOut() != null ? a.clockOut().toLocalTime().format(timeFmt) : "18:00", 10);
        var noteField = new JTextField(a.note() != null ? a.note() : "", 20);

        var panel = new JPanel(new java.awt.GridLayout(3, 2, 10, 10));
        panel.add(new JLabel("Check In (HH:mm):"));
        panel.add(inField);
        panel.add(new JLabel("Check Out (HH:mm):"));
        panel.add(outField);
        panel.add(new JLabel("Note:"));
        panel.add(noteField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Edit Attendance Record",
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result != JOptionPane.OK_OPTION) return;

        try {
            var newIn = Time.valueOf(LocalTime.parse(inField.getText().trim()));
            var newOut = Time.valueOf(LocalTime.parse(outField.getText().trim()));
            if (newOut.toLocalTime().isBefore(newIn.toLocalTime())) {
                JOptionPane.showMessageDialog(this, "Check Out must be after Check In.");
                return;
            }
            App.getSingleton().getAttendanceService().update(
                new Attendance(a.id(), a.employeeId(), a.date(), newIn, newOut, noteField.getText())
            );
            loadTableData();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Invalid time. Use HH:mm (e.g. 09:00).");
        }
    }

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {
        int row = tblRecords.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Please select a record to delete.");
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this record?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            DefaultTableModel model = (DefaultTableModel) tblRecords.getModel();
            int id = (int) model.getValueAt(row, 0);
            App.getSingleton().getAttendanceService().deleteById(id);
            loadTableData();
        }
    }

    private void btnBreaksActionPerformed(java.awt.event.ActionEvent evt) {
        int row = tblRecords.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Please select a record to manage breaks.");
            return;
        }
        DefaultTableModel model = (DefaultTableModel) tblRecords.getModel();
        int attendanceId = (int) model.getValueAt(row, 0);
        manageBreaks(attendanceId);
    }

    private void manageBreaks(int attendanceId) {
        var dialog = new javax.swing.JDialog(this, "Manage Breaks - Attendance #" + attendanceId, true);
        dialog.setLayout(new java.awt.BorderLayout(10, 10));

        String[] cols = { "#", "Break Start", "Break End" };
        var tableModel = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        var table = new javax.swing.JTable(tableModel);
        table.setFillsViewportHeight(true);
        dialog.add(new javax.swing.JScrollPane(table), java.awt.BorderLayout.CENTER);

        var btnAdd = new javax.swing.JButton("Add");
        var btnDelete = new javax.swing.JButton("Delete");
        var btnClose = new javax.swing.JButton("Close");
        var btnPanel = new javax.swing.JPanel();
        btnPanel.add(btnAdd);
        btnPanel.add(btnDelete);
        btnPanel.add(btnClose);
        dialog.add(btnPanel, java.awt.BorderLayout.SOUTH);

        var breakService = App.getSingleton().getBreakService();
        var dateFmt = DateTimeFormatter.ofPattern("HH:mm");

        Runnable refreshTable = () -> {
            tableModel.setRowCount(0);
            int i = 1;
            for (var b : breakService.getByAttendanceId(attendanceId)) {
                tableModel.addRow(new Object[]{
                    i++,
                    b.breakStart().toLocalTime().format(dateFmt),
                    b.breakEnd().toLocalTime().format(dateFmt)
                });
            }
        };
        refreshTable.run();

        btnAdd.addActionListener(e -> {
            String startStr = JOptionPane.showInputDialog(dialog, "Break start (HH:mm):");
            if (startStr == null) return;
            String endStr = JOptionPane.showInputDialog(dialog, "Break end (HH:mm):");
            if (endStr == null) return;
            try {
                var start = Time.valueOf(LocalTime.parse(startStr.trim()));
                var end = Time.valueOf(LocalTime.parse(endStr.trim()));
                if (!end.toLocalTime().isAfter(start.toLocalTime())) {
                    JOptionPane.showMessageDialog(dialog, "End must be after start.");
                    return;
                }
                breakService.create(attendanceId, start, end);
                refreshTable.run();
                loadTableData();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Invalid format. Use HH:mm (e.g. 12:30).");
            }
        });

        btnDelete.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(dialog, "Select a break to delete.");
                return;
            }
            var breaks = breakService.getByAttendanceId(attendanceId);
            if (row < breaks.size()) {
                breakService.deleteById(breaks.get(row).id());
                refreshTable.run();
                loadTableData();
            }
        });

        btnClose.addActionListener(e -> dialog.dispose());

        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {
        dispose();
    }

    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnBreaks;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnFilter;
    private javax.swing.JButton btnShowAll;
    private javax.swing.JComboBox<Integer> cmbYear;
    private javax.swing.JComboBox<String> cmbMonth;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblRecords;
    private SalaryService salaryService = new SalaryService();
}
