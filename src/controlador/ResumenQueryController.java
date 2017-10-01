package controlador;



import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

/** 
 * Clase controladora de la escena gráfica que despliega el resumen 
 * de una consulta o simulación en la ventana del proyecto.
 * Es necesaria una representación en una clase para realizar la 
 * transición entre escenas por código, reemplaza la zona central
 * de la gráfica por el resumen que representa esta clase.
 * - Cada atributo corresponde a un elemento gráfico, 
 * clase creada automáticamente usando generadores del entorno de desarollo-
 *
 */
public class ResumenQueryController implements Initializable{

	@FXML
	private BorderPane resumenDataSet;

	@FXML
	private Label labelDataSetName;

	@FXML
	private VBox vBox;

	@FXML
	private Label texto1;

	@FXML
	private Label texto2;

	@FXML
	private Label texto3;

	@FXML
	private Label texto4;

	@FXML
	private Label valor1;

	@FXML
	private Label valor2;

	@FXML
	private Label valor3;

	@FXML
	private Label valor4;

	@FXML
	private Label texto5;

	@FXML
	private Label valor5;

	@FXML
	private Label texto6;

	@FXML
	private Label valor6;

	@FXML
	private Label labelTituloSim;

	@FXML
	private Label texto11;

	@FXML
	private Label texto21;

	@FXML
	private Label texto31;

	@FXML
	private Label texto41;

	@FXML
	private BorderPane titulo2;

	@FXML
	private Label valor11;

	@FXML
	private Label valor21;

	@FXML
	private Label valor31;

	@FXML
	private Label valor41;

	@FXML
	private Label texto51;

	@FXML
	private Label valor51;

	@FXML
	private Label texto61;

