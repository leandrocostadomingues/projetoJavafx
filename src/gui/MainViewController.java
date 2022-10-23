package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import application.Main;
import gui.util.Alerts;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import model.services.DepartmentService;
import model.services.SellerService;

public class MainViewController implements Initializable {

	
	@FXML
	private MenuItem menuItemDepartment;
	@FXML
	private MenuItem menuItemAbout;

	@FXML
	public void onmenuItemSellerAction() {
		 

		loadView("/gui/SellerList.fxml", (SellerListController controller) -> {

			controller.setSellerService(new SellerService());
			controller.updateTableView();

		});
		
		
	}

	@FXML
	public void menuItemDepartmentAction() {

		loadView("/gui/DepartmentList.fxml", (DepartmentListController controller) -> {

			controller.setDepartmentService(new DepartmentService());
			controller.updateTableView();

		});

	}

	@FXML
	public void menuItemAboutAction() {

		loadView("/gui/About.fxml", x -> {});

	}

	@Override
	public void initialize(URL ur, ResourceBundle rb) {

	}

	private synchronized <T> void loadView(String absoluteName, Consumer<T> initializingAction) { // Consumer serve para fazer uma
		// chamada de funcao lambida para nao precisar de ficar fazendo loadView toda hora com diferentes metodos de implementação
		
		// synchronized ele garante que o processamento vai ter prioridade no multtrade
// neste metodo pega referencia da tela main atraves public static Scene getMainScene() que fornece seus dados de layout 
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			VBox newVBox = loader.load();

			Scene mainScene = Main.getMainScene();
			VBox mainVBox = (VBox) ((ScrollPane) mainScene.getRoot()).getContent();

			Node mainMenu = mainVBox.getChildren().get(0);
			mainVBox.getChildren().clear();
			mainVBox.getChildren().add(mainMenu);
			mainVBox.getChildren().addAll(newVBox.getChildren());

			T controller = loader.getController();

			initializingAction.accept(controller);
		} catch (IOException e) {
			Alerts.showAlert("IO EXCEPTION", "ERRO LOADING VIEW ", e.getMessage(), AlertType.ERROR);
		}

	}

}
