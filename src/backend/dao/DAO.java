/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend.dao;

import java.util.Collection;

/**
 *
 * @author MonkeyAndres
 * @param <T>
 */
public interface DAO<T> {
	public void create(T elem);

	public T search(int id);
	public Collection<T> search(String n);
	
	public Collection<T> getAll();
	
	public void edit(long id, T elem);
	
	public void delete(T elem);
}
