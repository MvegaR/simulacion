package controlador;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory.IntegerSpinnerValueFactory;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import modelo.ResultadoDataSet;



public class VentanaController implements Initializable{
	
	private final String[] dataSets = {"CACM", "MED", "CRAN", "CISI", "LISA", "ADI", "TIME", "ISWC2015"};
	@FXML
	private TreeView<String> tree;
	@FXML
	private BorderPane panelContenido;
	@FXML
	private TableView<String> tablaDatos;
	@FXML
	private Label dataSetName;
	@FXML
	private ProgressBar cargaLecturaDataSet;
	@FXML
	private Button buttonLeerDataSet;
	@FXML
	private Label labelCantidadDocumentos;
	@FXML
	private Label labelCantidadConsultas;
	@FXML
	private Label labelPalabrasTotalesComunes;
	@FXML
	private Label labelPalabrasTotalesNoComunes;
	@FXML
	private Label labelRelevanciasAcertadas;
	@FXML
	private Spinner<IntegerSpinnerValueFactory> spinnerPin;
	@FXML
	private Button buttonProcesar;
	@FXML
	private Slider sliderIntervalos;
	@FXML
	private Button buttonGenerarF;
	@FXML
	private ProgressBar cargaF;
	@FXML
	private Button buttonVerFuncion;
	@FXML
	private Slider sliderSensibilidad;
	@FXML
	private Button buttonSimular;
	@FXML
	private ProgressBar cargaS;
	@FXML
	private Button buttonVerResultadoS;
	@FXML
	private Label labelRelAcertadas;
	
