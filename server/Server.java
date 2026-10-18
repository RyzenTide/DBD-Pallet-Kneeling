import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

/** Dependency-free static file server. Run from the repository root with Java 17+. */
public class Server {
    public static void main(String[] args) throws Exception {
        int port = Integer.parseInt(System.getenv().getOrDefault("PORT", "8080"));
        String host = System.getenv().getOrDefault("HOST", "127.0.0.1");
        Path root = Path.of("public").toRealPath();
        Map<String, String> types = Map.of("html", "text/html; charset=utf-8", "css", "text/css; charset=utf-8",
            "js", "text/javascript; charset=utf-8", "svg", "image/svg+xml", "jpg", "image/jpeg", "jpeg", "image/jpeg");
        HttpServer server = HttpServer.create(new InetSocketAddress(host, port), 0);
        server.createContext("/", exchange -> {
            try {
                String method = exchange.getRequestMethod();
                if (!method.equals("GET") && !method.equals("HEAD")) {
                    exchange.getResponseHeaders().set("Allow", "GET, HEAD");
                    exchange.sendResponseHeaders(405, -1);
                    return;
                }
                String request = exchange.getRequestURI().getPath();
                Path file = root.resolve(request.equals("/") ? "index.html" : request.substring(1)).normalize();
                if (!file.startsWith(root) || !Files.isRegularFile(file) || !file.toRealPath().startsWith(root)) {
                    exchange.sendResponseHeaders(404, -1);
                    return;
                }
                byte[] body = Files.readAllBytes(file);
                String name = file.getFileName().toString();
                String ext = name.substring(name.lastIndexOf('.') + 1);
                var headers = exchange.getResponseHeaders();
                headers.set("Content-Type", types.getOrDefault(ext, "application/octet-stream"));
                headers.set("X-Content-Type-Options", "nosniff");
                headers.set("Content-Security-Policy", "default-src 'self'; img-src 'self' data:; style-src 'self'; script-src 'self'; base-uri 'none'; frame-ancestors 'none'");
                headers.set("Referrer-Policy", "no-referrer");
                headers.set("Content-Length", Integer.toString(body.length));
                exchange.sendResponseHeaders(200, method.equals("HEAD") ? -1 : body.length);
                if (!method.equals("HEAD")) exchange.getResponseBody().write(body);
            } catch (IllegalArgumentException e) {
                exchange.sendResponseHeaders(400, -1);
            } finally {
                exchange.close();
            }
        });
        server.start();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> server.stop(0)));
        System.out.printf("Kneeling Pallets listening on %s:%d%n", host, server.getAddress().getPort());
    }
}
