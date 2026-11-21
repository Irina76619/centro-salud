/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package gui;

import app.Sistema;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.Paciente;

public class PanelReportes extends javax.swing.JPanel {

    private Sistema sistema;
    private DefaultTableModel modelo;
// STRICT: rechaza fechas imposibles como 31/02/2026
    private static final DateTimeFormatter FECHA
            = DateTimeFormatter.ofPattern("dd/MM/uuuu").withResolverStyle(ResolverStyle.STRICT);

    public PanelReportes(Sistema sistema) {
        initComponents();
        this.sistema = sistema;
        modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int fila, int col) {
                return false;
            }
        };
        tblResultado.setModel(modelo);
    }

    /**
     * Lo llama MenuAdminFrame al abrir la pestaña: deja la pantalla limpia.
     */
    public void recargar() {
        modelo.setRowCount(0);
        modelo.setColumnIdentifiers(new String[]{});
        txtFecha.setText("");
        txtMes.setText("");
        txtAnio.setText("");
        txtDni.setText("");
    }

    /**
     * Cambia los encabezados y llena la tabla con las filas que devuelve
     * ServicioReportes.
     */
    private void mostrar(String[] columnas, List<Object[]> filas) {
        modelo.setRowCount(0);
        modelo.setColumnIdentifiers(columnas);
        for (Object[] fila : filas) {
            modelo.addRow(fila);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnCitasMedico = new javax.swing.JButton();
        btnCitasEspecialidad = new javax.swing.JButton();
        btnRecetas = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        txtFecha = new javax.swing.JTextField();
        btnCitasDia = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        txtMes = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtAnio = new javax.swing.JTextField();
        btnCitasMes = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        txtDni = new javax.swing.JTextField();
        btnHistorial = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblResultado = new javax.swing.JTable();

        btnCitasMedico.setText("Citas por médico");
        btnCitasMedico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCitasMedicoActionPerformed(evt);
            }
        });

        btnCitasEspecialidad.setText("Citas por especialidad");
        btnCitasEspecialidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCitasEspecialidadActionPerformed(evt);
            }
        });

        btnRecetas.setText("Recetas y tratamientos");
        btnRecetas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRecetasActionPerformed(evt);
            }
        });

        jLabel1.setText("Día (dd/MM/yyyy):");

        btnCitasDia.setText("Citas del día");
        btnCitasDia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCitasDiaActionPerformed(evt);
            }
        });

        jLabel2.setText("Mes:");

        txtMes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMesActionPerformed(evt);
            }
        });

        jLabel3.setText("Año:");

        txtAnio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAnioActionPerformed(evt);
            }
        });

        btnCitasMes.setText("Citas del mes");
        btnCitasMes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCitasMesActionPerformed(evt);
            }
        });

        jLabel4.setText("DNI del paciente:");

        btnHistorial.setText("Historial paciente");
        btnHistorial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHistorialActionPerformed(evt);
            }
        });

        tblResultado.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(tblResultado);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(124, 124, 124)
                                .addComponent(btnCitasMedico)
                                .addGap(26, 26, 26)
                                .addComponent(btnCitasEspecialidad))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(39, 39, 39)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 157, Short.MAX_VALUE)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(txtMes, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtAnio, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtFecha)
                                    .addComponent(txtDni, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))))
                        .addGap(31, 31, 31)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnRecetas)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(btnHistorial, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE)
                                .addComponent(btnCitasMes, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnCitasDia, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 653, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(47, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCitasMedico)
                    .addComponent(btnCitasEspecialidad)
                    .addComponent(btnRecetas))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtMes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtAnio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(txtDni, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(btnCitasDia)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCitasMes)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnHistorial)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(30, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtMesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMesActionPerformed

    private void txtAnioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAnioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAnioActionPerformed

    private void btnCitasDiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCitasDiaActionPerformed
        try {
            LocalDate fecha = LocalDate.parse(txtFecha.getText().trim(), FECHA);
            mostrar(new String[]{"Fecha y hora", "Paciente", "Médico", "Motivo", "Estado"},
                    sistema.reportes.filasCitasPorDia(fecha));
        } catch (DateTimeParseException ex) {                          // RN-08
            JOptionPane.showMessageDialog(this, "Fecha inválida. Use dd/MM/yyyy (ej. 05/10/2026).");
        }
    }//GEN-LAST:event_btnCitasDiaActionPerformed

    private void btnCitasMedicoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCitasMedicoActionPerformed
        mostrar(new String[]{"Médico", "Fecha y hora", "Paciente", "Motivo", "Estado"},
                sistema.reportes.filasCitasPorMedico());
    }//GEN-LAST:event_btnCitasMedicoActionPerformed

    private void btnCitasEspecialidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCitasEspecialidadActionPerformed
        mostrar(new String[]{"Especialidad", "Fecha y hora", "Paciente", "Médico", "Motivo", "Estado"},
                sistema.reportes.filasCitasPorEspecialidad());
    }//GEN-LAST:event_btnCitasEspecialidadActionPerformed

    private void btnRecetasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRecetasActionPerformed
        mostrar(new String[]{"Fecha", "Paciente", "Diagnóstico", "Tratamiento", "Receta"},
                sistema.reportes.filasRecetasYTratamientos());
    }//GEN-LAST:event_btnRecetasActionPerformed

    private void btnCitasMesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCitasMesActionPerformed
        try {
            int mes = Integer.parseInt(txtMes.getText().trim());
            int anio = Integer.parseInt(txtAnio.getText().trim());
            if (mes < 1 || mes > 12 || anio < 2000 || anio > 2100) {   // RN-08
                JOptionPane.showMessageDialog(this, "Mes entre 1 y 12 y año entre 2000 y 2100.");
                return;
            }
            mostrar(new String[]{"Fecha y hora", "Paciente", "Médico", "Motivo", "Estado"},
                    sistema.reportes.filasCitasPorMes(mes, anio));
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Escriba el mes y el año como números.");
        }
    }//GEN-LAST:event_btnCitasMesActionPerformed

    private void btnHistorialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHistorialActionPerformed
        String dni = txtDni.getText().trim();
        if (!dni.matches("\\d{8}")) {                                   // RN-08
            JOptionPane.showMessageDialog(this, "El DNI debe tener 8 dígitos.");
            return;
        }
        Paciente p = sistema.pacienteDAO.buscar(dni);
        if (p == null) {
            JOptionPane.showMessageDialog(this, "No existe un paciente con ese DNI.");
            return;
        }
        mostrar(new String[]{"Fecha", "Diagnóstico", "Tratamiento"},
                sistema.reportes.filasHistorialPorPaciente(p));
    }//GEN-LAST:event_btnHistorialActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCitasDia;
    private javax.swing.JButton btnCitasEspecialidad;
    private javax.swing.JButton btnCitasMedico;
    private javax.swing.JButton btnCitasMes;
    private javax.swing.JButton btnHistorial;
    private javax.swing.JButton btnRecetas;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblResultado;
    private javax.swing.JTextField txtAnio;
    private javax.swing.JTextField txtDni;
    private javax.swing.JTextField txtFecha;
    private javax.swing.JTextField txtMes;
    // End of variables declaration//GEN-END:variables
}
