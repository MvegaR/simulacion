package controlador;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;



public class VentanaController implements Initializable{
	@FXML
	private TreeView<String> tree;
	
	@FXML
	private BorderPane panelContenido;
	
	@FXML
	private TableView<String> tablaDatos;
	
	private final String[] dataSets = {"CACM", "MED", "CRAN", "CISI", "LISA", "ADI", "TIME", "ISWC2015"};
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
		System.out.println("Test!!!!!!!!!");

		
	}

}
