/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend.models;

/**
 *
 * @author MonkeyAndres
 */
public class Categoria {
    private long id;
    private String nombre;

    public Categoria(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }
	
	/**
	 * Convierto la instancia en un String que se inserta mas facilmente
	 * en la base de datos.
	 */
	public String toSQLInsert(){
		String sqlStr = ""+this.id+", '"+this.nombre+"'";
		return sqlStr;
	}
	
	/**
	 * Convierto la instancia en un String para actualizar datos
	 * Formato: "id = 1, nombre = 'Limpieza' ..."
	 */
	public String toString(){
		String sqlStr = "id = "+this.id+", nombre = '"+this.nombre+"'";
		return sqlStr;
	}

	public long getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}
