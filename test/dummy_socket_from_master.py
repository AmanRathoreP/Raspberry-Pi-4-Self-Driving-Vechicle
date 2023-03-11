"""
This file will help to test the java application on the observer by establishing a connection between observer and master like software, which will pretend to be master even though it is a test file.
"""


import socket
import time

IP_ADDRESS = "localhost"
PORT = 5000
s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
s.connect((IP_ADDRESS, PORT))

for i in range(100):
    data = f"Hello, Java! {i}"
    s.sendall(data.encode())
    time.sleep(0.1)