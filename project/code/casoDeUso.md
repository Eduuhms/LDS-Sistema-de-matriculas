```code
@startuml
left to right direction
:Secretária da universidade: as s 
:Aluno: as a
:ResponsavelMatricula: as rm
:Professor: as p
:Usuário: as u
:Responsável financeiro: as rf


rectangle SistemaMatricula {

s --> (Gerar currículo)
s --> (Manter informações da disciplina)
s --> (Manter informações dos professores)
s --> (Manter informações dos alunos)

(Matricular) as matricular
(Matricular em disciplina obrigatória) .>  matricular : include
 (Matricular em disciplina optativa) .> matricular : include


a --> matricular


(Gerar boleto de cobrança) as gerarBoleto
rf --> gerarBoleto
gerarBoleto --> a

p --> (Acessar alunos matriculados em uma disciplina)

u --> (Cadastrar no sistema)
u --> (Entrar no sistema)

rm --> (Cancelar a matricula)
}


rm <|-- s
rm <|--  a
u <|-- rm
u <|--  s
u <|--  p
@enduml

```
