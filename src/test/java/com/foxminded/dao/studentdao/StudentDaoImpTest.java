package com.foxminded.dao.studentdao;

import org.dbunit.DataSourceBasedDBTestCase;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.Test;
import org.dbunit.dataset.IDataSet;

class StudentDaoImpTest extends DataSourceBasedDBTestCase {

    private static final String driverSQL = "org.h2.Driver";

    static {
        try {
            Class.forName(driverSQL);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected javax.sql.DataSource getDataSource() {
        JdbcDataSource dataSource = new JdbcDataSource();
            dataSource.setURL("jdbc:h2:~/test;DB_CLOSE_DELAY=-1;init=runscript from 'classpath:createTableStudents.sql'");
            dataSource.setUser("sa");
            dataSource.setPassword("");
        return dataSource;
    }


    @Override
    protected IDataSet getDataSet() throws Exception {
        return new FlatXmlDataSetBuilder().build(getClass().getClassLoader().getResourceAsStream("SalaryTestDataSet.xml"));
    }

    @Override
    protected DatabaseOperation getSetUpOperation() throws Exception {
        return DatabaseOperation.REFRESH;
    }

    @Override
    protected DatabaseOperation getTearDownOperation() throws Exception {
        return DatabaseOperation.DELETE_ALL;
    }

    @Test
    void create() throws Exception {
        IDataSet expectedDataset = getDataSet();
        ITable expectedTable = expectedDataset.getTable("students");
        IDataSet databaseDataSet = getConnection().createDataSet();
        ITable actualTable = databaseDataSet.getTable("students");
        assertEquals(expectedTable,actualTable);
        getSetUpOperation();
        getTearDownOperation();
    }

    @Test
    void findAll() {
    }

    @Test
    void findId() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}