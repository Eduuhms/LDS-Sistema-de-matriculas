```code


@startuml

enum TIPOUSUARIO {
    ALUNO
    PROFESSOR
    RESPOSAVELFINANCEIRO
    SECRETARIA
}

class Usuario {
  - id: int
  - nome: String
  - email: String
  - senha: String
  - tipo: TipoUsuario
  + cadastrar()
  + entrar()
  + sair()
}

class Aluno{
  - matricula: String
  - curso: Curso
  - disciplinasObrigatorias: List<Disciplina>
  - disciplinasOptativas: List<Disciplina>
  - MAX_OBRIGATORIAS: const = 4
  - MAX_OPTATIVAS: const = 2
  + confirmarMatricula(): void
  + cancelarMatricula(disciplina: Disciplina): void
  + matricularDisciplina(disciplina: Disciplina): void
  
}

class Curso {
  - idCurso: int
  - nome: String
  - creditos: int
  - disciplinas: List<Disciplina>
  + adicionarDisciplina(disciplina: Disciplina): void
  + salvarRelacaoDisciplina(String: codigoDisciplina): void
}

class Disciplina {
  - nome: String
  - codigo: String
  - creditos: int
  - ehObrigatoria: boolean
  - alunosMatriculados: List<Aluno>
  - maxAlunos: const = 60
  - minAlunos: const = 3
  - status: String
  + statusDisciplina(): String
  + gerarCurriculo(): void
  + fecharMatriculas(): void
  + addAluno(aluno: Aluno): void
  + removerAluno(aluno: Aluno): void
  + alunosMatriculados(): List<Aluno>
  + cancelarDisciplina(): void
}

class Cobranca {
  + calcularValorTotal(): float
}

class ResponsavelFinanceiro{
  - disciplinasCobradas: List<Disciplina>
  + gerarCobranca(): void
}

class Professor{
  - disciplinas: List<Disciplina>
  + visualizarAlunos(disciplina: Disciplina): List<Aluno>
  + procurarDisciplina(Disciplina: Disciplina): disciplinaProcurada
}

class Secretaria {
  + gerarCurriculo(): void
  + attInformacoesAluno(): void
  + attInformacoesDisciplina(): void
  + attInformacoesProfessor(): void
  
}

interface ResponsavelMatricula{
cancelarMatricula(disciplina: Disciplina, aluno:Aluno): void
}

Usuario <|-- Professor
Usuario <|-- ResponsavelFinanceiro
ResponsavelMatricula <|.. Aluno
ResponsavelMatricula<|.. Secretaria
Usuario<|-- ResponsavelMatricula

Cobranca "0..*" -- "1" Aluno
Aluno "1" -- "0..*" Curso 
Aluno "3..60" -- "1..*" Disciplina 
Professor "1" -- "0..*" Disciplina 
Curso "1" -- "1.." Disciplina 
Aluno "1" o-- "0..*" ResponsavelFinanceiro 
ResponsavelFinanceiro "1" -- "0..*" Cobranca


@enduml
```