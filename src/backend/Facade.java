/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend;

import backend.dao.CategoriaDAO;
import backend.dao.ProductoDAO;
import backend.models.Producto;
import java.util.Vector;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author MonkeyAndres
 */
public class Facade {
	private static final ProductoDAO pDAO = new ProductoDAO();
	private static final CategoriaDAO cDAO = new CategoriaDAO();
}
