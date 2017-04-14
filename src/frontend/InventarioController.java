package frontend;

import backend.models.Producto;
import java.lang.reflect.InvocationTargetException;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.GridPane;

public class InventarioController implements Initializable {

	/**
	 * Imports generados por SceneBuider
	 * KISS :D
	 */

	@FXML
    private TreeView<String> treeView;
	
    @FXML
    private MenuItem newItem;

    @FXML
    private MenuItem editItem;

    @FXML
    private MenuItem deleteItem;

    @FXML
    private TableView<Producto> tableProductos;

    @FXML
    private TableColumn<Producto, Long> idColumn;

    @FXML
    private TableColumn<Producto, String> nombreColumn;

    @FXML
    private TableColumn<Producto, Integer> cantidadColumn;

    @FXML
    private TableColumn<Producto, Integer> precioColumn;

    @FXML
    private TableColumn<Producto, String> categoriaColumn;

    @FXML
    private TextField queryInput;

    @FXML
    private Button btnBuscar;

	// Datos de la tabla
	private ObservableList<Producto> data = FXCollections.observableArrayList();
	
	// Datos TreeView
	private ArrayList<TreeItem> items = new ArrayList<>();
	
    @FXML // Busca productos en DB
    void btnBuscarClick(ActionEvent event) {
		String s = queryInput.getText();
		
		data = Facade.searchProductos(s);
    }

    @FXML // Elimina el elemento seleccionado
    void deleteItemClick(ActionEvent event) {
		Producto p = tableProductos.getSelectionModel().getSelectedItem();
		
		Facade.eliminarProducto(p);
		
		actualizar();
    }

    @FXML // Edita el elemento seleccionado
    void editItemClick(ActionEvent event) {
		Producto pOriginal = tableProductos.getSelectionModel().getSelectedItem(); // Producto original
		Producto pNuevo = editarProducto(pOriginal); // Producto ya editado
		
		// Completo los parametros del nuevo producto
		pNuevo.setId(pOriginal.getId());
		pNuevo.setCategoria(pOriginal.getCategoria());
		
		System.out.println(pNuevo);
		
		if(pNuevo.todoOK()) Facade.editarProducto(pOriginal, pNuevo);
		
		actualizar();
    }

    @FXML // Crea un nuevo producto
    void newItemClick(ActionEvent event) throws InvocationTargetException{
		String str = treeView.getSelectionModel().getSelectedItem().getValue();
		Boolean r = Facade.comprobarCategoria(str);
		
		Producto p = crearProducto();
		p.setId(Facade.getNextId());
		p.setCategoria(str);
		
		if(p.todoOK()) Facade.guardarProducto(p);
		
		actualizar();
	}
	
	// Dialogo que crea el producto
	private Producto crearProducto(){
		Producto p = new Producto(); // Variable de retorno
		
		// Crea un objeto dialogo y pone sus titulos
		Dialog<Producto> dialog = new Dialog<>();
		dialog.setTitle("Nuevo Producto");
		dialog.setHeaderText("Introduce los parametros del producto");

		// Crea dos botones y los aniade
		ButtonType loginButtonType = new ButtonType("Crear", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

		// Crea un grid panel con sus propiedades
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));

		// Creo labels e inputs
		TextField nombre = new TextField();
		nombre.setPromptText("Nombre");
		TextField cantidad = new TextField();
		cantidad.setPromptText("Cantidad");
		TextField precio = new TextField();
		precio.setPromptText("Precio");
		
		// Coloco labels e inputs en el grid
		grid.add(new Label("Nombre:"), 0, 0);
		grid.add(nombre, 1, 0);
		grid.add(new Label("Cantidad:"), 0, 1);
		grid.add(cantidad, 1, 1);
		grid.add(new Label("Precio:"), 0, 2);
		grid.add(precio, 1, 2);

		// Pongo el grid en el dialog
		dialog.getDialogPane().setContent(grid);
		
