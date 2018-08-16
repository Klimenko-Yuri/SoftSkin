package com.skinexpert.service;

import com.skinexpert.entity.Component;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

/**
 * Created by Mihail Kolomiets on 09.08.18.
 */
public class ComponentService {

    private EntityManagerFactory factory = Persistence.createEntityManagerFactory("SoftSkin");
    private EntityManager manager = factory.createEntityManager();

    public Component addComponent(Component component) {
        manager.getTransaction().begin();
        component = manager.merge(component);
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

    public Component deleteById(long id) {

        Component component;

        manager.getTransaction().begin();
        component = manager.find(Component.class, id);
        manager.remove(component);
        manager.getTransaction().commit();

        return component;
    }


}
