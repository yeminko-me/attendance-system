package attendance.ui;

import attendance.service.App;
import attendance.dao.model.Employee;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class EmployeeManageUI extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(EmployeeManageUI.class.getName());

    public EmployeeManageUI() {
        initComponents();
        setLocationRelativeTo(null);
        loadTableData();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        txtUsername = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtPassword = new javax.swing.JPasswordField();
        jLabel3 = new javax.swing.JLabel();
        txtFullName = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtAddress = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtPhone = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblEmployees = new javax.swing.JTable();
        btnAdd = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnBack = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Manage Employees");

        jLabel1.setText("Username");
        jLabel2.setText("Password");
        jLabel3.setText("Full Name");
        jLabel4.setText("Address");
        jLabel5.setText("Phone");

        tblEmployees.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] { "ID", "Username", "Full Name", "Role", "Address", "Phone" }
        ) {
            Class[] types = new Class [] { Integer.class, String.class, String.class, String.class, String.class, String.class };
            boolean[] canEdit = new boolean [] { false, false, false, false, false, false };
            @Override
            public Class getColumnClass(int columnIndex) { return types[columnIndex]; }
            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) { return canEdit[columnIndex]; }
        });
        jScrollPane1.setViewportView(tblEmployees);

        btnAdd.setText("Add");
        btnAdd.addActionListener(this::btnAddActionPerformed);

        btnEdit.setText("Edit");
        btnEdit.addActionListener(this::btnEditActionPerformed);

        btnDelete.setText("Delete");
        btnDelete.addActionListener(this::btnDeleteActionPerformed);

        btnBack.setText("Back");
        btnBack.addActionListener(this::btnBackActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 700, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(10, 10, 10)
                        .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(jLabel2)
                        .addGap(10, 10, 10)
                        .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(jLabel3)
                        .addGap(10, 10, 10)
                        .addComponent(txtFullName, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(jLabel4)
                        .addGap(10, 10, 10)
                        .addComponent(txtAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(jLabel5)
                        .addGap(10, 10, 10)
                        .addComponent(txtPhone, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(100, 100, 100)
                        .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(txtFullName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(txtAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(txtPhone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAdd)
                    .addComponent(btnEdit)
                    .addComponent(btnDelete)
                    .addComponent(btnBack))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        pack();
    }

    private void loadTableData() {
        DefaultTableModel model = (DefaultTableModel) tblEmployees.getModel();
        model.setRowCount(0);

        App app = App.getSingleton();
        for (Employee emp : app.getEmployeeService().getAll()) {
            model.addRow(new Object[]{ emp.id(), emp.username(), emp.fullName(), emp.role(), emp.address(), emp.phone() });
        }
    }

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword());
        String fullName = txtFullName.getText().trim();
        String address = txtAddress.getText().trim();
        String phone = txtPhone.getText().trim();

        if (username.isEmpty() || password.isEmpty() || fullName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Username, Password, and Full Name are required.");
            return;
        }

        App app = App.getSingleton();
        boolean ok = app.getEmployeeService().registerUser(username, password, fullName, address, phone);
        if (ok) {
            JOptionPane.showMessageDialog(this, "Employee added successfully.");
            txtUsername.setText("");
            txtPassword.setText("");
            txtFullName.setText("");
            txtAddress.setText("");
            txtPhone.setText("");
            loadTableData();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to add employee. Username may exist.");
        }
    }

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {
        int row = tblEmployees.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Please select an employee to edit.");
            return;
        }
        DefaultTableModel model = (DefaultTableModel) tblEmployees.getModel();
        int id = (int) model.getValueAt(row, 0);

        Employee emp = App.getSingleton().getEmployeeService().getById(id);
        if (emp == null) return;

        var nameField = new javax.swing.JTextField(emp.fullName(), 15);
        var roleCombo = new javax.swing.JComboBox<String>(new String[]{"user", "admin"});
        roleCombo.setSelectedItem(emp.role());
        var addrField = new javax.swing.JTextField(emp.address(), 15);
        var phoneField = new javax.swing.JTextField(emp.phone(), 15);

        var panel = new javax.swing.JPanel(new java.awt.GridLayout(4, 2, 10, 10));
        panel.add(new javax.swing.JLabel("Full Name:"));
        panel.add(nameField);
        panel.add(new javax.swing.JLabel("Role:"));
        panel.add(roleCombo);
        panel.add(new javax.swing.JLabel("Address:"));
        panel.add(addrField);
        panel.add(new javax.swing.JLabel("Phone:"));
        panel.add(phoneField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Edit Employee #" + id,
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result != JOptionPane.OK_OPTION) return;

        String newName = nameField.getText().trim();
        if (newName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Full Name cannot be empty.");
            return;
        }

        App.getSingleton().getEmployeeService().update(
            new Employee(id, emp.username(), newName, (String) roleCombo.getSelectedItem(), addrField.getText(), phoneField.getText())
        );
        loadTableData();
    }

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {
        dispose();
    }

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {
        int row = tblEmployees.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Please select an employee to delete.");
            return;
        }
        DefaultTableModel model = (DefaultTableModel) tblEmployees.getModel();
        int id = (int) model.getValueAt(row, 0);

        if (id == App.getSingleton().getCurrentUser().id()) {
            JOptionPane.showMessageDialog(this, "You cannot delete yourself.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Delete this employee? All their attendance records will also be deleted.", "Confirm", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            App.getSingleton().getEmployeeService().deleteById(id);
            loadTableData();
        }
    }

    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnEdit;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblEmployees;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtFullName;
    private javax.swing.JTextField txtUsername;
    private javax.swing.JTextField txtAddress;
    private javax.swing.JTextField txtPhone;
}
