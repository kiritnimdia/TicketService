# TicketService

### Assumptions
1. Users are allocated seats based on the availability and ordering (best seats first).
2. Hold time for the seats is configurable. If the user doesn't reserve the seats before configurable seconds, then the holds are removed and user has to send a request again to hold the seats.
3. No notification for the expiration of seat holds.
4. Rows and seats per rows are configurable (keys and values are provided in properties file)
5. If request to hold number of seats exceed the available seat then available seats will put on hold.

===================================
# Requirements
===================================
+ Java 8
+ Maven 3

===================================
# Build Project By Maven
===================================

 mvn clean package

===================================
# Running Test
===================================
From a terminal run:

    mvn test
    