import java.io.FileWriter;
import java.io.IOException;

public class Usuario {
    private int id;
    private String nome;
    private String email;
    private String senha;
    private final TipoUsuario tipo;

    public Usuario(int id, String nome, String email, String senha, TipoUsuario tipo) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.tipo = tipo; 
    }

    public void cadastrar() {
        try (FileWriter writer = new FileWriter("LDS-Sistema-de-matriculas\\code\\ldsMaatriculas\\src\\csv\\usuarios.csv", true)) {
            writer.append(String.valueOf(id)).append(",")
                  .append(nome).append(",")
                  .append(email).append(",")
                  .append(senha).append(",")
                  .append(tipo.name()).append("\n"); // Salva o tipo no CSV
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void entrar() {}
    public void sair() {}

    public TipoUsuario getTipo() {
        return tipo;
    }
}