package vista;

import java.io.IOException;
import java.net.MalformedURLException;

import controlador.ResumenQueryController;
import controlador.VentanaController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainVentana extends Application {

	public static void main(String[] args) {
		launch(args);
	}
	@Override
	public void start(Stage windows) throws IOException {
		String fsp = System.getProperty("file.separator").toString();
		ResumenQueryController resumenController = null;
		VentanaController ventanaController = null;
		try {
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("resumenQuery.fxml"));
			Parent root = (Parent) loader.load();
			
			resumenController = (ResumenQueryController) loader.getController();

		
			Scene scene = new Scene(root);

			scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());

			windows.setScene(scene);
			windows.show(); 
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("ventana.fxml"));
			Parent root = loader.load();
			ventanaController = loader.getController();
			ventanaController.setResumenController(resumenController);

			Scene scene = new Scene(root);
			scene.getStylesheets().add(this.getClass().getResource("/css/style.css").toExternalForm());
			windows.setTitle("Proyecto título - Obtención de una función de distribución probabilística "
					+ "global para simular juicios de usuario - Por: Marcos Vega - 2017");
			

			windows.setScene(scene);
			windows.show();
			windows.setMaximized(true);
		
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}
}
