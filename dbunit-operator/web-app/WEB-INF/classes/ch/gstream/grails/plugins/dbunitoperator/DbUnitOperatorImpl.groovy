/*
 *   Copyright 2008-2011 G-Stream Technologies GmbH, http://www.g-stream.ch
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */



package ch.gstream.grails.plugins.dbunitoperator;


import java.io.File
import java.io.InputStream
import java.io.FileInputStream
import java.sql.SQLException
import java.util.List;

import javax.servlet.ServletContext

import groovy.sql.Sql

import org.apache.commons.logging.LogFactory
import org.dbunit.database.DatabaseConfig
import org.dbunit.database.DatabaseConnection
import org.dbunit.dataset.AbstractDataSet
import org.dbunit.dataset.xml.FlatXmlDataSet
import org.dbunit.dataset.xml.FlatXmlProducer
import org.dbunit.dataset.xml.XmlDataSet
import org.dbunit.ext.mysql.MySqlDataTypeFactory
import org.dbunit.operation.DatabaseOperation
import org.dbunit.ext.mssql.InsertIdentityOperation
import org.xml.sax.InputSource
import org.codehaus.groovy.grails.commons.spring.GrailsApplicationContext
import org.codehaus.groovy.grails.support.MockApplicationContext
import org.codehaus.groovy.grails.web.context.ServletContextHolder
import org.springframework.web.context.WebApplicationContext
import org.springframework.web.context.support.WebApplicationContextUtils
import org.dbunit.database.DatabaseSequenceFilter
import org.dbunit.dataset.FilteredDataSet
import javax.sql.DataSource

/**
 * <p>
 * DB-Unit Operator implementation. See 'DbUnitOperator.groovy' for further usage.
 * </p>
 *
 * Which paths and contexts are used for dbunit data files?
 *
 * - When running the grails application:
 *   WebApplicationContext is used --> <project-root>/web-app/
 *   Used during the startup of the grails application, typically when initial data
 *   should be stored through the BootStrap: DbUnitOperator.create().
 *
 * - When running the dbunit test cases:
 *   No context is used --> <project-root>/web-app/
 *   Used when running 'grails test-app'.
 *
 * - When defining absolute paths:
 *   WebApplicationContext, given context or no context is used --> absolute path is used
 *
 * @author Michael Gasche
 */
public class DbUnitOperatorImpl {

    def log = LogFactory.getLog(DbUnitOperatorImpl.class);

    private Configuration config = null
    private WebApplicationContext context = null

    private String initialData = null
    private String operationType = null
    private String dbunitXmlType = null

    /**
     * Default constructor.
     */
    public DbUnitOperatorImpl() {

        this(new Configuration())
    }

    /**
     *
     * Constructor.
     *
     * @param config
     *            data source config object
     */
    public DbUnitOperatorImpl(Configuration config) {

        this.config = config

        this.init()
    }

    /**
     *
     * Constructor.
     *
     * @param config
     *            data source config object
     * @param context
     *            web application context
     */
    public DbUnitOperatorImpl(Configuration config,
                              WebApplicationContext context) {

        this.config = config

        this.context = context

        this.init()
    }

    /**
     * Load appropriate data set from configured (DataSource.groovy) data file
     * and execute DBUnit with configured operation (DataSource.groovy).
     *
     * @throws Exception
     */
    public def create() throws Exception {

        this.innerOperate()
    }

    /**
     * Execute given operation with configured data file (DataSource.groovy).
     *
     * @throws Exception
     */
    public def operate(String operationType, String filePath) throws Exception {

        this.operationType = operationType
        this.initialData = filePath

        this.innerOperate()
    }

    /**
     * Initialize configuration from DataSource.groovy.
     */
    private def init() {

        this.initialData = config.getInitialData()
        this.operationType = config.getInitialOperation()
        this.dbunitXmlType = config.getDbUnitXmlType()

        if (initialData == null)
            throw new IllegalArgumentException(
                    "No 'initialData' property found within environment 'dataSource' section (DataSource.groovy)! Define here the DBUnit XML file.");

        if (operationType == null)
            throw new IllegalArgumentException(
                    "No 'operationType' property found within environment 'dataSource' section (DataSource.groovy)! Define here the DBUnit method.");

        if (dbunitXmlType == null) {
            log.warn "No 'dbunitXmlType' property found (DataSource.groovy); Using 'flat' XML dbunit file."
            dbunitXmlType = 'flat';
        }

        if (context == null) {

            ServletContext sctx = ServletContextHolder.getServletContext();
            if (sctx != null)
                context = WebApplicationContextUtils.getWebApplicationContext(sctx)
        }


    }

