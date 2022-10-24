import socket
listensocket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
listensocket.bind(('0.0.0.0', 8141))
#* Starts server
listensocket.listen(999)
print("Server started at " + socket.gethostname() + " on port " + str(8000))
#* Accepts the incoming connection
(clientsocket, address) = listensocket.accept()
print(f"New connection made! at address = {address}")
while True:
    message = clientsocket.recv(1024).decode()  #* Gets the incoming message
    print(message)
