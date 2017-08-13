package controlador;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
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
		System.out.println("Change");
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

	
	


}
