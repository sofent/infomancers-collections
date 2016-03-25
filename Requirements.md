## ObjectWeb's ASM ##

An open source project by an open source consortium.
  * [ObjectWeb's site](http://www.objectweb.org/).
  * [ASM's site](http://asm.objectweb.org/).
  * [ASM's download page](http://asm.objectweb.org/download/index.html).

## Java 5 or higher ##

No reason why not upgrade to Java 5, really. Or 6, for that matter.

So, download it now from [here](http://developers.sun.com/downloads/top.jsp).

## Using the -javaagent flag ##

To make the magic work, you need to specifiy the `yielder.jar` in your classpath (using the `-classpath` flag) **and** specify the jar as an [instrumentation agent](http://java.sun.com/j2se/1.5.0/docs/api/java/lang/instrument/Instrumentation.html) using the `-javaagent` flag. So, your application should load like this (supposing you only have the `yielder.jar` to load:)

```
java -cp lib/yielder.jar -javaagent:lib/yielder.jar com.mycompany.MyApp
```

