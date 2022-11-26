package pl.todo.todo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "TODO", urlPatterns = "/api/todos/*")
public class TODOServlet extends HttpServlet {
    private final Logger logger = LoggerFactory.getLogger(TODOServlet.class);

    private ObjectMapper mapper;
    private TODORepository repository;

    public TODOServlet() {
        this(new TODORepository(), new ObjectMapper());
    }

    public TODOServlet(TODORepository repository, ObjectMapper mapper) {
        this.mapper = mapper;
        this.repository = repository;
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("Got request with parameters: " + req.getParameterMap());
        resp.setContentType("application/jason;charset=UTF-8");
        mapper.writeValue(resp.getOutputStream(), repository.findAll());
    }
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/jason;charset=UTF-8");
        var todoPatch = req.getPathInfo();
        try{
            todoPatch = todoPatch.replaceFirst("/","");
            var todoNumber = Integer.parseInt(todoPatch);
            mapper.writeValue(resp.getOutputStream(), repository.toggleTodo(todoNumber));

        }catch (NumberFormatException e){
            logger.warn("Wrong patch used: " + todoPatch);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/jason;charset=UTF-8");
        var newTODO = mapper.readValue(req.getInputStream(),TODO.class);
        mapper.writeValue(resp.getOutputStream(), repository.addTodo(newTODO));
    }
}
