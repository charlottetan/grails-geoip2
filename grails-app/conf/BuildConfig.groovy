grails.project.work.dir = "target"

grails.project.dependency.resolver = "maven"
grails.project.dependency.resolution = {
    inherits("global") { }
    
    repositories {
        mavenLocal()        
        grailsCentral()
        mavenCentral()
        mavenRepo "http://repository.codehaus.org"
    }

    dependencies {
        runtime "com.maxmind.geoip2:geoip2:2.1.0", {
            excludes "junit", "mockito-core"
        }

        // otherwise causes a compilation error
        compile("org.codehaus.groovy.modules.http-builder:http-builder:0.7.2") {
            excludes "groovy"
        }
    }

    plugins {
        build(":release:3.0.1",
              ":rest-client-builder:1.0.3") {
            export = false
        }
    }
}
