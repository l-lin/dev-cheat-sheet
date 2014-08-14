Sample authentication using certificates
========================================

TODO


Tomcat configuration
--------------------

```xml
<?xml version='1.0' encoding='utf-8'?>
<!--...-->
<Connector
        protocol="HTTP/1.1"
        port="8443" maxThreads="200"
        scheme="https" secure="true" SSLEnabled="true"
        keystoreFile="/path/to/foobar.jks" keystorePass="foobarpwd"
        truststoreFile="/path/to/cacerts.jks" truststorePass="cacertspassword"
        clientAuth="false" sslProtocol="TLS"/>
<!--...-->
```
