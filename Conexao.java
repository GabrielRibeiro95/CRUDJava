import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
	
	//Defina aqui as credenciais do banco
	static final String SERVER="localhost", PORT="3306", USER="root", PW="";	
	
	public Connection getConexao() {
		Connection c=null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Conectando ao banco");	
			c=DriverManager.getConnection("jdbc:mysql://"+SERVER+":"+PORT+"/academico", USER, PW);
			System.out.println("Conectado ao banco");
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} catch (ClassNotFoundException cnf) {
			cnf.printStackTrace();
		}
		return c;
	}
	
}
