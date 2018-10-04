package com.skinexpert.service;

import com.skinexpert.entity.Component;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Created by Mihail Kolomiets on 09.08.18.
 */
public class ComponentService {

    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    private EntityManagerFactory factory = Persistence.createEntityManagerFactory("SoftSkin");
    private EntityManager manager = factory.createEntityManager();

    public List<Component> getListOfComponents(List<String> list) {

        manager.getTransaction().begin();

        Session session = (Session) manager.getDelegate();
        session.beginTransaction();
        Criteria criteria = session.createCriteria(Component.class);
        List<Component> name = criteria.add(Restrictions.in("name", list)).list();
        session.getTransaction().commit();
        return name;

    }

    public Component addComponent(Component component) {
        manager.getTransaction().begin();
        component = manager.merge(component);
        manager.getTransaction().commit();
        return component;
    }

    public Component get(long id) {
        Component component;

        manager.getTransaction().begin();
        component = manager.find(Component.class, id);
        manager.getTransaction().commit();

        return component;
    }

    public Component findByName(String name) {
        Component component;

        manager.getTransaction().begin();
        Session session = (Session) manager.getDelegate();
        Criteria criteria = session.createCriteria(Component.class);
        criteria.add(Restrictions.eq("name", name));

        try {
            component = (Component) criteria.uniqueResult();
        } catch (HibernateException he) {
            System.out.println("no found" + name);
            return null;
        }

        manager.getTransaction().commit();

        return component;
    }

    public List<Component> getAll() {

        List<Component> componentList;

        manager.getTransaction().begin();
        componentList = manager.createQuery("SELECT c from Component c").getResultList();
        manager.getTransaction().commit();

        return componentList;
    }

    public List<Component> findNameBySubstring(String search) {
        List<Component> components;
        search = search.toUpperCase();

        manager.getTransaction().begin();
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Component> query = builder.createQuery(Component.class);
        Root<Component> componentRoot = query.from(Component.class);
        query.select(componentRoot);
        Predicate predicate = builder.like(builder.upper(componentRoot.get("name")),
                "%" + search + "%");
        query.where(predicate);
        components = manager.createQuery(query).getResultList();
        manager.getTransaction().commit();

        return components;
    }

    public Component deleteById(long id) {

        Component component;

        manager.getTransaction().begin();
        component = manager.find(Component.class, id);
        manager.remove(component);
        manager.getTransaction().commit();

        return component;
    }


}
