/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frontend;

import backend.dao.CategoriaDAO;
import backend.dao.ProductoDAO;
import backend.models.Categoria;
import backend.models.Producto;
import java.util.ArrayList;

import java.util.Vector;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

/**
 *
 * @author MonkeyAndres
 */
public class Facade {
	private static ObservableList<Producto> data = FXCollections.observableArrayList(); //Variable con todos los datos
	
	private	static ProductoDAO pDAO = new ProductoDAO();
	private static CategoriaDAO cDAO = new CategoriaDAO();
	
	public static ObservableList<Producto> getAllProductos(){
		data.clear();
		
		Vector vData = (Vector) pDAO.getAll();
		
		try{
			for(int i = 0; vData.get(i) != null; i++){
				Producto p = (Producto) vData.get(i);
				data.add(p);
			}
		} catch(ArrayIndexOutOfBoundsException e){
			// System.out.println("No mas registros");
		}
		
		return data;
	}
	
	public static ObservableList<Producto> getProductosByCategoria(String s){
		Vector vCategorias = (Vector) cDAO.search(s);
		
		if(vCategorias.size() == 1){
			Categoria c = (Categoria) vCategorias.get(0);
			Vector vData = (Vector) pDAO.getByCategory(c);
			
			data.clear();
			
			try{
				for(int i = 0; vData.get(i) != null; i++){
					Producto p = (Producto) vData.get(i);
					data.add(p);
				}
			} catch(ArrayIndexOutOfBoundsException e){
				// System.out.println("No mas registros");
			}
			
		} else {
			System.err.println("No se pueden obtener");
		}
		
		return data;
	}
	
	public static ArrayList<TreeItem> getTreeViewItems(){
		ArrayList<TreeItem> items = new ArrayList<TreeItem>(); //Variable que almacena los items
		
		// Creo un Vector con todas las categorias
		Vector vCategorias = (Vector) cDAO.getAll();
		
		//Recojo la excepcion ArrayIndexOutOfBoundsException
		try{
			for(int i = 0; vCategorias.get(i) != null; i++){ //Bucle que pasa por todas las categorias
				Categoria c = (Categoria) vCategorias.get(i); // Almacena la categoria
				TreeItem treeItm = new TreeItem(c.getNombre()); // Crea un elemento padre con el nombre de la categoria
				items.add(treeItm); // Aniade esta categoria al elemento raiz
			}
		} catch(ArrayIndexOutOfBoundsException e){
			// System.out.println("No mas registros");
		}
		
		
		return items;
	}
	
	public static Boolean comprobarCategoria(String str){
		Boolean result = false;
		
		Vector vCategorias = (Vector) cDAO.search(str);
		
		if(vCategorias.size() > 0){
			result = true;
		}
		
		return result;
	}
	
	public static long getNextId(){
		long id = 0;
		id = pDAO.size() + 1;
		return id;
	}
	
	public static void guardarProducto(Producto p){
		pDAO.create(p);
		
		System.out.println(p);
	}
	
	public static void eliminarProducto(Producto p){
		pDAO.delete(p);
		
		System.out.println(p);
	}
	
	public static void editarProducto(Producto pOriginal, Producto pNuevo){
		pDAO.edit(pOriginal.getId(), pNuevo);
		
		System.out.println(pNuevo);
	}
	
	public static ObservableList<Producto> searchProductos(String s){
		data.clear();
		
		Vector vData = (Vector) pDAO.search(s);
		
		try{
			for(int i = 0; vData.get(i) != null; i++){
				Producto p = (Producto) vData.get(i);
				data.add(p);
			}
		} catch(ArrayIndexOutOfBoundsException e){
			// System.out.println("No mas registros");
		}
		
		return data;
	}
}
