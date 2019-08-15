
Java Common
=====

Reusable java code.

Getting Started
===============

### Prerequisites
 
  - ##### Java Version
    - Java 6 (or newer)


### How to Use

  - ##### Maven project
>> Add a dependency to the pom.xml as like below:
>>>
>>> ```
>>> <dependencies>
>>>     <dependency>
>>>         <groupId>com.ardikars.common</groupId>
>>>         <artifactId>common-annotation</artifactId>
>>>     </dependency>
>>>     <dependency>
>>>         <groupId>com.ardikars.common</groupId>
>>>         <artifactId>common-constant</artifactId>
>>>     </dependency>
>>>     <dependency>
>>>         <groupId>com.ardikars.common</groupId>
>>>         <artifactId>common-logging</artifactId>
>>>     </dependency>
>>>     <dependency>
>>>         <groupId>com.ardikars.common</groupId>
>>>         <artifactId>common-memory</artifactId>
>>>     </dependency>
>>>     <dependency>
>>>         <groupId>com.ardikars.common</groupId>
>>>         <artifactId>common-net</artifactId>
>>>     </dependency>
>>>     <dependency>
>>>         <groupId>com.ardikars.common</groupId>
>>>         <artifactId>common-tuple</artifactId>
>>>     </dependency>
>>>     <dependency>
>>>         <groupId>com.ardikars.common</groupId>
>>>         <artifactId>common-util</artifactId>
>>>     </dependency>
>>> </dependencies>
>>>
>>> <dependencyManagement>
>>>     <dependencies>
>>>         <dependency>
>>>             <groupId>com.ardikars.common</groupId>
>>>             <artifactId>common</artifactId>
>>>             <version>1.3.7</version>
>>>             <type>pom</type>
>>>             <scope>import</scope>
>>>         </dependency>
>>>     </dependencies>
>>> </dependencyManagement>
>>> ```

Build Java Common from Source
=============================

### Build
   - ```./mvnw clean package```
   
### Skip Test
   - ```./mvnw clean package -x test```
   
### Test
   - ```./mvnw clean site site:stage -Pcoverage,spotbugs,checkstyle,pmd```

License
=======

Apache License, Version 2.0

```
/**
 * Copyright 2017-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
```

Contact
=======

Email: Ardika Rommy Sanjaya (contact@ardikars.com)


Issues
======

Have a bug? Please create an issue here on GitHub!

https://github.com/ardikars/java-common/issues
