import ch.gstream.grails.plugins.dbunitoperator.DbUnitOperator

class BootStrap {

    def init = { servletContext ->

    	// dbunit-operator create; using the configured information from
	  	// DataSource.groovy (dbunitXmlType, initialData, initialOperation)
	  	DbUnitOperator.create()

	  	// dbunit-operator example: use other operation type
	  	//DbUnitOperator.create("UPDATE")

	  	// dbunit-operator example: use other operation type and data file
	  	//DbUnitOperator.create("INSERT", "data/test/data.xml")

	  	// Here, still other data can be created within database, the grails
	  	// 'dbCreate'-property only should correspond to the dbunit-operator
	  	// property 'initialOperation' for reasonable results
	  	// ...
    }
}
