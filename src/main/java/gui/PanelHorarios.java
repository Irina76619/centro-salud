package gui;

import app.Sistema;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.Horario;
import modelo.Medico;

public class PanelHorarios extends javax.swing.JPanel {

    private Sistema sistema;
    private DefaultTableModel modelo;
    private static final DateTimeFormatter HORA = DateTimeFormatter.ofPattern("HH:mm");

    public PanelHorarios(Sistema sistema) {
        initComponents();
        this.sistema = sistema;

        modelo = new DefaultTableModel(
                new String[]{"ID", "Médico", "Día", "Inicio", "Fin"}, 0) {
            @Override
            public boolean isCellEditable(int fila, int col) {
                return false;
            }
        };
        tblHorarios.setModel(modelo);
        recargar();
    }

    /**
     * Vuelve a leer médicos y horarios (se llama al abrir la pestaña).
     */
    public void recargar() {
        cmbMedico.removeAllItems();
        for (Medico m : sistema.medicoDAO.listar()) {
            cmbMedico.addItem(m);
        }
        cargarTabla();
    }
    

    
    private void cargarTabla() {
        modelo.setRowCount(0);
        for (Horario h : sistema.horarioDAO.listar()) {
            modelo.addRow(new Object[]{h.getId(), h.getMedico().getNombreCompleto(),
                h.getDiaSemana(), h.getHoraInicio(), h.getHoraFin()});
        }
    }

    private void limpiar() {
        txtInicio.setText("");
        txtFin.setText("");
        cmbMedico.setEnabled(true);
        tblHorarios.clearSelection();
        recargar();
    }

    /**
     * Devuelve {inicio, fin} validados, o null si hay error (RN-08).
     */
    private LocalTime[] leerHoras() {
        try {
            LocalTime inicio = LocalTime.parse(txtInicio.getText().trim(), HORA);
            LocalTime fin = LocalTime.parse(txtFin.getText().trim(), HORA);
            if (!fin.isAfter(inicio)) {
                JOptionPane.showMessageDialog(this,
                        "La hora de fin debe ser posterior a la de inicio.");
                return null;
            }
            return new LocalTime[]{inicio, fin};
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(this, "Hora inválida. Use el formato HH:mm (ej. 09:30).");
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        cmbMedico = new javax.swing.JComboBox<>();
        cmbDia = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtInicio = new javax.swing.JTextField();
        txtFin = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblHorarios = new javax.swing.JTable();
        btnRegistrar = new javax.swing.JButton();
        btnActualizar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();

        cmbDia.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sabado", "Domingo" }));

        jLabel1.setText("INICIO");

        jLabel2.setText("FIN");

        tblHorarios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tblHorarios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblHorariosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblHorarios);

        btnRegistrar.setText("REGISTRAR");
        btnRegistrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarActionPerformed(evt);
            }
        });

        btnActualizar.setText("ACTUALIZAR");
        btnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarActionPerformed(evt);
            }
        });

        btnEliminar.setText("ELIMINAR");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        btnLimpiar.setText("LIMPIAR");
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 629, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(198, 198, 198)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(90, 90, 90)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cmbDia, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cmbMedico, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtFin)
                                    .addComponent(txtInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(52, 52, 52)
                        .addComponent(btnRegistrar, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(34, 34, 34)
                        .addComponent(btnActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(39, 39, 39)
                        .addComponent(btnLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(34, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(cmbMedico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbDia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtFin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnEliminar)
                        .addComponent(btnLimpiar)
                        .addComponent(btnActualizar))
                    .addComponent(btnRegistrar))
                .addContainerGap(21, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        int fila = tblHorarios.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un horario de la tabla.");
            return;
        }
        int r = JOptionPane.showConfirmDialog(this, "¿Eliminar este horario?",
                "Confirmar", JOptionPane.YES_NO_OPTION);
        if (r == JOptionPane.YES_OPTION) {
            sistema.horarioDAO.eliminar((int) modelo.getValueAt(fila, 0));
            limpiar();
        }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnRegistrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarActionPerformed
        Medico m = (Medico) cmbMedico.getSelectedItem();
        if (m == null) {
            JOptionPane.showMessageDialog(this, "Registre primero un médico.");
            return;
        }
        LocalTime[] horas = leerHoras();
        if (horas == null) {
            return;
        }

        String dia = (String) cmbDia.getSelectedItem();
        // No se permite un horario que se cruce con otro del mismo médico.
        if (sistema.horarioDAO.hayCruce(m, dia, horas[0], horas[1], 0)) {
            JOptionPane.showMessageDialog(this, "El médico ya tiene un horario que se cruza ese día.");
            return;
        }
        if (sistema.horarioDAO.insertar(new Horario(dia, horas[0], horas[1], m))) {
            JOptionPane.showMessageDialog(this, "Horario registrado.");
            limpiar();
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo registrar el horario.");
        }
    }//GEN-LAST:event_btnRegistrarActionPerformed

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        int fila = tblHorarios.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un horario de la tabla.");
            return;
        }
        LocalTime[] horas = leerHoras();
        if (horas == null) {
            return;
        }

        Horario h = sistema.horarioDAO.buscar((int) modelo.getValueAt(fila, 0));
        String dia = (String) cmbDia.getSelectedItem();
        // Se ignora el propio horario al buscar cruces.
        if (sistema.horarioDAO.hayCruce(h.getMedico(), dia, horas[0], horas[1], h.getId())) {
            JOptionPane.showMessageDialog(this, "El médico ya tiene un horario que se cruza ese día.");
            return;
        }
        h.setDiaSemana(dia);
        h.setHoraInicio(horas[0]);
        h.setHoraFin(horas[1]);
        if (sistema.horarioDAO.actualizar(h)) {
            JOptionPane.showMessageDialog(this, "Horario actualizado.");
            limpiar();
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo actualizar el horario.");
        }
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        limpiar();
    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void tblHorariosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHorariosMouseClicked
        int fila = tblHorarios.getSelectedRow();
        if (fila < 0) {
            return;
        }
        Horario h = sistema.horarioDAO.buscar((int) modelo.getValueAt(fila, 0));
        cmbDia.setSelectedItem(h.getDiaSemana());
        txtInicio.setText(h.getHoraInicio().format(HORA));
        txtFin.setText(h.getHoraFin().format(HORA));
        // seleccionar el médico en el combo y bloquearlo (no se cambia al editar)
        for (int i = 0; i < cmbMedico.getItemCount(); i++) {
            if (cmbMedico.getItemAt(i).getCodigoMedico()
                    .equals(h.getMedico().getCodigoMedico())) {
                cmbMedico.setSelectedIndex(i);
                break;
            }
        }
        cmbMedico.setEnabled(false);
    }//GEN-LAST:event_tblHorariosMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JButton btnRegistrar;
    private javax.swing.JComboBox<String> cmbDia;
    private javax.swing.JComboBox<Medico> cmbMedico;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblHorarios;
    private javax.swing.JTextField txtFin;
    private javax.swing.JTextField txtInicio;
    // End of variables declaration//GEN-END:variables
}
