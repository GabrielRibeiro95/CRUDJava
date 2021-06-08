import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class TrabalhoBD {

	static ArrayList<Aluno> alunos = new ArrayList<Aluno>();
	static CRUD crud=new CRUD();
	static Connection conexao;
	static SimpleDateFormat formato=new SimpleDateFormat("dd/MM/yyyy");
	
	//Chama tela de alteração
	public static class ListenerAlterar implements ActionListener {
		int mat;
		public ListenerAlterar(int mat) {
			this.mat=mat;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			new TelaAlterar(mat);
		}	
	}
	
	//Exclui o registro pesquisado
	public static class ListenerExcluir implements ActionListener {
		int mat;
		public ListenerExcluir(int mat) {
			this.mat=mat;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				crud.delete(conexao, crud.selectByMat(conexao, mat));
				JFrame aviso=new JFrame("AVISO");
				aviso.setSize(100, 100);
				aviso.setVisible(true);
				JLabel add = new JLabel("Aluno excluído");
				aviso.add(add);
			} catch (NullPointerException npe) {
				
			}
		}
	}
	
	//Chama tela de pesquisa por nome
	public static class ListenerConsultaPorNome implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			TelaPesquisaNome tpn=new TelaPesquisaNome();
		}
	}
	
	//Chama tela de pesquisa por matrícula
	public static class ListenerConsultaPorMat implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			TelaPesquisaID tpn=new TelaPesquisaID();
		}
	}
	
	//Chama tela de escolher entre matrícula e nome 
	public static class ListenerConsulta implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			JFrame pesquisa=new JFrame("Pesquisar por...");
			JButton pNome=new JButton("Nome");
			JButton pMat=new JButton("Matrícula");
			pesquisa.setSize(160, 120);
			pesquisa.setVisible(true);
			pesquisa.setLayout(new GridLayout(2,1));
			pNome.addActionListener(new ListenerConsultaPorNome());
			pMat.addActionListener(new ListenerConsultaPorMat());
			pesquisa.add(pNome);
			pesquisa.add(pMat);
		}
	}
	
	//Tela para alterar valores
	public static class TelaAlterar extends JFrame implements ActionListener {
		JTextField tnome, tdata_Nasc, tCR, tcurso;
		int mat;
		
		public TelaAlterar(int matricula) {
			super("Alterar");
			try {
				this.mat=matricula;
				Aluno a=crud.selectByMat(conexao, matricula);
				JButton alterar=new JButton("Alterar");
				alterar.addActionListener(this);
				JLabel nome = new JLabel("Nome:");
				JLabel data_Nasc = new JLabel("Data de Nascimento:");
				JLabel CR = new JLabel("CR:");
				JLabel curso = new JLabel("Curso:");
				tnome=new JTextField(a.nome);
				tdata_Nasc=new JTextField(formato.format(a.data_Nasc));
				tCR=new JTextField(String.valueOf(a.CR));
				tcurso=new JTextField(a.curso);
				
				Container c1=new Container();
				c1.setLayout(new GridLayout(4,2));
				c1.add(nome);
				c1.add(tnome);
				c1.add(data_Nasc);
				c1.add(tdata_Nasc);
				c1.add(CR);
				c1.add(tCR);
				c1.add(curso);
				c1.add(tcurso);
				
				Container c2=new Container();
				c2.setLayout(new GridLayout(1,1));
				c2.add(alterar);
				
				setSize(320,240);
				setVisible(true);
				setLayout(new BorderLayout());
				add(BorderLayout.CENTER, c1);
				add(BorderLayout.SOUTH, c2);
			} catch (NullPointerException npe) {
				JFrame aviso=new JFrame("AVISO");
				aviso.setSize(100, 100);
				aviso.setVisible(true);
				JLabel add = new JLabel("Aluno não encontrado");
				aviso.add(add);
			}
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				crud.update(conexao,new Aluno(mat, tnome.getText(), new Date(formato.parse(tdata_Nasc.getText()).getTime()), Double.parseDouble(tCR.getText()), tcurso.getText()));
				JFrame aviso=new JFrame("AVISO");
				aviso.setSize(100, 100);
				aviso.setVisible(true);
				JLabel add = new JLabel("Dados alterados");
				aviso.add(add);
			} catch (NumberFormatException nfe) {
				JFrame aviso=new JFrame("AVISO");
				aviso.setSize(100, 100);
				aviso.setVisible(true);
				JLabel add = new JLabel("Digite valores válidos");
				aviso.add(add);
			} catch (ParseException pe) {
				JFrame aviso=new JFrame("AVISO");
				aviso.setSize(100, 100);
				aviso.setVisible(true);
				JLabel add = new JLabel("Digite valores válidos");
				aviso.add(add);
			}
		}
	}
	
	//Tela de resultado de pesquisa
	public static class TelaResultado extends JFrame {
		public TelaResultado(int matricula) {
			super("Resultados");
			try {
				Aluno a=crud.selectByMat(conexao, matricula);
				JButton excluir=new JButton("Excluir");
				JButton alterar=new JButton("Alterar");
				JLabel mat = new JLabel("Matrícula:"+a.mat);
				JLabel nome = new JLabel("Nome:"+a.nome);
				JLabel data_Nasc = new JLabel("Data de Nascimento:"+a.data_Nasc);
				JLabel CR = new JLabel("CR:"+a.CR);
				JLabel curso = new JLabel("Curso:"+a.curso);
				
				ListenerAlterar la=new ListenerAlterar(a.mat);
				alterar.addActionListener(la);
				ListenerExcluir le=new ListenerExcluir(a.mat);
				excluir.addActionListener(le);
				
				Container c1=new Container();
				c1.setLayout(new GridLayout(5,1));
				c1.add(mat);
				c1.add(nome);
				c1.add(data_Nasc);
				c1.add(CR);
				c1.add(curso);
				
				Container c2=new Container();
				c2.setLayout(new GridLayout(1,2));
				c2.add(alterar);
				c2.add(excluir);
				
				setSize(320,240);
				setVisible(true);
				setLayout(new BorderLayout());
				add(BorderLayout.CENTER, c1);
				add(BorderLayout.SOUTH, c2);
			} catch (NullPointerException npe) {
				;
			}
		}
		
		public TelaResultado(String nomeTexto) {
			super("Resultados");
			try {
				Aluno a=crud.selectByNome(conexao, nomeTexto);
				JButton excluir=new JButton("Excluir");
				JButton alterar=new JButton("Alterar");
				JLabel mat = new JLabel("Matrícula:"+a.mat);
				JLabel nome = new JLabel("Nome:"+a.nome);
				JLabel data_Nasc = new JLabel("Data de Nascimento:"+a.data_Nasc);
				JLabel CR = new JLabel("CR:"+a.CR);
				JLabel curso = new JLabel("Curso:"+a.curso);
				
				ListenerAlterar la=new ListenerAlterar(a.mat);
				alterar.addActionListener(la);
				ListenerExcluir le=new ListenerExcluir(a.mat);
				excluir.addActionListener(le);
				
				Container c1=new Container();
				c1.setLayout(new GridLayout(5,1));
				c1.add(mat);
				c1.add(nome);
				c1.add(data_Nasc);
				c1.add(CR);
				c1.add(curso);
				
				Container c2=new Container();
				c2.setLayout(new GridLayout(1,2));
				c2.add(alterar);
				c2.add(excluir);
				
				setSize(320,240);
				setVisible(true);
				setLayout(new BorderLayout());
				add(BorderLayout.CENTER, c1);
				add(BorderLayout.SOUTH, c2);
			} catch (NullPointerException npe) {
				;
			}
		}

	}
	
	//Tela para pesquisar um nome
	public static class TelaPesquisaNome extends JFrame implements ActionListener {
		JTextField nome;
		public TelaPesquisaNome() {
			super("Pesquisar nome");
			nome=new JTextField();
			JButton pesquisar=new JButton("Pesquisar");
			setSize(320,130);
			setVisible(true);
			setLayout(new FlowLayout());
			nome.setPreferredSize(new Dimension(200,30));
			add(new JLabel("Nome:"));
			add(nome);
			pesquisar.addActionListener(this);
			add(pesquisar);
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			new TelaResultado(nome.getText());
		}
	}
	
	//Tela para pesquisar um ID(Matrícula)
	public static class TelaPesquisaID extends JFrame implements ActionListener {
		JTextField mat;
		public TelaPesquisaID() {
			super("Pesquisar matrícula");
			mat=new JTextField();
			JButton pesquisar=new JButton("Pesquisar");
			setSize(320,130);
			setVisible(true);
			setLayout(new FlowLayout());
			mat.setPreferredSize(new Dimension(200,30));
			add(new JLabel("Matrícula:"));
			add(mat);
			pesquisar.addActionListener(this);
			add(pesquisar);
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				new TelaResultado(Integer.parseInt(mat.getText()));
			} catch (NumberFormatException nfe) {
				JFrame aviso=new JFrame("AVISO");
				aviso.setSize(100, 100);
				aviso.setVisible(true);
				JLabel add = new JLabel("Digite um valor válido");
				aviso.add(add);
			}
		}
	}
	
	//TelaInicial
	public static class TelaInicial extends JFrame implements ActionListener {
		JTextField nome, data_Nasc, CR;
		JTextField curso;
		JButton adicionar=new JButton("Adicionar");
		JButton consultar=new JButton("Consultar");
		Container c1, c2, c3;
		ListenerConsulta consulta=new ListenerConsulta();
		
		public TelaInicial() {
			super("CRUD Application");
			c1=getContentPane();
			c2=new JPanel();
			c3=new JPanel();
			nome=new JTextField();
			data_Nasc=new JTextField();
			CR=new JTextField();
			curso=new JTextField();
			adicionar.addActionListener(this);
			consultar.addActionListener(consulta);
			
			c3.setLayout(new GridLayout(1,2));
			c3.add(adicionar);
			c3.add(consultar);
			
			c2.setLayout(new GridLayout(4,2));
			c2.add(new JLabel("  Nome"));
			c2.add(nome);
			c2.add(new JLabel("  Data de Nascimento"));
			c2.add(data_Nasc);
			c2.add(new JLabel("  CR"));
			c2.add(CR);
			c2.add(new JLabel("  Curso"));
			c2.add(curso);
		
			c1.setLayout(new BorderLayout());
			c1.add(BorderLayout.CENTER, c2);
			c1.add(BorderLayout.SOUTH, c3);
			
			setSize(320, 240);
			setVisible(true);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				Aluno novo=new Aluno(nome.getText(), new Date(formato.parse(data_Nasc.getText()).getTime()), Double.parseDouble(CR.getText()), curso.getText());
				alunos.add(novo);
				crud.create(conexao, novo);
				JFrame aviso=new JFrame("AVISO");
				aviso.setSize(100, 100);
				aviso.setVisible(true);
				JLabel add = new JLabel("Aluno adicionado");
				aviso.add(add);
			} catch (NumberFormatException nfe) {
				JFrame aviso=new JFrame("AVISO");
				aviso.setSize(280, 120);
				aviso.setVisible(true);
				aviso.add(new JLabel("Digite valores válidos!"));
			} catch (ParseException pe) {
				JFrame aviso=new JFrame("AVISO");
				aviso.setSize(280, 120);
				aviso.setVisible(true);
				aviso.add(new JLabel("Digite valores válidos!"));
			}
		}
		
	}
	
	//Função que imprime os dados de um objeto aluno 
	public static void printAluno(Aluno aluno) {
		System.out.println("Nome:"+aluno.nome);
		System.out.println("Matrícula:"+aluno.mat);
		System.out.println("CR:"+aluno.CR);
		System.out.println("Data de Nascimento:"+aluno.data_Nasc);
		System.out.println("Curso:"+aluno.curso);
	}
	
	//Imprime os dados de um array de alunos 
	public static void printAlunos(ArrayList<Aluno> alunos) {
		for (int i=0; i<alunos.size(); i++) {
			printAluno(alunos.get(i));
		}
	}
	
	//Função main
	public static void main(String[] args) throws ParseException {
		conexao=new Conexao().getConexao();
		TelaInicial janela=new TelaInicial();
		/*try {
			//conexao.close();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}*/
	}

}
