/*
 *   Copyright 2010 G-Stream Technologies GmbH
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

 
/**
 * Gant script that generates a DBUnit test from template class.
 * 
 * @author Michael Gasche
 */
 
import org.codehaus.groovy.grails.commons.GrailsClassUtils as GCU
includeTargets << grailsScript("_GrailsInit")
includeTargets << grailsScript("_GrailsCreateArtifacts")
target ( name : 'default' , description : 'Creates a DBUnit Test (integration test) for a given domain name' ) {

	depends(checkVersion, parseArguments)

    promptForName(type: "DBUnit Test (integration test)")

    def artifactName = argsMap["params"][0]

	def className       = GCU.getClassNameRepresentation(artifactName)
	def propertyName    = GCU.getPropertyNameRepresentation(artifactName)
	def fileName        = "test/integration/${className}Tests.groovy"
	
	Ant.sequential {  
		
		copy(file:"${dbunitOperatorPluginDir}/src/templates/DbUnitTests.groovy",
			 tofile:fileName) 
		replace(file:fileName, 
				token:"XclassNameX", value:"${className}" )
		replace(file:fileName, 
				token:"XpropertyNameX", value:"${propertyName}" )
	}
	
    println 'DBUnit Test generated at ' + fileName + '.'
}
