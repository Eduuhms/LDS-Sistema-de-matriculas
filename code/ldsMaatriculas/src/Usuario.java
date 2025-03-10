import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public abstract class Usuario {
    private static int proximoId = encontrarUltimoId() + 1; 
    protected int id;
    protected String nome;
    protected String email;
    protected String senha;
    protected final TipoUsuario tipo;

    public Usuario(){
        this.id = 0;
        this.nome = null;
        this.email = null;
        this.senha = null;
        this.tipo = null;
    }

    public Usuario(String nome, String email, String senha, TipoUsuario tipo) {
        this.id = proximoId++;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.tipo = tipo;
    }

    public Usuario(String nome, String email, String senha, TipoUsuario tipo, int id){
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.tipo = tipo;
        this.id = id;
    }

    public abstract void setDados();

    public abstract void setDados(Boolean classes);

    private static int encontrarUltimoId() {
        int ultimoId = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader("code\\ldsMaatriculas\\src\\csv\\usuarios.csv"))) {
            String linha;
            boolean primeiraLinha = true; 
            while ((linha = reader.readLine()) != null) {
                if (primeiraLinha) {
                    primeiraLinha = false; 
                    continue;
                }
                if (linha.trim().isEmpty()) {
                    continue; 
                }
                String[] dados = linha.split(",");
                int idAtual = Integer.parseInt(dados[0]);
                if (idAtual > ultimoId) {
                    ultimoId = idAtual;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ultimoId;
    }

    public void cadastrar() {
        try (FileWriter writer = new FileWriter("code\\ldsMaatriculas\\src\\csv\\usuarios.csv", true)) {
            writer.append("\n")
                  .append(String.valueOf(id)).append(",")
                  .append(nome).append(",")
                  .append(email).append(",")
                  .append(senha).append(",")
                  .append(tipo.name());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Usuario entrar(String email, String senha) {
        try (BufferedReader reader = new BufferedReader(new FileReader("code\\ldsMaatriculas\\src\\csv\\usuarios.csv"))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                if (linha.trim().isEmpty()) {
                    continue; 
                }
                String[] dados = linha.split(",");
                if (dados[2].equals(email) && dados[3].equals(senha)) {
                    String id = dados[0];
                    String nome = dados[1];
                    TipoUsuario tipo = TipoUsuario.valueOf(dados[4]);

                    switch (tipo) {
                        case ALUNO:
                            return new Aluno(nome, email, senha, Integer.parseInt(id));
                        case PROFESSOR:
                            return new Professor(nome, email, senha, id);
                        case SECRETARIA:
                            return new Secretaria(nome, email, senha);
                        case RESPOSAVELFINANCEIRO:
                            return new ResponsavelFinanceiro(nome, email, senha);
                        default:
                            throw new IllegalArgumentException("Tipo de usuário desconhecido: " + tipo);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void sair() {
        System.out.println("Usuário " + nome + " saiu do sistema.");
    }

    public TipoUsuario getTipo() {
        return tipo;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail(){
        return email;
    }
}