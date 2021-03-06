package controlador;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.SortedSet;
import java.util.TreeSet;
import javafx.scene.control.TableCell;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory.IntegerSpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import modelo.Consulta;
import modelo.DistributionEquation;
import modelo.Documento;
import modelo.FormatoEcuacion;
import modelo.FormatoSimulacion;
import modelo.Precision;
import modelo.ProbabilisticInterval;
import modelo.Relevancia;
import modelo.ResultadoDataSet;
import modelo.ResultadoDoc;
import modelo.ResultadoQuery;
/**
 * Clase controladora de la ventana principal.
 * Contiene eventos de cada botón y elemento de control y navegación de la gráfica.
 * @author Marcos
 *
 */
@SuppressWarnings("rawtypes")
public class VentanaController implements Initializable{
	/** Array con los nombres de las los data set utilizados en el proyecto */
	private String[] dataSets;
	/** Puntero al controlador del panel de resumen, para reemplazar el panel central cuando se necesite */
	private ResumenQueryController resumenController;
	@FXML
	private TreeView<String> tree;
	@FXML
	private BorderPane panelContenido;
	@FXML
	private BorderPane resumenDataSet;
	@FXML
	private TableView tablaDatos;
	@FXML
	private Label labelDataSetName;
	@FXML
	private ProgressBar cargaLecturaDataSet;
	@FXML
	private Button buttonLeerDataSet;
	@FXML
	private ProgressBar cargaProcesarConsultas;
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
	private ToggleButton toogleButtonVerResultadoS;
	@FXML
	private Label labelRelAcertadas;
	@FXML
	private Label labelRelAcertadasReal;
	@FXML
	private Label labelRelAcertadasValor;
	@FXML
	private Label lavelRelRealValor;
	/*@FXML
    private Button botonBuscar;
	 */
	/** Map para acceder a un resultado de datos dado el nombre del dataSet */
	private HashMap <String, ResultadoDataSet> mapDataSets = new HashMap<>();
	/**  Map para acceder a un resultado de datos de la simulacion dado el nombre del dataSet*/
	private HashMap <String, Simulador> mapSimulador = new HashMap<>();
	/**  Map para acceder a un resultado de datos de la función dado el nombre del dataSet*/
	private HashMap <String, DistributionEquation> mapGetEquation = new HashMap<>();
	/**  Map para acceder a un resultado de documentos dado el nombre del dataSet*/
	private HashMap <String, ArrayList <Documento>> mapDocumentos = new HashMap<>();
	/**  Map para acceder a un resultado de consultas dado el nombre del dataSet*/
	private HashMap <String, ArrayList <Consulta>> mapConsultas = new HashMap<>();
	/**  Map para acceder a un resultado de palabras comunes dado el nombre del dataSet*/
	private HashMap <String, ArrayList <String>> mapPalabrasComunes = new HashMap<>();
	/**  Map para acceder a un resultado de relevancias dado el nombre del dataSet*/
	private HashMap <String, ArrayList <Relevancia>> mapRelevancias = new HashMap<>();
	/**  Map para acceder a un resultado de palabras válidas dado el nombre del dataSet*/
	private HashMap <String, SortedSet <String>> MapSetDePalabras = new HashMap<>();
	/**  Map para recordar puntero de los ítems de consultas de una consulta para poder quitarlos si es necesario*/
	private HashMap <String, ArrayList <TreeItem<String>>> mapHijosConsultas = new HashMap<>();
	/** hilo de generar función */
	private Thread hiloFuncion;
	/** hilo de generar simulación */
	private Thread hiloSimulacion;
	/**
	 * Método que se ejecuta previamente a desplegar la escena gráfica en JavaFX
	 * Contiene la carga inicial y la asignación de eventos a los elementos gráficos.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.cargarDBs();
		TreeItem<String> rootItem = new TreeItem<String> ("DataSets", null);
		for(String s: dataSets){
			ImageView ico = new ImageView(
					new Image(VentanaController.class.getClassLoader()
							.getResource("img/dbicon.png").toExternalForm()));
			ico.setFitWidth(20);
			ico.setFitHeight(20);
			TreeItem<String> itemDataSet = new TreeItem<String>(s, ico);
			rootItem.getChildren().add(itemDataSet);
		}
		tree.getSelectionModel().selectedItemProperty().addListener(e->updateData());
		tree.setRoot(rootItem);
		getButtonLeerDataSet().setDisable(true);
		disableDataSetControl();
		disableFunctionControl();
		disableSimularControl();
		getButtonLeerDataSet().setOnAction(e -> leerDataSet());
		getButtonProcesar().setOnAction(e -> procesarDataSet());
		getSliderIntervalos().valueProperty().addListener(e ->sliderIntervalos());
		getSliderSensibilidad().valueProperty().addListener(e ->
		getButtonSimular().setText("Simular con sensibilidad "+(int)getSliderSensibilidad().getValue()));
		getButtonGenerarF().setOnAction(e -> generarFuncion((int)getSliderIntervalos().getValue()));
		getButtonVerFuncion().setOnAction(e -> tablaFuncion(getLabelDataSetName().getText()));
		getButtonSimular().setOnAction(e -> generarSimulacion((double)getSliderSensibilidad().getValue()));
		getToogleButtonVerResultadoS().setOnAction( e -> cambiarIconoConsultasArbol());
		ImageView icoFuncion = new ImageView(new Image(VentanaController.class.getClassLoader().
				getResource("img/FxIcon.png").toExternalForm()));
		icoFuncion.setFitWidth(25);
		icoFuncion.setFitHeight(25);
		buttonGenerarF.setGraphic(icoFuncion);
		ImageView icoSim = new ImageView(new Image(VentanaController.class.getClassLoader().
				getResource("img/simulationIcon.png").toExternalForm()));
		icoSim.setFitWidth(25);
		icoSim.setFitHeight(25);
		buttonSimular.setGraphic(icoSim);
		ImageView proceIcon = new ImageView(new Image(VentanaController.class.getClassLoader().
				getResource("img/procesarIcon.png").toExternalForm()));
		proceIcon.setFitWidth(25);
		proceIcon.setFitHeight(25);
		getButtonProcesar().setGraphic(proceIcon);
		ImageView leerIcon = new ImageView(new Image(VentanaController.class.getClassLoader().
				getResource("img/leerIcon.png").toExternalForm()));
		leerIcon.setFitWidth(25);
		leerIcon.setFitHeight(25);
		getButtonLeerDataSet().setGraphic(leerIcon);
		ImageView verEcuIcon = new ImageView(new Image(VentanaController.class.getClassLoader().
				getResource("img/verEcuIcon.png").toExternalForm()));
		verEcuIcon.setFitWidth(25);
		verEcuIcon.setFitHeight(25);
		getButtonVerFuncion().setGraphic(verEcuIcon);
		ImageView verSimuIcon = new ImageView(new Image(VentanaController.class.getClassLoader().
				getResource("img/verIcon.png").toExternalForm()));
		verSimuIcon.setFitWidth(25);
		verSimuIcon.setFitHeight(25);
		getToobleButtonVerResultadoS().setGraphic(verSimuIcon);
	}
	/**
	 * Método de evento de slider de intervalos
	 */
	private void sliderIntervalos(){
		getButtonGenerarF().setText("Generar función con "+(int)getSliderIntervalos().getValue()+" intervalos");
		if((int)getSliderIntervalos().getValue() == 0){
			getButtonGenerarF().setDisable(true);
		}else{
			buttonGenerarF.setDisable(false);
		}
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
		TreeItem<String> padre = null;
		if(tree.getSelectionModel() != null && tree.getSelectionModel().getSelectedItem() != null){
			padre = tree.getSelectionModel().getSelectedItem().getParent();
		}
		//Al seleccionar un dataset en el arbol:
		if(padre != null && padre.getValue().equals("DataSets")){
			System.out.println("Seleccionado data set " + selectItem.getValue());
			if(getPanelContenido().getChildren().contains(getResumenController().getResumenDataSet())){
				getPanelContenido().getChildren().remove(getResumenController().getResumenDataSet());
				getPanelContenido().setCenter(getResumenDataSet());
			}
			getLabelDataSetName().setText(selectItem.getValue());
			//mapConsultas,mapDocumentos,mapPalabrascomunes, mapSetdePalabras se trabajan en conjunto
			if(!getMapConsultas().containsKey(selectItem.getValue().toString())){
				disableDataSetControl();
				disableFunctionControl();
				disableSimularControl();
				getButtonLeerDataSet().setDisable(false);
				getLabelCantidadConsultas().setText("0000");
				getLabelCantidadDocumentos().setText("0000");
				getLabelPalabrasTotalesComunes().setText("0000");
				getLabelPalabrasTotalesNoComunes().setText("0000");
				getCargaLecturaDataSet().setProgress(0);
				getCargaProcesarConsultas().setProgress(0.0);
				getCargaF().setProgress(0.0);
				getCargaS().setProgress(0.0);
				getToogleButtonVerResultadoS().setDisable(true);
				getToogleButtonVerResultadoS().setDisable(true);
				getToogleButtonVerResultadoS().setDisable(true);
				getLabelRelAcertadas().setText("Relevancias acertadas en simulación");
				getLabelRelAcertadasReal().setText("Relevancias (true) acertadas en simulación");
				getLabelRelAcertadasValor().setText("??%" );
				getLavelRelRealValor().setText("??%" );
			}else{
				getSpinnerPin().setDisable(false);
				getButtonProcesar().setDisable(false);
				getLabelCantidadConsultas().setText(""+mapConsultas.get(selectItem.getValue()).size());
				getLabelCantidadDocumentos().setText(""+mapDocumentos.get(selectItem.getValue()).size());
				getLabelPalabrasTotalesComunes().setText(""+mapPalabrasComunes.get(selectItem.getValue()).size());
				getLabelPalabrasTotalesNoComunes().setText(""+MapSetDePalabras.get(selectItem.getValue()).size());
				getCargaLecturaDataSet().setProgress(1.0);
				//map dataset
				if(!getMapDataSets().containsKey(selectItem.getValue().toString())){
					getCargaProcesarConsultas().setProgress(0.0);
					getCargaF().setProgress(0.0);
					getCargaS().setProgress(0.0);
					disableFunctionControl();
					disableSimularControl();
					getToogleButtonVerResultadoS().setDisable(true);
					getToogleButtonVerResultadoS().setDisable(true);
					getToogleButtonVerResultadoS().setDisable(true);
					getLabelRelAcertadas().setText("Relevancias acertadas en simulación");
					getLabelRelAcertadasReal().setText("Relevancias (true) acertadas en simulación");
					getLabelRelAcertadasValor().setText("??%" );
					getLavelRelRealValor().setText("??%" );
				}else{
					getCargaProcesarConsultas().setProgress(1.0);
					enableFunctionControl();
					//map función
					if(!getMapGetEquation().containsKey(selectItem.getValue().toString())){
						disableSimularControl();
						getButtonVerFuncion().setDisable(true);
						getCargaF().setProgress(0.0);
						getCargaS().setProgress(0.0);
						getToogleButtonVerResultadoS().setDisable(true);
					}else{
						getButtonVerFuncion().setDisable(false);
						enableSimularControl();
						getCargaF().setProgress(1.0);
						//map simulación
						if(!getMapSimulador().containsKey(selectItem.getValue().toString())){
							getToogleButtonVerResultadoS().setDisable(true);
							getToogleButtonVerResultadoS().setDisable(true);
							getCargaS().setProgress(0);
							getLabelRelAcertadas().setText("Relevancias acertadas en simulación");
							getLabelRelAcertadasReal().setText("Relevancias (true) acertadas en simulación");
							getLabelRelAcertadasValor().setText("??%" );
							getLavelRelRealValor().setText("??%" );
						}else{
							getToogleButtonVerResultadoS().setDisable(false);
							getCargaS().setProgress(1.0);
							getLabelRelAcertadas().setText("Relevancias acertadas en simulación");
							Integer totalAcertado = getMapSimulador().get(selectItem.getValue().toString()).getTotalGlobalAcertados();
							Integer totalFallado= getMapSimulador().get(selectItem.getValue().toString()).getTotalGlobalFallados();
							Integer total = totalFallado+totalAcertado;
							getLabelRelAcertadasValor().setText( (totalAcertado*100)/(total.doubleValue())+"%" );
							getLabelRelAcertadasReal().setText("Relevancias (true) acertadas en simulación");
							Integer totalAcertadoTrue = getMapSimulador().get(getTree().getSelectionModel().getSelectedItem().getValue().toString()).getTotalGlobalRealesYSimulados();
							Integer totalFalladoTrue= getMapSimulador().get(getTree().getSelectionModel().getSelectedItem().getValue().toString()).getTotalRelevantesFallados();
							Integer totalTrue = totalFalladoTrue+totalAcertadoTrue;
							getLavelRelRealValor().setText( (totalAcertadoTrue*100)/(totalTrue.doubleValue())+"%" );
						}
					}
				}
			}
		}else if(padre == null){ //raiz
			if(getPanelContenido().getChildren().contains(getResumenController().getResumenDataSet())){
				getPanelContenido().getChildren().remove(getResumenController().getResumenDataSet());
				getPanelContenido().setCenter(getResumenDataSet());
			}
			getLabelDataSetName().setText(tree.getRoot().getValue());
			disableDataSetControl();
			disableFunctionControl();
			disableSimularControl();
		}else if(padre != null && !padre.getValue().equals("DataSets")){
			System.out.println(padre.getValue());
			if(getToogleButtonVerResultadoS().isSelected() && mapSimulador.containsKey(padre.getValue())){
				cambiarIconoConsultasArbol();
				tablaConsultaSimulada(selectItem, padre.getValue());
				cambiarCentroToResumenQuerySimulada(selectItem, padre);
			}else{
				cambiarIconoConsultasArbol();
				tablaConsulta(selectItem, padre.getValue());
				cambiarCentroToResumenQuery(selectItem, padre);
			}
		}
	}
	/**
	 * Método para actualizar los datos del resumen de una consulta
	 * @param selectItem Contiene el id de la consulta (TreeItem)
	 * @param padre Contiene el nombre de del data set (TreeItem)
	 */
	private void cambiarCentroToResumenQuery(TreeItem<String> selectItem, 
			TreeItem<String> padre) {
		if(getResumenController() != null){
			ResultadoQuery rq = getResultadoQueryById(selectItem.getValue().split(".* ID: ")[1],padre.getValue());
			getResumenController().getLabelDataSetName().setText(
					getTree().getSelectionModel().getSelectedItem().getValue());
			getResumenController().getTexto1().setText("Precisión promedio");
			getResumenController().getTexto2().setText("Recall promedio");
			getResumenController().getTexto3().setText("Total relevantes");
			getResumenController().getTexto4().setText("Total relevantes desplegados");
			getResumenController().getTexto5().setText("");
			getResumenController().getTexto6().setText("");
			getResumenController().getValor1().setText(rq.getPrecisionPromedio().toString());
			getResumenController().getValor2().setText(rq.getRecallPromedio().toString());
			getResumenController().getValor3().setText(rq.getTotalDocRelevantesTotales().toString());
			getResumenController().getValor4().setText(rq.getTotalDocReleventesDesplegados().toString());
			getResumenController().getValor5().setText("");
			getResumenController().getValor6().setText("");
			getResumenController().getTitulo2().setVisible(false);
			getResumenController().getLabelTituloSim().setText("");
			getResumenController().getTexto11().setText("");
			getResumenController().getTexto21().setText("");
			getResumenController().getTexto31().setText("");
			getResumenController().getTexto41().setText("");
			getResumenController().getTexto51().setText("");
			getResumenController().getTexto61().setText("");
			getResumenController().getValor11().setText("");
			getResumenController().getValor21().setText("");
			getResumenController().getValor31().setText("");
			getResumenController().getValor41().setText("");
			getResumenController().getValor51().setText("");
			getResumenController().getValor61().setText("");
			getPanelContenido().getChildren().remove(getResumenDataSet());
			getPanelContenido().setCenter(getResumenController().getResumenDataSet());
		}
	}
	/**
	 * Método para actualizar los datos del resumen de una consulta simulada
	 * @param selectItem Contiene el id de la consulta (TreeItem)
	 * @param padre Contiene el nombre de del data set (TreeItem)
	 */
	private void cambiarCentroToResumenQuerySimulada(TreeItem<String> selectItem, 
			TreeItem<String> padre) {
		if(getResumenController() != null){
			Integer index = 0;
			for(ResultadoQuery rqt: mapDataSets.get(padre.getValue()).getResultadosConsultas()){
				if(rqt.getIdQuery().toString().equals(selectItem.getValue().split(".* ID: ")[1].toString())){
					break;
				}
				index++;
			}
			Simulador simulador = mapSimulador.get(padre.getValue());
			getResumenController().getLabelDataSetName().setText(getTree().getSelectionModel().getSelectedItem().getValue());
			getResumenController().getLabelTituloSim().setText("Simulación " +getLabelDataSetName().getText());
			getResumenController().getTexto1().setText("Total relevantes desplegados originales");
			getResumenController().getTexto2().setText("Total relevantes desplegados simulados");
			getResumenController().getTexto3().setText("Total relevantes simulados y originales");
			getResumenController().getTexto4().setText("Total acertados");
			getResumenController().getTexto5().setText("Total fallados");
			getResumenController().getTexto6().setText("Tolerancia");
			getResumenController().getValor1().setText
			(simulador.getResultadosResumen().get(index).getTotalDesplegadosOriginales().toString());
			getResumenController().getValor2().setText
			(simulador.getResultadosResumen().get(index).getTotalDesplegadosSimulados().toString());
			getResumenController().getValor3().setText
			(simulador.getResultadosResumen().get(index).getTotalSimuladosYOriginales().toString());
			getResumenController().getValor4().setText
			(simulador.getResultadosResumen().get(index).getTotalAcertados().toString());
			getResumenController().getValor5().setText
			(simulador.getResultadosResumen().get(index).getTotalFallados().toString());
			getResumenController().getValor6().setText
			(simulador.getResultadosResumen().get(index).getTolerancia().toString());
			getPanelContenido().setCenter(getResumenController().getResumenDataSet());
			getResumenController().getTitulo2().setVisible(true);
			getResumenController().getLabelTituloSim().setText("Resumen global simulación");
			getResumenController().getTexto11().setText("Total fallados");
			getResumenController().getTexto21().setText("Total acertados");
			getResumenController().getTexto31().setText("Total relevantes fallados");
			getResumenController().getTexto41().setText("Total relevantes acertados");
			getResumenController().getTexto51().setText("Total relevantes \"true\" original");
			getResumenController().getTexto61().setText("Total relevantes \"true\" simulado");
			getResumenController().getValor11().setText(simulador.getTotalGlobalFallados().toString());
			getResumenController().getValor21().setText(simulador.getTotalGlobalAcertados().toString());
			getResumenController().getValor31().setText(simulador.getTotalRelevantesFallados().toString());
			getResumenController().getValor41().setText(simulador.getTotalGlobalRealesYSimulados().toString());
			getResumenController().getValor51().setText(simulador.getTtotalRelevantesDesplegados().toString());
			getResumenController().getValor61().setText(simulador.getTtotalRelevantesSimuladosDesplegados().toString());
			getPanelContenido().getChildren().remove(getResumenDataSet());
			getPanelContenido().setCenter(getResumenController().getResumenDataSet());
		}
	}
	/**
	 * Entrega un resultado de consulta para obtener información del resumen
	 * @param idQ ID de la consulta (string)
	 * @param nombreDB Nombre del data set (string)
	 * @return Resultado {@link ResultadoQuery}
	 */
	private ResultadoQuery getResultadoQueryById(String idQ, String nombreDB){
		for(ResultadoQuery rq: mapDataSets.get(nombreDB).getResultadosConsultas()){
			if(rq.getIdQuery().toString().equals(idQ.toString())){
				return rq;
			}
		}
		return null;
	}
	/**
	 * Método que actualiza la tabla de datos con información detallada de una consulta
	 * @param selectItem Contiene el id de la consulta (TreeItem)
	 * @param nombreDB Contiene el nombre de del data set (TreeItem)
	 */
	@SuppressWarnings("unchecked")
	private void tablaConsulta(TreeItem<String> selectItem, String nombreDB){
		String idConsultaSeleccionada = selectItem.getValue().split(".* ID: ")[1];
		TableColumn<ResultadoDoc, Integer> idConsulta = new TableColumn("ID Consulta");
		idConsulta.setCellValueFactory(new PropertyValueFactory<>("idQuery"));
		TableColumn<ResultadoDoc, Integer> idDocumento = new TableColumn("ID Documento");
		idDocumento.setCellValueFactory(new PropertyValueFactory<>("idDoc"));
		TableColumn<ResultadoDoc, Double> similitud = new TableColumn("Similitud");
		similitud.setCellValueFactory(new PropertyValueFactory<>("disCos"));
		TableColumn<ResultadoDoc, Boolean> isRel = new TableColumn("¿Es relevante?");
		isRel.setCellValueFactory(new PropertyValueFactory<>("isRel"));
		isRel.setCellFactory(column -> {
			return new TableCell<ResultadoDoc, Boolean>() {
				@Override
				protected void updateItem(Boolean item, boolean empty){
					super.updateItem(item, empty);
					if (item == null || empty) {
						setText(null);
						setStyle("");
					} else {
						setText(item.toString());
						if (item.equals(true)) {
							setStyle("-fx-background-color: RGB(179,255,179)");
						}else{
							setStyle("");
						}
					}
				}
			};
		});
		TableColumn<ResultadoDoc, Double> precision = new TableColumn("Precisión");
		precision.setCellValueFactory(new PropertyValueFactory<>("precision"));
		TableColumn<ResultadoDoc, Double> recall = new TableColumn("Recall");
		recall.setCellValueFactory(new PropertyValueFactory<>("recall"));
		TableColumn<ResultadoDoc, Integer> totalPalabras = new TableColumn("Total palabras");
		totalPalabras.setCellValueFactory(new PropertyValueFactory<>("tWords"));
		getTablaDatos().getColumns().clear();
		getTablaDatos().getColumns().addAll(idConsulta, idDocumento, similitud, isRel, precision, recall, totalPalabras);
		getTablaDatos().setItems(getResultadoDocumentoConsulta(idConsultaSeleccionada, nombreDB));
	}
	/**
	 * Método que actualiza la tabla de datos con información detallada de una consulta simulada
	 * @param selectItem Contiene el id de la consulta (TreeItem)
	 * @param nombreDB Contiene el nombre de del data set (TreeItem)
	 */
	@SuppressWarnings("unchecked")
	private void tablaConsultaSimulada(TreeItem<String> selectItem, String nombreDB){
		String idConsultaSeleccionada = selectItem.getValue().split(".* ID: ")[1];
		TableColumn<FormatoSimulacion, Boolean> igual = new TableColumn<FormatoSimulacion, Boolean>("¿Igual?");
		igual.setCellValueFactory(new PropertyValueFactory<>("igual"));
		igual.setCellFactory(column -> {
			return new TableCell<FormatoSimulacion, Boolean>() {
				@Override
				protected void updateItem(Boolean item, boolean empty){
					super.updateItem(item, empty);
					if (item == null || empty) {
						setText(null);
						setStyle("");
					} else {
						setText(item.toString());
						if (item.equals(true)) {
							setStyle("-fx-background-color: RGB(179,255,179)");
						} else {
							setStyle("-fx-background-color: RGB(225,179,179)");
						}
					}
				}
			};
		});
		TableColumn<FormatoSimulacion, Integer> idDocumento = new TableColumn("ID Consulta");
		idDocumento.setCellValueFactory(new PropertyValueFactory<>("idq"));
		TableColumn<FormatoSimulacion, Integer> similitud = new TableColumn("ID Documento");
		similitud.setCellValueFactory(new PropertyValueFactory<>("iddoc"));
		TableColumn<FormatoSimulacion, Double> sim = new TableColumn("Similitud");
		sim.setCellValueFactory(new PropertyValueFactory<>("sim"));
		TableColumn<FormatoSimulacion, Boolean> userRelReal = new TableColumn("Relevancia real");
		userRelReal.setCellValueFactory(new PropertyValueFactory<>("userRelReal"));
		userRelReal.setCellFactory(column -> {
			return new TableCell<FormatoSimulacion, Boolean>() {
				@Override
				protected void updateItem(Boolean item, boolean empty){
					super.updateItem(item, empty);
					if (item == null || empty) {
						setText(null);
						setStyle("");
					} else {
						setText(item.toString());
						if (item.equals(true)) {
							setStyle("-fx-background-color: RGB(255,255,0)");
						}else{
							setStyle("");
						}
					}
				}
			};
		});
		TableColumn<FormatoSimulacion, Boolean> userRelSim = new TableColumn("Relevancia simualda");
		userRelSim.setCellValueFactory(new PropertyValueFactory<>("userRelSim"));
		getTablaDatos().getColumns().clear();
		getTablaDatos().getColumns().addAll(igual, idDocumento, similitud, sim, userRelReal, userRelSim);
		getTablaDatos().setItems(getResultadoDocumentoConsultaSimulada(idConsultaSeleccionada, nombreDB));
	}
	/**
	 * Método que muestra la función en la tabla
	 * @param nombreDB Nombre del data set
	 */
	@SuppressWarnings("unchecked")
	private void tablaFuncion(String nombreDB){
		//System.out.println("Seleccionado consulta "+ selectItem.getValue());
		TableColumn<FormatoEcuacion, Integer> numero = new TableColumn("#");
		numero.setCellValueFactory(new PropertyValueFactory<>("numero"));
		TableColumn<FormatoEcuacion, Double> min = new TableColumn("X min");
		min.setCellValueFactory(new PropertyValueFactory<>("min"));
		TableColumn<FormatoEcuacion, Double> max = new TableColumn("X max");
		max.setCellValueFactory(new PropertyValueFactory<>("max"));
		TableColumn<FormatoEcuacion, Double> valor = new TableColumn("Valor");
		valor.setCellValueFactory(new PropertyValueFactory<>("valor"));
		getTablaDatos().getColumns().clear();
		getTablaDatos().getColumns().addAll(numero, min, max, valor);
		ObservableList<FormatoEcuacion> lista = FXCollections.observableArrayList();
		for(ProbabilisticInterval inte: mapGetEquation.get(nombreDB).getIntervalos()){
			Integer index = mapGetEquation.get(nombreDB).getIntervalos().indexOf(inte);
			FormatoEcuacion forEcu = new FormatoEcuacion(index+1,
					inte.getMin(), inte.getMax(), mapGetEquation.get(nombreDB).getProbabilidades().get(index));
			lista.add(forEcu);
		}
		getTablaDatos().setItems(lista);
	}
	/**
	 * Método que obtiene el resultado detallado de una consulta dado el id de la consulta y el nombre del data set
	 * @param idQ Id de la consulta
	 * @param nombreDB Nombre del data set
	 * @return Resultado detallado de la consulta {@link ResultadoDoc}
	 */
	private ObservableList<ResultadoDoc> getResultadoDocumentoConsulta(String idQ, String nombreDB){
		ObservableList<ResultadoDoc> lista = FXCollections.observableArrayList();
		for(ResultadoQuery rq: mapDataSets.get(nombreDB).getResultadosConsultas()){
			if(rq.getIdQuery().toString().equals(idQ.toString())){
				for(ResultadoDoc rd: rq.getResultadosDocumentos()){
					lista.add(rd);
				}
			}
		}
		return lista;
	}
	/**
	 * Método que obtiene el resultado detallado de una consulta simulada
	 * dado el id de la consulta y el nombre del data set
	 * @param idQ Id de la consulta
	 * @param nombreDB Nombre del data set
	 * @return Resultado detallado de la consulta en una lista ver {@link ResultadoDoc}
	 */
	private ObservableList<FormatoSimulacion> getResultadoDocumentoConsultaSimulada(String idQ, String nombreDB){
		ObservableList<FormatoSimulacion> lista = FXCollections.observableArrayList();
		Integer index = 0;
		for(ResultadoQuery rq: mapDataSets.get(nombreDB).getResultadosConsultas()){
			if(rq.getIdQuery().toString().equals(idQ.toString())){
				break;
			}
			index++;
		}
		for(FormatoSimulacion fs: getMapSimulador().get(nombreDB).getResultados().get(index)){
			lista.add(fs);
		}
		return lista;
	}
	/**
	 * Método de evento para la generación de la función desde la interfaz gráfica
	 * @param intervalos Intervalos de la función
	 */
	private void generarFuncion(int intervalos){
		hiloFuncion = new Thread(new Runnable() {
			@Override
			public void run() {
				getButtonGenerarF().setDisable(true);
				String nombreDB = getLabelDataSetName().getText();
				if(!mapGetEquation.containsKey(nombreDB)){
					mapGetEquation.put(nombreDB,
							new GetEquation(intervalos, mapDataSets.get(nombreDB), getCargaF()).generarEquation());
				}else{
					mapGetEquation.replace(nombreDB, mapGetEquation.get(nombreDB),
							new GetEquation(intervalos, mapDataSets.get(nombreDB), getCargaF()).generarEquation());
				}
				getButtonGenerarF().setDisable(false);
				getButtonVerFuncion().setDisable(false);
				getSliderSensibilidad().setDisable(false);
				getButtonSimular().setDisable(false);
			}
		});
		hiloFuncion.start();
	}
	/**
	 * Método de evento para generar la simulación y sus resultados para mostrarlos en la tabla
	 * @param sensibilidad Sensibilidad seleccionada para la simulación
	 */
	private void generarSimulacion(Double sensibilidad){
		hiloSimulacion = new Thread(new Runnable() {
			@Override
			public void run() {
				String name = getLabelDataSetName().getText();
				Simulador simu = new Simulador(getMapDataSets().get(name), getMapGetEquation().get(name));
				simu.setBar(getCargaS());
				simu.simular(sensibilidad/100.0);
				if(!getMapSimulador().containsKey(getLabelDataSetName().getText())){
					getMapSimulador().put(name, simu);
				}else{
					getMapSimulador().replace(name, getMapSimulador().get(name), simu);
				}
				simu.generarResultados();
				getToobleButtonVerResultadoS().setDisable(false);
				getCargaS().setProgress(1.0);
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						getLabelRelAcertadas().setText("Relevancias acertadas en simulación");
						Integer totalAcertado = getMapSimulador().get(getTree().getSelectionModel().getSelectedItem().getValue().toString()).getTotalGlobalAcertados();
						Integer totalFallado= getMapSimulador().get(getTree().getSelectionModel().getSelectedItem().getValue().toString()).getTotalGlobalFallados();
						Integer total = totalFallado+totalAcertado;
						getLabelRelAcertadasValor().setText( (totalAcertado*100)/(total.doubleValue())+"%" );
						getLabelRelAcertadasReal().setText("Relevancias (true) acertadas en simulación");
						Integer totalAcertadoTrue = getMapSimulador().get(getTree().getSelectionModel().getSelectedItem().getValue().toString()).getTotalGlobalRealesYSimulados();
						Integer totalFalladoTrue= getMapSimulador().get(getTree().getSelectionModel().getSelectedItem().getValue().toString()).getTotalRelevantesFallados();
						Integer totalTrue = totalFalladoTrue+totalAcertadoTrue;
						getLavelRelRealValor().setText( (totalAcertadoTrue*100)/(totalTrue.doubleValue())+"%" );
						cambiarIconoConsultasArbol();
					}
				});
			}
		});
		hiloSimulacion.start();
	}
	/**
	 * Desactiva los botones y otros controles de la sección de simular
	 */
	private void disableSimularControl(){
		getSliderSensibilidad().setDisable(true);
		getButtonSimular().setDisable(true);
		getToobleButtonVerResultadoS().setDisable(true);
	}
	/**
	 * Activa los botones y otros controles de la sección de simular
	 */
	private void enableSimularControl(){
		getSliderSensibilidad().setDisable(false);
		getButtonSimular().setDisable(false);
		getToobleButtonVerResultadoS().setDisable(false);
	}
	/**
	 * Desactiva los botones y otros controles de la sección del dataSet
	 */
	private void disableDataSetControl(){
		getButtonLeerDataSet().setDisable(true);
		getSpinnerPin().setDisable(true);
		getButtonProcesar().setDisable(true);
	}
	/**
	 * Desactiva los botones y otros controles de la sección de la función
	 */
	private void disableFunctionControl(){
		getSliderIntervalos().setDisable(true);
		getButtonGenerarF().setDisable(true);
		getButtonVerFuncion().setDisable(true);
	}
	/**
	 * Activa los botones y otros controles de la sección de la función
	 */
	private void enableFunctionControl(){
		getSliderIntervalos().setDisable(false);
		getButtonGenerarF().setDisable(false);
		getButtonVerFuncion().setDisable(false);
	}
	/**
	 * Método que ejecuta el cálculo de matriz de frecuencia y frecuencia inversa
	 * y carga los resultados de cada consulta del data set seleccionado
	 */
	private void procesarDataSet(){
		Thread hiloProcesar = new Thread(new Runnable() {
			@Override
			public void run() {
				getTree().setDisable(true);
				getButtonProcesar().setDisable(true);
				getButtonLeerDataSet().setDisable(true);
				getButtonGenerarF().setDisable(true);
				String nombreDB = getLabelDataSetName().getText();
				ResultadoDataSet dataSet = new ResultadoDataSet(nombreDB, mapConsultas.get(nombreDB).size(),
						mapDocumentos.get(nombreDB).size(),
						mapPalabrasComunes.get(nombreDB).size(),  MapSetDePalabras.get(nombreDB).size());
				Matrices matriz = new Matrices(MapSetDePalabras.get(nombreDB), mapDocumentos.get(nombreDB),
						mapConsultas.get(nombreDB), mapRelevancias.get(nombreDB), dataSet.getResultadosConsultas());
				ArrayList<Precision> precisiones = new ArrayList<>();
				Double con = 0.0;
				matriz.obtenerFrecuencias(getCargaProcesarConsultas());
				getCargaProcesarConsultas().setProgress(0.66);
				matriz.obtenerFrecuenciasInversas(getCargaProcesarConsultas());
				for(Consulta q: mapConsultas.get(nombreDB)){
					getCargaProcesarConsultas().setProgress( 0.66 + ((con++)/(mapConsultas.get(nombreDB).size()) ) /3  );
					String value = getSpinnerPin().getValue() + "";
					matriz.obtenerPrecision(q, Integer.parseInt(value), precisiones, dataSet, false);
				}
				if(mapDataSets.containsKey(nombreDB)){
					mapDataSets.remove(mapDataSets.get(nombreDB));
					mapDataSets.put(nombreDB, dataSet);
				}else{
					mapDataSets.put(nombreDB, dataSet);
				}
				ArrayList<TreeItem<String>> listaDeHijos = new ArrayList<>();
				if(!mapHijosConsultas.containsKey(nombreDB)){
					mapHijosConsultas.put(nombreDB, listaDeHijos);
					for(ResultadoQuery rq: dataSet.getResultadosConsultas()){
						ImageView ico = new ImageView(new Image(VentanaController.class.getClassLoader().getResource("img/queryIcon.png").toExternalForm()));
						ico.setFitWidth(20);
						ico.setFitHeight(20);
						TreeItem<String> itemQuery = new TreeItem<String>("Query "+nombreDB+" ID: "+rq.getIdQuery().toString(),ico);
						tree.getSelectionModel().getSelectedItem().getChildren().add(itemQuery);
						listaDeHijos.add(itemQuery);
					}
				}
				getCargaProcesarConsultas().setProgress(1.0);
				getTree().setDisable(false);
				enableFunctionControl();
				getButtonProcesar().setDisable(false);
				getButtonLeerDataSet().setDisable(false);
				if(getMapGetEquation().containsKey(nombreDB)){
					getButtonVerFuncion().setDisable(false);
				}else{
					getButtonVerFuncion().setDisable(true);
					getCargaF().setProgress(0);
				}
				if(getMapSimulador().containsKey(nombreDB)){
					getToobleButtonVerResultadoS().setDisable(false);
				}else{
					getToobleButtonVerResultadoS().setDisable(true);
					getCargaS().setProgress(0);
				}
			}
		});
		hiloProcesar.start();
	}
	/**
	 * Método que realiza la lectura del data set seleccionado
	 */
	private void leerDataSet(){
		Thread hiloLeer = new Thread(new Runnable() {
			@Override
			public void run() {
				getTree().setDisable(true);
				getButtonLeerDataSet().setDisable(true);
				String fsp = System.getProperty("file.separator").toString();
				//palabras comunes
				File palabrasComunesFile = new File("datasets"+fsp+"palabras_comunes");
				ArrayList<File> documentosFiles = null;
				File documentosFile = null;
				File consultasFile = null;
				File relevanciasFile = null;
				String nombreDB = getLabelDataSetName().getText();
				if(getLabelDataSetName().getText().equals("CACM")){
					documentosFile = new File("datasets"+fsp+"cacm"+fsp+"cacm.all");
					consultasFile= new File("datasets"+fsp+"cacm"+fsp+"query.text");
					relevanciasFile = new File("datasets"+fsp+"cacm"+fsp+"qrels.text");
				}else if(getLabelDataSetName().getText().equals("MED")){
					documentosFile= new File("datasets"+fsp+"med"+fsp+"MED.ALL");
					consultasFile= new File("datasets"+fsp+"med"+fsp+"MED.QRY");
					relevanciasFile= new File("datasets"+fsp+"med"+fsp+"MED.REL");
				}else if(getLabelDataSetName().getText().equals("CRAN")){
					documentosFile= new File("datasets"+fsp+"cran"+fsp+"cran.all.1400");
					consultasFile= new File("datasets"+fsp+"cran"+fsp+"cran.qry");
					relevanciasFile= new File("datasets"+fsp+"cran"+fsp+"cranFix.rel");
				}else if(getLabelDataSetName().getText().equals("CISI")){
					documentosFile= new File("datasets"+fsp+"cisi"+fsp+"CISI.all");
					consultasFile= new File("datasets"+fsp+"cisi"+fsp+"CISI.qry");
					relevanciasFile= new File("datasets"+fsp+"cisi"+fsp+"CISI.rel");
				}else if(getLabelDataSetName().getText().equals("LISA")){
					documentosFiles= new ArrayList<>();
					documentosFiles.add(new File("datasets"+fsp+"lisa"+fsp+"LISA0.501"));
					documentosFiles.add(new File("datasets"+fsp+"lisa"+fsp+"LISA1.501"));
					documentosFiles.add(new File("datasets"+fsp+"lisa"+fsp+"LISA2.501"));
					documentosFiles.add(new File("datasets"+fsp+"lisa"+fsp+"LISA3.501"));
					documentosFiles.add(new File("datasets"+fsp+"lisa"+fsp+"LISA4.501"));
					documentosFiles.add(new File("datasets"+fsp+"lisa"+fsp+"LISA5.501"));
					documentosFiles.add(new File("datasets"+fsp+"lisa"+fsp+"LISA5.627"));
					documentosFiles.add(new File("datasets"+fsp+"lisa"+fsp+"LISA5.850"));
					consultasFile= new File("datasets"+fsp+"lisa"+fsp+"LISA.QUE");
					relevanciasFile= new File("datasets"+fsp+"lisa"+fsp+"LISA.REL");
				}else if(getLabelDataSetName().getText().equals("ADI")){
					documentosFile= new File("datasets"+fsp+"adi"+fsp+"ADI.ALL");
					consultasFile= new File("datasets"+fsp+"adi"+fsp+"ADI.QRY");
					relevanciasFile= new File("datasets"+fsp+"adi"+fsp+"ADI.REL");
				}else if(getLabelDataSetName().getText().equals("TIME")){
					documentosFile= new File("datasets"+fsp+"time"+fsp+"TIME.ALL");
					consultasFile= new File("datasets"+fsp+"time"+fsp+"TIME.QUE");
					relevanciasFile= new File("datasets"+fsp+"time"+fsp+"TIME.REL");
				}else if(getLabelDataSetName().getText().equals("ISWC2015")){
					documentosFile = new File("datasets"+fsp+"iswc2015"+fsp+"docs.txt");
					consultasFile= new File("datasets"+fsp+"iswc2015"+fsp+"qrys.txt");
					relevanciasFile = new File("datasets"+fsp+"iswc2015"+fsp+"rel.txt");
				}else if(getLabelDataSetName().getText().length() > 0){
					documentosFile = new File("datasets"+fsp+getLabelDataSetName().getText().toLowerCase()+fsp+"docs.txt");
					consultasFile= new File("datasets"+fsp+getLabelDataSetName().getText().toLowerCase()+fsp+"qrs.txt");
					relevanciasFile = new File("datasets"+fsp+getLabelDataSetName().getText().toLowerCase()+fsp+"rel.txt");
				}else{
					getCargaLecturaDataSet().setProgress(1);
					getSpinnerPin().setDisable(false);
					getButtonProcesar().setDisable(false);
					getTree().setDisable(false);
					getButtonLeerDataSet().setDisable(false);
					return;
					
				}
				getCargaLecturaDataSet().setProgress(0);
				ArrayList<Documento> documentos = new ArrayList<>();
				ArrayList<Consulta> consultas = new ArrayList<>();
				ArrayList<String> palabrasComunes = new ArrayList<>();
				ArrayList<Relevancia> relevancias = new ArrayList<>();
				SortedSet<String> setDePalabras = new TreeSet<String>();
				if(nombreDB.equals("LISA")){
					Documento.generarDocumentosLisa(documentosFiles, documentos);
					getCargaLecturaDataSet().setProgress(0.25);
					Consulta.generarConsultasLisa(consultasFile, consultas);
					getCargaLecturaDataSet().setProgress(0.50);
				}else if(nombreDB.equals("TIME")){
					Documento.generarDocumentosTime(documentosFile, documentos);
					getCargaLecturaDataSet().setProgress(0.25);
					Consulta.generarConsultasTime(consultasFile, consultas);
					getCargaLecturaDataSet().setProgress(0.50);
				}else{
					Documento.generarDocumentos(documentosFile, documentos);
					getCargaLecturaDataSet().setProgress(0.25);
					Consulta.generarConsultas(consultasFile, consultas);
					getCargaLecturaDataSet().setProgress(0.50);
				}
				if(nombreDB.equals("MED")){
					Relevancia.getRelevancia(relevanciasFile, relevancias, 2);
				}else if(nombreDB.equals("LISA")){
					Relevancia.getRelevanciaLisa(relevanciasFile, relevancias);
				}else if(nombreDB.equals("NPL")){
					Relevancia.getRelevanciaNPL(relevanciasFile, relevancias);
				}else if(nombreDB.equals("TIME")  || nombreDB.equals("ISWC2015")){
					Relevancia.getRelevanciaTIMEyISWC2015(relevanciasFile, relevancias);
				}else{
					Relevancia.getRelevancia(relevanciasFile, relevancias, 1);
				}
				getCargaLecturaDataSet().setProgress(0.75);
				Generar.getPalabrasComunes(palabrasComunesFile, palabrasComunes);
				for(Documento d: documentos){
					d.generarSetPalabras(palabrasComunes);
				}
				for(Consulta c: consultas){
					c.generarSetPalabras(palabrasComunes);
				}
				for(Documento d: documentos){
					for(String s: d.getPalabrasValidas()){
						setDePalabras.add(s);
					}
				}
				getCargaLecturaDataSet().setProgress(1.0);
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						getLabelCantidadConsultas().setText(""+consultas.size());
						getLabelCantidadDocumentos().setText(""+documentos.size());
						getLabelPalabrasTotalesComunes().setText(""+palabrasComunes.size());
						getLabelPalabrasTotalesNoComunes().setText(""+setDePalabras.size());
					}
				});
				if(mapConsultas.containsKey(nombreDB)){
					mapConsultas.get(nombreDB).clear();
					mapConsultas.get(nombreDB).addAll(consultas);
				}else{
					mapConsultas.put(nombreDB, consultas);
				}
				if(mapDocumentos.containsKey(nombreDB)){
					mapDocumentos.get(nombreDB).clear();
					mapDocumentos.get(nombreDB).addAll(documentos);
				}else{
					mapDocumentos.put(nombreDB, documentos);
				}
				if(mapPalabrasComunes.containsKey(nombreDB)){
					mapPalabrasComunes.get(nombreDB).clear();
					mapPalabrasComunes.get(nombreDB).addAll(palabrasComunes);
				}else{
					mapPalabrasComunes.put(nombreDB, palabrasComunes);
				}
				if(MapSetDePalabras.containsKey(nombreDB)){
					MapSetDePalabras.get(nombreDB).clear();
					MapSetDePalabras.get(nombreDB).addAll(setDePalabras);
				}else{
					MapSetDePalabras.put(nombreDB, setDePalabras);
				}
				if(mapRelevancias.containsKey(nombreDB)){
					mapRelevancias.get(nombreDB).clear();
					mapRelevancias.get(nombreDB).addAll(relevancias);
				}else{
					mapRelevancias.put(nombreDB, relevancias);
				}
				getCargaLecturaDataSet().setProgress(1);
				getSpinnerPin().setDisable(false);
				getButtonProcesar().setDisable(false);
				getTree().setDisable(false);
				getButtonLeerDataSet().setDisable(false);
			}
		});
		hiloLeer.start();
	}
	/**
	 * Método privado para el cambió de icono de entre consulta y consulta simulada
	 */
	private void cambiarIconoConsultasArbol(){
		for(String s: mapHijosConsultas.keySet()){
			for(TreeItem<String> t: mapHijosConsultas.get(s)){
				ImageView ico;
				if(getToobleButtonVerResultadoS().isSelected() && mapSimulador.containsKey(s)){
					ico = new ImageView(new Image(VentanaController.class.getClassLoader().getResource("img/simulationIcon.png").toExternalForm()));
				}else{
					ico = new ImageView(new Image(VentanaController.class.getClassLoader().getResource("img/queryIcon.png").toExternalForm()));
				}
				ico.setFitWidth(20);
				ico.setFitHeight(20);
				t.setGraphic(ico);
			}
		}
	}
	/**
	 * Método privado que carga los nombres de las bases de datos presentes en la carpeta "datasets"
	 */
	private void cargarDBs(){
		File files = new File("datasets");
		if(files.isDirectory()){
			dataSets = new String[files.listFiles().length];
			int i = 0;
			for(File f: files.listFiles()){
				if(f.isDirectory()){
					dataSets[i] = f.getName().toUpperCase();
					i++;
				}
			}
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
	public TableView getTablaDatos() {
		return tablaDatos;
	}
	/**
	 * @param tablaDatos the tablaDatos to set
	 */
	public void setTablaDatos(TableView tablaDatos) {
		this.tablaDatos = tablaDatos;
	}
	/**
	 * @return the labelDataSetName
	 */
	public Label getLabelDataSetName() {
		return labelDataSetName;
	}
	/**
	 * @param labelDataSetName the dataSetName to set
	 */
	public void setLabelDataSetName(Label labelDataSetName) {
		this.labelDataSetName = labelDataSetName;
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
	public ToggleButton getToobleButtonVerResultadoS() {
		return toogleButtonVerResultadoS;
	}
	/**
	 * @param toogleButtonVerResultadoS the buttonVerResultadoS to set
	 */
	public void setToobleButtonVerResultadoS(ToggleButton toogleButtonVerResultadoS) {
		this.toogleButtonVerResultadoS = toogleButtonVerResultadoS;
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
	/**
	 * @return the cargaProcesarConsultas
	 */
	public ProgressBar getCargaProcesarConsultas() {
		return cargaProcesarConsultas;
	}
	/**
	 * @param cargaProcesarConsultas the cargaProcesarConsultas to set
	 */
	public void setCargaProcesarConsultas(ProgressBar cargaProcesarConsultas) {
		this.cargaProcesarConsultas = cargaProcesarConsultas;
	}
	/**
	 * @return the toogleButtonVerResultadoS
	 */
	public ToggleButton getToogleButtonVerResultadoS() {
		return toogleButtonVerResultadoS;
	}
	/**
	 * @param toogleButtonVerResultadoS the toogleButtonVerResultadoS to set
	 */
	public void setToogleButtonVerResultadoS(ToggleButton toogleButtonVerResultadoS) {
		this.toogleButtonVerResultadoS = toogleButtonVerResultadoS;
	}
	/**
	 * @return the mapSimulador
	 */
	public HashMap<String, Simulador> getMapSimulador() {
		return mapSimulador;
	}
	/**
	 * @param mapSimulador the mapSimulador to set
	 */
	public void setMapSimulador(HashMap<String, Simulador> mapSimulador) {
		this.mapSimulador = mapSimulador;
	}
	/**
	 * @return the mapGetEquation
	 */
	public HashMap<String, DistributionEquation> getMapGetEquation() {
		return mapGetEquation;
	}
	/**
	 * @param mapGetEquation the mapGetEquation to set
	 */
	public void setMapGetEquation(HashMap<String, DistributionEquation> mapGetEquation) {
		this.mapGetEquation = mapGetEquation;
	}
	/**
	 * @return the mapDocumentos
	 */
	public HashMap<String, ArrayList<Documento>> getMapDocumentos() {
		return mapDocumentos;
	}
	/**
	 * @param mapDocumentos the mapDocumentos to set
	 */
	public void setMapDocumentos(HashMap<String, ArrayList<Documento>> mapDocumentos) {
		this.mapDocumentos = mapDocumentos;
	}
	/**
	 * @return the mapConsultas
	 */
	public HashMap<String, ArrayList<Consulta>> getMapConsultas() {
		return mapConsultas;
	}
	/**
	 * @param mapConsultas the mapConsultas to set
	 */
	public void setMapConsultas(HashMap<String, ArrayList<Consulta>> mapConsultas) {
		this.mapConsultas = mapConsultas;
	}
	/**
	 * @return the mapPalabrasComunes
	 */
	public HashMap<String, ArrayList<String>> getMapPalabrasComunes() {
		return mapPalabrasComunes;
	}
	/**
	 * @param mapPalabrasComunes the mapPalabrasComunes to set
	 */
	public void setMapPalabrasComunes(HashMap<String, ArrayList<String>> mapPalabrasComunes) {
		this.mapPalabrasComunes = mapPalabrasComunes;
	}
	/**
	 * @return the mapRelevancias
	 */
	public HashMap<String, ArrayList<Relevancia>> getMapRelevancias() {
		return mapRelevancias;
	}
	/**
	 * @param mapRelevancias the mapRelevancias to set
	 */
	public void setMapRelevancias(HashMap<String, ArrayList<Relevancia>> mapRelevancias) {
		this.mapRelevancias = mapRelevancias;
	}
	/**
	 * @return the mapSetDePalabras
	 */
	public HashMap<String, SortedSet<String>> getMapSetDePalabras() {
		return MapSetDePalabras;
	}
	/**
	 * @param mapSetDePalabras the mapSetDePalabras to set
	 */
	public void setMapSetDePalabras(HashMap<String, SortedSet<String>> mapSetDePalabras) {
		MapSetDePalabras = mapSetDePalabras;
	}
	/**
	 * @return the resumenDataSet
	 */
	public BorderPane getResumenDataSet() {
		return resumenDataSet;
	}
	/**
	 * @param resumenDataSet the resumenDataSet to set
	 */
	public void setResumenDataSet(BorderPane resumenDataSet) {
		this.resumenDataSet = resumenDataSet;
	}
	/**
	 * @return the mapHijosConsultas
	 */
	public HashMap<String, ArrayList<TreeItem<String>>> getMapHijosConsultas() {
		return mapHijosConsultas;
	}
	/**
	 * @param mapHijosConsultas the mapHijosConsultas to set
	 */
	public void setMapHijosConsultas(HashMap<String, ArrayList<TreeItem<String>>> mapHijosConsultas) {
		this.mapHijosConsultas = mapHijosConsultas;
	}
	/**
	 * @return the resumenController
	 */
	public ResumenQueryController getResumenController() {
		return resumenController;
	}
	/**
	 * @param resumenController the resumenController to set
	 */
	public void setResumenController(ResumenQueryController resumenController) {
		this.resumenController = resumenController;
	}
	/**
	 * @return the labelRelAcertadasReal
	 */
	public Label getLabelRelAcertadasReal() {
		return labelRelAcertadasReal;
	}
	/**
	 * @param labelRelAcertadasReal the labelRelAcertadasReal to set
	 */
	public void setLabelRelAcertadasReal(Label labelRelAcertadasReal) {
		this.labelRelAcertadasReal = labelRelAcertadasReal;
	}
	/**
	 * @return the labelRelAcertadasValor
	 */
	public Label getLabelRelAcertadasValor() {
		return labelRelAcertadasValor;
	}
	/**
	 * @param labelRelAcertadasValor the labelRelAcertadasValor to set
	 */
	public void setLabelRelAcertadasValor(Label labelRelAcertadasValor) {
		this.labelRelAcertadasValor = labelRelAcertadasValor;
	}
	/**
	 * @return the lavelRelRealValor
	 */
	public Label getLavelRelRealValor() {
		return lavelRelRealValor;
	}
	/**
	 * @param lavelRelRealValor the lavelRelRealValor to set
	 */
	public void setLavelRelRealValor(Label lavelRelRealValor) {
		this.lavelRelRealValor = lavelRelRealValor;
	}
}
