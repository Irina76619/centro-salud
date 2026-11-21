package gui;

import app.Sistema;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.Cita;
import modelo.Medico;
import modelo.Paciente;

public class PanelCitas extends javax.swing.JPanel {

    private Sistema sistema;
    private DefaultTableModel modelo;
    private static final DateTimeFormatter FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter HORA = DateTimeFormatter.ofPattern("HH:mm");

    public PanelCitas(Sistema sistema) {
        initComponents();
        this.sistema = sistema;

        modelo = new DefaultTableModel(new String[]{"ID", "Fecha y hora", "Paciente",
            "Médico", "Especialidad", "Motivo", "Estado"}, 0) {
            @Override
            public boolean isCellEditable(int fila, int col) {
                return false;
            }
        };
        tblCitas.setModel(modelo);
        recargar();
    }

    /**
     * Vuelve a leer pacientes, médicos y citas (se llama al abrir la pestaña).
     */
    public void recargar() {
        cmbPaciente.removeAllItems();
        for (Paciente p : sistema.pacienteDAO.listar()) {
            cmbPaciente.addItem(p);
        }
        cmbMedico.removeAllItems();
        for (Medico m : sistema.medicoDAO.listar()) {
            cmbMedico.addItem(m);
        }
        mostrar(sistema.citaDAO.listar());
    }

    private void mostrar(List<Cita> lista) {
        modelo.setRowCount(0);
        for (Cita c : lista) {
            modelo.addRow(new Object[]{c.getId(), c.getFechaHora().format(FECHA).concat(" ")
                .concat(c.getFechaHora().format(HORA)), c.getPaciente().getNombreCompleto(),
                c.getMedico().getNombreCompleto(), c.getMedico().getEspecialidad().getNombre(),
                c.getMotivo(), c.getEstado()});
        }
    }

    private void limpiar() {
        txtFecha.setText("");
        txtHora.setText("");
        txtMotivo.setText("");
        cmbPaciente.setEnabled(true);
        cmbMedico.setEnabled(true);
        tblCitas.clearSelection();
        recargar();
    }

    /**
     * Devuelve la fecha y hora escritas, o null si el formato es incorrecto
     * (RN-08).
     */
    private LocalDateTime leerFechaHora() {
        try {
            LocalDate fecha = LocalDate.parse(txtFecha.getText().trim(), FECHA);
            LocalTime hora = LocalTime.parse(txtHora.getText().trim(), HORA);
            return LocalDateTime.of(fecha, hora);
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(this,
                    "Fecha u hora inválida.\nUse dd/MM/aaaa (ej. 28/10/2026) y HH:mm (ej. 09:30).");
            return null;
        }
    }

    /**
     * Devuelve la cita seleccionada en la tabla, o null.
     */
    private Cita citaSeleccionada() {
        int fila = tblCitas.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione una cita de la tabla.");
            return null;
        }
        return sistema.citaDAO.buscar((int) modelo.getValueAt(fila, 0));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        cmbPaciente = new javax.swing.JComboBox<>();
        cmbMedico = new javax.swing.JComboBox<>();
        txtFecha = new javax.swing.JTextField();
        txtHora = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblCitas = new javax.swing.JTable();
        btnRegistrar = new javax.swing.JToggleButton();
        btnReprogramar = new javax.swing.JToggleButton();
        btnCancelar = new javax.swing.JToggleButton();
        btnEliminar = new javax.swing.JToggleButton();
        btnVerPaciente = new javax.swing.JToggleButton();
        btnLimpiar = new javax.swing.JToggleButton();
        jLabel3 = new javax.swing.JLabel();
        txtMotivo = new javax.swing.JTextField();

        jLabel1.setText("FECHA");

        jLabel2.setText("HORA");

