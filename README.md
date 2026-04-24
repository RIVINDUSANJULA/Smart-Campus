# Smart Campus Sensor & REST API

## Project Overview
The "Smart Campus" API is a backend service designed to manage university rooms and the diverse array of sensors. Built using Java and the JAX-RS framework, use ->REST API architecture. 

Elements include:
* HTTP endpoints (`/rooms`, `/sensors`, `/sensors/{id}/read`).
* **Thread-safe collections** - (`ConcurrentHashMap` & `CopyOnWriteArrayList`) --> Ensure data consistency during concurrent client requests, therefore no need to use DB.
* **Error Handling:** --> (409, 422, 403, 500).
* LoggerFilter --> To track REST API (Traffic).

---

## Build & Launch Instructions

Project - Maven
Packeged --> '.war'

### Requirements to Run

JDK (21)
Tomcat


**1 Clone My Repo**
```bash
git clone https://github.com/RIVINDUSANJULA/Smart-Campus.git
cd Smart-Campus
```

**2 Build -- Project (Maven)**

**Maven NEEDED TO BE INSTALLLED**

```bash
mvn clean package
```
*Generate File : `SmartCampus-1.0-SNAPSHOT.war` in `/target`.*

**3 Deploy to a Server (e.g., Apache Tomcat)**
1. Download - Apache Tomcat (v9).
2. **.war** (File is in /target folder) - Copy -> to Tomcat (apache-tomcat-9.0.117/webapps)
3. Start Server:

```bash
cd apache-tomcat-9.inHerePleasePutYourVersion/bin
./startup.sh
```   
Above Code is For Mac (Because I am coding this via a MacBook sorry.)
Above Cide will start the server and unpack the *`.war`* file

**4 API**
URL: `http://localhost:8080/SmartCampus-1.0-SNAPSHOT/api/v1/`

---

## cURL Commands

**1. API Details**
Just What We Build.

```bash
curl -X GET http://localhost:8080/SmartCampus-1.0-SNAPSHOT/api/v1 -H "Accept: application/json"
```

***postman*** GET
```bash
http://localhost:8080/SmartCampus-1.0-SNAPSHOT/api/v1"
```


**2. Create a New Room**
```bash
curl -X POST http://localhost:8080/SmartCampus-1.0-SNAPSHOT/api/v1/rooms \
-H "Content-Type: application/json" \
-d '{"id": "CS-101", "name": "Computer Science Lab", "capacity": 40}'
```

***postman*** POST
```bash
http://localhost:8080/SmartCampus-1.0-SNAPSHOT/api/v1/rooms 
```
```bash
Headder --> Content Type - application/json
Body --> {"id": "CS-101", "name": "Computer Science Lab", "capacity": 40}
```


**3. Register a New Sensor to the Room**
```bash
curl -X POST http://localhost:8080/SmartCampus-1.0-SNAPSHOT/api/v1/sensors \
-H "Content-Type: application/json" \
-d '{"id": "TEMP-001", "type": "Temperature", "status": "ACTIVE", "roomId": "CS-101", "currentValue": 22.5}'
```

***postman*** POST
```bash
http://localhost:8080/SmartCampus-1.0-SNAPSHOT/api/v1/sensors
```

```bash
Headder --> Content Type - application/json
Body --> {"id": "TEMP-001", "type": "Temperature", "status": "ACTIVE", "roomId": "CS-101", "currentValue": 22.5}
```

**4. Filter Sensors by Type**
```bash
curl -X GET "http://localhost:8080/SmartCampus-1.0-SNAPSHOT/api/v1/sensors?type=Temperature" -H "Accept: application/json"
```

***postman*** POST
```bash
http://localhost:8080/SmartCampus-1.0-SNAPSHOT/api/v1/sensors?type=Temperature
```
```bash
param (parameter) --> type - Temperature
Headder --> Accept - application/json
```


