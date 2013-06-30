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



package ch.gstream.grails.plugins.dbunitoperator;


import groovy.util.ConfigObject;
import org.codehaus.groovy.grails.commons.ApplicationHolder;
import org.apache.commons.logging.LogFactory;

/**
 * <p>
 * Configuration loads and holds the configuration parameters for
 * dbunit-operator.
 * </p>
 *
 * @author Michael Gasche
 */
class Configuration {

    def log = LogFactory.getLog(Configuration.class);

    /**
     * DBUnit XML type: flat
     */
    public static final String XML_TYPE_FLAT = "flat";
    /**
     * DBUnit XML type: structured
     */
    public static final String XML_TYPE_STRUCTURED = "structured";

    private static final String KEY_DATASOURCE = "dataSource";
    private static final String KEY_INITIAL_DATA = "initialData";
    private static final String KEY_INITIAL_OPERATION = "initialOperation";
    private static final String KEY_XML_TYPE = "dbunitXmlType";
    private static final String KEY_ORDER_TABLES = "orderTables";

    private ConfigObject config = null;

    private String initialData = null;
    private String operationType = null;
    private String dbunitXmlType = null;
    private boolean orderTables = false;

    private String url = null;
    private String username = null;
    private String password = null;
    private String driver = null;

    private String jndiName = null
    
    /**
     * Constructor loading configuration parameters from DataSource.groovy.
     */
    public Configuration() {
        this((ConfigObject) ApplicationHolder.getApplication().getConfig().get(
                KEY_DATASOURCE));
    }

    /**
     * Constructor.
     *
     * @param config
     *            configuration object
     */
    public Configuration(ConfigObject config) {

        this.config = config;

        this.dbunitXmlType = config.getProperty(KEY_XML_TYPE).toString();
        this.initialData = config.getProperty(KEY_INITIAL_DATA).toString();
        this.operationType = config.getProperty(KEY_INITIAL_OPERATION).toString();

        this.orderTables = false;
        Object o = config.getProperty(KEY_ORDER_TABLES)
        if (o != null)
            this.orderTables = new Boolean(config.getProperty(KEY_ORDER_TABLES).toString()).booleanValue();

        log.debug "ORDER TABLES = " + this.orderTables;

        this.url = config.getProperty("url").toString();
        this.username = config.getProperty("username").toString();
        this.password = config.getProperty("password").toString();
        this.driver = config.getProperty("driverClassName").toString();
        this.jndiName = config.getProperty("jndiName")
    }

    /**
     * Get data source.
     *
     * @return data source
     */
    public ConfigObject getDataSource() {
        return this.config;
    }

    /**
     * Get initial data.
     *
     * @return initial data
     */
    public String getInitialData() {
        return this.initialData;
    }

    /**
     * Get initial operation.
     *
     * @return initial operation
     */
    public String getInitialOperation() {
        return this.operationType;
    }

    /**
     * Get dbunit operation type.
     *
     * @return dbunit operation type
     */
    public String getDbUnitXmlType() {
        return this.dbunitXmlType;
    }

    /**
     * Get db url.
     *
     * @return db url
     */
    public String getUrl() {
        return url;
    }

    /**
     * Get db user name.
     *
     * @return db user name
     */
    public String getUsername() {
        return username;
    }

    /**
     * Get db password.
     *
     * @return db password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Get db driver class name.
     *
     * @return db driver class name
     */
    public String getDriver() {
        return driver;
    }

    /**
     * Resolve table dependencies and order tables?
     *
     * @return <code>true</code> if table dependencies
     *         should be resolved and tables be ordered,
     *         otherwise <code>false</code>
     */
    public boolean getOrderTables() {
        return orderTables;
    }

    /**
     * A simple test if this datasource is JNDI based.
     * @return  <code>true</code> if config is JNDI based,
     *         otherwise <code>false</code>
     */
    public isJndiBased() {
    	 
    	if (jndiName == null) {
    		return false
    	}
    	else if (jndiName.length() == 0) {
    		return false
    	}
    	else if(jndiName.equals("{}")) {
    		return false
    	}
    	else {
    		return true
    	}
	}

    /**
     * Return JNDI name.
     * @return JNDI name
     */
    public String getJndiName() {
        return this.jndiName
    }

}
