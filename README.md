# 💻 Projeto de Loja de Computadores

## 📌 Descrição

Este projeto é uma aplicação web desenvolvida em **Spring Boot** para simular uma loja de computadores. Criado como parte da disciplina de **Programação Web** na universidade, ele demonstra funcionalidades comuns em e-commerces, como:

- Listagem de produtos
- Carrinho de compras
- Gerenciamento de usuários
- Autenticação e autorização

Utiliza **Spring Data JPA** para persistência de dados e **Thymeleaf** para renderização de páginas web.

---

## ✅ Funcionalidades

- 🖥️ **Catálogo de Produtos:** Exibição de computadores com seus respectivos detalhes
- 🛒 **Carrinho de Compras:** Adicionar, remover e gerenciar produtos
- 🔐 **Autenticação e Autorização:** Login, registro e controle de acesso (usuário/admin)
- 👥 **Gerenciamento de Usuários:** Acesso exclusivo para administradores
- 📦 **Gerenciamento de Produtos:** CRUD de produtos disponível para administradores
- 📄 **Páginas Estáticas:** Sobre, Contato e Admin

---

## 🛠️ Tecnologias Utilizadas

- **Spring Boot** – Framework para aplicações Java
- **Spring Data JPA** – Persistência e acesso ao banco
- **Thymeleaf** – Template engine para HTML
- **Maven** – Gerenciador de dependências e build
- **H2 Database** – Banco de dados embutido para testes
- **PostgreSQL** – Suporte configurado (H2 é padrão)
- **HTML5, CSS3 e JavaScript** – Frontend básico

---

## 📁 Estrutura do Projeto

```
computadores_project/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/university/project/computadores/
│   │   │       ├── ComputadoresApplication.java
│   │   │       ├── config/         # Configurações de segurança
│   │   │       ├── controller/     # Controladores MVC
│   │   │       ├── model/          # Modelos de dados
│   │   │       ├── repository/     # Repositórios JPA
│   │   │       └── service/        # Lógica de negócio
│   │   └── resources/
│   │       ├── application.properties
│   │       ├── data.sql
│   │       ├── static/
│   │       └── templates/
│   └── test/
│       └── com/university/project/computadores/
│           └── PasswordEncoderTest.java
├── pom.xml
└── README.md
```

---

## ▶️ Como Executar o Projeto

### 🔧 Pré-requisitos

- Java JDK 17 ou superior
- Maven 3.x
- Git (opcional)

### 🚀 Passos

1. **Clone o repositório:**
   ```bash
   git clone https://github.com/seu-usuario/computadores_project.git
   cd computadores_project
   ```

2. **Compile o projeto com Maven:**
   ```bash
   mvn clean install
   ```

3. **Execute a aplicação Spring Boot:**
   ```bash
   mvn spring-boot:run
   ```

Acesse no navegador: [http://localhost:8080](http://localhost:8080)

---

## 🗃️ Console do H2 Database

Se estiver usando o banco H2 (padrão), acesse:

🔗 [http://localhost:8080/h2-console](http://localhost:8080/h2-console)

- **JDBC URL:** `jdbc:h2:file:./computadoresdb`
- **User Name:** `sa`
- **Password:** *(deixe em branco)*
