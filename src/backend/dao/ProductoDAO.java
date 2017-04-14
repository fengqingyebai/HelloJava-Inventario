/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend.dao;

import backend.DBUtils; //Importo la clase DBUtils
import backend.models.Categoria; //Importo el modelo
import backend.models.Producto; //Importo el modelo

//Herramientas de JDBC
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.Collection;
import java.util.Vector;

/**
 *
 * @author MonkeyAndres
 */
public class ProductoDAO implements DAO<Producto> {
	private Connection con = DBUtils.getConnection(); //Obtengo la conexion con el metodo getConnection

	Collection result = new Vector<Producto>(); //Creo aqui una coleccion de Productos
	
	/**
	 * Sobreescribo los metodos de la interfaz DAO
	 * @param elem
	 */
	
	//Este metodo inserta nuevos productos en la DB
	@Override
	public void create(Producto elem) {
		try{
			Statement statement = con.createStatement();
			
			String sql;
			sql = "INSERT INTO Productos(id, nombre, cantidad, precio, categoria) ";
			sql += "VALUES("+elem.toSQLInsert()+")";
			
			/**
			 En este bloque siguiente hago dos cosas:
			 * Ejecuto el insert
			 * Compuebo si el numero afectado de filas es 1
			 
			 Note: El metodo executeUpdate() devuelve un int con las filas a las que ha afectado el update.
			 */
			int affectedRows = statement.executeUpdate(sql);
			if(affectedRows == 1){
				System.out.println("Producto insertado correctamente");
			}
		}
		catch(SQLException e){
			System.err.println("Error al insertar el producto");
		}
	}
	
	//Este metodo busca productos por su ID
	@Override
	public Producto search(int id) {
		
		Producto p = null;
		
		try{
			Statement statement = con.createStatement();
			
			String sql;
			sql = "SELECT * FROM Productos ";
			sql += "WHERE id = "+id;
			
			ResultSet rs = statement.executeQuery(sql);
			
			if(rs == null) System.err.println("El producto no existe");
			else{
				while(rs.next()){
					int idn = rs.getInt("id");
					String name = rs.getString("nombre");
					int c = rs.getInt("cantidad");
					int pr = rs.getInt("precio");
					String cat = rs.getString("categoria");
					
					p = new Producto(idn, name, c, pr, cat);
				}
			}
		}
		catch(SQLException e){
			System.err.println("Error al buscar el Producto");
		}
		
		return p;
	}
	
	//Este metodo busca productos por su Nombre
	@Override
	public Collection<Producto> search(String n) {
		
		result.clear();
		
		try{
			Statement statement = con.createStatement();
			
			String sql;
			sql = "SELECT * FROM Productos ";
			sql += "WHERE nombre LIKE '%"+n+"%'";
			
			ResultSet rs = statement.executeQuery(sql);
			
			if(rs == null) System.err.println("El producto no existe");
			else{
				while(rs.next()){
					int idn = rs.getInt("id");
					String name = rs.getString("nombre");
					int c = rs.getInt("cantidad");
					int pr = rs.getInt("precio");
					String cat = rs.getString("categoria");
					
					Producto p = new Producto(idn, name, c, pr, cat);
					
					result.add(p);
				}
			}
		}
		catch(SQLException e){
			System.err.println("Error al buscar el Producto");
		}
		
		return result;
	}	
	
	//Este metodo busca productos por su Precio
	public Collection<Producto> searchByPrice(int precio) {
		
		result.clear();
		
		try{
			Statement statement = con.createStatement();
			
			String sql;
			sql = "SELECT * FROM Productos ";
			sql += "WHERE nombre = "+precio;
			
			ResultSet rs = statement.executeQuery(sql);
			
			if(rs == null) System.err.println("El producto no existe");
			else{
				while(rs.next()){
					int idn = rs.getInt("id");
					String name = rs.getString("nombre");
					int c = rs.getInt("cantidad");
					int pr = rs.getInt("precio");
					String cat = rs.getString("categoria");
					
					Producto p = new Producto(idn, name, c, pr, cat);
					
					result.add(p);
				}
			}
		}
		catch(SQLException e){
			System.err.println("Error al buscar el Producto");
		}
		
		return result;
	}	

	/**
	 * Este metodo edita un producto conociendo solo su id
	 * A partir de ello lo seleciona y reemplaza por el segundo parametro que es un Producto
	 * 
	 * @param id
	 * @param elem
	 */
	@Override
	public void edit(long id, Producto elem) { 
		try{
			Statement statement = con.createStatement();
			
			String sql;
			sql = "UPDATE Productos ";
			sql += "SET "+elem.toString();
			sql += " WHERE id = "+id;
			
			int affectedRows = statement.executeUpdate(sql);
			if(affectedRows == 1){
				System.out.println("Producto editado correctamente");
			}
		}
		catch(SQLException e){
			System.err.println("Error al editar el producto");
		}
	}
	
	//Este metodo elimina objetos
	@Override
	public void delete(Producto elem) {
		try{
			Statement statement = con.createStatement();
			
			String sql;
			sql = "DELETE FROM Productos ";
			sql += "WHERE id = "+elem.getId();
			
			int affectedRows = statement.executeUpdate(sql);
			if(affectedRows == 1){
				System.out.println("Producto eliminado correctamente");
			}
		}
		catch(SQLException e){
			System.err.println("Error al elimnar el producto");
		}
	}

	//Este metodo devuelve todos los objetos
	@Override
	public Collection<Producto> getAll() {
		
		result.clear();
		
		try{
			Statement statement = con.createStatement();
			
			String sql;
			sql = "SELECT * FROM Productos";
			
			ResultSet rs = statement.executeQuery(sql);
			
			while(rs.next()){
				int idn = rs.getInt("id");
				String name = rs.getString("nombre");
				int c = rs.getInt("cantidad");
				int pr = rs.getInt("precio");
				String cat = rs.getString("categoria");

				Producto p = new Producto(idn, name, c, pr, cat);

				result.add(p);
			}
		}
		catch(SQLException e){
			System.err.println("Error al buscar el Producto");
		}
		
		return result;
	}
	
	// Devuelve los productos de una categoria
	public Collection<Producto> getByCategory(Categoria category){
		
		result.clear();
		
		try{
			Statement statement = con.createStatement();
			
			String sql;
			sql = "SELECT * FROM Productos ";
			sql += "WHERE categoria = '"+category.getNombre()+"'";
			
			ResultSet rs = statement.executeQuery(sql);
			
			while(rs.next()){
				int idn = rs.getInt("id");
				String name = rs.getString("nombre");
				int c = rs.getInt("cantidad");
				int pr = rs.getInt("precio");
				String cat = rs.getString("categoria");

				Producto p = new Producto(idn, name, c, pr, cat);

				result.add(p);
			}
		}
		catch(SQLException e){
			System.err.println("Error al buscar el Producto");
		}
		
		return result;
	}
	
	// Devuelve el ultimo id
	public long size(){
		long id = 0;
		
		try{
			Statement statement = con.createStatement();
			
			String sql;
			sql = "SELECT * FROM Productos";
			
			ResultSet rs = statement.executeQuery(sql);
			
			while(rs.next()){
				id = rs.getInt("id");
			}
		}
		catch(SQLException e){
			System.err.println("Error al buscar el Producto");
		}
		
		return id;
	}
	
}