**5. Post a New Sensor Reading (Sub-Resource)**
```bash
curl -X POST http://localhost:8080/SmartCampus-1.0-SNAPSHOT/api/v1/sensors/TEMP-001/read \
-H "Content-Type: application/json" \
-d '{"id": "READ-999", "timestamp": 1713800000000, "value": 23.1}'
```

***POSTMAN*** POST
```bash
http://localhost:8080/SmartCampus-1.0-SNAPSHOT/api/v1/sensors/TEMP-001/read
```
```bash
Headder --> Content-Type - application/json
Body --> {"id": "READ-999", "timestamp": 1713800000000, "value": 23.1}
```


---

## Conceptual Report

### Part 1: Service Architecture & Setup

#### 1. Project & Application Configuration
**Question: In your report, explain the default lifecycle of a JAX-RS Resource class. Is a new instance instantiated for every incoming request, or does the runtime treat it as a singleton? Elaborate on how this architectural decision impacts the way you manage and synchronize your in-memory data structures (maps/lists) to prevent data loss or race conditions.**

**Answer:** 
**JAX-RS**: This is simply the Java toolkit used to build web APIs.

**Per-request vs. Singleton:** By default, JAX-RS creates a brand new object for every single HTTP request it receives (per-request). It does not use one shared object for everyone (which is called a Singleton).

**Thread Pool & Race Conditions:** The server handles many users at once using a thread pool (a group of worker processes). Because these workers are processing requests at the exact same time, they might try to change your data simultaneously. This causes a race condition—a bug where data gets overwritten or messed up because workers "raced" to change it at the same time.

**Synchronization:** To fix this, you have to use synchronization (creating traffic rules for your workers). By using special Java tools like ConcurrentHashMap, you ensure that workers wait their turn when updating data, preventing crashes and data loss.

#### 2. The ”Discovery” Endpoint
**Question: Why is the provision of ”Hypermedia” (links and navigation within responses) considered a hallmark of advanced RESTful design (HATEOAS)? How does this approach benefit client developers compared to static documentation?**

**Answer:**
**HATEOAS / Hypermedia:** This is a fancy term for an API that returns links along with its data.

**Benefit:** Instead of making frontend developers hardcode specific URLs into their app, your API gives them a map of links to follow. This keeps the frontend and backend decoupled (independent). If you change a URL on the backend later, the frontend automatically follows the new link, so nothing breaks.


### Part 2: Room Management

#### 1. Room Resource Implementation
**Question: When returning a list of rooms, what are the implications of returning only IDs versus returning the full room objects? Consider network bandwidth and client side processing.**

**Answer:**
**Payload & Serialization:** Returning just the Room IDs makes the payload (the size of the data sent over the internet) very small. This makes it extremely fast for the server to process (serialization).

**The N+1 Query Problem:** The downside is that if the frontend actually needs to show the sensor details, it has to make 1 request for the room, plus N extra requests for every single sensor ID. This causes high latency (delays) and makes the mobile app or website feel slow.

#### 2. Room Deletion & Safety Logic
**Question: Is the DELETE operation idempotent in your implementation? Provide a detailed justification by describing what happens if a client mistakenly sends the exact same DELETE request for a room multiple times.**

**Answer:** 
**Idempotent:** An action is "idempotent" if doing it once has the exact same result as doing it 100 times.

**Server State vs. HTTP Response:** Your delete logic is idempotent for the server state (whether you click delete once or ten times, the room is gone). However, the HTTP response is not idempotent. The first time, it replies with 204 No Content (Success). If you accidentally send the exact same request again, it replies with 404 Not Found (because the room is already gone).



### Part 3: Sensor Operations & Linking

#### 1. Sensor Resource & Integrity
**Question: We explicitly use the @Consumes (MediaType.APPLICATION_JSON) annotation on the POST method. Explain the technical consequences if a client attempts to send data in a different format, such as text/plain or application/xml. How does JAX-RS handle this mismatch?**

