/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package gui;

import app.Sistema;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.Cita;
import modelo.HistorialClinico;
import modelo.Medico;
import modelo.Paciente;
import modelo.Receta;

public class VentanaHistorial extends javax.swing.JFrame {

    private Sistema sistema;
    private Medico medico;
    private DefaultTableModel modeloPacientes;
    private DefaultTableModel modeloHistorial;
    private DefaultTableModel modeloRecetas;
    private List<HistorialClinico> mostrados = new ArrayList<>();
    private static final DateTimeFormatter FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public VentanaHistorial(Sistema sistema, Medico medico) {
        initComponents();
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        this.sistema = sistema;
        this.medico = medico;

        modeloPacientes = crearModelo("DNI", "Paciente");
        modeloHistorial = crearModelo("Fecha", "Diagnóstico", "Tratamiento", "Atendido por");
        modeloRecetas = crearModelo("Medicamento", "Indicaciones");
        tblPacientes.setModel(modeloPacientes);
        tblHistorial.setModel(modeloHistorial);
        tblRecetas.setModel(modeloRecetas);

        habilitarEdicion(false);
        cargarPacientes();
        pack();
        setLocationRelativeTo(null);
    }

    /**
     * Crea un modelo de tabla que no se puede editar a mano.
     */
    private DefaultTableModel crearModelo(String... columnas) {
        return new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int fila, int col) {
                return false;
            }
        };
    }

    /**
     * Tabla de apoyo: solo los pacientes que tienen citas con este médico
     * (RN-03).
     */
    private void cargarPacientes() {
        modeloPacientes.setRowCount(0);
        Set<String> yaAgregados = new LinkedHashSet<>();
        for (Cita c : sistema.citaDAO.listarPorMedico(medico)) {
            Paciente p = c.getPaciente();
            if (yaAgregados.add(p.getDni())) {
                modeloPacientes.addRow(new Object[]{p.getDni(), p.getNombreCompleto()});
            }
        }
    }

    /**
     * ¿El paciente tiene alguna cita con este médico? (RN-03)
     */
    private boolean esPacienteDelMedico(Paciente p) {
        for (Cita c : sistema.citaDAO.listarPorMedico(medico)) {
            if (c.getPaciente().getDni().equals(p.getDni())) {
                return true;
            }
        }
        return false;
    }

    /**
     * ¿El registro corresponde a una atención de este médico? (RN-11)
     */
    private boolean esPropio(HistorialClinico h) {
        return h.getCita() != null
                && h.getCita().getMedico().getCodigoMedico().equals(medico.getCodigoMedico());
    }

    private void habilitarEdicion(boolean activo) {
        txtDiagnostico.setEditable(activo);
        txtTratamiento.setEditable(activo);
        btnGuardar.setEnabled(activo);
    }

    private void limpiarEdicion() {
        txtDiagnostico.setText("");
        txtTratamiento.setText("");
        modeloRecetas.setRowCount(0);
        habilitarEdicion(false);
    }

    private void verHistorial() {
        modeloHistorial.setRowCount(0);
        mostrados = new ArrayList<>();
        limpiarEdicion();

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
        if (!esPacienteDelMedico(p)) {                                  // RN-03
            JOptionPane.showMessageDialog(this, "Ese paciente no tiene citas con usted.");
            return;
        }
        mostrados = sistema.historialDAO.listarPorPaciente(p);
        for (HistorialClinico h : mostrados) {
            String quien = (h.getCita() == null) ? "-" : h.getCita().getMedico().getNombreCompleto();
            modeloHistorial.addRow(new Object[]{h.getFecha().format(FECHA),
                h.getDiagnostico(), h.getTratamiento(), quien});
        }
        if (mostrados.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El paciente aún no tiene registros en su historial.");
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblPacientes = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtDiagnostico = new javax.swing.JTextField();
        btnVer = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblRecetas = new javax.swing.JTable();
        txtDni = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblHistorial = new javax.swing.JTable();
        txtTratamiento = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        btnGuardar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Mis pacientes (clic para elegir):");

        tblPacientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tblPacientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblPacientesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblPacientes);

        jLabel2.setText("DNI del paciente:");

        jLabel3.setText("Historial clínico:");

        jLabel4.setText("Diagnóstico:");

        jLabel5.setText("Recetas de la atención seleccionada:");

        btnVer.setText("Ver historial");
        btnVer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVerActionPerformed(evt);
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
        jScrollPane2.setViewportView(tblRecetas);

        tblHistorial.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tblHistorial.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblHistorialMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblHistorial);

        jLabel6.setText("Tratamiento:");

        btnGuardar.setText("GUARDAR");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 595, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 587, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 595, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(txtTratamiento, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(txtDiagnostico, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addContainerGap(44, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtDni, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnVer, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(93, 93, 93))))
            .addGroup(layout.createSequentialGroup()
                .addGap(276, 276, 276)
                .addComponent(btnGuardar)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtDni, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnVer))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3)))
                .addGap(12, 12, 12)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel5)
                .addGap(2, 2, 2)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtDiagnostico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtTratamiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btnGuardar)
                .addContainerGap(30, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnVerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerActionPerformed
        verHistorial();
    }//GEN-LAST:event_btnVerActionPerformed

    private void tblPacientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPacientesMouseClicked
        int fila = tblPacientes.getSelectedRow();
        if (fila < 0) {
            return;
        }
        txtDni.setText((String) modeloPacientes.getValueAt(fila, 0));
        verHistorial();
    }//GEN-LAST:event_tblPacientesMouseClicked

    private void tblHistorialMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHistorialMouseClicked
        int fila = tblHistorial.getSelectedRow();
        if (fila < 0 || fila >= mostrados.size()) {
            return;
        }
        HistorialClinico h = mostrados.get(fila);

        txtDiagnostico.setText(h.getDiagnostico());
        txtTratamiento.setText(h.getTratamiento());
        habilitarEdicion(esPropio(h));          // si no es suyo, queda solo lectura (RN-11)

        modeloRecetas.setRowCount(0);
        if (h.getCita() != null) {
            for (Receta r : sistema.recetaDAO.listarPorCita(h.getCita())) {
                modeloRecetas.addRow(new Object[]{r.getMedicamento(), r.getIndicaciones()});
            }
        }
    }//GEN-LAST:event_tblHistorialMouseClicked

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        int fila = tblHistorial.getSelectedRow();
        if (fila < 0 || fila >= mostrados.size()) {
            JOptionPane.showMessageDialog(this, "Seleccione un registro de la tabla de historial.");
            return;
        }
        HistorialClinico h = mostrados.get(fila);
        if (!esPropio(h)) {                                                  // RN-11
            JOptionPane.showMessageDialog(this, "Solo puede editar los registros de sus propias atenciones.");
            return;
        }
        String diagnostico = txtDiagnostico.getText().trim();
        String tratamiento = txtTratamiento.getText().trim();
        if (diagnostico.isEmpty() || tratamiento.isEmpty()) {                // RN-10
            JOptionPane.showMessageDialog(this, "El diagnóstico y el tratamiento son obligatorios.");
            return;
        }
// se modifica el MISMO objeto con sus setters y luego se llama a actualizar()
        h.setDiagnostico(diagnostico);
        h.setTratamiento(tratamiento);
        sistema.historialDAO.actualizar(h);

        JOptionPane.showMessageDialog(this, "Registro actualizado.");
        verHistorial();
    }//GEN-LAST:event_btnGuardarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnVer;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable tblHistorial;
    private javax.swing.JTable tblPacientes;
    private javax.swing.JTable tblRecetas;
    private javax.swing.JTextField txtDiagnostico;
    private javax.swing.JTextField txtDni;
    private javax.swing.JTextField txtTratamiento;
    // End of variables declaration//GEN-END:variables
}
