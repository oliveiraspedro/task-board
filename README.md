# ToDoBoard
Uma aplicação de gerenciamento de tarefas (To-Do List) aonde os usuários podem criar boards de tarefas e gerencia-las podendo adicionar tarefas novas, visualizar as tarefas criadas e gerenciar quais tarefas não foram iniciadas, quais foram iniciadas e quais foram concluída.

## 🌟 Tecnologias Utilizadas

Este projeto foi construído utilizando as seguintes tecnologias:

* **Linguagem:** Java (JDK 17+)
* **Framework:** Spring Boot
* **Banco de Dados:** PostgreSQL
* **Gerenciador de Dependências:** Maven

---

## ⚙️ Configuração e Execução Local

Siga estes passos para configurar e rodar o projeto na sua máquina local.

### Pré-requisitos

Você precisará ter instalado na sua máquina:

1.  **Git**
2.  **Java Development Kit (JDK) 17 ou superior**
3.  **Maven**
4.  **PostgreSQL** (Servidor rodando localmente)

### 1. Clonar o Repositório

Abra o terminal e clone o projeto:
```
git clone [https://github.com/seu-usuario/seu-projeto.git](https://github.com/seu-usuario/seu-projeto.git)
cd seu-projeto
```

### 2. Configurar o Banco de Dados (PostgreSQL)
O projeto utiliza o PostgreSQL e gerencia as credenciais de forma segura através de variáveis de ambiente carregadas pelo arquivo .env.

Crie um novo banco de dados no seu servidor PostgreSQL. O nome deve ser o mesmo que você usará no arquivo de configuração (ex: todoboard_db).

### Configurar Credenciais (Arquivo .env)
Crie um novo arquivo na raiz do projeto chamado .env.
Copie e cole o conteúdo do arquivo .env.example para o novo arquivo .env.
Preencha as variáveis (DB_USER, DB_PASSWORD, etc.) com as suas credenciais locais do PostgreSQL.

Exemplo do arquivo .env (para você preencher):
```
DB_HOST=localhost
DB_PORT=5432
DB_USER=postgres
DB_PASSWORD="sua_senha_secreta_aqui" 
DB_NAME=seu_nome_do_banco
```

### 3. Instalar Dependências e Compilar
Navegue até o diretório raiz do projeto no terminal e execute o Maven:
```
mvn clean install
```

### 4. Rodar a Aplicação
Inicie o aplicativo Spring Boot:
```
mvn spring-boot:run
```