**Answer:**
**@Consumes:** This tag acts as a bouncer for your API. It tells Java, "Only let this request in if it is formatted as JSON."

**HTTP 415 & Boilerplate:** If a user tries to send text or XML instead, Java automatically blocks it and sends an HTTP 415 Unsupported Media Type error. The huge benefit here is that you don't have to write boilerplate code (boring, repetitive setup code) to manually check if the data format is correct. Java does it for you.

#### 2. Filtered Retrieval & Search
**Question: You implemented this filtering using @QueryParam. Contrast this with an alternative design where the type is part of the URL path (e.g., /api/vl/sensors/type/CO2). Why is the query parameter approach generally considered superior for filtering and searching collections?**

**Answer:**
**URL Path:** This is like a strict folder structure (e.g., /sensors/type/CO2).

**Query Parameter:** This is a flexible search filter (e.g., /sensors?type=CO2).

**Benefit:** URL Paths are too strict for searching. Query parameters are much better because they are flexible and stackable. You can easily search for multiple things at once (e.g., ?type=CO2&status=active) without having to redesign your clean URL folder structure.

### Part 4: Deep Nesting with Sub - Resources

#### 1. The Sub-Resource Locator Pattern

**Question: Discuss the architectural benefits of the Sub-Resource Locator pattern. How does delegating logic to separate classes help manage complexity in large APIs compared to defining every nested path (e.g., sensors/{id}/readings/{rid}) in one massive controller class?**

**Answer:**
**Sub-Resource Locator:** This is a technique where your main code (SensorResource) catches a request and passes the heavy lifting off to a helper class (SensorReadingResource).

**Single Responsibility Principle & Monoliths:** This follows the Single Responsibility Principle, which means every file should only do one specific job. By splitting the work up, you prevent your API from becoming a Monolith (a massive, messy, thousand-line file that is impossible to read or fix).

### Part 5: Advanced Error Handling, Exception Mapping & Logging

#### 2. Dependency Validation

**Question: Why is HTTP 422 often considered more semantically accurate than a standard 404 when the issue is a missing reference inside a valid JSON payload?** 

**Answer:**
**Semantic Accuracy:** This means using the exact right tool (or error code) for the job.

**404 vs. 422:** An HTTP 404 means the URL itself doesn't exist. But an HTTP 422 (Unprocessable Entity) means the URL is fine, and the JSON format is fine, but there is a logical error inside like a missing foreign key (trying to assign a sensor to a room that doesn't exist). 422 is much more accurate here.

#### 4. The Global Safety Net

**Question: From a cybersecurity standpoint, explain the risks associated with exposing internal Java stack traces to external API consumers. What specific information could an attacker gather from such a trace?**

**Answer:**
**Stack Trace:** This is the giant wall of red error text Java spits out when it crashes.

**Information Leakage & Fingerprinting:** If you show this red text to external users, it causes Information Leakage (spilling your server's secrets). Hackers use this to do Fingerprinting, figuring out exactly what versions of Java and libraries you are using.

**CVEs:** Once they know your exact setup, they search for CVEs (Common Vulnerabilities and Exposures—publicly known security holes) to easily hack your system. Your GlobalExceptionMapper prevents this by hiding the crash details and just saying "500 Internal Server Error."

#### 5. API Request & Response Logging Filters
**Question: Why is it advantageous to use JAX-RS filters for cross-cutting concerns like logging, rather than manually inserting Logger.info() statements inside every single resource method?**

**Answer:**
**Cross-cutting Concerns & AOP:** Logging is a cross-cutting concern, meaning it's a chore that every part of your app has to do.

**JAX-RS Filters:** Instead of manually typing logger.info() in every single file (which causes messy code and human error), you use JAX-RS Filters. This utilizes Aspect-Oriented Programming (AOP) which is just a fancy way of saying "setting up a checkpoint at the front door." The filter automatically logs every request entering and leaving your app without you having to touch your main code.
```
