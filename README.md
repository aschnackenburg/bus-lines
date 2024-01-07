# Bus-lines
This application counts the number of stops on each bus line using Trafiklabs API for SL lines and stops. 
In this application I defined a bus stop as a stop with a unique name. Even if a bus would stop at the same stop
in different directions (for example taking a detour and then the same way back) when running from the first stop
to the last stop I only count the stop once.

## Application structure
I wanted to keep the logic that has with Trafiklabs API very separate, so I separated the client/service that uses
the object model from Trafiklab from the rest of the application. `TrafiklabService` returns an internal object model
to be able to quickly be able to remake the API call and conversion when the API will be switched out without touching
the business logic. `TrafiklabClient` has an interface to be able to use a test implementation in unit tests.

`BusLinesService` contains the business logic, as well as a cache-like storage that would refresh each day. This means
that the first call each day calls Trafiklabs APIs and will take a few seconds, but for subsequent calls the information
is stored in-memory.

I originally built only `BusLinesController` but since the task included the instruction to be able to list all stops
for the line with most stops, I added `BusLinesWithMostStopsController` just to be sure in case fetching the top 10 lines 
with most stops and then check the response for the name of the line with most stops and fetch info for that specific 
line wasn't enough.

`RestControllerExceptionHandler` handles errors, and returns them as a simple response object. I added handling for one
specific exception, and one generic handler for `Exception` to not send out any stack trace to an end user.

For testing, there is unit tests for most of the application. I have tests that uses most of the application context to
make sure the components work correctly with each other. I have mostly focused on happy-path tests, with a single test
to verify that the response is a 404 if a line isn't found. I am not testing `TrafiklabClient`, and am instead using a 
test implementation that reads a json-file instead of making a call.

## Running the application
Before building or running, add your api key from Trafiklab to [application.properties](src/main/resources/application.properties) 
to `trafiklab.api-key` replacing `YOUR-TRAFIKLAB-API-KEY`.

### Building the application
This application is built using gradle. While in the project folder, build using the command ```./gradlew build```. 
The jar-file is located in [build/libs](build/libs).

### Running the application
If not running from the IDE, after building run using the command ```java -jar build/libs/bus-lines-0.0.1-SNAPSHOT.jar```.

## Improvements given more time
Some improvements I would make to this application given more time would be
- Swagger documentation.
- Remove the in-memory cache-like solution for either a cache or some kind of persistent storage.
- Add more exception handling and retry-functionality when the requests to Trafiklab would fail.
- Add more unit tests for the business logic in `BusLinesService`

### Problems while building the application
When I built the application, the biggest problem I had was that I got an error when building using `gradlew` locally when
at the same time building it using IntelliJ IDEA worked without issue. I managed to solve it by checking the error logs 
and after some trial and error (including a bit of reading on stack overflow) adding include `'src/test/**'` to 
build.gradle solved the issue. 

Another minor problem I had was understanding exactly what was meant by `the most bus stops`. For me, it can mean either 
how many times the bus stops if it would make every stop when running from A to B or each stop a line has based on the name
of the stop, regardless on how many times it stops there. In the end, after sending an email and getting a reply I decided 
to go with the definition of stops with unique name. I mainly went this route due to time constraints and not having to 
think about edge-cases. For example, if you count each stop how would you count a stop that is only used in one direction?