		// Creo el producto de retorno
		dialog.setResultConverter(dialogButton -> {
			if (dialogButton == loginButtonType) {
				long c = 0;
				int pr = 0;
				
				try{
					c = Integer.parseInt(cantidad.getText());
					pr = Integer.parseInt(precio.getText());
				} catch(NumberFormatException e){
					System.out.println("Error al convertir tipo");
				}
				
				p.setNombre(nombre.getText());
				p.setCantidad(c);
				p.setPrecio(pr);
			}
			return null;
		});

		dialog.showAndWait(); // Muestro el dialogo y espero
		
		return p;
	}
	
	// Dialogo para editar producto
	private Producto editarProducto(Producto pOriginal){
		Producto p = new Producto();
		
		// Crea un objeto dialogo y pone sus titulos
		Dialog<Producto> dialog = new Dialog<>();
		dialog.setTitle("Editar Producto");
		dialog.setHeaderText("Introduce los parametros del producto");

		// Crea dos botones y los aniade
		ButtonType loginButtonType = new ButtonType("Editar", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

		// Crea un grid panel con sus propiedades
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));

		// Datos del producto ORIGINAL (Esta vez necesito Strings)
		String strC = String.valueOf(pOriginal.getCantidad());
		String strPr = String.valueOf(pOriginal.getPrecio());
		
		// Creo labels e inputs
		TextField nombre = new TextField();
		nombre.setText(pOriginal.getNombre());
		
		TextField cantidad = new TextField();
		cantidad.setText(strC);
		
		TextField precio = new TextField();
		precio.setText(strPr);
		
		// Coloco labels e inputs en el grid
		grid.add(new Label("Nombre:"), 0, 0);
		grid.add(nombre, 1, 0);
		grid.add(new Label("Cantidad:"), 0, 1);
		grid.add(cantidad, 1, 1);
		grid.add(new Label("Precio:"), 0, 2);
		grid.add(precio, 1, 2);

		// Pongo el grid en el dialog
		dialog.getDialogPane().setContent(grid);
		
		// Creo el producto de retorno
		dialog.setResultConverter(dialogButton -> {
			if (dialogButton == loginButtonType) {
				long c = 0;
				int pr = 0;
				
				try{
					c = Integer.parseInt(cantidad.getText());
					pr = Integer.parseInt(precio.getText());
				} catch(NumberFormatException e){
					System.out.println("Error al convertir tipo");
				}
				
				p.setNombre(nombre.getText());
				p.setCantidad(c);
				p.setPrecio(pr);
			}
			return null;
		});

		dialog.showAndWait(); // Muestro el dialogo y espero
		
		return p;
	}
	
	// Actualizo datos de la tabla
	private void actualizar(){
		String str = treeView.getSelectionModel().getSelectedItem().getValue();
		data = Facade.getProductosByCategoria(str);
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		// Defino el modelo de la tabla
		idColumn.setCellValueFactory(new PropertyValueFactory("id"));
		nombreColumn.setCellValueFactory(new PropertyValueFactory("nombre"));
		cantidadColumn.setCellValueFactory(new PropertyValueFactory("cantidad"));
		precioColumn.setCellValueFactory(new PropertyValueFactory("precio"));
		categoriaColumn.setCellValueFactory(new PropertyValueFactory("categoria"));
		
		//Obtengo datos del Facade y los llevo a la vista
		data = Facade.getAllProductos();
		tableProductos.setItems(data);
		
		/**
		 * Modelo y muestro el treeview
		 */
		items = Facade.getTreeViewItems(); // Obtengo todos los elementos del FACADE
		
		TreeItem rootItem = new TreeItem("Productos"); // Elemento root
		rootItem.getChildren().addAll(items); // Aniado los items al elemento root

		treeView.setRoot(rootItem); // Muestro los datos en el TreeView
		
		// Evento onChange
		treeView.getSelectionModel().selectedItemProperty().addListener( new ChangeListener() {

			@Override // Detecta el cambio de elemento selecionado
			public void changed(ObservableValue observable, Object oldValue, Object newValue) {

				TreeItem<String> selectedItem = (TreeItem<String>) newValue;
				//System.out.println("Selected Text : " + selectedItem.getValue());
				
				// Mete los nuevos datos en la lista DATA
				try{
					data = Facade.getProductosByCategoria(selectedItem.getValue());
				} catch(NullPointerException e){
					
				}
			}

		});
	}

}