	private HashMap<String, ResultadoDataSet> mapDataSets;
	

	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		  TreeItem<String> rootItem = new TreeItem<String> ("DataSets", null);
		  for(String s: dataSets){
			  TreeItem<String> itemDataSet = new TreeItem<String>(s, null);
			  rootItem.getChildren().add(itemDataSet);
		  }
		  tree.getSelectionModel().selectedItemProperty().addListener(e->updateData());
		  tree.setRoot(rootItem);
		
	}
	
	/**
	 * Método de control principal para la ventana, usado en el evento de selección de ítem en el árbol
	 * o cualquier situación de actualización de datos de la ventana
	 * Diferencia entre selección de una base de datos para desplegar su resumen
	 * y la selección de una consulta para desplegar el detalle
	 */
	
	private void updateData() {
		System.out.println("Tree update");
		TreeItem<String> selectItem =  tree.getSelectionModel().getSelectedItem();
		TreeItem<String> padre = tree.getSelectionModel().getSelectedItem().getParent();
		if(padre != null && !selectItem.equals("DataSets")){
			System.out.println("Seleccionado data set " + selectItem.getValue());
		}

	}
	
	/**
	 * @return the tree
	 */
	public TreeView<String> getTree() {
		return tree;
	}

	/**
	 * @param tree the tree to set
	 */
	public void setTree(TreeView<String> tree) {
		this.tree = tree;
	}

	/**
	 * @return the panelContenido
	 */
	public BorderPane getPanelContenido() {
		return panelContenido;
	}

	/**
	 * @param panelContenido the panelContenido to set
	 */
	public void setPanelContenido(BorderPane panelContenido) {
		this.panelContenido = panelContenido;
	}

	/**
	 * @return the tablaDatos
	 */
	public TableView<String> getTablaDatos() {
		return tablaDatos;
	}

	/**
	 * @param tablaDatos the tablaDatos to set
	 */
	public void setTablaDatos(TableView<String> tablaDatos) {
		this.tablaDatos = tablaDatos;
	}

	/**
	 * @return the dataSetName
	 */
	public Label getDataSetName() {
		return dataSetName;
	}

	/**
	 * @param dataSetName the dataSetName to set
	 */
	public void setDataSetName(Label dataSetName) {
		this.dataSetName = dataSetName;
	}

	/**
	 * @return the dataSets
	 */
	public String[] getDataSets() {
		return dataSets;
	}

	/**
	 * @return the cargaLecturaDataSet
	 */
	public ProgressBar getCargaLecturaDataSet() {
		return cargaLecturaDataSet;
	}

	/**
	 * @param cargaLecturaDataSet the cargaLecturaDataSet to set
	 */
	public void setCargaLecturaDataSet(ProgressBar cargaLecturaDataSet) {
		this.cargaLecturaDataSet = cargaLecturaDataSet;
	}

	/**
	 * @return the buttonLeerDataSet
	 */
	public Button getButtonLeerDataSet() {
		return buttonLeerDataSet;
	}

	/**
	 * @param buttonLeerDataSet the buttonLeerDataSet to set
	 */
	public void setButtonLeerDataSet(Button buttonLeerDataSet) {
		this.buttonLeerDataSet = buttonLeerDataSet;
	}

	/**
	 * @return the labelCantidadDocumentos
	 */
	public Label getLabelCantidadDocumentos() {
		return labelCantidadDocumentos;
	}

	/**
	 * @param labelCantidadDocumentos the labelCantidadDocumentos to set
	 */
	public void setLabelCantidadDocumentos(Label labelCantidadDocumentos) {
		this.labelCantidadDocumentos = labelCantidadDocumentos;
	}

	/**
	 * @return the labelCantidadConsultas
	 */
	public Label getLabelCantidadConsultas() {
		return labelCantidadConsultas;
	}

	/**
	 * @param labelCantidadConsultas the labelCantidadConsultas to set
	 */
	public void setLabelCantidadConsultas(Label labelCantidadConsultas) {
		this.labelCantidadConsultas = labelCantidadConsultas;
	}

	/**
	 * @return the labelPalabrasTotalesComunes
	 */
	public Label getLabelPalabrasTotalesComunes() {
		return labelPalabrasTotalesComunes;
	}

	/**
	 * @param labelPalabrasTotalesComunes the labelPalabrasTotalesComunes to set
	 */
	public void setLabelPalabrasTotalesComunes(Label labelPalabrasTotalesComunes) {
		this.labelPalabrasTotalesComunes = labelPalabrasTotalesComunes;
	}

	/**
	 * @return the labelPalabrasTotalesNoComunes
	 */
	public Label getLabelPalabrasTotalesNoComunes() {
		return labelPalabrasTotalesNoComunes;
	}

	/**
	 * @param labelPalabrasTotalesNoComunes the labelPalabrasTotalesNoComunes to set
	 */
	public void setLabelPalabrasTotalesNoComunes(Label labelPalabrasTotalesNoComunes) {
		this.labelPalabrasTotalesNoComunes = labelPalabrasTotalesNoComunes;
	}

	/**
	 * @return the labelRelevanciasAcertadas
	 */
	public Label getLabelRelevanciasAcertadas() {
		return labelRelevanciasAcertadas;
	}

	/**
	 * @param labelRelevanciasAcertadas the labelRelevanciasAcertadas to set
	 */
	public void setLabelRelevanciasAcertadas(Label labelRelevanciasAcertadas) {
		this.labelRelevanciasAcertadas = labelRelevanciasAcertadas;
	}

	/**
	 * @return the spinnerPin
	 */
	public Spinner<IntegerSpinnerValueFactory> getSpinnerPin() {
		return spinnerPin;
	}

	/**
	 * @param spinnerPin the spinnerPin to set
	 */
	public void setSpinnerPin(Spinner<IntegerSpinnerValueFactory> spinnerPin) {
		this.spinnerPin = spinnerPin;
	}

	/**
	 * @return the buttonProcesar
	 */
	public Button getButtonProcesar() {
		return buttonProcesar;
	}

	/**
	 * @param buttonProcesar the buttonProcesar to set
	 */
	public void setButtonProcesar(Button buttonProcesar) {
		this.buttonProcesar = buttonProcesar;
	}

	/**
	 * @return the sliderIntervalos
	 */
	public Slider getSliderIntervalos() {
		return sliderIntervalos;
	}

	/**
	 * @param sliderIntervalos the sliderIntervalos to set
	 */
	public void setSliderIntervalos(Slider sliderIntervalos) {
		this.sliderIntervalos = sliderIntervalos;
	}

	/**
	 * @return the buttonGenerarF
	 */
	public Button getButtonGenerarF() {
		return buttonGenerarF;
	}

	/**
	 * @param buttonGenerarF the buttonGenerarF to set
	 */
	public void setButtonGenerarF(Button buttonGenerarF) {
		this.buttonGenerarF = buttonGenerarF;
	}

	/**
	 * @return the cargaF
	 */
	public ProgressBar getCargaF() {
		return cargaF;
	}

	/**
	 * @param cargaF the cargaF to set
	 */
	public void setCargaF(ProgressBar cargaF) {
		this.cargaF = cargaF;
	}

	/**
	 * @return the buttonVerFuncion
	 */
	public Button getButtonVerFuncion() {
		return buttonVerFuncion;
	}

	/**
	 * @param buttonVerFuncion the buttonVerFuncion to set
	 */
	public void setButtonVerFuncion(Button buttonVerFuncion) {
		this.buttonVerFuncion = buttonVerFuncion;
	}

	/**
	 * @return the sliderSensibilidad
	 */
	public Slider getSliderSensibilidad() {
		return sliderSensibilidad;
	}

	/**
	 * @param sliderSensibilidad the sliderSensibilidad to set
	 */
	public void setSliderSensibilidad(Slider sliderSensibilidad) {
		this.sliderSensibilidad = sliderSensibilidad;
	}

	/**
	 * @return the buttonSimular
	 */
	public Button getButtonSimular() {
		return buttonSimular;
	}

	/**
	 * @param buttonSimular the buttonSimular to set
	 */
	public void setButtonSimular(Button buttonSimular) {
		this.buttonSimular = buttonSimular;
	}

	/**
	 * @return the cargaS
	 */
	public ProgressBar getCargaS() {
		return cargaS;
	}

	/**
	 * @param cargaS the cargaS to set
	 */
	public void setCargaS(ProgressBar cargaS) {
		this.cargaS = cargaS;
	}

	/**
	 * @return the buttonVerResultadoS
	 */
	public Button getButtonVerResultadoS() {
		return buttonVerResultadoS;
	}

	/**
	 * @param buttonVerResultadoS the buttonVerResultadoS to set
	 */
	public void setButtonVerResultadoS(Button buttonVerResultadoS) {
		this.buttonVerResultadoS = buttonVerResultadoS;
	}

	/**
	 * @return the labelRelAcertadas
	 */
	public Label getLabelRelAcertadas() {
		return labelRelAcertadas;
	}

	/**
	 * @param labelRelAcertadas the labelRelAcertadas to set
	 */
	public void setLabelRelAcertadas(Label labelRelAcertadas) {
		this.labelRelAcertadas = labelRelAcertadas;
	}

	/**
	 * @return the mapDataSets
	 */
	public HashMap<String, ResultadoDataSet> getMapDataSets() {
		return mapDataSets;
	}

	/**
	 * @param mapDataSets the mapDataSets to set
	 */
	public void setMapDataSets(HashMap<String, ResultadoDataSet> mapDataSets) {
		this.mapDataSets = mapDataSets;
	}

	
	


}
