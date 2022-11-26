package pl.todo.todo;
import pl.todo.HibernateUtil;

import java.util.List;

class TODORepository {

    TODORepository() {
    }
    List<TODO> findAll(){
        var session = HibernateUtil.getSessionFactory().openSession();
        var transaction = session.beginTransaction();
        var result = session.createQuery("from TODO", TODO.class).list();
        transaction.commit();
        session.close();
        return result;
    }
    public TODO toggleTodo(Integer id){
        var session = HibernateUtil.getSessionFactory().openSession();
        var transaction = session.beginTransaction();
        var result = session.get(TODO.class, id);
        result.setDone(!result.isDone());
        transaction.commit();
        session.close();
        return result;
    }
    public TODO addTodo(TODO newTODO){
        var session = HibernateUtil.getSessionFactory().openSession();
        var transaction = session.beginTransaction();
        session.persist(newTODO);
        transaction.commit();
        session.close();
        return newTODO;
    }
}
