/*
 *   Copyright 2008-2011 G-Stream Technologies
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

import org.springframework.web.context.WebApplicationContext

import ch.gstream.grails.plugins.dbunitoperator.DbUnitOperatorImpl


/**
 * DB-Unit Operator.
 *
 * Initial data creator for Grails application (See BootStrap.groovy). It uses
 * DBUnit and reads the data file (parameter: initialData) and the DBUnit
 * operation (parameter: initialOperation) from the DataSource.groovy.
 *
 * The data will be initially created within the database by using the
 * configured operation (parameter: initialOperation). The initial data creator
 * should be used in BootStrap.groovy and it reads the parameter values
 * according to the Grails environment currently used.
 *
 * XML data files can be 'flat' or 'structured' (parameter: dbunitXmlType). For
 * definition See http://dbunit.sourceforge.net/components.html.
 *
 * @author Michael Gasche
 */
class DbUnitOperator {

	/**
	 * Create initial data based on configuration (DBUnit-operation, initial data,
	 * DBUnit dataset file format) within 'DataSource.groovy'.
	 *
	 * Usually will be called by 'BootStrap.groovy'.
	 */
    static create() {

    	def op = new DbUnitOperatorImpl()
    	op.create()
    }
    
	/**
	 * Create initial data based on configuration (DBUnit-operation, initial data,
	 * DBUnit dataset file format) within 'DataSource.groovy'.
	 *
	 * Usually will be called by 'BootStrap.groovy'.
	 *
	 * @param webApplicationContext the web application context to inject in a real
	 *        environment other than test/integration
	 */
    static create(webApplicationContext) { 
		
    	def op = new DbUnitOperatorImpl(new Configuration(), webApplicationContext)
    	op.create()
    }	

	/**
	 * Operate data based on configuration (initial data, DBUnit dataset file format)
	 * within 'DataSource.groovy' and given DBUnit-operation 'operationType' specified.
	 *
	 * @param operationType DBUnit-operation type (String, e.g. "CLEAN_INSERT").
	 */
    static operate(operationType) {
    	def op = new DbUnitOperatorImpl()
    	op.operate (operationType)
    }

	/**
	 * Operate data based on configuration (DBUnit dataset file format) within
	 * 'DataSource.groovy' and given dataset file 'filePath' and DBUnit-operation
	 * 'operationType' specified.
	 *
	 * @param operationType DBUnit-operation type (String, e.g. "CLEAN_INSERT").
	 * @param filePath DBUnit dataset file (Flat XML or structured XML, based on
	 *        the configuration). Root-path is '[Grails-Project]/web-app/'.
	 */
    static operate(operationType, String filePath) {

		def op = new DbUnitOperatorImpl()
    	op.operate (operationType, filePath)
    }
    
	/**
	 * Operate data based on configuration (initial data, DBUnit dataset file format)
	 * within 'DataSource.groovy' and given DBUnit-operation 'operationType' specified.
	 *
	 * @param operationType DBUnit-operation type (String, e.g. "CLEAN_INSERT").
	 * @param webApplicationContext the web application context to inject in a real
	 *        environment other than test/integration
	 */
    static operate(operationType, webApplicationContext) {
    	
    	def op = new DbUnitOperatorImpl(new Configuration(), webApplicationContext)
    	op.create(operationType)
    }
    
}
