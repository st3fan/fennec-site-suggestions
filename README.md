
What is this?
-------------

This is a proof of concept change to Firefox for Android to implement site name suggestions.

I think this is specially useful on mobile where you want to go to your destination with as little clicks and keypresses as possible. The awesomebar does a really good job for that, but not so much when you start with a clean profile. Which is likely a very common case for Firefox for Android users.

Implementation
--------------

This is using a site suggestion service that takes a prefix like 'fl' and and then returns a list of populair sites like ['flickr.com', 'flipkart.com', 'flipora.com']. The service was written in Java and uses an open source Trie implementation as its database. On a development workstation it serves between 7500 and 11000 suggestions per second.

The suggestions are currently taken from the public Alexa Top One Million sites. This is a good list but it is unfiltered. It does contain adult domain names and these will show up in this demo. So please be aware of that when you play with this.

On the Android side I took the easy route to implement this and simply replaced the search term suggestions with these site name suggestions. The changes are minimal and not a good example of how to do this. It is a proof of concept.

The code for the server and the change to Firefox for Android are included, as well as a development build that should run on your device if you allow installs from unknown sources.

As of August 31, 2012 the service is also deployed on EC2 and the build in this repository points to that. That server will not be online forever but it is easy to run your own.


Advantages of doing this on the server
--------------------------------------

* It makes the Privacy and Security discussion simpler. Mozilla would host the servers so no data is sent to third parties. We will do the right thing.
* We could serve suggestions that are localized.
* We can keep the list of suggestions up to date and not rely on Firefox release schedules.
* It keeps the download size of Fennec small. The Alex list is 22MB. It can be compressed but that results in other issues on mobile.
* More CPU power and flexibility on the server side

Disadvantages of doing this on the server
-----------------------------------------

* We need to make network requests from Fennec
* We need to scale this to millions of users
* The service would need to be hosted somewhere. It is likely a relatively simple (read-only) service. But still, ops is busy enough already.

Challenges
----------

* Getting high quality data; The Alexa Top Million Sites list is a good start but it is unfiltered and global. It will be a challenge to acquire or collect high quality top-sites data for different countries. This data can possibly be extracted from anonymized DNS dumps if ISPs are willing to work with us.
* Hosting this is obviously always an interesting task. But this service would not be mission critical. If the API does not respond then there would simply be no suggestions. The user can still type a destination and go there of course.
* 
