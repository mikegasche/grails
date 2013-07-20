/*
 *   Copyright 2008-2011 G-Stream Technologies GmbH
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

package ch.gstream.grails.plugins.dbunitoperator

import grails.util.Holders
import org.apache.commons.logging.LogFactory


/**
 * <p>
 * Configuration loads and holds the configuration parameters for
 * dbunit-operator.
 * </p>
 *
 * @author Michael Gasche
 */
class Configuration {

    def log = LogFactory.getLog(getClass())

    /**
     * DBUnit XML type: flat
     */
    public static final String XML_TYPE_FLAT = "flat"
    /**
     * DBUnit XML type: structured
     */
    public static final String XML_TYPE_STRUCTURED = "structured"

    private static final String KEY_DATASOURCE = "dataSource"
    private static final String KEY_INITIAL_DATA = "initialData"
    private static final String KEY_INITIAL_OPERATION = "initialOperation"
    private static final String KEY_XML_TYPE = "dbunitXmlType"
    private static final String KEY_ORDER_TABLES = "orderTables"
	private static final String KEY_QUALIFIED_TABLE_NAMES = "qualified_table_names"

    private ConfigObject config

    private String initialData
    private String operationType
    private String dbunitXmlType
    private boolean orderTables = false
	private boolean qualifiedTableNames = false

    private String url
    private String username
    private String password
    private String driver

    private String jndiName = null

    /**
     * Constructor loading configuration parameters from DataSource.groovy.
     */
    Configuration() {
        this((ConfigObject) Holders.config.get(KEY_DATASOURCE))
    }

    /**
     * Constructor.
     *
     * @param config
     *            configuration object
     */
    Configuration(ConfigObject config) {

        this.config = config

        this.dbunitXmlType = config.getProperty(KEY_XML_TYPE)
        this.initialData = config.getProperty(KEY_INITIAL_DATA)
        this.operationType = config.getProperty(KEY_INITIAL_OPERATION)

        this.orderTables = false
        Object o = config.getProperty(KEY_ORDER_TABLES)
        if (o != null) {
            this.orderTables = Boolean.getBoolean(config.getProperty(KEY_ORDER_TABLES).toString())
        }
		
		this.qualifiedTableNames = false
		Object oqtn = config.getProperty(KEY_QUALIFIED_TABLE_NAMES)
		if (oqtn != null) {
			this.qualifiedTableNames = Boolean.getBoolean(config.getProperty(KEY_QUALIFIED_TABLE_NAMES).toString())
		}

        log.debug "ORDER TABLES = " + this.orderTables

        this.url = config.getProperty("url")
        this.username = config.getProperty("username")
        this.password = config.getProperty("password")
        this.driver = config.getProperty("driverClassName")
        this.jndiName = config.getProperty("jndiName")
    }

    /**
     * Get data source.
     *
     * @return data source
     */
    ConfigObject getDataSource() {
        return this.config
    }

    /**
     * Get initial data.
     *
     * @return initial data
     */
    String getInitialData() {
        return this.initialData
    }

    /**
     * Get initial operation.
     *
     * @return initial operation
     */
    String getInitialOperation() {
        return this.operationType
    }

    /**
     * Get dbunit operation type.
     *
     * @return dbunit operation type
     */
    String getDbUnitXmlType() {
        return this.dbunitXmlType
    }

    /**
     * Get db url.
     *
     * @return db url
     */
    String getUrl() {
        return url
    }

    /**
     * Get db user name.
     *
     * @return db user name
     */
    String getUsername() {
        return username
    }

    /**
     * Get db password.
     *
     * @return db password
     */
    String getPassword() {
        return password
    }

    /**
     * Get db driver class name.
     *
     * @return db driver class name
     */
    String getDriver() {
        return driver
    }

    /**
     * Resolve table dependencies and order tables?
     *
     * @return <code>true</code> if table dependencies
     *         should be resolved and tables be ordered,
     *         otherwise <code>false</code>
     */
    boolean getOrderTables() {
        return orderTables
    }

    /**
     * A simple test if this datasource is JNDI based.
     * @return  <code>true</code> if config is JNDI based,
     *         otherwise <code>false</code>
     */
    boolean isJndiBased() {

    	if (jndiName == null) {
    		return false
    	}
    	if (jndiName.length() == 0) {
    		return false
    	}
    	if(jndiName.equals("{}")) {
    		return false
    	}
    	return true
	}

    /**
     * Return JNDI name.
     * @return JNDI name
     */
    String getJndiName() {
        return this.jndiName
    }
	
	/**
	 * Return Qualified Table Name name.
	 * @return Qualified Table Name name
	 */
	String getQualifiedTableNames() {
		return this.qualifiedTableNames
	}
}
