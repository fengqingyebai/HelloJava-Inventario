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
public class Producto {
    private long id;
    private String nombre;
    private long cantidad;
    private int precio;
    private String categoria;

    public Producto(long id, String nombre, long cantidad, int precio, String categoria) {
        this.id = id;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.precio = precio;
        this.categoria = categoria;
    }

	public Producto() {
        this.nombre = null;
        this.categoria = null;
	}
	
	/**
	 * Convierto la instancia en un String que se inserta mas facilmente
	 * en la base de datos.
	 */
	public String toSQLInsert(){
		String sqlStr = ""+this.id+", '"+this.nombre+"', "+this.cantidad+", "+this.precio+", '"+this.categoria+"'";
		return sqlStr;
	}
	
	/**
	 * Convierto la instancia en un String para actualizar datos
	 * Formato: "nombre = 'Lejia', precio = 2 ..."
	 */
	public String toString(){
		String sqlStr = "id = "+this.id+", nombre = '"+this.nombre+"', cantidad = "+this.cantidad+", precio = "+this.precio+", categoria = '"+this.categoria+"'";
		return sqlStr;
	}

	public long getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public long getCantidad() {
		return cantidad;
	}

	public void setCantidad(long cantidad) {
		this.cantidad = cantidad;
	}

	public int getPrecio() {
		return precio;
	}

	public void setPrecio(int precio) {
		this.precio = precio;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	
	public Boolean todoOK(){
		Boolean r = false;
		
		if(this.id > 0 && this.nombre != null && this.cantidad > 0 && this.precio != 0) r = true;
		
		return r;
	}
}
