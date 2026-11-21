/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package gui;

import app.Sistema;
import modelo.Administrativo;

public class MenuAdminFrame extends javax.swing.JFrame {

    private Sistema sistema;
    private Administrativo admin;
    private PanelMedicos panelMedicos;
    private PanelHorarios panelHorarios;
    private PanelCitas panelCitas;
    private PanelReportes panelReportes;
    
    public MenuAdminFrame(Sistema sistema, Administrativo admin) {
        initComponents();
        this.sistema = sistema;
        this.admin = admin;

        setTitle("MediCare Java - Administrativo");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        lblBienvenida.setText("Bienvenido(a): " + admin.getNombreCompleto());

        cargarPestanas();

        // Integrantes del grupo (pedido en el punto b de la guía)
        javax.swing.JLabel lblIntegrantes = new javax.swing.JLabel(
                "Integrantes: David Cervantes 1  |  Irina Cortez 2  |  Leo Martinez 3",
                javax.swing.SwingConstants.CENTER);
        lblIntegrantes.setBorder(javax.swing.BorderFactory.createEmptyBorder(6, 0, 6, 0));
        getContentPane().add(lblIntegrantes, java.awt.BorderLayout.PAGE_END);
    }

    private void cargarPestanas() {
    panelMedicos = new PanelMedicos(sistema);
    panelHorarios = new PanelHorarios(sistema);
    panelCitas = new PanelCitas(sistema);
    panelReportes = new PanelReportes(sistema);

    tabs.addTab("Pacientes", new PanelPacientes(sistema));
    tabs.addTab("Médicos", panelMedicos);
    tabs.addTab("Especialidades", new PanelEspecialidades(sistema));
    tabs.addTab("Horarios", panelHorarios);
    tabs.addTab("Citas", panelCitas);
    tabs.addTab("Reportes", panelReportes);

    tabs.addChangeListener(e -> {
        if (tabs.getSelectedComponent() == panelMedicos) panelMedicos.recargar();
        if (tabs.getSelectedComponent() == panelHorarios) panelHorarios.recargar();
        if (tabs.getSelectedComponent() == panelCitas) panelCitas.recargar();
        if (tabs.getSelectedComponent() == panelReportes) panelReportes.recargar();
    });
}

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jpanel = new javax.swing.JPanel();
        lblBienvenida = new javax.swing.JLabel();
        btnCerrarSesion = new javax.swing.JButton();
        tabs = new javax.swing.JTabbedPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lblBienvenida.setText("BIENVENIDO");

        btnCerrarSesion.setText("Cerrar sesión");
        btnCerrarSesion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarSesionActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpanelLayout = new javax.swing.GroupLayout(jpanel);
        jpanel.setLayout(jpanelLayout);
        jpanelLayout.setHorizontalGroup(
            jpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpanelLayout.createSequentialGroup()
                .addGap(339, 339, 339)
                .addGroup(jpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblBienvenida, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCerrarSesion))
                .addContainerGap(353, Short.MAX_VALUE))
        );
        jpanelLayout.setVerticalGroup(
            jpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpanelLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(lblBienvenida)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCerrarSesion)
                .addContainerGap(34, Short.MAX_VALUE))
        );

        getContentPane().add(jpanel, java.awt.BorderLayout.PAGE_START);
        getContentPane().add(tabs, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCerrarSesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarSesionActionPerformed
        new LoginFrame(sistema).setVisible(true);
        dispose();
    }//GEN-LAST:event_btnCerrarSesionActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCerrarSesion;
    private javax.swing.JPanel jpanel;
    private javax.swing.JLabel lblBienvenida;
    private javax.swing.JTabbedPane tabs;
    // End of variables declaration//GEN-END:variables
}