    /**
     * Create database connection.
     *
     * @return database connection
     * @throws Exception
     */
    private def DatabaseConnection createDatabaseConnection() throws Exception {

        DatabaseConnection conn = null

        try {

            Sql sql = null

            //we use our configuration based on a jndi configuration
            if (config.isJndiBased()) {
                log.info "this datasource is jndi based..."

                try {
                    def ctx = new javax.naming.InitialContext()

                    DataSource con = ctx.lookup(config.getJndiName())

                    assert con != null, "sorry there was no datasource returned for the jndiName: ${config.getJndiName()}"
                    sql = Sql.newInstance(con.connection)
                }
                catch (Throwable e) {
                    log.error(e.getMessage(), e)
                    throw e
                }
            }
            //we use the standard configuration based on
            //the explicit configuration in the configuration
            else {
                log.info "this datasource is explicit declared..."
                sql = Sql.newInstance(config.getUrl(), config.getUsername(), config.getPassword(), config.getDriver());
            }

            assert sql != null, "the sql instance should never be null!"

            conn = new DatabaseConnection(sql.getConnection());

            // workaround since dbunit complains
            if (!config.isJndiBased()) {
            	String s = config.getDriver();
	            if (s.startsWith("com.mysql")) {
	                conn.getConfig().setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY,new MySqlDataTypeFactory())
	            } else if (s.startsWith("org.hsqldb")) {
	                conn.getConfig().setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY,new org.dbunit.ext.hsqldb.HsqldbDataTypeFactory())
	            } else if (s.startsWith("oracle.jdbc")) {
	                conn.getConfig().setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY,new org.dbunit.ext.oracle.OracleDataTypeFactory())
	            } else if (s.startsWith("org.postgresql")) {
	                conn.getConfig().setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY,new org.dbunit.ext.postgresql.PostgresqlDataTypeFactory())
	            } else if (s.startsWith("org.h2")) {
	                conn.getConfig().setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY,new org.dbunit.ext.h2.H2DataTypeFactory())
	            } else {
	                conn.getConfig().setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY,new org.dbunit.dataset.datatype.DefaultDataTypeFactory())
	            }
            }

        } catch (Exception e) {

            log.error(e.getMessage(), e)
            throw new Exception("Couldn't create database connection!", e)
        }

        return conn
    }

    /**
     * Do appropriate DB unit operation.
     *
     * @throws Exception
     */
    private def innerOperate() throws Exception {

        if (this.initialData == null) {
            log.warn "No initial data file has been set, so DB unit operator quits here."
            return
        }

        if (this.operationType == null) {
            log.warn "No operation type has been set, so DB unit operator quits here."
            return
        }

        DatabaseConnection conn = createDatabaseConnection()

        try {

            def sets = createDataSets()
            sets.each { set ->
                if (config.getOrderTables())
                    getOperation().execute(conn, new FilteredDataSet(new DatabaseSequenceFilter(conn), set))
                else
                    getOperation().execute(conn, set)
            }

        } catch (Exception e) {

            throw new Exception(e.getMessage(), e)

        } finally {
            try {
                conn.close()
            } catch (SQLException e) {
                log.warn "Couldn't close db connection!", e
            }
        }
    }

    /**
     * Create flat or structured XML data-sets.
     *
     * @return data sets
     * @throws Exception
     */
    private def List createDataSets() throws Exception {

        def sets = []

        // create unique root path!
        def realPath
        if (context != null)
        	realPath = context.getServletContext().getRealPath('.')
        else
        	realPath = '.'
        	
        def path = new File(realPath)
        def rootPath = path.getPath()
        if (rootPath.endsWith('.'))
            rootPath = rootPath.substring(0, rootPath.length() - 1)

        // log root path
        log.debug "ROOT-PATH: '" + rootPath + "'."

        InputSource is = null

        this.initialData.split(',').each { currInitData ->

            currInitData = currInitData.toString().trim()

            if (currInitData.length() == 0) {
                log.debug "No initial file specified."
                return sets
            }

            try {
                if (new File(currInitData).isAbsolute() || context == null)
                    is = new InputSource(new FileInputStream(currInitData))
                else
                    is = new InputSource(new FileInputStream(rootPath + File.separator + currInitData))
            } catch (IOException ioex) {
                log.warn "Data file '$currInitData' doesn't exist or isn't readable!", ioex
            }

            if (dbunitXmlType.equals("flat")) {
                final FlatXmlProducer producer = new FlatXmlProducer(is)
                sets.add(new FlatXmlDataSet(producer))
            } else if (dbunitXmlType.equals("structured")) {
                sets.add(new XmlDataSet(is))
            } else
                throw new IllegalArgumentException(
                        "Value `"
                                + this.dbunitXmlType
                                + "` for property `dbunitXmlType` is not allowed or property is missing! Valid values are `flat` or `structured`.")
        }

        return sets

    }

    /**
     * Get appropriate database operation.
     *
     * @return database operation
     */
    private def DatabaseOperation getOperation() {

        if ("UPDATE".equals(this.operationType))
            return DatabaseOperation.UPDATE
        else if ("INSERT".equals(this.operationType))
            return DatabaseOperation.INSERT
        else if ("REFRESH".equals(this.operationType))
            return DatabaseOperation.REFRESH
        else if ("DELETE".equals(this.operationType))
            return DatabaseOperation.DELETE
        else if ("DELETE_ALL".equals(this.operationType))
            return DatabaseOperation.DELETE_ALL
        else if ("CLEAN_INSERT".equals(this.operationType))
            return DatabaseOperation.CLEAN_INSERT
        else if ("NONE".equals(this.operationType))
            return DatabaseOperation.NONE
        else if ("MSSQL_CLEAN_INSERT".equals(this.operationType))
            return InsertIdentityOperation.CLEAN_INSERT
        else if ("MSSQL_INSERT".equals(this.operationType))
            return InsertIdentityOperation.INSERT
        else if ("MSSQL_REFRESH".equals(this.operationType))
            return InsertIdentityOperation.REFRESH
        else if ("TRUNCATE_TABLE".equals(this.operationType))
            return InsertIdentityOperation.TRUNCATE_TABLE
        else
            throw new IllegalArgumentException(
                    "Type must be one of: UPDATE, INSERT, "
                            + "REFRESH, DELETE, DELETE_ALL, CLEAN_INSERT, MSSQL_INSERT, TRUNCATE_TABLE, "
                            + "or MSSQL_REFRESH but was: "
                            + this.operationType)
    }

}
