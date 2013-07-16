import ch.gstream.grails.plugins.dbunitoperator.DbUnitOperator

class BootStrap {

    def init = { servletContext ->

    	// dbunit-operator create; using the configured information from
	  	// DataSource.groovy (dbunitXmlType, initialData, initialOperation)
	  	DbUnitOperator.create()

    	// OR:
    	// create a dbunit-operator with given web application context in a
	  	// real environment othe than test/integration
	  	// e.g.:
        //   ServletContext sctx = ServletContextHolder.getServletContext()
        //   WebApplicationContext webApplicationContext =
	  	//   WebApplicationContextUtils.getWebApplicationContext(sctx)	  	
	  	//DbUnitOperator.create(webApplicationContext)
	  	
	  	
	  	// dbunit-operator example: use other operation type
	  	//DbUnitOperator.operate("UPDATE")
	  	
	  	// dbunit-operator example: use other operation type and data file
	  	//DbUnitOperator.operate("INSERT", "data/test/data.xml")

	  	
	  	// Here, still other data can be created within database, the grails
	  	// 'dbCreate'-property only should correspond to the dbunit-operator
	  	// property 'initialOperation' for reasonable results
	  	// ...
	  	
    }
    
}
