/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package gui;

import app.Sistema;
import modelo.Medico;

public class MenuMedicoFrame extends javax.swing.JFrame {

    private Sistema sistema;
    private Medico medico;

    public MenuMedicoFrame(Sistema sistema, Medico medico) {
        initComponents();
        this.sistema = sistema;
        this.medico = medico;

        setTitle("MediCare Java - Médico");
        lblBienvenida.setText("Bienvenido(a) Dr(a). " + medico.getNombreCompleto()
                + "  |  Especialidad: " + medico.getEspecialidad().getNombre());
        pack();
        setLocationRelativeTo(null);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblBienvenida = new javax.swing.JLabel();
        btnMisCitas = new javax.swing.JButton();
        btnMiHorario = new javax.swing.JButton();
        btnHistorial = new javax.swing.JButton();
        btnCerrarSesion = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lblBienvenida.setText("BIENVENIDO");

        btnMisCitas.setText("Mis citas");
        btnMisCitas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMisCitasActionPerformed(evt);
            }
        });

        btnMiHorario.setText("Mi horario");
        btnMiHorario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMiHorarioActionPerformed(evt);
            }
        });

        btnHistorial.setText("Mi historial");
        btnHistorial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHistorialActionPerformed(evt);
            }
        });

        btnCerrarSesion.setText("Cerrar sesión");
        btnCerrarSesion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarSesionActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(105, 105, 105)
                        .addComponent(lblBienvenida, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(190, 190, 190)
                        .addComponent(btnCerrarSesion, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(205, 205, 205)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnMisCitas, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnMiHorario)
                            .addComponent(btnHistorial))))
                .addContainerGap(104, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(lblBienvenida)
                .addGap(43, 43, 43)
                .addComponent(btnMisCitas)
                .addGap(31, 31, 31)
                .addComponent(btnMiHorario)
                .addGap(37, 37, 37)
                .addComponent(btnHistorial)
                .addGap(35, 35, 35)
                .addComponent(btnCerrarSesion)
                .addContainerGap(107, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnMisCitasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMisCitasActionPerformed
        new VentanaMisCitas(sistema, medico).setVisible(true);
    }//GEN-LAST:event_btnMisCitasActionPerformed

    private void btnMiHorarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMiHorarioActionPerformed
        new VentanaMiHorario(sistema, medico).setVisible(true);   
    }//GEN-LAST:event_btnMiHorarioActionPerformed

    private void btnHistorialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHistorialActionPerformed
        new VentanaHistorial(sistema, medico).setVisible(true); 
    }//GEN-LAST:event_btnHistorialActionPerformed

    private void btnCerrarSesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarSesionActionPerformed
        new LoginFrame(sistema).setVisible(true);
        dispose();
    }//GEN-LAST:event_btnCerrarSesionActionPerformed

    /**
     * @param args the command line arguments
     */
  
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCerrarSesion;
    private javax.swing.JButton btnHistorial;
    private javax.swing.JButton btnMiHorario;
    private javax.swing.JButton btnMisCitas;
    private javax.swing.JLabel lblBienvenida;
    // End of variables declaration//GEN-END:variables
}
