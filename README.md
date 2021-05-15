# PravegaTwitterConsumer

Pravega Twitter Consumer do the following activities 
1. Consumes the tweets from pravega stream/scope
2. Classify the tweet based on a Classification Model API
3. Push the classified tweet object on the elasticsearch server

## Pre-reqs:
1. Pravega Server must be running at: tcp://127.0.0.1:9090
2. Elastic Search must be running at: http://127.0.0.1:9200 