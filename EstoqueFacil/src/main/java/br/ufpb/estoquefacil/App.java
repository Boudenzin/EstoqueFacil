package br.ufpb.estoquefacil;
import io.javalin.Javalin;
import io.javalin.http.HttpStatus;

public class App {

    public static void main(String[] args) {

        // 2. Configuração e Inicialização
        var app = Javalin.create(config -> {
            config.requestLogger.http((ctx, ms) -> {
                System.out.println("Pedido recebido: " + ctx.method());
            });
            // Outras configurações (CORS, arquivos estáticos, etc)
        }).start(7070); // Define a porta do servidor

        // 3. Definição de Rotas (Endpoints)
        app.get("/", ctx -> ctx.result("Servidor de Estoque Online"));

        // Exemplo de rota de API
        app.get("/status", ctx -> {
            ctx.status(HttpStatus.OK);
            ctx.result("Sistema operando normalmente");
        });

        // 4. Tratamento de Exceções Global (Opcional, mas recomendado)
        app.exception(Exception.class, (e, ctx) -> {
            ctx.status(500).result("Erro interno no servidor");
        });
    }
}