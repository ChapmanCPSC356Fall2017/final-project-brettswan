# final-project-brettswan
final-project-brettswan created by GitHub Classroom

## FinNet

I started this project with the goal of building a neural network on my home computer that I could on-demand train stock data on and return predictions directly 
to the device. I got most of the way there, with a successfully trained neural network, and a Python server running flask to handle requests, which the app is programmed
to make requests to (and does so successfully via my home's public IP on port 3500). I was not however able to get the output from the neural network into an understandable data format
for the app to read, so I settled for running some very basic statistics for the company queried. I would like to get this completely functional as I plan on switching to android soon and
the kind of insights the neural network provides could be very fruitful. Anyways, there are actually 2 web calls, one to the same stock API from class to return the first round of data,
then on the second request, it takes the stock ticker and a date to analyze back to, and sends that in the url to my home computer, which parses the url, and then makes yet another API call 
to a financial API called AlphaVantage, where I get the daily open, close, high, low, and volume for the requested stock for as far back as their data goes, and then run summary statistics, repackage into JSON,
and respond to the GET request.
