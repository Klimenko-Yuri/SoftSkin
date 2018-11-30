package com.skinexpert.dao.impl;

import com.skinexpert.config.HibernateConfig;
import com.skinexpert.dao.ComponentDao;
import com.skinexpert.entity.Component;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import javax.persistence.*;
import javax.persistence.criteria.*;
import java.util.List;

/**
 * Created by Mihail Kolomiets on 09.08.18.
 */
public class HibernateComponentDaoImpl implements ComponentDao {

    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    private EntityManager manager;
    private Logger logger;

    public HibernateComponentDaoImpl() {
        this.logger = Logger.getLogger(this.getClass());
        this.manager = HibernateConfig.getFactory().createEntityManager();
    }

    public Component saveOrUpdate(Component component) {
        EntityTransaction transaction = manager.getTransaction();
        try {
            transaction.begin();
            component = manager.merge(component);
            transaction.commit();
        } catch (RuntimeException e) {
            logger.error("Exception during add component request", e);
            transaction.rollback();
            throw e;
        }
        return component;
    }

    public Component deleteById(long id) {
        EntityTransaction transaction = manager.getTransaction();
        try {
            transaction.begin();
            Component component = findById(id);
            manager.remove(component);
            transaction.commit();
            return component;
        } catch (RuntimeException e) {
            logger.error("Exception during delete component by id", e);
            transaction.rollback();
            throw e;
        }
    }

    public Component findById(long id) {
        try {
            return manager.find(Component.class, id);
        } catch (RuntimeException e) {
            logger.error("Exception during finding component by id", e);
            throw e;
        }
    }

    public Component findByName(String name) {
        try {
            Session session = (Session) manager.getDelegate();
            Criteria criteria = session.createCriteria(Component.class);
            criteria.add(Restrictions.eq("name", name));

            //Add new restriction for finding by name in English
            criteria.add(Restrictions.eq("nameENG", name));

            return (Component) criteria.uniqueResult();
        } catch (RuntimeException e) {
            logger.error("Exception during find by name request", e);
            throw e;
        }
    }

    public List<Component> getAll() {
        try {
            return manager.createQuery("SELECT c from Component c").getResultList();
        } catch (RuntimeException e) {
            logger.error("Exception during getAll request", e);
            throw e;
        }
    }

    public List<Component> findNameBySubstring(String search) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Component> query = builder.createQuery(Component.class);
        Root<Component> componentRoot = query.from(Component.class);
        query.select(componentRoot);

        Predicate predicateRUS = builder.like(builder.upper(componentRoot.get("name")),
                "%" + search.toUpperCase() + "%");
        Predicate predicateENG = builder.like(builder.upper(componentRoot.get("nameENG")),
                "%" + search.toUpperCase() + "%");
        Predicate predicate = builder.or(predicateRUS,predicateENG);
        query.where(predicate);
        return manager.createQuery(query).getResultList();
    }

    public List<Component> getListOfComponents(List<String> stringList) {
        Session session = (Session) manager.getDelegate();
        Criteria criteria = session.createCriteria(Component.class);
        return criteria.add(Restrictions.in("name", stringList)).list();
    }


}
