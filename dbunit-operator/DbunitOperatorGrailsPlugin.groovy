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
class DbunitOperatorGrailsPlugin {
	 
    // the plugin version
    def version = "1.7"
    // the version or versions of Grails the plugin is designed for
    def grailsVersion = "2.0.0 > *"
    // the other plugins this plugin depends on
    def dependsOn = [:]
    // resources that are excluded from plugin packaging
    def pluginExcludes = [
            "grails-app/views/error.gsp",
            "grails-app/domain/TestDoCustomer.groovy",
            "grails-app/domain/TestDoProject.groovy",
            "grails-app/conf/BootStrap.groovy",
            "grails-app/conf/BuildConfig.groovy",
            "grails-app/conf/Config.groovy",
            "grails-app/conf/DataSource.groovy",
            "grails-app/conf/UrlMappings.groovy",
            "test/integration/DummyTests.groovy",
            "test/integration/MyTests.groovy"
    ]

    // TODO Fill in these fields
    def author = "Michael Gasche"
    def authorEmail = "grails (at) g-stream (full-stop) ch"
    def title = "DBUnit Operator"
    def description = '''\
The dbunit-operator integrates effortless and appropriate within data-source configuration and helps to create initial data within database through BootStrap.groovy with help of DbUnit. Different DbUnit dataset files (Flat or Strcutured XML) and DbUnit operations (CLEAN_INSERT, UPDATE, etc.) can be specified for different environments (See DataSource.groovy).
Furthermore, the dbunit-operator provides a simple test case 'DbUnitTestCase' (derived from GroovyTestCase) to create jUnit/DbUnit-Tests with seperate test dataset files (See MyTest.groovy).
'''

    // URL to the plugin's documentation
    def documentation = "http://grails.org/DbunitOperator+Plugin"

    def doWithSpring = {
    }

    def doWithApplicationContext = { applicationContext ->
    }

    def doWithWebDescriptor = { xml ->
    }

    def doWithDynamicMethods = { ctx ->
    }

    def onChange = { event ->
    }

    def onConfigChange = { event ->
    }
}
