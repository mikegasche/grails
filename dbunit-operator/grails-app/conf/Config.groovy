log4j = {

	appenders {
		console name:'stdout', layout:pattern(conversionPattern: '%c{2} %m%n')
	}

	root {
		warn 'stdout'
		additivity = true
	}

	error 'org.codehaus.groovy.grails',
			'org.springframework',
			'org.hibernate',
			'net.sf.ehcache.hibernate'

	debug  'ch.gstream.grails'
}
grails.views.default.codec="none" // none, html, base64
grails.views.gsp.encoding="UTF-8"
