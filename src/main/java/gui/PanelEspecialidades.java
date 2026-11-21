package gui;

import app.Sistema;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.Especialidad;
import modelo.Medico;

public class PanelEspecialidades extends javax.swing.JPanel {

    private Sistema sistema;
    private DefaultTableModel modelo;

    public PanelEspecialidades(Sistema sistema) {
        initComponents();
        this.sistema = sistema;

        modelo = new DefaultTableModel(new String[]{"ID", "Nombre"}, 0) {
            @Override
            public boolean isCellEditable(int fila, int col) { return false; }
        };
        tblEspecialidades.setModel(modelo);
        cargarTabla();
    }

    private void cargarTabla() {
        modelo.setRowCount(0);
        for (Especialidad e : sistema.especialidadDAO.listar()) {
            modelo.addRow(new Object[]{e.getId(), e.getNombre()});
        }
    }

    private void limpiar() {
        txtNombre.setText("");
        tblEspecialidades.clearSelection();
        cargarTabla();
    }

   
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtNombre = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblEspecialidades = new javax.swing.JTable();
        btnRegistrar = new javax.swing.JButton();
        btnActualizar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();

        jLabel1.setText("Nombre");

        tblEspecialidades.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tblEspecialidades.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblEspecialidadesMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblEspecialidades);

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
                        .addGap(31, 31, 31)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(38, 38, 38)
                        .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 480, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnRegistrar, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(47, 47, 47)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnRegistrar)
                    .addComponent(btnActualizar)
                    .addComponent(btnEliminar)
                    .addComponent(btnLimpiar))
                .addContainerGap(39, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnRegistrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarActionPerformed
     String nombre = txtNombre.getText().trim();
        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese el nombre de la especialidad.");
            return;
        }
        if (sistema.especialidadDAO.insertar(new Especialidad(nombre))) {
            JOptionPane.showMessageDialog(this, "Especialidad registrada.");
            limpiar();
        } else {
            JOptionPane.showMessageDialog(this, "Esa especialidad ya existe.");
        }
    }//GEN-LAST:event_btnRegistrarActionPerformed

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
       int fila = tblEspecialidades.getSelectedRow();
        String nombre = txtNombre.getText().trim();
        if (fila < 0 || nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Seleccione una fila e ingrese el nuevo nombre.");
            return;
        }
        Especialidad nueva = new Especialidad(nombre);
        nueva.setId((int) modelo.getValueAt(fila, 0));
        if (sistema.especialidadDAO.actualizar(nueva)) {
            // los médicos deben apuntar al objeto nuevo
            for (Medico m : sistema.medicoDAO.listar()) {
                if (m.getEspecialidad().getId() == nueva.getId()) m.setEspecialidad(nueva);
            }
            JOptionPane.showMessageDialog(this, "Especialidad actualizada.");
            limpiar();
        } else {
            JOptionPane.showMessageDialog(this, "Ya existe otra especialidad con ese nombre.");
        }
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
         int fila = tblEspecialidades.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione una especialidad de la tabla.");
            return;
        }
        int id = (int) modelo.getValueAt(fila, 0);
        for (Medico m : sistema.medicoDAO.listar()) {                 // RN-12
            if (m.getEspecialidad().getId() == id) {
                JOptionPane.showMessageDialog(this, "No se puede eliminar: hay médicos con esa especialidad.");
                return;
            }
        }
        sistema.especialidadDAO.eliminar(id);
        limpiar();
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        limpiar();
    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void tblEspecialidadesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblEspecialidadesMouseClicked
       int fila = tblEspecialidades.getSelectedRow();
        if (fila >= 0) txtNombre.setText(modelo.getValueAt(fila, 1).toString());
    }//GEN-LAST:event_tblEspecialidadesMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JButton btnRegistrar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tblEspecialidades;
    private javax.swing.JTextField txtNombre;
    // End of variables declaration//GEN-END:variables
}
