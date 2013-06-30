import ch.gstream.grails.plugins.dbunitoperator.DbUnitTestCase

/**
 * Dummy tests.
 *
 * NOTE: Test cases must be placed within 'test/integration' directory.
 * To run the test(s) type 'grails test-app'.
 *
 * @author "Michael Gasche"
 */
class DummyTests extends DbUnitTestCase {

	// specify your testing datasets, the root path is
	// the project root path for dbunit-operator test cases
	def getDatasets() {
		["data/test/data0.xml", "data/test/data1.xml"]
	}

    void testDummySomething() {

    	// TODO: do testing...
		println Project.findAll()
		println Customer.findAll()
    }

    protected getDbUnitCleanupOperationType() {
    	"CLEAN_INSERT"
    }
}
