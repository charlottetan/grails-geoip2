import com.maxmind.geoip2.DatabaseReader.Builder

class Geoip2GrailsPlugin {
    def version = "1.0"
    def grailsVersion = "2.4 > *"
    def title = "GeoIP2 Plugin"
    def author = "Charlotte Tan"
    def authorEmail = "charlottethl@gmail.com"
    def description = 'Similar to the [Grails GeoIP plugin|http://grails.org/plugin/geoip] but uses the GeoLite2 database by MaxMind.'
    def documentation = "http://grails.org/plugin/geoip2"
    def license = "MIT"
    def organization = [ name: "Epic Games", url: "http://epicgames.com/" ]
    def issueManagement = [system: 'GITHUB', url: 'https://github.com/charlottethl/grails-geoip2/issues']
    def scm = [url: 'https://github.com/charlottethl/grails-geoip2/']

    def doWithSpring = {
        def conf = application.config.grails.plugin.geoip2

        if (!conf) {
            log.error("No configuration found, exiting.")
            return
        }

        log.info("Configuring Grails GeoIP 2.")

        def dataResource

        try {
            if (conf.data.resource) {
                dataResource = application.parentContext.getResource(conf.data.resource).file
            }
        } catch (e) {
            log.error("Unable to find GeoIP2 database: \"${conf.data.resource}\".")
            return
        }

        if (!dataResource) {
            log.error("GeoIP2 database not installed. Please try re-installing with 'grails refresh-dependencies'.")
            return
        }

        /** geo2Builder */
        geo2Builder(Builder, dataResource)

        log.info("Finished configuring Grails GeoIP 2.")
    }
}
