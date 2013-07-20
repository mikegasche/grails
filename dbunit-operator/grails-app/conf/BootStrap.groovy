import ch.gstream.grails.plugins.dbunitoperator.DbUnitOperator

class BootStrap {

    def init = { servletContext ->

    	// dbunit-operator create; using the configured information from
	  	// DataSource.groovy (dbunitXmlType, initialData, initialOperation)
	  	DbUnitOperator create

    	// OR:
    	// create a dbunit-operator with given web application context in a
	  	// real environment other than test/integration
	  	// e.g.: 
        // WebApplicationContext webApplicationContext =
	  	//     WebApplicationContextUtils.getWebApplicationContext(servletContext)	  	
	  	// DbUnitOperator.create webApplicationContext
	  	
	  	
	  	// dbunit-operator example: use other operation type
	  	//DbUnitOperator.operate "CLEAN_INSERT"
	  	
	  	// dbunit-operator example: use other operation type and data file
	  	//DbUnitOperator.operate("INSERT", "data/test/data0.xml")

	  	
	  	// Here, still other data can be created within database, the grails
	  	// 'dbCreate'-property only should correspond to the dbunit-operator
	  	// property 'initialOperation' for reasonable results
	  	// ...
	  	
    }
    
}
