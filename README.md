# log4j-loggly

* [Log4j 1.x](http://logging.apache.org/log4j/)
* [Loggly](https://www.loggly.com/)

Log4J appender that send entries to Loggly service, it is not optimized for massive network usage, we suggest to use it only for a limited set of entries like error/warning logging.

The easiest way to implement is to add our Maven Repository
```xml
<repositories>
	...
	<repository>
		<id>etalia-public-release</id>
		<name>Etalia Public Release</name>
		<url>http://repo.etalia.net/artifactory/public</url>
		<layout>default</layout>
		<releases>
			<enabled>true</enabled>
		</releases>
		<snapshots>
			<enabled>false</enabled>
		</snapshots>
	</repository>
	<repository>
		<id>etalia-public-snapshot</id>
		<name>Etalia Public Snapshot</name>
		<url>http://repo.etalia.net/artifactory/public-snapshot</url>
		<layout>default</layout>
		<releases>
			<enabled>false</enabled>
		</releases>
		<snapshots>
			<enabled>true</enabled>
		</snapshots>
	</repository>
	...
</repositories>
```

add the dependecy in your pom.xml

```xml
<dependency>
	<groupId>net.etalia</groupId>
	<artifactId>log4j-loggly</artifactId>
	<version>1.1.1-SNAPSHOT</version>
</dependency>
```

and then add this to your log4j configuration

```properties
log4j.rootLogger={other-appenders}, LOGGLY
...
log4j.appender.LOGGLY=net.etalia.log4j.LogglyAppender
log4j.appender.LOGGLY.Layout=org.apache.log4j.PatternLayout
log4j.appender.LOGGLY.Layout.ConversionPattern=%d{yyMMdd.HHmmss,SSS} %t %C{1}.%M %p: %m%n
log4j.appender.LOGGLY.token={put-your-loggly-token-here}
log4j.appender.LOGGLY.tags={optionally-put-your-loggly-tags-here}
log4j.appender.LOGGLY.level=ERROR
```
