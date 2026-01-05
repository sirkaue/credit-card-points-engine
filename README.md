# 💳 Credit Card Points Engine

> **Motor de fidelidade inteligente** para cálculo de pontos de cartão de crédito, construído com **Clean Architecture**, **DDD Lite** e **SOLID**, com foco em extensibilidade, testabilidade e qualidade de código.

---

## 📋 Índice

- [Sobre o Projeto](#-sobre-o-projeto)
- [O Problema](#-o-problema)
- [A Solução](#-a-solução)
- [Conceitos e Práticas Aplicadas](#-conceitos-e-práticas-aplicadas)
- [Começando](#-começando)
- [Uso da API](#-uso-da-api)
- [Testes e Qualidade](#-testes-e-qualidade)

---

## 🎯 Sobre o Projeto

Este projeto nasceu de uma pergunta simples: **"Como escrever código que sobrevive às mudanças?"**

Desenvolvedores experientes sabem que requisitos mudam, frameworks evoluem e equipes crescem. Por isso, construí este motor de cálculo de pontos de fidelidade não apenas para resolver um problema de negócio, mas para demonstrar na prática como aplicar **arquitetura evolutiva** e **design orientado a princípios**.

### O Diferencial

Enquanto muitos projetos focam apenas em fazer o código funcionar, este vai além:

- 🎯 **Clean Architecture na prática real** - Não é teoria de livro, é código que você pode ler, entender e modificar sem medo
- 🧩 **SOLID aplicado, não decorado** - Cada classe respeita os princípios, tornando extensões triviais e bugs raros
- 🎨 **DDD Lite que faz sentido** - Entidades ricas que validam suas próprias regras, Value Objects imutáveis, separação clara entre domínio e infraestrutura
- 🔍 **Qualidade mensurável** - CI/CD com SonarQube garantindo que cada commit mantém padrões altos de qualidade
- 🧪 **Testabilidade como design driver** - Se não pode ser testado facilmente, o design precisa melhorar

### Por Que Este Projeto Importa

Imagine entrar em uma base de código onde:

- Você pode adicionar um novo tipo de programa de fidelidade **sem alterar código existente**
- Os testes rodam em **milissegundos** porque não dependem de Spring Context
- A lógica de negócio está **completamente isolada** do framework
- Cada mudança passa por **análise automática de qualidade** antes de ser integrada

**Isso não é acidental - é arquitetura intencional.**

---

## 🎯 O Problema

Em uma **fintech** que emite cartões de crédito, cada transação deve gerar **pontos de fidelidade**, mas o cálculo envolve múltiplos fatores e desafios arquiteturais:

### Desafios Enfrentados

- ❌ **Transações em diferentes moedas** (BRL, USD, EUR...) exigindo normalização
- ❌ **Múltiplas categorias de cartão** (SILVER, GOLD, PLATINUM) com regras distintas
- ❌ **Diferentes programas de fidelidade** que alteram cálculos dinamicamente
- ❌ **Regras de negócio voláteis** que mudam frequentemente
- ❌ **Necessidade de extensibilidade** sem quebrar código existente
- ❌ **Alta cobertura de testes** para garantir confiabilidade
- ❌ **Isolamento da lógica de negócio** de frameworks e infraestrutura

---

## 💡 A Solução

Este projeto implementa um **motor de cálculo de pontos** robusto e extensível, aplicando as melhores práticas de engenharia de software:

### Soluções Implementadas

- ✅ **Clean Architecture** com 3 camadas isoladas e dependências invertidas
- ✅ **Domain-Driven Design Lite** (Entities ricas, Value Objects, Domain Services)
- ✅ **SOLID aplicado** em toda a base de código
- ✅ **Strategy Pattern** para extensibilidade
- ✅ **Dependency Inversion** garantindo testabilidade
- ✅ **CI/CD automatizado** com GitHub Actions + SonarQube
- ✅ **Alta cobertura de testes** unitários e de integração

### Como Funciona

**Fórmula:** `PONTOS = VALOR_USD × MULTIPLICADOR_ESTRATÉGIA × MULTIPLICADOR_CARTÃO`

**Exemplo:**

- Compra de 100 BRL (÷ 6.0 = 16.67 USD)
- Estratégia TRAVEL (2.0x)
- Cartão GOLD (1.5x)
- **Resultado:** 50 pontos

---

## 💡 Conceitos e Práticas Aplicadas

### 🏛️ Clean Architecture

A aplicação segue o modelo de **Clean Architecture** proposto por Robert C. Martin (Uncle Bob), organizando o código em três camadas com dependências que **sempre apontam para dentro**:

**Domain Layer (núcleo)** - Contém as regras de negócio puras: entidades ricas (`Transaction`, `Card`), value objects imutáveis (`CardType`, `Currency`) e interfaces de estratégias. Sem dependências externas, completamente testável e reutilizável.

**Application Layer (casos de uso)** - Orquestra a lógica de negócio através de interactors e define contratos via ports (interfaces). Depende apenas do domínio, permanecendo independente de frameworks.

**Infrastructure Layer (adaptadores)** - Implementa os detalhes técnicos: controllers REST, mappers e configurações do Spring. É a única camada que conhece frameworks externos e pode ser substituída sem afetar o core.

**Benefícios alcançados:**

- ✅ Lógica de negócio completamente isolada do Spring Boot
- ✅ Facilidade para trocar frameworks ou adicionar novos adaptadores
- ✅ Testes unitários sem necessidade de contexto Spring
- ✅ Regras de negócio reutilizáveis em diferentes contextos

### 🎨 Domain-Driven Design (DDD Lite)

Aplicação de conceitos táticos do DDD:

**Entities (Entidades Ricas):**

```java
public class Transaction {
    // Não é apenas um DTO, contém comportamento e invariantes
    private final BigDecimal amount;
    private final Currency currency;
    
    // Valida regras de negócio no construtor
    public Transaction(...) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidTransactionException("Amount must be positive");
        }
        // ...
    }
}
```

**Value Objects:**

```java
public enum CardType {
    SILVER(BigDecimal.ONE),           // 1.0x
    GOLD(new BigDecimal("1.5")),      // 1.5x
    PLATINUM(new BigDecimal("2.0"));  // 2.0x
    
    // Comportamento encapsulado no próprio enum
    public BigDecimal apply(BigDecimal points) {
        return points.multiply(this.multiplier);
    }
}
```

**Domain Services:**

```java
// Estratégias de cálculo como serviços de domínio
public interface PointsStrategy {
    BigDecimal calculatePoints(Transaction transaction);
}
```

### 🔨 Princípios SOLID

#### **S - Single Responsibility Principle**

Cada classe tem uma única responsabilidade:

- `TransactionMapper`: apenas converte Request → Domain
- `PointsCalculationResult`: apenas encapsula resultado
- `CalculatePointsInteractor`: apenas orquestra o caso de uso

#### **O - Open/Closed Principle**

Aberto para extensão, fechado para modificação:

```java
// Para adicionar nova estratégia, basta criar nova implementação
public class VIPPointsStrategy implements PointsStrategy {
    @Override
    public BigDecimal calculatePoints(Transaction transaction) {
        return transaction.getAmount().multiply(new BigDecimal("3.0")); // 3x pontos
    }
}
// Nenhum código existente precisa ser modificado!
```

#### **L - Liskov Substitution Principle**

Todas as estratégias são intercambiáveis:

```java
PointsStrategy strategy = strategyPort.getStrategy(strategyType);
// Qualquer implementação funciona corretamente
BigDecimal points = strategy.calculatePoints(transaction);
```

#### **I - Interface Segregation Principle**

Interfaces coesas e específicas:

```java
// Ports segregados por responsabilidade
public interface PointsStrategyPort { ... }
public interface CardTypeStrategyPort { ... }
```

#### **D - Dependency Inversion Principle**

Dependência de abstrações, não de implementações:

```java
public class CalculatePointsInteractor {
    private final PointsStrategyPort strategyPort;  // Interface, não classe concreta
    
    // Implementação concreta injetada pelo framework
    public CalculatePointsInteractor(PointsStrategyPort strategyPort) {
        this.strategyPort = strategyPort;
    }
}
```

### 🎭 Design Patterns

**Strategy Pattern:**

```java
// Algoritmo selecionado em runtime
PointsStrategy strategy = strategyPort.getStrategy(transaction.getStrategyType());
BigDecimal basePoints = strategy.calculatePoints(transaction);
```

**Mapper Pattern:**

```java
// Isola conversão entre camadas
Transaction tx = transactionMapper.toTransaction(request);
PointsResponse response = responseMapper.toResponse(tx, result);
```

### 🔄 CI/CD e Qualidade de Código

**Pipeline Automatizado:**

```yaml
GitHub Actions → Build → Tests → SonarQube Analysis → Docker Build
```

**Análise de Qualidade (SonarQube):**

- ✅ Análise de code smells
- ✅ Detecção de bugs potenciais
- ✅ Verificação de vulnerabilidades de segurança
- ✅ Cobertura de testes (com JaCoCo)
- ✅ Análise de duplicação de código
- ✅ Quality Gates configurados

**Ferramentas de Teste:**

- **JUnit 5**: Framework de testes
- **Mockito**: Mocks e stubs para isolamento
- **JaCoCo**: Relatórios de cobertura de código

---

## Clean Architecture

A aplicação segue o modelo de **Clean Architecture** proposto por Robert C. Martin, organizando o código em três camadas com dependências que **sempre apontam para dentro**:

**Domain Layer** - Regras de negócio puras com entidades ricas (`Transaction`, `Card`), value objects (`CardType`, `Currency`) e estratégias. Sem dependências externas, completamente testável.

**Application Layer** - Orquestra a lógica através de interactors e define contratos via ports (interfaces). Depende apenas do domínio.

**Infrastructure Layer** - Implementa detalhes técnicos: controllers REST, mappers e configurações Spring. Única camada que conhece frameworks externos.

### 🎨 DDD Lite & SOLID

Aplicação prática de conceitos táticos do DDD e todos os 5 princípios SOLID:

- **Entities Ricas**: Não são DTOs anêmicos, validam suas próprias regras
- **Value Objects**: Imutáveis com comportamento encapsulado
- **Single Responsibility**: Cada classe tem um único propósito
- **Open/Closed**: Extensível sem modificação (novas estratégias apenas implementam interface)
- **Liskov Substitution**: Todas as estratégias são intercambiáveis
- **Interface Segregation**: Ports segregados por responsabilidade
- **Dependency Inversion**: Dependência de abstrações, não de implementações

### 🎭 Design Patterns

- **Strategy Pattern** - Algoritmos de cálculo selecionáveis em runtime
- **Mapper Pattern** - Isolamento de conversões entre camadas

### CI/CD e Qualidade

Pipeline automatizado com GitHub Actions executando a cada push: build, testes unitários, análise de cobertura (JaCoCo) e quality gates do SonarQube. Cobertura mínima de 80%, zero tolerância para bugs e vulnerabilidades.

---

## 🛠️ Tecnologias

Construído com **Java 21** (Records, Pattern Matching, Virtual Threads) e **Spring Boot 3.x**, demonstrando uso de frameworks sem acoplamento. Testes com **JUnit 5** e **Mockito**, cobertura via **JaCoCo**, qualidade garantida por **SonarQube/SonarCloud**.

---

## 🚀 Começando

### Pré-requisitos

```bash
# Verificar versão do Java
java -version  # Deve ser 21+

# Verificar versão do Maven
mvn -version   # Deve ser 3.9+
```

### Instalação

```bash
# 1. Clonar o repositório
git clone https://github.com/seu-usuario/credit-card-points-engine.git
cd credit-card-points-engine

# 2. Compilar o projeto
mvn clean install

# 3. Executar a aplicação
mvn spring-boot:run

# 4. Acessar a API
# http://localhost:8080/api/points/calculate
```

---

## 📡 Uso da API

### Endpoint Principal

```
POST /api/points/calculate
Content-Type: application/json
```

### Request Body

```json
{
  "cardId": "card-123",              // ID único do cartão
  "cardType": "PLATINUM",            // CLASSIC | GOLD | PLATINUM
  "owner": "João Silva",             // Nome do titular
  "currency": "BRL",                 // BRL | USD | EUR
  "amount": "1000",                  // Valor da transação
  "timestamp": "2025-12-14T10:00:00Z", // ISO 8601
  "strategyType": "TRAVEL"           // DEFAULT | CASHBACK | TRAVEL
}
```

### Response

```json
{
  "transactionId": "uuid",           // ID único da transação
  "cardType": "PLATINUM",            // Categoria do cartão
  "strategyType": "TRAVEL",          // Estratégia aplicada
  "currency": "USD",                 // Moeda base (sempre USD)
  "amountUsd": "166.67",             // Valor convertido (2 decimais)
  "points": 666                      // Pontos calculados (inteiro)
}
```

---

## 🧪 Testes e Qualidade

### Estratégia de Testes

Pirâmide de testes com foco em testes unitários rápidos (milissegundos, sem Spring Context). Testes de domínio validam entidades e estratégias isoladamente, testes de aplicação verificam orquestração completa dos casos de uso.

```bash
# Executar testes
mvn clean test

# Gerar relatório de cobertura
mvn clean test jacoco:report
```

### Integração com SonarQube

Integrado ao **SonarCloud** com Quality Gates rigorosos: cobertura > 80%, duplicação < 3%, ratings A para manutenibilidade/confiabilidade/segurança. Análise automática via GitHub Actions a cada push.

**Benefícios:** Refatoração segura, documentação viva através de testes, feedback rápido via CI/CD, qualidade contínua monitorada.

---

## 📄 Licença

MIT License - Veja o arquivo [LICENSE](LICENSE) para detalhes.

---

## 👤 Autor

**Kauê Silva**

- GitHub: [@sirkaue](https://github.com/sirkaue)
- LinkedIn: [linkedin.com/in/sirkaue](https://linkedin.com/in/sirkaue)

---

## 💬 Reflexão Final

Software não é apenas sobre fazer funcionar - é sobre **fazer bem feito**. Este projeto demonstra que arquitetura limpa não é complexidade desnecessária, mas uma forma inteligente de lidar com a complexidade inevitável do software real.

**Para recrutadores e líderes técnicos:** Este código reflete minha abordagem ao desenvolvimento - pragmática, fundamentada em princípios e sempre focada em entregar valor através de qualidade sustentável.

---

<div align="center">

**Código limpo não é acidente, é escolha.**

⭐ Se este projeto inspirou você, deixe uma estrela no repositório!

</div>
