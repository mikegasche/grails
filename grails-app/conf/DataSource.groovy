dataSource {
	pooled = true
	driverClassName = "org.h2.Driver"
	username = "sa"
	password = ""
	dbCreate = "update"
	dbunitXmlType = "flat" // dbunit-operator data file type: 'flat' or 'structured'
	orderTables = false // resolve table dependencies and order tables? (if true: dbunit-operator is slower)
	jndiName = null
}
hibernate {
    cache.use_second_level_cache=true
    cache.use_query_cache=true
    cache.provider_class='net.sf.ehcache.hibernate.EhCacheProvider'
}
// environment specific settings
environments {
	development {
		dataSource {
			dbCreate = "create-drop" // one of 'create', 'create-drop','update'
			url = "jdbc:h2:mem:devDb"
			initialData = "data/init/data-a.xml, data/init/data-b.xml"  // 1-n dbunit-operator Flat-XML or XML comma-separated data files (See http://dbunit.sourceforge.net/components.html)
			//initialData = "/Users/Mike/data.xml" // You can use absolute paths
			initialOperation = "CLEAN_INSERT" // dbunit-operator operation
		}
	}
	test {
		dataSource {
			dbCreate = "create-drop" // one of 'create', 'create-drop','update'
			url = "jdbc:h2:mem:devDb"
			initialData = "data/test/init.xml"  // 1-n dbunit-operator Flat-XML or XML comma-separated data files (See http://dbunit.sourceforge.net/components.html)
			initialOperation = "CLEAN_INSERT" // dbunit-operator operation
		}
	}
	production {
		dataSource {
			dbCreate = "update"
			url = "jdbc:h2:file:prodDb;shutdown=true"
		}
	}
}