        tblCitas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tblCitas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblCitasMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblCitas);

        btnRegistrar.setText("REGISTRAR");
        btnRegistrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarActionPerformed(evt);
            }
        });

        btnReprogramar.setText("REPROGRAMAR");
        btnReprogramar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReprogramarActionPerformed(evt);
            }
        });

        btnCancelar.setText("CANCELAR");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        btnEliminar.setText("ELIMINAR");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        btnVerPaciente.setText("VER PACIENTE");
        btnVerPaciente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVerPacienteActionPerformed(evt);
            }
        });

        btnLimpiar.setText("LIMPIAR");
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarActionPerformed(evt);
            }
        });

        jLabel3.setText("MOTIVO");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 633, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(17, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(46, 46, 46)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(cmbPaciente, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(cmbMedico, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtHora)
                            .addComponent(txtFecha)
                            .addComponent(txtMotivo, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(63, 63, 63))))
            .addGroup(layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnRegistrar, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnReprogramar, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(93, 93, 93)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnVerPaciente, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(64, 64, 64))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(cmbPaciente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cmbMedico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtHora, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtMotivo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnVerPaciente)
                        .addGap(18, 18, 18)
                        .addComponent(btnLimpiar))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(btnRegistrar)
                            .addGap(18, 18, 18)
                            .addComponent(btnReprogramar))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(btnCancelar)
                            .addGap(18, 18, 18)
                            .addComponent(btnEliminar))))
                .addContainerGap(33, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        Cita c = citaSeleccionada();
        if (c == null) {
            return;
        }
        if (!c.getEstado().equals("PENDIENTE")) {                     // RN-09
            JOptionPane.showMessageDialog(this, "Solo se pueden cancelar citas PENDIENTES.");
            return;
        }
        int r = JOptionPane.showConfirmDialog(this, "¿Cancelar la cita " + c.getId() + "?",
                "Confirmar", JOptionPane.YES_NO_OPTION);
        if (r == JOptionPane.YES_OPTION) {
            c.setEstado("CANCELADA");
            sistema.citaDAO.actualizar(c);
            limpiar();
        }
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        limpiar();
    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void btnRegistrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarActionPerformed
        Paciente p = (Paciente) cmbPaciente.getSelectedItem();
        Medico m = (Medico) cmbMedico.getSelectedItem();
        String motivo = txtMotivo.getText().trim();
        if (p == null) {                                              // RN-05
            JOptionPane.showMessageDialog(this, "Registre primero al paciente.");
            return;
        }
        if (m == null) {
            JOptionPane.showMessageDialog(this, "Registre primero un médico.");
            return;
        }
        if (motivo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese el motivo de la cita.");
            return;
        }
        LocalDateTime fh = leerFechaHora();
        if (fh == null) {
            return;
        }

        if (sistema.citaDAO.insertar(new Cita(fh, motivo, "PENDIENTE", p, m))) {
            JOptionPane.showMessageDialog(this, "Cita registrada.");
            limpiar();
        } else {                                                      // RN-07
            JOptionPane.showMessageDialog(this,
                    "El médico ya tiene una cita a esa fecha y hora.");
        }
    }//GEN-LAST:event_btnRegistrarActionPerformed

    private void btnReprogramarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReprogramarActionPerformed
        Cita c = citaSeleccionada();
        if (c == null) {
            return;
        }
        if (!c.getEstado().equals("PENDIENTE")) {                     // RN-09
            JOptionPane.showMessageDialog(this, "Solo se pueden reprogramar citas PENDIENTES.");
            return;
        }
        String motivo = txtMotivo.getText().trim();
        if (motivo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese el motivo de la cita.");
            return;
        }
        LocalDateTime nueva = leerFechaHora();
        if (nueva == null) {
            return;
        }

        LocalDateTime anterior = c.getFechaHora();
        String motivoAnterior = c.getMotivo();
        c.setFechaHora(nueva);
        c.setMotivo(motivo);                       // antes de actualizar, para que se guarde
        if (sistema.citaDAO.actualizar(c)) {
            JOptionPane.showMessageDialog(this, "Cita reprogramada.");
            limpiar();
        } else {
            c.setFechaHora(anterior);              // deshace los cambios
            c.setMotivo(motivoAnterior);
            JOptionPane.showMessageDialog(this,
                    "El médico ya tiene una cita a esa fecha y hora.");
        }
    }//GEN-LAST:event_btnReprogramarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        Cita c = citaSeleccionada();
        if (c == null) {
            return;
        }
        if (c.getEstado().equals("ATENDIDA")) {                       // RN-11
            JOptionPane.showMessageDialog(this, "No se puede eliminar una cita ATENDIDA.");
            return;
        }
        int r = JOptionPane.showConfirmDialog(this, "¿Eliminar la cita " + c.getId() + "?",
                "Confirmar", JOptionPane.YES_NO_OPTION);
        if (r == JOptionPane.YES_OPTION) {
            sistema.citaDAO.eliminar(c.getId());
            limpiar();
        }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnVerPacienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerPacienteActionPerformed
        Paciente p = (Paciente) cmbPaciente.getSelectedItem();
        if (p == null) {
            return;
        }
        mostrar(sistema.citaDAO.listarPorPaciente(p));
    }//GEN-LAST:event_btnVerPacienteActionPerformed

    private void tblCitasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblCitasMouseClicked
        int fila = tblCitas.getSelectedRow();
        if (fila < 0) {
            return;
        }
        Cita c = sistema.citaDAO.buscar((int) modelo.getValueAt(fila, 0));

        txtFecha.setText(c.getFechaHora().format(FECHA));
        txtHora.setText(c.getFechaHora().format(HORA));
        txtMotivo.setText(c.getMotivo());

        for (int i = 0; i < cmbPaciente.getItemCount(); i++) {
            if (cmbPaciente.getItemAt(i).getDni().equals(c.getPaciente().getDni())) {
                cmbPaciente.setSelectedIndex(i);
                break;
            }
        }
        for (int i = 0; i < cmbMedico.getItemCount(); i++) {
            if (cmbMedico.getItemAt(i).getCodigoMedico()
                    .equals(c.getMedico().getCodigoMedico())) {
                cmbMedico.setSelectedIndex(i);
                break;
            }
        }
        cmbPaciente.setEnabled(false);   // al reprogramar no se cambian paciente ni médico
        cmbMedico.setEnabled(false);
    }//GEN-LAST:event_tblCitasMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton btnCancelar;
    private javax.swing.JToggleButton btnEliminar;
    private javax.swing.JToggleButton btnLimpiar;
    private javax.swing.JToggleButton btnRegistrar;
    private javax.swing.JToggleButton btnReprogramar;
    private javax.swing.JToggleButton btnVerPaciente;
    private javax.swing.JComboBox<Medico> cmbMedico;
    private javax.swing.JComboBox<Paciente> cmbPaciente;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblCitas;
    private javax.swing.JTextField txtFecha;
    private javax.swing.JTextField txtHora;
    private javax.swing.JTextField txtMotivo;
    // End of variables declaration//GEN-END:variables
}
