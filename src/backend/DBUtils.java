/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import backend.OSValidator;

/**
 *
 * @author MonkeyAndres
 */
public class DBUtils {
	private static String url = System.getProperty("user.dir");
	private static Connection con = null;

	public static Connection getConnection(){
		if(con == null){
			try{
                            if(OSValidator.isWindows()){
                                con = DriverManager.getConnection("jdbc:sqlite:"+url+"\\src\\backend\\myDB.db");
                            } else {
                                con = DriverManager.getConnection("jdbc:sqlite:"+url+"//src//backend//myDB.db");
                            }
			}
			catch(SQLException e){
				System.err.println("No se ha podido obtener la conexion");
			}
		}
		return con;
	}
}
