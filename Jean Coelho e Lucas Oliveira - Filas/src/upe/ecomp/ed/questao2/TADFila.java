package upe.ecomp.ed.questao2;

public interface TADFila<T> {
		
	public void inserir(T e);
	
	public T remover();
	
	public void imprimir();
	
	public int size();
	
	public boolean isEmpty();
	
	
	}
