DistributedSail
===============

Implementation approach of the OpenRDF SAIL API in a distributed database setting.

Concept
-------
The idea is to connect multiple SAIL-based stores via a middleware to form a distributed database.
This if mainly for research purposes, but can also be used (eventually) in real scenarios.
More details to a later point.

Usage
-----
Yeah, I will explain it in detail asap. Code is documented, though :)

Acknowledgements
----------------
* [OpenRDF](http://www.openrdf.org/) to offer their [SAIL API](http://openrdf.callimachus.net/sesame/2.7/docs/articles/sail-api.docbook?view) as a connection between this approach and other RDF storage systems.
* [ZeroMQ](http://zeromq.org/), especially [JeroMQ](https://github.com/zeromq/jeromq) for offering a simple, but mighty middleware.
* Special thanks to [Heinrich Hartmann's RequestDispatcher](https://github.com/HeinrichHartmann/RequestDispatcher) for offering me an even simpler way to use JeroMQ.
