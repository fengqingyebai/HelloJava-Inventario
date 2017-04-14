/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend.dao;

import backend.DBUtils;
import backend.models.Categoria;
import backend.models.Producto;


//Imports de SQL y UTIL
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.Vector;

/**
 *
 * @author MonkeyAndres
 */
public class CategoriaDAO implements DAO<Categoria> {
	private Connection con = DBUtils.getConnection(); //Obtengo la conexion con el metodo getConnection

	Collection result = new Vector<Categoria>();
	
	// Inserta una nueva categoria
	@Override
	public void create(Categoria elem) {
		try{
			Statement statement = con.createStatement();
			
			String sql;
			sql = "INSERT INTO Categoria(id, nombre) ";
			sql += "VALUES("+elem.toSQLInsert()+")";
			
			/**
			 En este bloque siguiente hago dos cosas:
			 * Ejecuto el insert
			 * Compuebo si el numero afectado de filas es 1
			 
			 Note: El metodo executeUpdate() devuelve un int con las filas a las que ha afectado el update.
			 */
			int affectedRows = statement.executeUpdate(sql);
			if(affectedRows == 1){
				System.out.println("Categoria insertada correctamente");
			}
		}
		catch(SQLException e){
			System.err.println("Error al insertar la categoria");
		}
	}
	
	// Busca una categoria por su ID
	@Override
	public Categoria search(int id) {	
		Categoria c = null;
		
		try{
			Statement statement = con.createStatement();
			
			String sql;
			sql = "SELECT * FROM Categoria ";
			sql += "WHERE id = "+id;
			
			ResultSet rs = statement.executeQuery(sql);
			
			if(rs == null) System.err.println("La categoria no existe");
			else{
				while(rs.next()){
					int idn = rs.getInt("id");
					String name = rs.getString("nombre");
					
					c = new Categoria(idn, name);
				}
			}
		}
		catch(SQLException e){
			System.err.println("Error al buscar la categoria");
		}
		
		return c;
	}

	// Busca una categoria por su Nombre
	@Override
	public Collection<Categoria> search(String n) {
		result.clear();
		
		try{
			Statement statement = con.createStatement();
			
			String sql;
			sql = "SELECT * FROM Categoria ";
			sql += "WHERE nombre = '"+n+"'";
			
			ResultSet rs = statement.executeQuery(sql);
			
			if(rs == null) System.err.println("La categoria no existe");
			else{
				while(rs.next()){
					int idn = rs.getInt("id");
					String name = rs.getString("nombre");
										
					Categoria c = new Categoria(idn, name);
					
					result.add(c);
				}
			}
		}
		catch(SQLException e){
			System.err.println("Error al buscar la categoria");
		}
		
		return result;
	}

	// Lista todas las categorias
	@Override
	public Collection<Categoria> getAll() {
		result.clear();
		try{
			Statement statement = con.createStatement();
			
			String sql;
			sql = "SELECT * FROM Categoria";
			
			ResultSet rs = statement.executeQuery(sql);
			
			while(rs.next()){
				int idn = rs.getInt("id");
				String name = rs.getString("nombre");

				Categoria c = new Categoria(idn, name);

				result.add(c);
			}
		}
		catch(SQLException e){
			System.err.println("Error al buscar la Categoria");
		}
		
		return result;
	}

	// Edita una categoria
	@Override
	public void edit(long id, Categoria elem) {
		try{
			Statement statement = con.createStatement();
			
			String sql;
			sql = "UPDATE Categoria ";
			sql += "SET "+elem.toString();
			sql += " WHERE id = "+id;
			
			int affectedRows = statement.executeUpdate(sql);
			if(affectedRows == 1){
				System.out.println("Categoria editado correctamente");
			}
		}
		catch(SQLException e){
			System.err.println("Error al editar la categoria");
		}
	}

	// Elimina una categoria
	@Override
	public void delete(Categoria elem) {
		try{
			Statement statement = con.createStatement();
			
			String sql;
			sql = "DELETE FROM Categoria ";
			sql += "WHERE id = "+elem.getId();
			
			int affectedRows = statement.executeUpdate(sql);
			if(affectedRows == 1){
				System.out.println("Categoria eliminado correctamente");
			}
		}
		catch(SQLException e){
			System.err.println("Error al elimnar la Categoria");
		}
	}
	
}
