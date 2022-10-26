import socket

host, port = "192.168.0.103", 8474

def data_rcv_socket():
    global clientsocket #* making clientsocket as global so that we can access it even after function ends
    listensocket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    listensocket.bind(('0.0.0.0', port))
    #* Starts server
    listensocket.listen(999)
    print("Server started at " + socket.gethostname() + " on port " + str(port))
    #* Accepts the incoming connection
    (clientsocket, address) = listensocket.accept()
    print(
        f"New connection made! at address = {address} this connection is established to get data from server as a string!")


data_rcv_socket()

while True:
    message = clientsocket.recv(1024).decode()  # * Gets the incoming message
    print(message)
