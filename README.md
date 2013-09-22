AngrassBat
==========

This is an intelligence gathering bot for the (awesome) game Ingress.

The idea is that the easiest points can be gotten from unclaimed portals. It is a total pain to search for unclaimed portals because you have to zoom in like crazy. Since I hate slow / tedious processes, I've decided to automate this.

This project was my first experience with screen scraping. The program uses the Java Robot class to take a screen shot. Then it passes it through a filter (for pixels with a high white value) and uses the Connected Components algorithm for counting the number of portals. That's the technical meat of it, mostly it just drags the map around.

There is a bug for players who are using this along the 180th meridian. That means you folk in Siberia, Antarctica, and Fiji. It is pretty much a longitudal integer overflow, where the program will loop around the globe if it passes over the 180th and ends up outside of it's search grid. Feel free to submit a patch if this bug affects you.
