/**
 * @author Fernando Moraes Oliveira
 * Matéria 4716 - Engenharia de Software 2
 * 3º ADS - Tarde
 * Iniciado em 04/05/2016
 */

package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.Despesas;

public class CondominioDao implements ICondominioDao {
	
	private Connection c;

	public CondominioDao() {
		GenericDao gDao = new GenericDao();
		c = gDao.getConnection();
	}

	@Override
	public void insereDespesa(Despesas despesa) throws SQLException {
		
		String sql = "INSERT INTO despesa_condominio ("
				+ "despesa, valor, dtVencimento, dtCadastro, dtAlterado) VALUES (?,?,?,?,?)";
		PreparedStatement ps = c.prepareStatement( sql );
		ps.setString( 1, despesa.getDespesa() );
		ps.setFloat( 2, despesa.getValor() );
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date vencimento = null;
		Date cadastro = null;
		Date alterado = null;
		
		try {
			vencimento = sdf.parse( despesa.getDtVencimento() );
//			cadastro = sdf.parse( despesa.getDtCadastro() );
//			alterado = sdf.parse( despesa.getDtAlterado() );
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		ps.setDate( 3, new java.sql.Date( vencimento.getTime( ) ));
		ps.setDate( 4, new java.sql.Date( cadastro.getTime( ) ));
		ps.setDate( 5, new java.sql.Date( alterado.getTime( ) ));
		ps.execute();
		ps.close();
	}

	@Override
	public void atualizaDespesa(Despesas despesa) throws SQLException {
		
		String sql = "UPDATE despesa_condominio SET despesa = ?, valor = ?, "
				+ "dtVencimento = ?, dtCadastro = ?, dtAlterad = ? WHERE id = ?";
		PreparedStatement ps = c.prepareStatement(sql);
		ps.setInt(1, despesa.getId());
		ps.setString( 2, despesa.getDespesa() );
		ps.setFloat( 3, despesa.getValor() );
		ps.setDate( 4, new java.sql.Date( Long.parseLong( despesa.getDtVencimento() )));
//		ps.setDate( 5, new java.sql.Date( Long.parseLong( despesa.getDtCadastro() )));
//		ps.setDate( 6, new java.sql.Date( Long.parseLong( despesa.getDtAlterado() )));
		ps.execute();
		ps.close();
	}

	@Override
	public void excluiDespesa(Despesas despesa) throws SQLException {
		
		String sql = "DELETE despesa_condominio WHERE id = ?";
		PreparedStatement ps = c.prepareStatement(sql);
		ps.setInt(1, despesa.getId());
		ps.execute();
		ps.close();
	}

	@Override
	public List<Despesas> consultaDespesas() throws SQLException {
		
		String sql = "SELECT id, despesa, valor, dtVencimento, dtCadastro, "
				+ "dtAlterado FROM despesa_condominio";
		PreparedStatement ps = c.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		List<Despesas> ListaDespesas = new ArrayList<Despesas>();
		while (rs.next()) {
			Despesas despesas = new Despesas();
			despesas.setId( rs.getInt("id") );
			despesas.setDespesa( rs.getString("despesa") );
			despesas.setValor( rs.getFloat("valor") );
			
			DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			java.util.Date venc = rs.getDate("dtVencimento");
			java.util.Date cad = rs.getDate("dtCadastro");
			java.util.Date alt = rs.getDate("dtAlterado");
			String dtVenc =  sdf.format( venc );
//			String dtCad =  sdf.format( cad );
//			String dtAlt =  sdf.format( alt );
			
			despesas.setDtVencimento( dtVenc );
//			despesas.setDtCadastro( dtCad );
//			despesas.setDtAlterado( dtAlt );
			ListaDespesas.add( despesas );
		}
		rs.close();
		ps.close();
		return ListaDespesas;
	}

	@Override
	public Despesas consultaDespesa(Despesas despesa)
			throws SQLException {
		
		String sql = "SELECT id, despesa, valor, dtVencimento, dtCadastro, "
				+ "dtAlterado FROM despesa_condominio WHERE id = ?";
		PreparedStatement ps = c.prepareStatement(sql);
		ps.setInt(1, despesa.getId());
		ResultSet rs = ps.executeQuery();
		Despesas desp = new Despesas();
		if (rs.next()) {
			desp.setId( rs.getInt("id") );
			desp.setDespesa( rs.getString("despesa") );
			desp.setValor( rs.getFloat("valor") );
			desp.setDtVencimento( rs.getString("dtVencimento") );
//			desp.setDtCadastro( rs.getString("dtCadastro") );
//			desp.setDtAlterado( rs.getString("dtAlterado") );
		}
		rs.close();
		ps.close();
		return desp;
	}
}