# churds-web-metrics
[![Build Status](https://travis-ci.com/cameronhurd/churds-web-metrics.svg?branch=master)](https://travis-ci.com/cameronhurd/churds-web-metrics)

# What
A simple web metric library built for the contrast security Java Instrumentation Engineer Project

# How to use this library to track metrics
## Add the dependency
```
<repositories>
  <repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
  </repository>
</repositories>
... 
<dependency>
    <groupId>com.github.cameronhurd</groupId>
    <artifactId>churds-web-metrics</artifactId>
    <version>0.0.1</version>
</dependency>
```
## Setup the web servlet filter
If your using spring boot add this to your application config:
```
@ServletComponentScan(basePackages = {"churd.metrics.filter"})
```
Otherwise you can add churd.metrics.filter.WebMetricsServletFilter in web.xml (it is a standard javax.servlet.Filter).
