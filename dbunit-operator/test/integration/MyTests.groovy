import ch.gstream.grails.plugins.dbunitoperator.DbUnitTestCase

/**
 * Example tests.
 *
 * NOTE: Test cases must be placed within 'test/integration' directory.
 * To run the test(s) type 'grails test-app'.
 *
 * @author "Michael Gasche"
 */
class MyTests extends DbUnitTestCase {

	// specify your testing datasets, the root path is
	// the project root path for dbunit-operator test cases
	def getDatasets() {
		["data/test/data0.xml", "data/test/data1.xml"]
	}

	// test 1st relation setup by data0.xml
	void testFirstRelation() {

		assertEquals Customer.get(1), Project.get(1).customer
	}

	// test 2nd relation setup by data1.xml
	void testSecondRelation() {

		assertEquals Customer.get(2), Project.get(2).customer
	}

	// print the data for control purposes
	void testPrintData() {

		def l

		l = Customer.findAll()
		printData(l)

		l = Project.findAll()
		printData(l)
	}

	def printData = {
		println it
	}

}
