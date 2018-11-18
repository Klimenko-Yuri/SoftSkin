package com.skinexpert.dao.impl;


import com.skinexpert.dao.ComponentDao;
import com.skinexpert.entity.Component;
import com.skinexpert.entity.TypeComponent;
import org.dbunit.Assertion;
import org.dbunit.DBTestCase;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;

import java.util.ArrayList;
import java.util.List;

public class HibernateComponentDaoImplTest extends DBTestCase {
    private ComponentDao componentDao;

    public HibernateComponentDaoImplTest(String name) {
        super(name);

        System.setProperty( PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS, "org.h2.Driver" );
        System.setProperty( PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL, "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME, "sa");
        System.setProperty( PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD, "" );

        this.componentDao = new HibernateComponentDaoImpl();
    }


    @Override
    protected IDataSet getDataSet() throws Exception {
        return new FlatXmlDataSetBuilder().build(
                Thread.currentThread().getContextClassLoader().getResourceAsStream("component/ComponentDataSet.xml"));
    }

    public void testSaveOrUpdate_shouldCreateNewComponent() throws Exception {
        Component component = new Component("cognac", "useful with lemon", TypeComponent.MIDDLE, true);

        componentDao.saveOrUpdate(component);

        IDataSet actualDataSet = getConnection().createDataSet();
        ITable actualTable = actualDataSet.getTable("component");

        IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(
                ClassLoader.getSystemResourceAsStream("component/component-dataset-save.xml"));
        ITable expectedTable = expectedDataSet.getTable("component");

        Assertion.assertEquals(expectedTable, actualTable);
    }

    public void testDeleteById() throws Exception {
        componentDao.deleteById(2);
        componentDao.deleteById(3);

        IDataSet actualDataSet = getConnection().createDataSet();
        ITable actualTable = actualDataSet.getTable("component");

        IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(
                ClassLoader.getSystemResourceAsStream("component/component-dataset-delete.xml"));
        ITable expectedTable = expectedDataSet.getTable("component");

        Assertion.assertEquals(expectedTable, actualTable);
    }

    public void testFindById() {
        Component expectedComponent = new Component("water", "some description about", TypeComponent.GOOD, true);
        expectedComponent.setId(1L);
        assertEquals(expectedComponent, componentDao.findById(1L));
    }

    public void testFindByName() {
        Component expectedComponent = new Component("martini", "some description about", TypeComponent.MIDDLE, true);
        expectedComponent.setId(4L);
        assertEquals(expectedComponent, componentDao.findByName("martini"));
    }

    public void testGetAll() {
        List<Component> expectedList = new ArrayList<>();
        Component first = new Component("water", "some description about", TypeComponent.GOOD, true);
        first.setId(1L);
        Component second = new Component("absent", "some description about", TypeComponent.MIDDLE, true);
        second.setId(2L);
        Component third = new Component("vodka", "some description about", TypeComponent.DANGER, true);
        third.setId(3L);
        Component fourth = new Component("martini", "some description about", TypeComponent.MIDDLE, true);
        fourth.setId(4L);
        Component fifth = new Component("wine", "some description about", TypeComponent.GOOD, true);
        fifth.setId(5L);

        expectedList.add(first);
        expectedList.add(second);
        expectedList.add(third);
        expectedList.add(fourth);
        expectedList.add(fifth);

        assertEquals(expectedList, componentDao.getAll());
    }

    public void findNameBySubstring() {

    }
}