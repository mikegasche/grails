import ch.gstream.grails.plugins.dbunitoperator.DbUnitTestCase

class XclassNameXTests extends DbUnitTestCase {

	// specify your testing datasets, the root path is
	// the project root path for dbunit-operator test cases
	def getDatasets() {
		["data/test/data0.xml", "data/test/data1.xml"]
	}

    void testXclassNameXSomething() {

    	// TODO: do testing...
    }

}
