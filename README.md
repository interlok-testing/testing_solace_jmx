# JMX proxy via Solace Testing

[![license](https://img.shields.io/github/license/interlok-testing/testing_solace_jmx.svg)](https://github.com/interlok-testing/testing_solace_jmx/blob/develop/LICENSE)
[![Actions Status](https://github.com/interlok-testing/testing_solace_jmx/actions/workflows/gradle-build.yml/badge.svg)](https://github.com/interlok-testing/testing_solace_jmx/actions/workflows/gradle-build.yml)

Project tests interlok-jmx-Solace features

## What it does

This project contains a single instance of Interlok that will attempt to connect to a locally running Solace broker upon start-up.  Once started three separate channels will initialize themselves to bridge messages.  However this project is more interested in showing how you might use JMS as the messaging proxy for JMX.
Here we'll be using  Solace as that messaging proxy.
Once set up you'll be able to communicate with your Interlok instances via JMS rather JMXMP.
 
![Solace diagram](/solace.png "Solace diagram")
 
## Getting started

* `./gradlew clean build`
* `(cd ./build/distribution && java -jar lib/interlok-boot.jar)`

## Configuring the client side

Our client will simply be the UI, which will usually communicate to instances via JMXMP.  Add a new instance on the dashboard page and set the following properties;
 - __url__ = service:jmx:solace:///smf://localhost:55555?jmx.type=Topic&jmx.destination=jmxTopic
 - __username__ = admin (or other configured user)
 - __password__ = admin (or other configured password)
