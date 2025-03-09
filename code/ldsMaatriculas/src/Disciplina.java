import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Disciplina {
    private static final int maxAlunos = 60;
    private static final int minAlunos = 3;
    
    private String nome;
    private String codigo;
    private int creditos;
    private boolean ehObrigatoria;
    private String status;

    public Disciplina(String nome,
        String codigo,
        int creditos,
        boolean ehObrigatoria,
        String status){
            this.codigo = codigo;
            this.creditos = creditos;
            this.ehObrigatoria = ehObrigatoria;
            this.status = status;
        }

    public Disciplina(String nome,
        String codigo,
        int creditos,
        boolean ehObrigatoria){
            this.codigo = codigo;
            this.creditos = creditos;
            this.ehObrigatoria = ehObrigatoria;
            this.status = "ABERTA";
        }

    public Disciplina(String codigo){
        this.codigo = codigo;
        this.nome = null;
        this.creditos = 0;
        this.ehObrigatoria = false;
        this.status = "ABERTA";
    }

    public Disciplina(String codigo, String nome){
        this.codigo = codigo;
        this.nome = nome;
    }

    public String statusDisciplina() { return ""; }
    public void gerarCurriculo() {}
    public void fecharMatriculas() {}
    public void addAluno(Aluno aluno) {}
    public void removerAluno(Aluno aluno) {}

    public List<Aluno> alunosMatriculados() {
        List<Aluno> alunos = new ArrayList<>();
        Aluno aluno;
        List<String> linhas = LeituraCsv.lerCsv("code\\ldsMaatriculas\\src\\csv\\alunos.csv");
        
        for (String linha : linhas){
            String[] dados = linha.split(",");
            if (this.ehObrigatoria){
                for (String codigoDisciplina : dados[4].split(";")){
                    if (codigoDisciplina.equalsIgnoreCase(this.codigo)){
                        aluno = new Aluno(dados[1], dados[2]);
                        alunos.add(aluno);
                        break;
                    }
                    
                }
            }
            else {
                for (String codigoDisciplina : dados[5].split(";")){
                    if (codigoDisciplina.equalsIgnoreCase(this.codigo)){
                        aluno = new Aluno(dados[1], dados[2]);
                        alunos.add(aluno);
                        break;
                    }
                }
            }
        }

        return alunos;
    }
    public void cancelarDisciplina() {}

    public void preencherComDadosCsv() throws Exception{
        try (BufferedReader reader = new BufferedReader(new FileReader("code\\ldsMaatriculas\\src\\csv\\Disciplinas.csv"))) {
            String linha;
            boolean primeiraLinha = true; // Flag para identificar a primeira linha
            while ((linha = reader.readLine()) != null) {
                if (primeiraLinha) {
                    primeiraLinha = false; // Pula a primeira linha
                    continue;
                }
                if (linha.trim().isEmpty()) {
                    continue; // Ignora linhas vazias
                }

                String[] dados = linha.split(",");
                String codigoCsv = dados[1];

                if (codigoCsv.equals(this.codigo)){
                    this.nome = dados[0];
                    this.creditos = Integer.parseInt(dados[2]);
                    this.ehObrigatoria = Boolean.parseBoolean(dados[3]);
                    this.status = dados[4];
                    return;
                }
                
            }
            throw new Exception("Disciplina não encontrada no csv!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getNome() {
        return nome;
    }

    public String getCodigo(){
        return codigo;
    }

    @Override
	public boolean equals(Object outroObjeto) {
		
		Disciplina outraDisciplina = (Disciplina) outroObjeto;
        Boolean resultado = this.codigo.equals(outraDisciplina.getCodigo());
        if (resultado == true){
            return resultado;
        } else {
            return (this.nome.equalsIgnoreCase(outraDisciplina.getNome()));
        }
	}
}
