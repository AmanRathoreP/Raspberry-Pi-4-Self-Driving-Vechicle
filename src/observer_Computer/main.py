import numpy as np
import cv2
import socket

host, port = "192.168.0.104", 8141


server = socket.socket()
server.bind((host, port))
server.listen(0)
connection, client_address = server.accept()
connection = connection.makefile('rb')
host_name = socket.gethostname()
host_ip = socket.gethostbyname(host_name)

try:
    print("Host: ", host_name + ' ' + host_ip)
    print("Connection from: ", client_address)
    print("Streaming...")
    print("Press 'q' to exit")

    # need bytes here
    stream_bytes = b' '
    while True:
        stream_bytes += connection.read(1024)
        first = stream_bytes.find(b'\xff\xd8')
        last = stream_bytes.find(b'\xff\xd9')
        if first != -1 and last != -1:
            jpg = stream_bytes[first:last + 2]
            stream_bytes = stream_bytes[last + 2:]
            image = cv2.imdecode(np.frombuffer(
                jpg, dtype=np.uint8), cv2.IMREAD_COLOR)
            image = cv2.flip(image, -1)
            cv2.imshow('image', image)

            if cv2.waitKey(1) & 0xFF == ord('q'):
                break
except Exception as e:
    print(e)
    connection.close()
    server.close()
