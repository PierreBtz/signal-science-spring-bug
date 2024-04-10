# Signal Science Spring Bug

This repositories demonstrates an issue with versions 2.5.3 and 2.5.4 of `sigsci-module-java`.

Reproduction steps:

* `mvn spring-boot:run`, observe the failure:

```text
Caused by: java.lang.NoClassDefFoundError: org/apache/commons/fileupload/FileItemFactory
	at com.signalsciences.jakartafilter.SigSciFilter.<init>(SigSciFilter.java:43) ~[sigsci-module-java-2.5.3.jar:2.5.3]
	at eu.pierrebeitz.signalsciencespringbug.SignalScienceSpringBugApplication$SignalSciencesConfig.signalSciencesRegistration(SignalScienceSpringBugApplication.java:26) ~[classes/:na]
	at eu.pierrebeitz.signalsciencespringbug.SignalScienceSpringBugApplication$SignalSciencesConfig$$SpringCGLIB$$0.CGLIB$signalSciencesRegistration$0(<generated>) ~[classes/:na]
```

You can modify the sigsci version with the `sigsci.version` property (default is `2.5.4`), observe that:

* `mvn spring-boot:run -Dsigsci.version=2.5.3` fails (same exception as `2.5.4`)
* `mvn spring-boot:run -Dsigsci.version=2.5.2` works

Looking into the dependency tree of `sigsci-module-java`, it seems that several dependencies are missing (or they 
are shaded or something but I don't see them in the jar):

```text
[INFO] +- com.signalsciences:sigsci-module-java:jar:2.5.4:compile
[INFO] |  \- jakarta.servlet:jakarta.servlet-api:jar:6.0.0:compile
```

compared to a working version:

```text
INFO] +- com.signalsciences:sigsci-module-java:jar:2.5.2:compile
[INFO] |  +- commons-fileupload:commons-fileupload:jar:1.5:compile
[INFO] |  |  \- commons-io:commons-io:jar:2.11.0:compile
[INFO] |  +- org.msgpack:msgpack-core:jar:0.9.3:compile
[INFO] |  +- com.github.jnr:jnr-unixsocket:jar:0.38.19:compile
[INFO] |  |  +- com.github.jnr:jnr-ffi:jar:2.2.13:compile
[INFO] |  |  |  +- com.github.jnr:jffi:jar:1.3.10:compile
[INFO] |  |  |  +- com.github.jnr:jffi:jar:native:1.3.10:runtime
[INFO] |  |  |  +- org.ow2.asm:asm:jar:9.2:compile
[INFO] |  |  |  +- org.ow2.asm:asm-commons:jar:9.2:compile
[INFO] |  |  |  +- org.ow2.asm:asm-analysis:jar:9.2:compile
[INFO] |  |  |  +- org.ow2.asm:asm-tree:jar:9.2:compile
[INFO] |  |  |  +- org.ow2.asm:asm-util:jar:9.2:compile
[INFO] |  |  |  +- com.github.jnr:jnr-a64asm:jar:1.0.0:compile
[INFO] |  |  |  \- com.github.jnr:jnr-x86asm:jar:1.0.2:compile
[INFO] |  |  +- com.github.jnr:jnr-constants:jar:0.10.4:compile
[INFO] |  |  +- com.github.jnr:jnr-enxio:jar:0.32.14:compile
[INFO] |  |  \- com.github.jnr:jnr-posix:jar:3.1.16:compile
[INFO] |  +- com.github.seancfoley:ipaddress:jar:5.3.4:compile
[INFO] |  \- jakarta.servlet:jakarta.servlet-api:jar:6.0.0:compile
```