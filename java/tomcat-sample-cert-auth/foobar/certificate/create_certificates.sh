#!/bin/bash
# Script that generate the CA and the certificates

if [ -z "$JAVA_HOME" ]
then
    echo "Please set JAVA_HOME"
    exit -1
fi

# Delete all previously created certificates
find . ! -name '*.sh' -delete

#######################################################
# Certification Authority                             #
#######################################################

# Since we do not have any certification authority, we will generate our own.
openssl genrsa -out foobarCA.key 1024
openssl req -new -x509 -days 3650 -key foobarCA.key -out foobarCA.crt -subj "/C=FR/ST=IDF/L=Paris/O=FoobarCA/OU=/CN=*.local.fr"
mkdir -p demoCA/newcerts
touch demoCA/index.txt
echo '01' > demoCA/serial

# Add the root certificate to cacerts of your JVM
keytool -delete -noprompt -trustcacerts -alias foobarCA -keystore ${JAVA_HOME}/jre/lib/security/cacerts -storepass changeit
keytool -import -noprompt -trustcacerts -alias foobarCA -file foobarCA.crt -keystore ${JAVA_HOME}/jre/lib/security/cacerts -storepass changeit

# Create the trustore with the root certificate in it
keytool -import -keystore cacerts.jks -storepass cacertspassword -alias foobarCA -file foobarCA.crt -noprompt

#######################################################
# Foobar certificate                                  #
#######################################################

# Generate the keystore
keytool -genkey -v -alias foobar -keyalg RSA -validity 3650 -keystore foobar.jks -dname "CN=foobar.local.fr, OU=, O=Foobar, L=Paris, ST=IDF, C=FR" -storepass foobarpwd -keypass foobarpwd
# Then, generate the CSR to sign:
keytool -certreq -alias foobar -file foobar.csr -keystore foobar.jks -storepass foobarpwd
# Sign the certificate to the CA. startdate and enddate format: YYMMDDHHMMSSZ (the same as an ASN1 UTCTime structure)
openssl ca -batch -startdate 150813080000Z -enddate 250813090000Z -keyfile foobarCA.key -cert foobarCA.crt -policy policy_anything -out foobar.crt -infiles foobar.csr
# Add the root certificate to the keystores
keytool -importcert -alias foobarCA -file foobarCA.crt -keystore foobar.jks -storepass foobarpwd -noprompt
# Add signed certificate to the keystores
keytool -importcert -alias foobar -file demoCA/newcerts/01.pem -keystore foobar.jks -storepass foobarpwd -noprompt

#######################################################
# Certificate used in the browser                     #
#######################################################

# Generate the keystore
keytool -genkey -v -alias browser -keyalg RSA -validity 3650 -keystore browser.jks -dname "CN=browser.local.fr, OU=, O=browser, L=Paris, ST=IDF, C=FR" -storepass browserpwd -keypass browserpwd
# Then, generate the CSR to sign:
keytool -certreq -alias browser -file browser.csr -keystore browser.jks -storepass browserpwd
# Sign the certificate to the CA:
openssl ca -batch -keyfile foobarCA.key -cert foobarCA.crt -policy policy_anything -out browser.crt -infiles browser.csr
# Add the root certificate tot the keystores
keytool -importcert -alias foobarCA -file foobarCA.crt -keystore browser.jks -storepass browserpwd -noprompt
# Add signed certificate to the keystores
keytool -importcert -alias browser -file demoCA/newcerts/02.pem -keystore browser.jks -storepass browserpwd -noprompt
# Export certificates in PKCS12 format for test use (in browser)
keytool -importkeystore -srckeystore browser.jks -destkeystore browser.p12 -srcstoretype JKS -deststoretype PKCS12 -srcstorepass browserpwd -deststorepass browserpwd -srcalias browser -destalias browserKey -srckeypass browserpwd -destkeypass browserpwd -noprompt
