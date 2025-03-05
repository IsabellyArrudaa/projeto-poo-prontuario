package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.DatabaseConnection;
import model.Exame;
import model.Paciente;

public class ExameDAO implements GenericDAO<Exame, Long>{

	private DatabaseConnection db;

    public ExameDAO(DatabaseConnection db) {
        this.db = db;
    }
    
    
	@Override
	public void add(Exame obj) {
		// TODO Auto-generated method stub
		String sql = "INSERT INTO exames (descricao, data_exame, pacientes_idpacientes) VALUES (?, ?, ?)";
		
		try (Connection conn = db.getConnection();
				PreparedStatement pstm = conn.prepareStatement(sql)) {
			pstm.setString(1, obj.getDescricao());
			pstm.setDate(2, Date.valueOf(obj.getDataExame())); 	
			pstm.setLong(3, obj.getPaciente().getId());; 
			pstm.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Exame findByID(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Exame obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Exame obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Exame> getAll() {
	    List<Exame> exames = new ArrayList<>();
	    String sql = "SELECT e.idexames, e.descricao, e.data_exame, p.idpacientes, p.cpf, p.nome FROM exames e " +
	                 "JOIN pacientes p ON e.pacientes_idpacientes = p.idpacientes";

	    try (Connection conn = db.getConnection();
	         PreparedStatement pstm = conn.prepareStatement(sql);
	         ResultSet rs = pstm.executeQuery()) {

	        while (rs.next()) {
	            Paciente paciente = new Paciente(rs.getLong(4), rs.getString(5), rs.getString(6));
	            Exame exame = new Exame(rs.getLong(1), rs.getString(2), rs.getDate(3).toLocalDate(), paciente);
	            exames.add(exame);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return exames;
	}


}
