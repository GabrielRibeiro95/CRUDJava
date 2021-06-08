import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
	
	public Connection getConexao() {
		Connection c=null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Conectando ao banco");	
			c=DriverManager.getConnection("jdbc:mysql://localhost:3306/academico","root","");
			System.out.println("Conectado ao banco");
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} catch (ClassNotFoundException cnf) {
			cnf.printStackTrace();
		}
		return c;
	}
	
}
