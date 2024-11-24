
# KriRo AI Assistant
LLM agent system with langchain4g and some agents on board

Enjoy!


## Installation

Install java 17

```bash
%java -version
openjdk version "17.0.12" 2024-07-16
OpenJDK Runtime Environment Temurin-17.0.12+7 (build 17.0.12+7)
OpenJDK 64-Bit Server VM Temurin-17.0.12+7 (build 17.0.12+7, mixed mode)
```

maven download
https://maven.apache.org/download.cgi

```bash
% mvn --version
Apache Maven 3.9.8 (36645f6c9b5079805ea5009217e36f2cffd34256)
Maven home: /Users/pups/apache-maven-3.9.8
Java version: 17.0.12, vendor: Eclipse Adoptium, runtime: /Library/Java/JavaVirtualMachines/jdk-17.0.12+7/Contents/Home
Default locale: ru_RU, platform encoding: UTF-8
OS name: "mac os x", version: "15.0.1", arch: "aarch64", family: "mac"
```

clone project

```bash
%git clone https://github.com/jCat77/llm-agent-test.git
```  

build&run
```bash
% mvn clean package
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  1.346 s
[INFO] Finished at: 2024-11-25T01:07:13+03:00

% java -jar target/llm-agent-test-1.0-SNAPSHOT.jar
```  
## Usage/Examples

```javascript
User: привет
Kriro: Меня зовут Криро.
Я система, построенная на технологиях искуственного интеллекта
Я умею складывать и умножать числа, а еще отправлять почту.

User: сколько будет 2 + 2
Kriro: 4.0

```