	@FXML
	private Label valor61;
	@Override
	public void initialize(URL location, ResourceBundle resources) {

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
	  * @return the labelDataSetName
	  */
	 public Label getLabelDataSetName() {
		 return labelDataSetName;
	 }
	 /**
	  * @param labelDataSetName the labelDataSetName to set
	  */
	 public void setLabelDataSetName(Label labelDataSetName) {
		 this.labelDataSetName = labelDataSetName;
	 }
	 /**
	  * @return the texto1
	  */
	 public Label getTexto1() {
		 return texto1;
	 }
	 /**
	  * @param texto1 the texto1 to set
	  */
	 public void setTexto1(Label texto1) {
		 this.texto1 = texto1;
	 }
	 /**
	  * @return the texto2
	  */
	 public Label getTexto2() {
		 return texto2;
	 }
	 /**
	  * @param texto2 the texto2 to set
	  */
	 public void setTexto2(Label texto2) {
		 this.texto2 = texto2;
	 }
	 /**
	  * @return the texto3
	  */
	 public Label getTexto3() {
		 return texto3;
	 }
	 /**
	  * @param texto3 the texto3 to set
	  */
	 public void setTexto3(Label texto3) {
		 this.texto3 = texto3;
	 }
	 /**
	  * @return the texto4
	  */
	 public Label getTexto4() {
		 return texto4;
	 }
	 /**
	  * @param texto4 the texto4 to set
	  */
	 public void setTexto4(Label texto4) {
		 this.texto4 = texto4;
	 }
	 /**
	  * @return the valor1
	  */
	 public Label getValor1() {
		 return valor1;
	 }
	 /**
	  * @param valor1 the valor1 to set
	  */
	 public void setValor1(Label valor1) {
		 this.valor1 = valor1;
	 }
	 /**
	  * @return the valor2
	  */
	 public Label getValor2() {
		 return valor2;
	 }
	 /**
	  * @param valor2 the valor2 to set
	  */
	 public void setValor2(Label valor2) {
		 this.valor2 = valor2;
	 }
	 /**
	  * @return the valor3
	  */
	 public Label getValor3() {
		 return valor3;
	 }
	 /**
	  * @param valor3 the valor3 to set
	  */
	 public void setValor3(Label valor3) {
		 this.valor3 = valor3;
	 }
	 /**
	  * @return the valor4
	  */
	 public Label getValor4() {
		 return valor4;
	 }
	 /**
	  * @param valor4 the valor4 to set
	  */
	 public void setValor4(Label valor4) {
		 this.valor4 = valor4;
	 }
	 /**
	  * @return the texto5
	  */
	 public Label getTexto5() {
		 return texto5;
	 }
	 /**
	  * @param texto5 the texto5 to set
	  */
	 public void setTexto5(Label texto5) {
		 this.texto5 = texto5;
	 }
	 /**
	  * @return the valor5
	  */
	 public Label getValor5() {
		 return valor5;
	 }
	 /**
	  * @param valor5 the valor5 to set
	  */
	 public void setValor5(Label valor5) {
		 this.valor5 = valor5;
	 }
	 /**
	  * @return the texto6
	  */
	 public Label getTexto6() {
		 return texto6;
	 }
	 /**
	  * @param texto6 the texto6 to set
	  */
	 public void setTexto6(Label texto6) {
		 this.texto6 = texto6;
	 }
	 /**
	  * @return the valor6
	  */
	 public Label getValor6() {
		 return valor6;
	 }
	 /**
	  * @param valor6 the valor6 to set
	  */
	 public void setValor6(Label valor6) {
		 this.valor6 = valor6;
	 }
	 /**
	  * @return the labelTituloSim
	  */
	 public Label getLabelTituloSim() {
		 return labelTituloSim;
	 }
	 /**
	  * @param labelTituloSim the labelTituloSim to set
	  */
	 public void setLabelTituloSim(Label labelTituloSim) {
		 this.labelTituloSim = labelTituloSim;
	 }
	 /**
	  * @return the texto11
	  */
	 public Label getTexto11() {
		 return texto11;
	 }
	 /**
	  * @param texto11 the texto11 to set
	  */
	 public void setTexto11(Label texto11) {
		 this.texto11 = texto11;
	 }
	 /**
	  * @return the texto21
	  */
	 public Label getTexto21() {
		 return texto21;
	 }
	 /**
	  * @param texto21 the texto21 to set
	  */
	 public void setTexto21(Label texto21) {
		 this.texto21 = texto21;
	 }
	 /**
	  * @return the texto31
	  */
	 public Label getTexto31() {
		 return texto31;
	 }
	 /**
	  * @param texto31 the texto31 to set
	  */
	 public void setTexto31(Label texto31) {
		 this.texto31 = texto31;
	 }
	 /**
	  * @return the texto41
	  */
	 public Label getTexto41() {
		 return texto41;
	 }
	 /**
	  * @param texto41 the texto41 to set
	  */
	 public void setTexto41(Label texto41) {
		 this.texto41 = texto41;
	 }
	 /**
	  * @return the valor11
	  */
	 public Label getValor11() {
		 return valor11;
	 }
	 /**
	  * @param valor11 the valor11 to set
	  */
	 public void setValor11(Label valor11) {
		 this.valor11 = valor11;
	 }
	 /**
	  * @return the valor21
	  */
	 public Label getValor21() {
		 return valor21;
	 }
	 /**
	  * @param valor21 the valor21 to set
	  */
	 public void setValor21(Label valor21) {
		 this.valor21 = valor21;
	 }
	 /**
	  * @return the valor31
	  */
	 public Label getValor31() {
		 return valor31;
	 }
	 /**
	  * @param valor31 the valor31 to set
	  */
	 public void setValor31(Label valor31) {
		 this.valor31 = valor31;
	 }
	 /**
	  * @return the valor41
	  */
	 public Label getValor41() {
		 return valor41;
	 }
	 /**
	  * @param valor41 the valor41 to set
	  */
	 public void setValor41(Label valor41) {
		 this.valor41 = valor41;
	 }
	 /**
	  * @return the texto51
	  */
	 public Label getTexto51() {
		 return texto51;
	 }
	 /**
	  * @param texto51 the texto51 to set
	  */
	 public void setTexto51(Label texto51) {
		 this.texto51 = texto51;
	 }
	 /**
	  * @return the valor51
	  */
	 public Label getValor51() {
		 return valor51;
	 }
	 /**
	  * @param valor51 the valor51 to set
	  */
	 public void setValor51(Label valor51) {
		 this.valor51 = valor51;
	 }
	 /**
	  * @return the texto61
	  */
	 public Label getTexto61() {
		 return texto61;
	 }
	 /**
	  * @param texto61 the texto61 to set
	  */
	 public void setTexto61(Label texto61) {
		 this.texto61 = texto61;
	 }
	 /**
	  * @return the valor61
	  */
	 public Label getValor61() {
		 return valor61;
	 }
	 /**
	  * @param valor61 the valor61 to set
	  */
	 public void setValor61(Label valor61) {
		 this.valor61 = valor61;
	 }
	 /**
	  * @return the titulo2
	  */
	 public BorderPane getTitulo2() {
		 return titulo2;
	 }
	 /**
	  * @param titulo2 the titulo2 to set
	  */
	 public void setTitulo2(BorderPane titulo2) {
		 this.titulo2 = titulo2;
	 }
	/**
	 * @return the vBox
	 */
	public VBox getvBox() {
		return vBox;
	}
	/**
	 * @param vBox the vBox to set
	 */
	public void setvBox(VBox vBox) {
		this.vBox = vBox;
	}
	 






}
