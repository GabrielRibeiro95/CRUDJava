import java.sql.Date;

public class Aluno {
	int mat;
	String nome;
	Date data_Nasc;
	double CR;
	String curso;
	
	public Aluno(String nome, Date data_Nasc, double CR, String curso) {
		this.nome=nome;
		this.data_Nasc=data_Nasc;
		this.CR=CR;
		this.curso=curso;
	}
	
	public Aluno(int mat, String nome, Date data_Nasc, double CR, String curso) {
		this.mat=mat;
		this.nome=nome;
		this.data_Nasc=data_Nasc;
		this.CR=CR;
		this.curso=curso;
	}
	
}
