# 💰 CashFlow API

API RESTful para gerenciamento de receitas e despesas, desenvolvida com **Java + Spring Boot**, seguindo boas práticas de arquitetura, segurança e testes.

---

## 🚀 Tecnologias utilizadas

- Java 21
- Spring Boot
- Spring Security (JWT)
- Spring Data JPA
- Hibernate
- Maven
- Swagger (OpenAPI)
- JUnit + Mockito
- Map-Struct
- GitHub Actions (CI)

---

## 📌 Funcionalidades

- ✅ **Gestão de Usuários:** Cadastro, login e perfis de acesso.
- ✅ **Autenticação:** Proteção de endpoints via Token JWT.
- ✅ **Fluxo Financeiro:** CRUD completo de receitas (Income) e despesas (Expense).
- ✅ **Tratamento de Exceções:** Global Exception Handler para retornos de erro padronizados.
- ✅ **Validação de Dados:** Uso de Bean Validation para integridade dos inputs.
- ✅ **Testes automatizados:** Uso de JUnit e Mockito para rodar os testes.
- ✅ **Documentação:** Interface interativa para teste de endpoints.

---


## ⚙️ Como rodar o projeto

### 1. Pré-requisitos
* **JDK 21** instalado.
* **Maven 3.9+** instalado.
* **PostgreSQL** rodando localmente (ou ajuste para H2 no `application.properties`).

### 2. Variáveis de Ambiente
Para segurança, a API requer uma chave secreta para o JWT. Você pode defini-la no seu terminal ou no seu arquivo de propriedades:
```bash
export JWT_SECRET=sua_chave_secreta_aqui

# Clone este repositório
git clone [https://github.com/seu-usuario/cashflow-api.git](https://github.com/seu-usuario/cashflow-api.git)

# Entre na pasta do projeto
cd cashflow-api

# Execute o build e os testes para garantir que está tudo ok
mvn clean verify

# Inicie a aplicação
mvn spring-boot:run
```
---

## 📄 Documentação da API

A documentação interativa está disponível via Swagger: http://localhost:8080/swagger-ui/index.html

# rodar o projeto
mvn spring-boot:run
