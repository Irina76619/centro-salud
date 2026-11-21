/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package gui;

import app.Sistema;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.Cita;
import modelo.HistorialClinico;
import modelo.Receta;
public class VentanaAtencion extends javax.swing.JFrame {
private Sistema sistema;
private Cita cita;
private VentanaMisCitas ventanaCitas;
private DefaultTableModel modeloRecetas;

public VentanaAtencion(Sistema sistema, Cita cita, VentanaMisCitas ventanaCitas) {
    initComponents();
    setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    this.sistema = sistema;
    this.cita = cita;
    this.ventanaCitas = ventanaCitas;
    modeloRecetas = new DefaultTableModel(new String[]{"Medicamento", "Indicaciones"}, 0) {
        @Override
        public boolean isCellEditable(int fila, int col) {
            return false;
        }
    };
    tblRecetas.setModel(modeloRecetas);
    lblInfo.setText("Cita " + cita.getId() + " | Paciente: " + cita.getPaciente().getNombreCompleto()
            + " | " + cita.getFechaHora().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))
            + " | Motivo: " + cita.getMotivo());
    pack();
    setLocationRelativeTo(null);
}
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblInfo = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtDiagnostico = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtTratamiento = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtMedicamento = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtIndicaciones = new javax.swing.JTextField();
        btnQuitarReceta = new javax.swing.JButton();
        btnAgregarReceta = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblRecetas = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lblInfo.setText("info de la cita");

        jLabel2.setText("Diagnóstico");

        jLabel3.setText("Tratamiento ");

        jLabel4.setText("Medicamento");

        jLabel5.setText("Indicaciones");

        btnQuitarReceta.setText("Quitar");
        btnQuitarReceta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuitarRecetaActionPerformed(evt);
            }
        });

        btnAgregarReceta.setText("Agregar receta");
        btnAgregarReceta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarRecetaActionPerformed(evt);
            }
        });

        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        tblRecetas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(tblRecetas);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 89, Short.MAX_VALUE)
                                        .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addGap(43, 43, 43)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(txtIndicaciones, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtDiagnostico, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtTratamiento, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtMedicamento, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnAgregarReceta)
                            .addComponent(btnQuitarReceta, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap(46, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 545, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(73, 73, 73))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(lblInfo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtDiagnostico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAgregarReceta))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtTratamiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnQuitarReceta))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtMedicamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGuardar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtIndicaciones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancelar))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 389, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(32, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAgregarRecetaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarRecetaActionPerformed
        String medicamento = txtMedicamento.getText().trim();
String indicaciones = txtIndicaciones.getText().trim();
if (medicamento.isEmpty() || indicaciones.isEmpty()) {
    JOptionPane.showMessageDialog(this, "Escriba el medicamento y las indicaciones.");
    return;
}
modeloRecetas.addRow(new Object[]{medicamento, indicaciones});
txtMedicamento.setText("");
txtIndicaciones.setText("");
    }//GEN-LAST:event_btnAgregarRecetaActionPerformed

    private void btnQuitarRecetaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuitarRecetaActionPerformed
        int fila = tblRecetas.getSelectedRow();
if (fila < 0) {
    JOptionPane.showMessageDialog(this, "Seleccione una receta de la tabla.");
    return;
}
modeloRecetas.removeRow(fila);
    }//GEN-LAST:event_btnQuitarRecetaActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        String diagnostico = txtDiagnostico.getText().trim();
String tratamiento = txtTratamiento.getText().trim();

if (diagnostico.isEmpty() || tratamiento.isEmpty()) {            // RN-10
    JOptionPane.showMessageDialog(this, "El diagnóstico y el tratamiento son obligatorios.");
    return;
}
if (!txtMedicamento.getText().trim().isEmpty() || !txtIndicaciones.getText().trim().isEmpty()) {
    JOptionPane.showMessageDialog(this,
            "Tiene una receta sin agregar. Pulse \"Agregar receta\" o borre esos campos.");
    return;
}
if (!cita.getEstado().equals("PENDIENTE")) {                     // RN-09
    JOptionPane.showMessageDialog(this, "La cita ya está " + cita.getEstado() + ".");
    return;
}

LocalDate hoy = LocalDate.now();
// 1) historial clínico del paciente
sistema.historialDAO.insertar(new HistorialClinico(diagnostico, tratamiento, hoy, cita.getPaciente(), cita));
// 2) recetas vinculadas a la cita
for (int i = 0; i < modeloRecetas.getRowCount(); i++) {
    sistema.recetaDAO.insertar(new Receta((String) modeloRecetas.getValueAt(i, 0),
            (String) modeloRecetas.getValueAt(i, 1), hoy, cita));
}
// 3) la cita pasa a ATENDIDA
cita.setEstado("ATENDIDA");
sistema.citaDAO.actualizar(cita);

JOptionPane.showMessageDialog(this, "Cita atendida y cerrada.");
ventanaCitas.recargar();     // refresca la tabla de Mis citas
dispose();
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

  

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregarReceta;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnQuitarReceta;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblInfo;
    private javax.swing.JTable tblRecetas;
    private javax.swing.JTextField txtDiagnostico;
    private javax.swing.JTextField txtIndicaciones;
    private javax.swing.JTextField txtMedicamento;
    private javax.swing.JTextField txtTratamiento;
    // End of variables declaration//GEN-END:variables
}
