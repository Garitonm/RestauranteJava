package org.libertas.bd;

// MUDAR O NOME DAS INTANCIAS PARA RESTAURANTE !!!!
// MUDAR O BD PARA MARIADB
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

public class RestauranteDAO {
	public void inserir(Restaurante res) {
		Conexao con = new Conexao();
		try {
			String sql = "INSERT INTO restaurante"
					+ "(nome, localizacao, tipocomida, funcionamento, pontuacao) VALUES (?, ?, ?, ?, ?)";
			PreparedStatement prep = con.getConexao().prepareStatement(sql);
			prep.setString(1, res.getNome());
			prep.setString(2, res.getLocalizacao());
			prep.setString(3, res.getTipocomida());
			prep.setString(4, res.getFuncionamento());
			prep.setString(5, res.getPontuacao());
			prep.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
		con.desconectar();
	}
	
	public void alterar(Restaurante res) {
		Conexao con = new Conexao();
		try {
			String sql = "UPDATE restaurante"
					+ " SET nome =?, localizacao = ?, tipocomida = ?, funcionamento = ?, pontuacao = ? "
					+ "WHERE id = ? ";
			PreparedStatement prep = con.getConexao().prepareStatement(sql);
			prep.setString(1, res.getNome());
			prep.setString(2, res.getLocalizacao());
			prep.setString(3, res.getTipocomida());
			prep.setString(4, res.getFuncionamento());
			prep.setString(5, res.getPontuacao());
			
			prep.setInt(6, res.getId());
			prep.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
		con.desconectar();
		
	}
	public void excluir(Restaurante res) {
		Conexao con = new Conexao();
		try {
			String sql = " DELETE FROM restaurante "
					+ " WHERE id = ? ";
			PreparedStatement prep = con.getConexao().prepareStatement(sql);
			prep.setInt(1, res.getId());
			
			prep.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
		con.desconectar();
		
	}

	public Restaurante consultar(int id) {

		Restaurante res = new Restaurante();
		Conexao con = new Conexao();
		try {
			String sql = "SELECT * FROM pessoas WHERE idpessoa = " + id;
			Statement sta = con.getConexao().createStatement();
			ResultSet resul = sta.executeQuery(sql);
			while (resul.next()) {
				res.setId(resul.getInt("id"));
				res.setNome(resul.getString("nome"));
				res.setLocalizacao(resul.getString("localizacao"));
				res.setTipocomida(resul.getString("tipocomida"));
				res.setFuncionamento(resul.getString("funcionamento"));
				res.setPontuacao(resul.getString("pontuacao"));
			
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		con.desconectar();
		return res;
	}
		
	public List<Restaurante> listar(){
		List<Restaurante> lista = new LinkedList<Restaurante>();
			Conexao con = new Conexao();
		
		try {
			String sql = "SELECT * FROM restaurante ORDER BY id";
			Statement sta = con.getConexao().createStatement();
			ResultSet resul = sta.executeQuery(sql);
			while (resul.next()) {
				Restaurante res = new Restaurante();
				res.setId(resul.getInt("id"));
				res.setNome(resul.getString("nome"));
				res.setLocalizacao(resul.getString("localizacao"));
				res.setTipocomida(resul.getString("tipocomida"));
				res.setFuncionamento(resul.getString("funcionamento"));
				res.setPontuacao(resul.getString("pontuacao"));
				
				lista.add(res);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		con.desconectar();
		return lista;
	}
}

