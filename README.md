**Requirements**
* Java 8 or higher

**How to run and access**

````
Run the application via maven or build the jar and run the jar file. 

Then access
http://localhost:8080/matcher/{id}

{id} -> Whichever the worker id needs to be matched with relevant jobs

````

**Design**

Currently application caches the workers and jobs in memory and filter them when a 
worker id is given. This will not scale but will work as a POC. If this is to scale 
number of changes would have to be done on both the application and swipejobs API as well.
As for the application, data can be stored in a mongodb and use mongodb's geospatial queries
as these filtering includes location data. And for the swipejobs API currently it sends
all the data in one go, and it should be able to page or even filter. 

_**Matching Engine**_

For the matching engine it first consider the geo location of the worker 
and distance worker willing to travel to the geo location to the given job. Using
this method we can narrow down all the jobs available for a given worker. 
After narrowing down from the location it will take required certificates into the
consideration for further narrowing down the jobs which are related to the given 
worker. And finally it will limit the number of records to 3 without any special 
ordering.

How this is done currently is using a filter chain. System could have multiple filters
for the matching engine but for this one it uses the geo spatial, required certificates
and max records filters. And it's being built using a pre defined manner. But this can
be extended and filter chain can be configured in a dynamic manner.

Note: Distance calculator code was sourced from https://www.geodatasource.com

**Assumptions** 

* Data returned from swipejobs api will change per call to the API. Therefor each time 
new API is going to get called it will retrieve the data from swipejob api

* Currently it's only showing the available jobs. How many workers see the same job 
listing won't affect other searchers

* Driver licence or available date is not considered in the matching process.

* Before limiting the number of records it needs to sorted by starting date.


**Improvements**

* Improvement can be added for max record filter to use a strategy to sort the details
according to a given config and then limit.

* Filter chain to be able to configure dynamically rather than the current fixed chain
 
* Can cache/save data returned through api and implement a circuit breaker in case of the
swipejob api fails.
 