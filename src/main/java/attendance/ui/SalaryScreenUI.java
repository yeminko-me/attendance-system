package attendance.ui;

import attendance.dao.model.Employee;
import attendance.dto.SalaryResponse;
import attendance.service.App;
import attendance.service.SalaryService;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class SalaryScreenUI extends javax.swing.JFrame {

    private final SalaryService salaryService = new SalaryService();

    public SalaryScreenUI() {
        initComponents();
        setLocationRelativeTo(null);
        loadEmployees();
        cmbMonth.setSelectedIndex(LocalDate.now().getMonthValue() - 1);
        cmbYear.setSelectedItem(LocalDate.now().getYear());
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        cmbEmployee = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        cmbYear = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        cmbMonth = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        txtHourlyRate = new javax.swing.JTextField();
        btnCalculate = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtResult = new javax.swing.JTextArea();
        btnClose = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Calculate Salary");
        setResizable(false);

        jLabel1.setText("Employee:");

        jLabel2.setText("Year:");

        jLabel3.setText("Month:");

        jLabel4.setText("Hourly Rate:");

        txtHourlyRate.setText("1000");

        btnCalculate.setText("Calculate");
        btnCalculate.addActionListener(this::btnCalculateActionPerformed);

        txtResult.setEditable(false);
        txtResult.setColumns(20);
        txtResult.setRows(10);
        txtResult.setFont(new java.awt.Font("Yu Gothic UI", java.awt.Font.PLAIN, 14));
        jScrollPane1.setViewportView(txtResult);

        btnClose.setText("Close");
        btnClose.addActionListener(this::btnCloseActionPerformed);

        String[] months = { "January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December" };
        for (String m : months) {
            cmbMonth.addItem(m);
        }

        for (int y = LocalDate.now().getYear() - 5; y <= LocalDate.now().getYear() + 1; y++) {
            cmbYear.addItem(y);
        }

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
                        .addComponent(cmbEmployee, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addComponent(jLabel2)
                        .addGap(10, 10, 10)
                        .addComponent(cmbYear, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addComponent(jLabel3)
                        .addGap(10, 10, 10)
                        .addComponent(cmbMonth, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(10, 10, 10)
                        .addComponent(txtHourlyRate, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addComponent(btnCalculate, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 580, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(240, 240, 240)
                        .addComponent(btnClose, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(cmbEmployee, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(cmbYear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(cmbMonth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtHourlyRate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCalculate))
                .addGap(15, 15, 15)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(btnClose)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        pack();
    }

    private void loadEmployees() {
        ArrayList<Employee> employees = App.getSingleton().getEmployeeService().getAll();
        for (Employee e : employees) {
            cmbEmployee.addItem(e);
        }
        if (employees.size() > 0) {
            cmbEmployee.setSelectedIndex(0);
        }
    }

    private void btnCalculateActionPerformed(java.awt.event.ActionEvent evt) {
        Employee selected = (Employee) cmbEmployee.getSelectedItem();
        if (selected == null) {
            JOptionPane.showMessageDialog(this, "Please select an employee.");
            return;
        }

        int year = (int) cmbYear.getSelectedItem();
        int month = cmbMonth.getSelectedIndex() + 1;

        double hourlyRate;
        try {
            hourlyRate = Double.parseDouble(txtHourlyRate.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid hourly rate.");
            return;
        }

        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());

        SalaryResponse response = salaryService.calculateSalary(selected.id(), startDate, endDate, hourlyRate);

        StringBuilder sb = new StringBuilder();
        sb.append("Employee: ").append(response.employeeName()).append("\n");
        sb.append("Period: ").append(response.startDate()).append(" ~ ").append(response.endDate()).append("\n");
        sb.append("Work Days: ").append(response.workDays()).append(" days\n");
        sb.append("Total Gross Hours: ").append(response.totalHours()).append("\n");
        sb.append("Total Net Hours : ").append(response.totalNetHours()).append("\n");
        sb.append("Hourly Rate    : ").append(String.format("%,.0f", response.hourlyRate())).append(" yen\n");
        sb.append("----------------------------------------\n");
        sb.append("Total Salary   : ").append(String.format("%,.0f", response.totalSalary())).append(" yen\n");
        sb.append("\n--- Daily Breakdown ---\n");
        sb.append(String.format("%-12s %-6s %-6s %-8s %-8s %s\n", "Date", "In", "Out", "Gross", "Break", "Net"));
        sb.append("----------------------------------------------------------\n");
        for (var d : response.dailyDetails()) {
            sb.append(String.format("%-12s %-6s %-6s %-8s %-8s %s\n",
                d.date(), d.clockIn(), d.clockOut(), d.grossHours(), d.breakHours(), d.netHours()));
        }

        txtResult.setText(sb.toString());
    }

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {
        dispose();
    }

    private javax.swing.JButton btnCalculate;
    private javax.swing.JButton btnClose;
    private javax.swing.JComboBox<Employee> cmbEmployee;
    private javax.swing.JComboBox<Integer> cmbYear;
    private javax.swing.JComboBox<String> cmbMonth;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea txtResult;
    private javax.swing.JTextField txtHourlyRate;
}
