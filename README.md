[![Build Status][ci-img]][ci] [![Released Version][maven-img]][maven]

# OpenTracing Spring Tracer Configuration
This repository provides a tracer auto-configuration for Spring Boot.
The auto-configuration does following:

1. Returns tracer from `GlobalTracer`.
2. Resolves tracer from [tracerresolver](https://github.com/opentracing-contrib/java-tracerresolver).
3. If not tracer has been found it returns NoopTracer.
4. Registers tracer to GlobalTracer - if it has not been registered.

This auto-configuration is usually configured to run it no tracer bean has been provided.


## Development
Maven checkstyle plugin is used to maintain consistent code style based on [Google Style Guides](https://github.com/google/styleguide)

```shell
./mvnw clean install
```

## Release
Follow instructions in [RELEASE](RELEASE.md)

   [ci-img]: https://travis-ci.org/opentracing-contrib/java-spring-tracer-configuration.svg?branch=master
   [ci]: https://travis-ci.org/opentracing-contrib/java-spring-tracer-configuration
   [maven-img]: https://img.shields.io/maven-central/v/io.opentracing.contrib/java-spring-tracer-configuration.svg?maxAge=2592000
   [maven]: http://search.maven.org/#search%7Cga%7C1%7Cjava-spring-tracer-configuration

## License

[Apache 2.0 License](./LICENSE).
