package org.libertas;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;


import org.libertas.bd.RestauranteDAO;

import com.google.gson.Gson;


public class Restaurante extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
       
    public Restaurante() {
        super();
        
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		//out.println("Executando método GET");
		RestauranteDAO resdao = new RestauranteDAO();
		List<org.libertas.bd.Restaurante> lista = resdao.listar();
		Gson gson = new Gson();
		out.print(gson.toJson(lista));
		//System.out.println("to loko");
	
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		try {
		//PEGA O BODY DA REQUISIÇÃO CONTENDO OS DADOS PARA SER INSERIDO 
		StringBuilder sb = new StringBuilder();
		BufferedReader reader = request.getReader();
		String line;
		while ((line = reader.readLine()) !=null) {
			sb.append(line);
		}
			String boby = sb.toString();
			
			//TRANSFORMA O PARAÊMETRO DO BODY EM OBJETO JSON
			Gson gson = new Gson();
			org.libertas.bd.Restaurante res = gson.fromJson(boby, org.libertas.bd.Restaurante.class);
			
			//INSERE OBJETO NO BANCO DE DADOS
			RestauranteDAO resdao = new RestauranteDAO();
			resdao.inserir(res);
			
			Retorno r = new Retorno(true, "registro inserido com sucesso");
			out.print(gson.toJson(r));
			
	 } catch (Exception e) {
		 e.printStackTrace();
		 Gson gson = new Gson();
		 Retorno r = new Retorno(false, e.getMessage());
			out.print(gson.toJson(r));
		}
}
		
	
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		try {
			//PEGA O BOBY DA REQUISIÇÃO CONTENDO OS DADOS PARA SER INSERIDO 
			StringBuilder sb = new StringBuilder();
			BufferedReader reader = request.getReader();
			String line;
			while ((line = reader.readLine()) !=null) {
				sb.append(line);
			}
				String boby = sb.toString();
				
				//TRANSFORMA O PARAÊMETRO DO BODY EM OBJETO JSON
				Gson gson = new Gson();
				org.libertas.bd.Restaurante res = gson.fromJson(boby, org.libertas.bd.Restaurante.class);
				
				//INSERE OBJETO NO BANCO DE DADOS
				RestauranteDAO resdao = new RestauranteDAO();
				resdao.alterar(res);
				
				Retorno r = new Retorno(true, "REGISTRO ALTERADO COM SUCESSO");
				out.print(gson.toJson(r));
				
		 } catch (Exception e) {
			 e.printStackTrace();
			 Gson gson = new Gson();
			 Retorno r = new Retorno(false, e.getMessage());
			 out.print(gson.toJson(r));out.print(e.getMessage());
			}
	}
	
	
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		try {
			//pega o id passando por parâmetro 
			String id = request.getRequestURI();
			id = id.substring(id.lastIndexOf("/")+1);
			
			if (id.matches("\\d+")) {
			      // id é um número inteiro
			      int idInt = Integer.parseInt(id);
			   
			RestauranteDAO resdao = new RestauranteDAO();
			org.libertas.bd.Restaurante res = new org.libertas.bd.Restaurante();
			res.setId(Integer.parseInt(id));
			resdao.excluir(res);
			
			Retorno r = new Retorno(true, "registro excluido com sucesso");
			Gson gson = new Gson();
			out.print(gson.toJson(r));
			}
			else {
			      // id não é um número inteiro
			      Retorno r = new Retorno(false, "ID inválido");
			      Gson gson = new Gson();
			      out.print(gson.toJson(r));
			    }
		} catch (Exception e) {
			e.printStackTrace();
			Gson gson = new Gson();
			Retorno r = new Retorno(false, e.getMessage());
			out.print(gson.toJson(r));
		}
		
		
		
		
	}

}
