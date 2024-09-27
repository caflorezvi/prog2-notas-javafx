package co.edu.uniquindio.notas.controladores;

import co.edu.uniquindio.notas.modelo.Nota;
import co.edu.uniquindio.notas.modelo.NotaPrincipal;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import java.net.URL;
import java.util.ResourceBundle;

public class InicioControlador implements Initializable {

    @FXML
    private TextField txtTitulo;

    @FXML
    private ComboBox<String> txtCategoria;

    @FXML
    private TextArea txtNota;

    @FXML
    private DatePicker txtRecordatorio;

    @FXML
    private TableView<Nota> tablaNotas;

    @FXML
    private TableColumn<Nota, String> colTitulo;

    @FXML
    private TableColumn<Nota, String> colCategoria;

    @FXML
    private TableColumn<Nota, String> colTexto;

    @FXML
    private TableColumn<Nota, String> colFecha;
    @FXML
    private TableColumn<Nota, String> colRecordatorio;

    private final NotaPrincipal notaPrincipal; //Instancia de la clase NotaPrincipal

    private Nota notaSeleccionada; //Nota seleccionada de la tabla

    private ObservableList<Nota> notasObservable; //Lista observable de notas

    public InicioControlador() {
        notaPrincipal = new NotaPrincipal();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Asignar las propiedades de la nota a las columnas de la tabla
        colTitulo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitulo()));
        colCategoria.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCategoria()));
        colTexto.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescripcion()));
        colFecha.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFechaCreacion().toString()));
        colRecordatorio.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().obtenerDiferencia()));

        //Cargar categorias en el ComboBox
        txtCategoria.setItems( FXCollections.observableList(notaPrincipal.listarCategorias()) );

        //Inicializar lista observable y cargar las notas
        notasObservable = FXCollections.observableArrayList();
        cargarNotas();

        //Evento click en la tabla
        tablaNotas.setOnMouseClicked(e -> {
            //Obtener la nota seleccionada
            notaSeleccionada = tablaNotas.getSelectionModel().getSelectedItem();

            if(notaSeleccionada != null){
                txtTitulo.setText(notaSeleccionada.getTitulo());
                txtNota.setText(notaSeleccionada.getDescripcion());
                txtCategoria.setValue(notaSeleccionada.getCategoria());
                txtRecordatorio.setValue(notaSeleccionada.getRecordatorio().toLocalDate());
            }

        });
    }

    public void crearNota(ActionEvent e){
        try {
            notaPrincipal.agregarNota(
                    txtTitulo.getText(),
                    txtNota.getText(),
                    txtCategoria.getValue(),
                    txtRecordatorio.getValue()
            );

            limpiarCampos();
            actualizarNotas();
            mostrarAlerta("Nota creada correctamente", Alert.AlertType.INFORMATION);
        }catch (Exception ex){
            mostrarAlerta(ex.getMessage(), Alert.AlertType.ERROR);
        }

    }

    public void eliminarNota(ActionEvent e){

        if(notaSeleccionada != null) {
            try {
                notaPrincipal.eliminarNota(notaSeleccionada.getId());

                limpiarCampos();
                actualizarNotas();
                mostrarAlerta("Nota eliminada correctamente", Alert.AlertType.INFORMATION);
            } catch (Exception ex) {
                mostrarAlerta(ex.getMessage(), Alert.AlertType.ERROR);
            }

        }else{
            mostrarAlerta("Debe seleccionar una nota de la tabla", Alert.AlertType.WARNING);
        }
    }

    public void editarNota(ActionEvent e) {

        if(notaSeleccionada != null) {
            try {
                notaPrincipal.actualizarNota(
                        notaSeleccionada.getId(),
                        txtTitulo.getText(),
                        txtNota.getText(),
                        txtCategoria.getValue(),
                        txtRecordatorio.getValue()
                );

                limpiarCampos();
                actualizarNotas();
                mostrarAlerta("Nota actualizada correctamente", Alert.AlertType.INFORMATION);
            } catch (Exception ex) {
                mostrarAlerta(ex.getMessage(), Alert.AlertType.ERROR);
            }
        }else{
            mostrarAlerta("Debe seleccionar una nota de la tabla", Alert.AlertType.WARNING);
        }
    }

    /**
     * Agrega las notas a la lista observable y las muestra en la tabla
     */
    private void cargarNotas() {
        notasObservable.setAll(notaPrincipal.listarNotas());
        tablaNotas.setItems(notasObservable);
    }

    /**
     * Actualiza la lista observable de notas
     */
    public void actualizarNotas() {
        notasObservable.setAll(notaPrincipal.listarNotas());
    }

    /**
     * Muestra una alerta en pantalla
     * @param mensaje Mensaje a mostrar
     * @param tipo Tipo de alerta
     */
    private void mostrarAlerta(String mensaje, Alert.AlertType tipo){
        Alert alert = new Alert(tipo);
        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.show();
    }

    /**
     * Limpia los campos de texto del formulario
     */
    private void limpiarCampos(){
        txtTitulo.clear();
        txtNota.clear();
        txtCategoria.setValue(null);
        txtRecordatorio.setValue(null);
    }

}
