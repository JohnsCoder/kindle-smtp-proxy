package proxy.kindle.server;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import proxy.kindle.smtp.SmtpService;

import java.io.IOException;

public class BypassServer {

    public static void Boostrap() throws Exception {
        // Set the port you want to listen to
        int port = Integer.parseInt(System.getenv("PORT"));

        // Create the Jetty Server instance
        Server server = new Server(port);

        // Create the Servlet context and handler
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");

        // Add a simple servlet that will return "Hello World"
        context.addServlet(new ServletHolder(new HttpServlet() {
            @Override
            protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
                resp.getWriter().println("Hello, World!");

            }
        }), "/hello");

        // Set the context to the server
        server.setHandler(context);

        // Start the server
        server.start();
        new SmtpService().Interceptor();
        System.out.println("Server started on port " + port);

        // Join the server to keep it running
        server.join();

    }
}
