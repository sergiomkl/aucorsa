package controller;

import controller.bus.BusController;
import controller.conductor.ConductorController;
import controller.ruta.RutaController;
import controller.lugar.LugarController;
import controller.dao.ConductorDAO;
import models.Conductor;
import view.MainView;
import view.VistaDetallesView;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MainController {
    // ===== CUSTOM CONTROLLERS ===== \\
    private final BusController busController;
    private final ConductorController conductorController;
    private final RutaController rutaController;
    private final LugarController lugarController;
    private final MainView mainView;
    ConductorDAO  conductorDAO = new ConductorDAO();

    private JTable tablaConductor;

    // ===== CONSTRUCTOR ===== \\
    public MainController(MainView mainView){
        this.mainView = mainView;
        busController = new BusController(mainView.getBusPanel());
        conductorController = new ConductorController(mainView.getConductorPanel());
        rutaController = new RutaController(mainView.getRutaPanel());
        lugarController = new LugarController(mainView.getLugarPanel());
        mainView.getBtnRefresh().addActionListener(e -> refrescarTabla());
        mainView.getBtnDelete().addActionListener(e -> eliminarTabla());
        mainView.getBtnAdd().addActionListener(e -> agregarTabla());
        mainView.getBtnModify().addActionListener(e -> modificarTabla());

        tablaConductor = mainView.getConductorPanel().getTablaVista();

        tablaConductor.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e){
                if (e.getClickCount() == 2){
                    int fila = tablaConductor.getSelectedRow();
                    tablaConductor.getModel().getValueAt(fila, 0).toString();
                    Conductor conductor = new Conductor((int) tablaConductor.getModel().getValueAt(fila, 0),
                            tablaConductor.getModel().getValueAt(fila, 1).toString(),
                            tablaConductor.getModel().getValueAt(fila, 2).toString());

                    new VistaDetallesController(new VistaDetallesView(conductor));
                }
            }
        });



        mainView.addWindowListener(new WindowListener() {

            @Override
            public void windowOpened(WindowEvent e) {}

            @Override
            public void windowClosing(WindowEvent e) {

                // Confirmar el fin de la aplicacion
                int resultado = JOptionPane.showConfirmDialog(
                        mainView,
                        "¿Estás seguro de que te marchas ya?",
                        "¿Seguro?",
                        JOptionPane.YES_NO_OPTION
                );

                if (resultado == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }

            @Override public void windowClosed(WindowEvent e) {}
            @Override public void windowIconified(WindowEvent e) {}
            @Override public void windowDeiconified(WindowEvent e) {}
            @Override public void windowActivated(WindowEvent e) {}
            @Override public void windowDeactivated(WindowEvent e) {}
        });
    }

    private void agregarTabla(){
        int tabSelected = mainView.getPanelTablas().getSelectedIndex();
        String tab = mainView.getPanelTablas().getTitleAt(tabSelected);

        switch (tab) {
            case "Buses":
                busController.agregarBus();
                break;
            case "Conductores":
                conductorController.agregarConductor();
                break;
            case "Rutas":
                rutaController.agregarRuta();
                break;
            case "Lugares":
                lugarController.agregarLugar();
                break;
        }
    }

    private void modificarTabla(){
        int tabSelected = mainView.getPanelTablas().getSelectedIndex();
        String tab = mainView.getPanelTablas().getTitleAt(tabSelected);

        switch (tab) {
            case "Buses":
                busController.modificarBus();
                break;
            case "Conductores":
                conductorController.modificarConductor();
                break;
            case "Rutas":
                rutaController.modificarRuta();
                break;
            case "Lugares":
                lugarController.modificarLugar();
                break;
        }
    }

    private void eliminarTabla(){
        int tabSelected = mainView.getPanelTablas().getSelectedIndex();
        String tab = mainView.getPanelTablas().getTitleAt(tabSelected);

        switch (tab) {
            case "Buses":
                busController.eliminarBus();
                break;
            case "Conductores":
                conductorController.eliminarConductor();
                break;
            case "Rutas":
                rutaController.eliminarRuta();
                break;
            case "Lugares":
                lugarController.eliminarLugar();
                break;
        }
    }

    private void refrescarTabla() {
        // Obtiene el nombre de la pestaña seleccionada actualmente
        int tabSelected = mainView.getPanelTablas().getSelectedIndex();
        String tab = mainView.getPanelTablas().getTitleAt(tabSelected);

        switch (tab) {
            case "Buses":
                busController.cargarBuses();
                break;
            case "Conductores":
                conductorController.cargarConductores();
                break;
            case "Rutas":
                rutaController.cargarRutas();
                break;
            case "Lugares":
                lugarController.cargarLugares();
                break;
        }
    }
}
