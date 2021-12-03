#!/usr/bin/python3
import http.client
import uuid
import time
import random
from concurrent.futures import ThreadPoolExecutor

def createEvent():
    conn = http.client.HTTPConnection("localhost")
    id = str(uuid.uuid4())
    types = ['CLICK', 'VIEW']
    urls = ["https://www.google.com","https://www.facebook.com","https://www.youtube.com"]
    payload = "{\n\t\"uuid\":\""+ id +"\",\n\t\"url\":\""+random.choice(urls)+"\",\n\t\"type\": \""+random.choice(types)+"\",\n\t\"ip\":\"323.3253.34.999\"\n}"
    headers = { 'Content-Type': "application/json" }
    conn.request("PUT", "/event_generate/", payload, headers)
    res = conn.getresponse()

def generateTraffic():
    while True:
        createEvent()
        time.sleep(random.randint(3,5))

if __name__ == "__main__":
    executor = ThreadPoolExecutor(max_workers=5)
    executor.submit(generateTraffic)