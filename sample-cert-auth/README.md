Sample authentication using certificates
========================================

This project is a simple sample to display the authentication using certificates and Tomcat.

Getting started
===============

First of all, we need to generate the certificates.
The project already contains the [certificates](https://github.com/l-lin/dev-cheat-sheet/tree/master/sample-cert-auth/foobar/certificate) in which you can use for testing.

You will need the following:

* Tomcat 7+
* Java 7+
* A browser

Tomcat configuration
--------------------

Edit your server.xml and add the following connector:

```xml
<?xml version='1.0' encoding='utf-8'?>
<!--...-->
<Connector
        protocol="HTTP/1.1"
        port="8443" maxThreads="200"
        scheme="https" secure="true" SSLEnabled="true"
        keystoreFile="/path/to/foobar.jks" keystorePass="foobarpwd"
        truststoreFile="/path/to/cacerts.jks" truststorePass="cacertspassword"
        clientAuth="true" sslProtocol="TLS"/>
<!--...-->
```

:information_source: Don't forget to change the the path to the keystore and the truststore files.

Generating the war
------------------

`foobar` is a classic webapp. Just run `mvn package` at the root of the `foobar` project.

Accessing with a browser
------------------------

After generating the war, just put it on the `webapp` folder of your Tomcat and launch it.

When it's fully deployed, access to [https://localhost:8443/foobar/](https://localhost:8443/foobar/), and you will see the following page:

![Not authenticated](https://raw.githubusercontent.com/l-lin/dev-cheat-sheet/master/sample-cert-auth/images/not_authenticated.png)

Go to settings > certificates, and import the [browser.p12](https://github.com/l-lin/dev-cheat-sheet/blob/master/sample-cert-auth/foobar/certificate/browser.p12) (password is `browserpwd`).

Refresh the page, and you are now able to access to the application.
