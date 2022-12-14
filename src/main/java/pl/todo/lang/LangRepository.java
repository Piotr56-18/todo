package pl.todo.lang;

import pl.todo.HibernateUtil;

import java.util.List;
import java.util.Optional;

public class LangRepository {

    public List<Lang>findAll(){
        var session = HibernateUtil.getSessionFactory().openSession();
        var transaction = session.beginTransaction();
        var result = session.createQuery("from Lang", Lang.class).list();
        transaction.commit();
        session.close();
        return result;
    }

    public Optional<Lang> finfById(Integer id){
        var session = HibernateUtil.getSessionFactory().openSession();
        var transaction = session.beginTransaction();
        var result = session.get(Lang.class, id);
        transaction.commit();
        session.close();
        return Optional.ofNullable(result);
    }
    public LangRepository(){
    }
}
