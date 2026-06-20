package attendance.ui;

import attendance.service.App;
import attendance.dao.model.Attendance;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import javax.swing.JOptionPane;

public class MainUI extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(MainUI.class.getName());

    public MainUI() {
        initComponents();
        setLocationRelativeTo(null);
        loadData();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        lblWelcome = new javax.swing.JLabel();
        lblDate = new javax.swing.JLabel();
        lblStatus = new javax.swing.JLabel();
        btnClockIn = new javax.swing.JButton();
        btnClockOut = new javax.swing.JButton();
        btnViewRecords = new javax.swing.JButton();
        btnManageEmployees = new javax.swing.JButton();
        btnCalculateSalary = new javax.swing.JButton();
        btnLogout = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Attendance Management");
        setResizable(false);

        lblWelcome.setFont(new java.awt.Font("Yu Gothic UI", java.awt.Font.BOLD, 18));
        lblWelcome.setText("Welcome, User");

        lblDate.setText("2026-06-20");

            lblStatus.setText("Status: Not checked in");

        btnClockIn.setText("Check In");
        btnClockIn.addActionListener(this::btnClockInActionPerformed);

        btnClockOut.setText("Check Out");
        btnClockOut.addActionListener(this::btnClockOutActionPerformed);

        btnViewRecords.setText("View Attendance Records");
        btnViewRecords.addActionListener(this::btnViewRecordsActionPerformed);

        btnManageEmployees.setText("Manage Employees (Admin)");
        btnManageEmployees.addActionListener(this::btnManageEmployeesActionPerformed);

        btnCalculateSalary.setText("Calculate Salary (Admin)");
        btnCalculateSalary.addActionListener(this::btnCalculateSalaryActionPerformed);

        btnLogout.setText("Logout");
        btnLogout.addActionListener(this::btnLogoutActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblWelcome, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblDate, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(btnClockIn, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(btnClockOut, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnViewRecords, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnManageEmployees, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCalculateSalary, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLogout, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(40, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(lblWelcome)
                .addGap(10, 10, 10)
                .addComponent(lblDate)
                .addGap(15, 15, 15)
                .addComponent(lblStatus)
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnClockIn)
                    .addComponent(btnClockOut))
                .addGap(25, 25, 25)
                .addComponent(btnViewRecords)
                .addGap(10, 10, 10)
                .addComponent(btnManageEmployees)
                .addGap(10, 10, 10)
                .addComponent(btnCalculateSalary)
                .addGap(10, 10, 10)
                .addComponent(btnLogout)
                .addContainerGap(30, Short.MAX_VALUE))
        );

        pack();
    }

    private void loadData() {
        App app = App.getSingleton();
        var user = app.getCurrentUser();
        if (user != null) {
            lblWelcome.setText("Welcome, " + user.fullName());
        }

        LocalDate today = LocalDate.now();
        lblDate.setText(today.format(DateTimeFormatter.ofPattern("yyyy/MM/dd (E)")));

        refreshStatus();
        btnManageEmployees.setVisible(app.isAdmin());
        btnCalculateSalary.setVisible(app.isAdmin());
    }

    private void refreshStatus() {
        App app = App.getSingleton();
        Attendance todayAtt = app.getAttendanceService().getTodayAttendance(app.getCurrentUser().id());
        if (todayAtt == null) {
            lblStatus.setText("Status: Not checked in");
            btnClockIn.setEnabled(true);
            btnClockOut.setEnabled(false);
        } else if (todayAtt.clockOut() == null) {
            lblStatus.setText("Status: Checked in at " + todayAtt.clockIn().toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm")));
            btnClockIn.setEnabled(false);
            btnClockOut.setEnabled(true);
        } else {
            lblStatus.setText("Status: Completed - In: " + todayAtt.clockIn().toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm"))
                + " Out: " + todayAtt.clockOut().toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm")));
            btnClockIn.setEnabled(false);
            btnClockOut.setEnabled(false);
        }
    }

    private void btnClockInActionPerformed(java.awt.event.ActionEvent evt) {
        App app = App.getSingleton();
        boolean ok = app.getAttendanceService().clockIn(app.getCurrentUser().id());
        if (ok) {
            JOptionPane.showMessageDialog(this, "Checked in at " + LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
            refreshStatus();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to clock in. You may already have a record for today.");
        }
    }

    private void btnClockOutActionPerformed(java.awt.event.ActionEvent evt) {
        App app = App.getSingleton();
        boolean ok = app.getAttendanceService().clockOut(app.getCurrentUser().id());
        if (ok) {
            JOptionPane.showMessageDialog(this, "Checked out at " + LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
            refreshStatus();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to clock out.");
        }
    }

    private void btnViewRecordsActionPerformed(java.awt.event.ActionEvent evt) {
        new AttendanceScreenUI().setVisible(true);
    }

    private void btnManageEmployeesActionPerformed(java.awt.event.ActionEvent evt) {
        new EmployeeManageUI().setVisible(true);
    }

    private void btnCalculateSalaryActionPerformed(java.awt.event.ActionEvent evt) {
        new SalaryScreenUI().setVisible(true);
    }

    private void btnLogoutActionPerformed(java.awt.event.ActionEvent evt) {
        App.getSingleton().setCurrentUser(null);
        new LoginUI().setVisible(true);
        dispose();
    }

    private javax.swing.JButton btnClockIn;
    private javax.swing.JButton btnClockOut;
    private javax.swing.JButton btnLogout;
    private javax.swing.JButton btnManageEmployees;
    private javax.swing.JButton btnCalculateSalary;
    private javax.swing.JButton btnViewRecords;
    private javax.swing.JLabel lblDate;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JLabel lblWelcome;
}
