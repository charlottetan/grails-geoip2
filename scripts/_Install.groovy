includeTargets << grailsScript('_GrailsEvents')
includeTargets << grailsScript('_GrailsBootstrap')

def geoPath = '/data/maxmind'
def geoLiteSources = "${basedir}/web-app${geoPath}"
def appDir = "$basedir/grails-app"

def binaryDb = "GeoLite2-City.mmdb"
def zippedDb = "${binaryDb}.gz"

event('StatusUpdate', ['Downloading GeoLite2 database from MaxMind'])

ant.mkdir(dir: "${geoLiteSources}")

ant.delete(file: "${geoLiteSources}/${binaryDb}")

ant.get(dest: "${geoLiteSources}/${zippedDb}",
        src: "http://geolite.maxmind.com/download/geoip/database/${zippedDb}",
        verbose: true)

ant.gunzip(src: "${geoLiteSources}/${zippedDb}")

ant.delete(file: "${geoLiteSources}/${zippedDb}")

def configFile = new File(appDir, 'conf/Config.groovy')
if (configFile.exists() && configFile.text.indexOf("GeoIP2") == -1) {
    configFile.withWriterAppend {
        it.writeLine '\n\n// Added by GeoIP2 plugin:'
        it.writeLine "grails.plugin.geoip2.data.resource = '${geoPath}/${binaryDb}'"
    }
}

event('StatusFinal', ['GeoLite2 plugin installed successfully'])
