import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class CRUD {
	
	public void create(Connection c, Aluno aluno) {
		String sql="insert into aluno(nome_Aluno,data_Nasc,CR,curso) values (?,?,?,?)";
		String sql2="insert into curso(nome_Curso, instituicao) values (?,?)";
		try {
			PreparedStatement stm=c.prepareStatement(sql);
			PreparedStatement stm2=c.prepareStatement(sql2);
			stm.setString(1, aluno.nome);
			stm.setDate(2, aluno.data_Nasc);
			stm.setDouble(3, aluno.CR);
			stm.setString(4, aluno.curso);
			stm.executeUpdate();
			stm2.setString(1, aluno.curso);
			stm2.setString(2, "UFMA");
			stm2.executeUpdate();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
	}
	
	public Aluno selectByMat(Connection c, int key) {
		String sql="select mat_Aluno, nome_Aluno, data_Nasc, CR, curso from aluno where mat_Aluno=?";
		Aluno al;
		try {
			PreparedStatement stm=c.prepareStatement(sql);
			stm.setInt(1, key);
			ResultSet rs=stm.executeQuery();
			rs.next();
			al=new Aluno(rs.getInt(1), rs.getString(2), rs.getDate(3), rs.getDouble(4), rs.getString(5));
			return al;
		} catch (SQLException sqle) {
			JFrame aviso=new JFrame("AVISO");
			aviso.setSize(200, 100);
			aviso.setVisible(true);
			aviso.add(new JLabel("Matrícula não encontrada!"));
		} catch (NullPointerException e) {
			;
		}
		return null;
	}
	
	public Aluno selectByNome(Connection c, String key) {
		String sql="select mat_Aluno, nome_Aluno, data_Nasc, CR, curso from aluno where nome_Aluno=?";
		Aluno al;
		try {
			PreparedStatement stm=c.prepareStatement(sql);
			stm.setString(1, key);
			ResultSet rs=stm.executeQuery();
			rs.next();
			al=new Aluno(rs.getInt(1), rs.getString(2), rs.getDate(3), rs.getDouble(4), rs.getString(5));
			return al;
		} catch (SQLException sqle) {
			JFrame aviso=new JFrame("AVISO");
			aviso.setSize(200, 100);
			aviso.setVisible(true);
			aviso.add(new JLabel("Nome não encontrado!"));
		} catch (NullPointerException e) {
			;
		}
		return null;
	}
	
	public ArrayList<Aluno> read(Connection c) {
		ArrayList<Aluno> lista=new ArrayList();
		String sql="select mat_Aluno, nome_Aluno, data_Nasc, CR, curso from aluno";
		try {
			PreparedStatement stm=c.prepareStatement(sql);
			ResultSet rs=stm.executeQuery();
			while (rs.next()) {
				lista.add(new Aluno(rs.getInt(1), rs.getString(2), rs.getDate(3), rs.getDouble(4), rs.getString(5)));
			}
		} catch (SQLException sqle) {
			;
		}
		return lista;
	}
	
	public void update(Connection c, Aluno aluno) {
		String sql="update aluno set nome_Aluno=?, data_Nasc=?, CR=?, curso=? where mat_Aluno=?";
		try {
			PreparedStatement stm=c.prepareStatement(sql);
			stm.setString(1, aluno.nome);
			stm.setDate(2, aluno.data_Nasc);
			stm.setDouble(3, aluno.CR);
			stm.setString(4, aluno.curso);
			stm.setInt(5, aluno.mat);
			stm.executeUpdate();
		} catch (SQLException sqle) {
			//sqle.printStackTrace();
		}
	}
	
	public void delete(Connection c, Aluno aluno) {
		String sql="delete from aluno where mat_Aluno=?";
		try {
			PreparedStatement stm=c.prepareStatement(sql);
			stm.setInt(1, aluno.mat);
			stm.executeUpdate();
		} catch (SQLException sqle) {
			//sqle.printStackTrace();
		}
	}
	
}
