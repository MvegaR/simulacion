package controlador;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.SortedSet;
import java.util.TreeSet;

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
import javafx.scene.control.ToggleButton;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import modelo.Consulta;
import modelo.Documento;
import modelo.Relevancia;
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
	private Label labelDataSetName;
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
	private ToggleButton toogleButtonVerResultadoS;
	@FXML
	private Label labelRelAcertadas;
	
	private HashMap<String, ResultadoDataSet> mapDataSets = new HashMap<>();
	private HashMap<String, Simulador> mapSimulador = new HashMap<>();
	private HashMap<String, GetEquation> mapGetEquation = new HashMap<>();
	
	private HashMap<String, ArrayList<Documento>> mapDocumentos = new HashMap<>();
	private HashMap<String, ArrayList<Consulta>> mapConsultas = new HashMap<>();
	private HashMap<String, ArrayList<String>> mapPalabrasComunes = new HashMap<>();
	private HashMap<String, ArrayList<Relevancia>> mapRelevancias = new HashMap<>();
	private HashMap<String, SortedSet<String>> MapSetDePalabras = new HashMap<>();
	

	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		  TreeItem<String> rootItem = new TreeItem<String> ("DataSets", null);
		  for(String s: dataSets){
			  TreeItem<String> itemDataSet = new TreeItem<String>(s, null);
			  rootItem.getChildren().add(itemDataSet);
		  }
		  tree.getSelectionModel().selectedItemProperty().addListener(e->updateData());
		  tree.setRoot(rootItem);
		  
		  getButtonLeerDataSet().setOnAction(e -> leerDataSet());
		
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
		//Al seleccionar un dataset en el arbol:
		if(padre != null && !selectItem.getValue().equals("DataSets")){
			System.out.println("Seleccionado data set " + selectItem.getValue());
			getLabelDataSetName().setText(selectItem.getValue());
			//mapConsultas,mapDocumentos,mapPalabrascomunes, mapSetdePalabras se trabajan en conjunto
			if(!mapConsultas.containsKey(selectItem.getValue().toString())){
				disableDataSetControl();
				disableFunctionControl();
				disableSimularControl(); 
				getButtonLeerDataSet().setDisable(false);
				getLabelCantidadConsultas().setText("0000");
				getLabelCantidadDocumentos().setText("0000");
				getLabelPalabrasTotalesComunes().setText("0000");
				getLabelPalabrasTotalesNoComunes().setText("0000");
				getCargaLecturaDataSet().setProgress(0);
				
			}else{
				getSpinnerPin().setDisable(false);
				getButtonProcesar().setDisable(false);
				getLabelCantidadConsultas().setText(""+mapConsultas.get(selectItem.getValue()).size());
				getLabelCantidadDocumentos().setText(""+mapDocumentos.get(selectItem.getValue()).size());
				getLabelPalabrasTotalesComunes().setText(""+mapPalabrasComunes.get(selectItem.getValue()).size());
				getLabelPalabrasTotalesNoComunes().setText(""+MapSetDePalabras.get(selectItem.getValue()).size());
				getCargaLecturaDataSet().setProgress(1);
			}
		}else if(padre == null){ //raiz
			getLabelDataSetName().setText(selectItem.getValue());
			disableDataSetControl();
			disableFunctionControl();
			disableSimularControl(); 
		}

	}
	/*
	 * Desactiva los botones y otros controles de la sección de simular
	 */
	private void disableSimularControl(){
		getSliderSensibilidad().setDisable(true);
		getButtonSimular().setDisable(true);
		getToobleButtonVerResultadoS().setDisable(true);
	}
	/*
	 * Desactiva los botones y otros controles de la sección del dataSet
	 */
	private void disableDataSetControl(){
		getButtonLeerDataSet().setDisable(true);
		getSpinnerPin().setDisable(true);
		getButtonProcesar().setDisable(true);
	}
	/*
	 * Desactiva los botones y otros controles de la sección de la función
	 */
	private void disableFunctionControl(){
		getSliderIntervalos().setDisable(true);
		getButtonGenerarF().setDisable(true);
		getButtonVerFuncion().setDisable(true);
	}
	
	private void leerDataSet(){
		String fsp = System.getProperty("file.separator").toString();
		//palabras comunes (se uso en todas ya que mejora la precisión, pero es de cran)
		File palabrasComunesFile = new File("files"+fsp+"cacm"+fsp+"common_words");
		
		ArrayList<File> documentosFiles = null;
		File documentosFile = null; 
		File consultasFile = null; 
		File relevanciasFile = null;
		String nombreDB = getLabelDataSetName().getText();
	
		if(getLabelDataSetName().getText().equals("CACM")){
			documentosFile = new File("files"+fsp+"cacm"+fsp+"cacm.all");
			consultasFile= new File("files"+fsp+"cacm"+fsp+"query.text");
			relevanciasFile = new File("files"+fsp+"cacm"+fsp+"qrels.text");
		}else if(getLabelDataSetName().getText().equals("MED")){
			documentosFile= new File("files"+fsp+"med"+fsp+"MED.ALL");
			consultasFile= new File("files"+fsp+"med"+fsp+"MED.QRY");
			relevanciasFile= new File("files"+fsp+"med"+fsp+"MED.REL");

		}else if(getLabelDataSetName().getText().equals("CRAN")){
			documentosFile= new File("files"+fsp+"cran"+fsp+"cran.all.1400");
			consultasFile= new File("files"+fsp+"cran"+fsp+"cran.qry");
			relevanciasFile= new File("files"+fsp+"cran"+fsp+"cranFix.rel");
			
		}else if(getLabelDataSetName().getText().equals("CISI")){
			documentosFile= new File("files"+fsp+"cisi"+fsp+"CISI.all");
			consultasFile= new File("files"+fsp+"cisi"+fsp+"CISI.qry");
			relevanciasFile= new File("files"+fsp+"cisi"+fsp+"CISI.rel");
			
		}else if(getLabelDataSetName().getText().equals("LISA")){
			documentosFiles= new ArrayList<>();
			documentosFiles.add(new File("files"+fsp+"lisa"+fsp+"LISA0.501"));
			documentosFiles.add(new File("files"+fsp+"lisa"+fsp+"LISA1.501"));
			documentosFiles.add(new File("files"+fsp+"lisa"+fsp+"LISA2.501"));
			documentosFiles.add(new File("files"+fsp+"lisa"+fsp+"LISA3.501"));
			documentosFiles.add(new File("files"+fsp+"lisa"+fsp+"LISA4.501"));
			documentosFiles.add(new File("files"+fsp+"lisa"+fsp+"LISA5.501"));
			documentosFiles.add(new File("files"+fsp+"lisa"+fsp+"LISA5.627"));
			documentosFiles.add(new File("files"+fsp+"lisa"+fsp+"LISA5.850"));
			consultasFile= new File("files"+fsp+"lisa"+fsp+"LISA.QUE");
			relevanciasFile= new File("files"+fsp+"lisa"+fsp+"LISA.REL");
			
		}else if(getLabelDataSetName().getText().equals("ADI")){
			documentosFile= new File("files"+fsp+"adi"+fsp+"ADI.ALL");
			consultasFile= new File("files"+fsp+"adi"+fsp+"ADI.QRY");
			relevanciasFile= new File("files"+fsp+"adi"+fsp+"ADI.REL");

		}else if(getLabelDataSetName().getText().equals("TIME")){
			documentosFile= new File("files"+fsp+"time"+fsp+"TIME.ALL");
			consultasFile= new File("files"+fsp+"time"+fsp+"TIME.QUE");
			relevanciasFile= new File("files"+fsp+"time"+fsp+"TIME.REL");
			
		}else if(getLabelDataSetName().getText().equals("ISWC2015")){
			documentosFile= new File("files"+fsp+"iswc2015"+fsp+"docs.txt");
			consultasFile= new File("files"+fsp+"iswc2015"+fsp+"qrys.txt");
			relevanciasFile= new File("files"+fsp+"iswc2015"+fsp+"rel.txt");
		}else{
			return;
		}
		getCargaLecturaDataSet().setProgress(ProgressBar.INDETERMINATE_PROGRESS);
		ArrayList<Documento> documentos = new ArrayList<>();
		ArrayList<Consulta> consultas = new ArrayList<>();
		ArrayList<String> palabrasComunes = new ArrayList<>();
		ArrayList<Relevancia> relevancias = new ArrayList<>();
		SortedSet<String> setDePalabras = new TreeSet<String>();
		
		if(nombreDB.equals("LISA")){
			Documento.generarDocumentosLisa(documentosFiles, documentos);
			Consulta.generarConsultasLisa(consultasFile, consultas);
		}else if(nombreDB.equals("TIME")){
			Documento.generarDocumentosTime(documentosFile, documentos);
			Consulta.generarConsultasTime(consultasFile, consultas);
		}else{
			Documento.generarDocumentos(documentosFile, documentos);
			Consulta.generarConsultas(consultasFile, consultas);
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
		Generar.getPalabrasComunes(palabrasComunesFile, palabrasComunes);
		for(Documento d: documentos){
			d.generarSetPalabras(palabrasComunes);
		}
		for(Consulta c: consultas){
			c.generarSetPalabras(palabrasComunes);
		}
		
		
		for(Documento d: documentos){
			//System.out.println("Documento "+ d.getId()+" tiene: "+ d.getPalabrasValidas().size() + " palabras Validas");
			for(String s: d.getPalabrasValidas()){
				setDePalabras.add(s);
			}
		}
		
		getLabelCantidadConsultas().setText(""+consultas.size());
		getLabelCantidadDocumentos().setText(""+documentos.size());
		getLabelPalabrasTotalesComunes().setText(""+palabrasComunes.size());
		getLabelPalabrasTotalesNoComunes().setText(""+setDePalabras.size());
		
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
		
		getCargaLecturaDataSet().setProgress(1);
		getSpinnerPin().setDisable(false);
		getButtonProcesar().setDisable(false);
		
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
	 * @return the labelDataSetName
	 */
	public Label getLabelDataSetName() {
		return labelDataSetName;
	}

	/**
	 * @param dataSetName the dataSetName to set
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
	 * @param buttonVerResultadoS the buttonVerResultadoS to set
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

	
	


}
