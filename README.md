grails-geoip2
=============

Grails GeoIp2 plugin based on [MaxMind][maxmind] library

Summary
-------
Allows you to get location info based on a request, such as country, city, geographical coordinates.

Similar to the [Grails GeoIP plugin][grailsgeoip] but uses the [GeoLite2 database][geolite2db] and the [GeoIP2 Java API][geolite2api].

Installation
------------

Add the following to your `BuildConfig.groovy`:

```groovy
plugins {
	compile ':geoip2:1.0'
}
```

On installation, this plugin downloads the GeoLite2 database into your app's `web-app` folder at `web-app\data\maxmind`
and appends a config to your app's `Config.groovy`.

To update your database, reinstall the plugin by deleting your `target` folder and running `grails refresh-dependencies`.

Usage
-----

```groovy
import grails.plugins.geoip2.Geoip2Service

class MyController {
	Geoip2Service geoip2Service	

	def getCountryCode() {
		render geoip2Service.getCountryCode(request)
	}
}
```

Copyright and license
-------------------------
Copyright 2015 Charlotte Tan under the [MIT License](LICENSE). Supported by [Epic Games][epicgames].

This product includes GeoLite2 data created by MaxMind, available from
[MaxMind.com][maxmind].

[maxmind]: http://www.maxmind.com
[geolite2db]: http://dev.maxmind.com/geoip/geoip2/geolite2/
[geolite2api]: http://maxmind.github.io/GeoIP2-java/
[epicgames]: http://www.epicgames.com
[grailsgeoip]: http://grails.org/plugin/geoip