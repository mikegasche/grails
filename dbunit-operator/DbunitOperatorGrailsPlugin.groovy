/*
 *   Copyright 2008-2013 G-Stream Technologies GmbH
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
class DbunitOperatorGrailsPlugin {

	def version = "1.7"
	def grailsVersion = "2.0.0 > *"
	def pluginExcludes = [
		"grails-app/domain/**",
		"web-app/**"
	]

	def author = "Michael Gasche"
	def authorEmail = "grails@g-stream.ch"
	def title = "DBUnit Operator"
	def description = '''\
Integrates effortless and appropriate within data-source configuration and helps to create initial data within database through BootStrap.groovy with help of DbUnit. Different DbUnit dataset files (Flat or Strcutured XML) and DbUnit operations (CLEAN_INSERT, UPDATE, etc.) can be specified for different environments (See DataSource.groovy).
Furthermore, the dbunit-operator provides a simple test case 'DbUnitTestCase' (derived from GroovyTestCase) to create jUnit/DbUnit-Tests with seperate test dataset files (See MyTest.groovy).
'''

	def documentation = "http://grails.org/dbunit-operator"

	def license = "APACHE"
	def issueManagement = [system: 'GitHub', url: 'https://github.com/mikegasche/grails/issues']
	def scm = [url: 'https://github.com/mikegasche/grails/tree/master/dbunit-operator']
//	def organization = [name: "My Company", url: "http://www.my-company.com/"]
//	def developers = [[name: "Joe Bloggs", email: "joe@bloggs.net"]]
}
