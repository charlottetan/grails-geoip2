package grails.plugins.geoip2

import org.codehaus.groovy.grails.web.servlet.HttpHeaders

import com.maxmind.geoip2.DatabaseReader
import com.maxmind.geoip2.DatabaseReader.Builder
import com.maxmind.geoip2.exception.AddressNotFoundException
import com.maxmind.geoip2.model.CityResponse
import com.maxmind.geoip2.record.Country
import com.maxmind.geoip2.record.Location

import javax.annotation.PostConstruct
import javax.servlet.http.HttpServletRequest

/**
 * More information: http://maxmind.github.io/GeoIP2-java/doc/v2.1.0/
 */
class Geoip2Service {

    static transactional = false

    Builder geo2Builder
    DatabaseReader geo2DatabaseReader

    @PostConstruct
    protected void init() {
        log.info("GeoIp2Service initializing")
        geo2DatabaseReader = geo2Builder.build()
    }

    Location getLocation(HttpServletRequest request) {
        // provides lat, long, timezone, among other things
        CityResponse cityResponse = getCityResponse(request)
        return cityResponse?.location
    }

    Country getCountry(HttpServletRequest request) {
        // provides localized names and iso code
        CityResponse cityResponse = getCityResponse(request)
        return cityResponse?.country
    }

    String getCountryCode(HttpServletRequest request) {
        // directly get country code, uses getCountry
        return getCountry(request)?.isoCode
    }

    CityResponse getCityResponse(HttpServletRequest request) {
        // most generic form, called by everything.
        String ip = getIpAddress(request)
        InetAddress ipAddress = InetAddress.getByName(ip)
        CityResponse cityResponse
        try {
            cityResponse = geo2DatabaseReader.city(ipAddress)
        } catch (AddressNotFoundException ex) {
            log.info("No address found for ip: ${ip}")
        }
        return cityResponse
    }

    protected String getIpAddress(HttpServletRequest request) {
        String ip

        // x-forwarded for contains a list of comma separated IPs. use the first one.
        // http://docs.aws.amazon.com/ElasticLoadBalancing/latest/DeveloperGuide/TerminologyandKeyConcepts.html#x-forwarded-headers

        log.debug("Get IP from x forwarded for")
        String header = request.getHeader(HttpHeaders.X_FORWARDED_FOR)

        if (!header) {
            log.debug("Get IP from remote addr")
            header = request.remoteAddr
        }

        if (header) {
            String[] listOfIps = header.split("\\s*,\\s*")
            if (listOfIps.length) {
                ip = listOfIps[0]
            }
        }

        return ip
    }
}
