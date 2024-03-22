# MsForJvm

JVM Implementation from the vercel project ms https://github.com/vercel/ms

With this package you can convert from various time formats to milliseconds and vice versa.

## Examples

### Convert from readable string

```kotlin
val ms = MS()
ms.parse("2 days")  // 172800000
ms.parse("1d")      // 86400000
ms.parse("10h")     // 36000000
ms.parse("2.5 hrs") // 9000000
ms.parse("2h")      // 7200000
ms.parse("1m")      // 60000
ms.parse("5s")      // 5000
ms.parse("1y")      // 31557600000
ms.parse("100")     // 100
ms.parse("-3 days") // -259200000
ms.parse("-1h")     // -3600000
ms.parse("-200")    // -200
```

### Convert from Milliseconds

```kotlin
val ms = MS()
ms.format(60000)                    // "1m"
ms.format(2 * 60000)                // "2m"
ms.format(-3 * 60000)               // "-3m"
ms.format(ms.parse("10 hours"))     // "10h"
```

### Time Format Written-Out

```kotlin
val ms = MS()
ms.format(60000, Options(longFormat = true))                    // "1 minute"
ms.format(2 * 60000, Options(longFormat = true))                // "2 minutes"
ms.format(-3 * 60000, Options(longFormat = true))               // "-3 minutes"
ms.format(ms.parse("10 hours"), Options(longFormat = true))     // "10 hours"
```

### Time Format Written-Out with separated values

```kotlin
val ms = MS()
ms.format(60 * 1200, Options(splitTextOutput = true))                               // "1m 12s"
ms.format(24 * 60 * 60 * 10000, Options(splitTextOutput = true))                    // "1w 3d"
ms.format(24 * 60 * 60 * 1200, Options(splitTextOutput = true, longFormat = true))  // "1 day, 4 hours, 48 minutes"
ms.format(ms.parse("-10 days"), Options(splitTextOutput = true, longFormat = true)) // "-1 day, 4 hours, 48 minutes"
```

## Features

## Language Support

written in Kotlin (1.9.23) 
min. Java 11

## Caught a Bug?

Feel free to create a issue or a pull-request