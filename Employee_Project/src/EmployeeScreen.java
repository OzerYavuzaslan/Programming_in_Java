
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;


public class EmployeeScreen extends javax.swing.JDialog
{
    DefaultTableModel model;
    RunningProccess_Class process = new RunningProccess_Class();

    public EmployeeScreen(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        model = (DefaultTableModel) employee_table.getModel();
        show_employee_method();
    }

    public void show_employee_method()
    {
        model.setRowCount(0);
        ArrayList <Employee_Class> employee = new ArrayList<>();
        
        employee = process.bring_employee_method();
        
        if(employee != null)
        {
            for(Employee_Class emp: employee)
            {
                Object addition[] = {emp.getId(), emp.getName(), emp.getSurname(), emp.getDepartment(), emp.getSalary()};
                model.addRow(addition);
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        employee_table = new javax.swing.JTable();
        searchField = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        departmentField = new javax.swing.JTextField();
        nameField = new javax.swing.JTextField();
        surnameField = new javax.swing.JTextField();
        salaryField = new javax.swing.JTextField();
        messageLabel = new javax.swing.JLabel();
        addEmpButton = new javax.swing.JButton();
        updateButton = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        deleteButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        employee_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "Surname", "Department", "Salary"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        employee_table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                employee_tableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(employee_table);
        if (employee_table.getColumnModel().getColumnCount() > 0) {
            employee_table.getColumnModel().getColumn(0).setResizable(false);
            employee_table.getColumnModel().getColumn(1).setResizable(false);
            employee_table.getColumnModel().getColumn(2).setResizable(false);
            employee_table.getColumnModel().getColumn(3).setResizable(false);
            employee_table.getColumnModel().getColumn(4).setResizable(false);
        }

        searchField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                searchFieldKeyReleased(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setText("Name:");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setText("Surname:");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setText("Department:");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel4.setText("Salary:");

        messageLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        messageLabel.setForeground(new java.awt.Color(255, 0, 0));

        addEmpButton.setText("Add New Employee");
        addEmpButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addEmpButtonActionPerformed(evt);
            }
        });

        updateButton.setText("Update Employee");
        updateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateButtonActionPerformed(evt);
            }
        });

        deleteButton.setText("Delete an Employee");
        deleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 612, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(searchField)
                            .addComponent(jSeparator1)
                            .addComponent(jLabel4)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addGap(22, 22, 22)
                                        .addComponent(surnameField, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel3)
                                            .addComponent(jLabel1))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(departmentField)
                                            .addComponent(nameField)
                                            .addComponent(salaryField, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 137, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(addEmpButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(updateButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(deleteButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(102, 102, 102))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(76, 76, 76)
                                .addComponent(messageLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 432, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jSeparator2)
                    .addComponent(jSeparator3, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(searchField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(nameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addEmpButton))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(surnameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(updateButton))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(departmentField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(deleteButton))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(salaryField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21)
                .addComponent(messageLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 278, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public void search_dynamically_method(String search)
    {
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<>(model);
        
        employee_table.setRowSorter(tr);
        
        tr.setRowFilter(RowFilter.regexFilter(search));
    }
    
    private void searchFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchFieldKeyReleased
        String search = searchField.getText();
        
        search_dynamically_method(search);
    }//GEN-LAST:event_searchFieldKeyReleased

    private void addEmpButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addEmpButtonActionPerformed
        messageLabel.setText("");
        
        String name = nameField.getText();
        String surname = surnameField.getText();
        String department = departmentField.getText();
        String salary = salaryField.getText();
        
        process.add_employee_method(name, surname, department, salary);
        show_employee_method();
        messageLabel.setText("New employee has been added successfuly.");
    }//GEN-LAST:event_addEmpButtonActionPerformed

    private void employee_tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_employee_tableMouseClicked
        int selected_row = employee_table.getSelectedRow();
        
        nameField.setText(model.getValueAt(selected_row, 1).toString());
        surnameField.setText(model.getValueAt(selected_row, 2).toString());
        departmentField.setText(model.getValueAt(selected_row, 3).toString());
        salaryField.setText(model.getValueAt(selected_row, 4).toString());
    }//GEN-LAST:event_employee_tableMouseClicked

    private void updateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateButtonActionPerformed
        messageLabel.setText("");
        
        String name = nameField.getText();
        String surname = surnameField.getText();
        String department = departmentField.getText();
        String salary = salaryField.getText();
        
        int selected_row = employee_table.getSelectedRow();
        
        if(selected_row == -1)
        {
            if(model.getRowCount() == 0)
                messageLabel.setText("Employee table is empty now!");
            else
                messageLabel.setText("Please select a employee to update");
        }
        else
        {
            int id = (int) model.getValueAt(selected_row, 0);
            process.update_employee_method(id, name, surname, department, salary);
            show_employee_method();
            messageLabel.setText("Employee has been updated successfuly.");  
        }
    }//GEN-LAST:event_updateButtonActionPerformed

    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButtonActionPerformed
        messageLabel.setText("");
        
        int selected_row = employee_table.getSelectedRow();
        
        if(selected_row == -1)
        {
            if(model.getRowCount() == 0)
                messageLabel.setText("Employee table is empty now!");
            else
                messageLabel.setText("Please select a employee to update");
        }
        else
        {
            int id = (int) model.getValueAt(selected_row, 0);
            process.delete_employee_method(id);
            show_employee_method();
            messageLabel.setText("Employee has been deleted successfuly.");  
        }
    }//GEN-LAST:event_deleteButtonActionPerformed

    public static void main(String args[])
    {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(EmployeeScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EmployeeScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EmployeeScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EmployeeScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                EmployeeScreen dialog = new EmployeeScreen(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addEmpButton;
    private javax.swing.JButton deleteButton;
    private javax.swing.JTextField departmentField;
    private javax.swing.JTable employee_table;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JLabel messageLabel;
    private javax.swing.JTextField nameField;
    private javax.swing.JTextField salaryField;
    private javax.swing.JTextField searchField;
    private javax.swing.JTextField surnameField;
    private javax.swing.JButton updateButton;
    // End of variables declaration//GEN-END:variables
}
