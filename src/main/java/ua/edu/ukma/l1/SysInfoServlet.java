package ua.edu.ukma.l1;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "sysInfoServlet", value = "/sysinfo")
public class SysInfoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html; charset=UTF-8");
        Runtime runtime = Runtime.getRuntime();

        int cores = runtime.availableProcessors();
        long totalMemory = runtime.totalMemory() / (1024 * 1024); // Конвертація в MB
        long freeMemory = runtime.freeMemory() / (1024 * 1024);   // Конвертація в MB
        String osName = System.getProperty("os.name");

        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h2>Характеристики сервера:</h2>");
        out.println("<ul>");
        out.println("<li><b>ОС:</b> " + osName + "</li>");
        out.println("<li><b>Кількість ядер CPU:</b> " + cores + "</li>");
        out.println("<li><b>Загальна RAM (JVM):</b> " + totalMemory + " MB</li>");
        out.println("<li><b>Вільна RAM (JVM):</b> " + freeMemory + " MB</li>");
        out.println("</ul>");
        out.println("</body></html>");
    }
}
