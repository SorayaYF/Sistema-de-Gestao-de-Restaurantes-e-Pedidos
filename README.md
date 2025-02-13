# 🍽 Sistema de Gestão de Restaurantes e Pedidos

![Java](https://img.shields.io/badge/Java-21-007396?style=for-the-badge&logo=java&logoColor=white)
![OOP](https://img.shields.io/badge/POO-Orientação%20a%20Objetos-000000?style=for-the-badge)
![GitHub](https://img.shields.io/badge/GitHub-Repository-181717?style=for-the-badge&logo=github)
![Último Commit](https://img.shields.io/github/last-commit/SorayaYF/Sistema-de-Gestao-de-Restaurantes-e-Pedidos?style=for-the-badge)

O **Sistema de Gestão de Restaurantes e Pedidos** é um projeto desenvolvido em **Java 21**, que explora conceitos de **Programação Orientada a Objetos (POO)**, aplicando princípios de **Clean Code** para oferecer um sistema de gerência de mesas, pedidos e pagamentos em um restaurante.

---

## 📜 Descrição

Este projeto permite ao usuário:
- **Gerenciar Mesas** (criar, adicionar clientes, liberar clientes, etc.)
- **Abrir Pedidos** (no local ou para entrega), garantindo que as mesas não fiquem vazias quando houver pedidos no local.
- **Fechar Pedidos** com diferentes formas de pagamento (cartão ou dinheiro), incluindo verificação de limite, troco, etc.
- **Listar Pedidos Abertos** e **Histórico de Pedidos Fechados**.

Também demonstra o uso de exceções personalizadas para lidar com casos de mesa duplicada, mesa sobrecarregada, pagamentos recusados e pedidos inválidos.

---

## 🚀 Como Executar

1. **Clone o repositório**:
    ```bash
    git clone https://github.com/SorayaYF/Sistema-de-Gestao-de-Restaurantes-e-Pedidos.git
    cd Sistema-de-Gestao-de-Restaurantes-e-Pedidos
    ```

2. **Compile e Execute**:
   - Abra o projeto em sua IDE de preferência (IntelliJ, Eclipse, VSCode, etc.) ou compile no terminal:
     ```bash
     javac -cp src src/br/com/ifsc/poo/restaurante/SistemaGestaoRestaurante.java
     java -cp src br.com.ifsc.poo.restaurante.SistemaGestaoRestaurante
     ```
   - As interações ocorrerão através de janelas de diálogo (`JOptionPane`).

3. **Siga as Instruções**:
   - No menu principal, escolha as opções conforme desejado para **adicionar mesas**, **abrir pedidos**, **fechar pedidos**, etc.

---

## 🍜 Funcionalidades

1. **Adicionar Mesas**  
   - Evita criação duplicada (exceção `MesaJaExistenteException`).
   - Controla capacidade e ocupação atual.

2. **Abrir Pedidos**  
   - **No Local**: vincula a mesa, adiciona clientes, garante que a ocupação seja > 0.  
   - **Para Entrega**: aplica taxa de entrega fixa.  
   - Lança exceção (`PedidoInvalidoException`) se o valor base for inválido.

3. **Fechar Pedidos**  
   - Retira o pedido da lista de abertos e salva no histórico.  
   - Permite escolher **Cartão** ou **Dinheiro** (com troco), usando exceções de pagamento se necessário.

4. **Liberar Clientes**  
   - No caso de pedido no local, ao fechar o pedido, pergunta quantos clientes serão liberados da mesa selecionada.

5. **Listar Pedidos**  
   - **Abertos**: Exibe todos os pedidos em andamento.  
   - **Histórico**: Exibe todos os pedidos já finalizados com valor total calculado.

---

## 💻 Tecnologias Utilizadas

- **Java 21**
- **Maven**  
---
