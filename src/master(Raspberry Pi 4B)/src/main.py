import serial
from packages.my_SDC_class import *

# * timeout=0.001 is also enough
ser = serial.Serial('/dev/ttyACM0', 115200, timeout=0.009)

listensocket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
addr, port = "0.0.0.0", 8947
listensocket.bind((addr, port))
# Starts server
listensocket.listen(999)
print("Server started at " + socket.gethostname() + " on port " + str(port))
# Accepts the incoming connection
(clientsocket, address) = listensocket.accept()
print("New connection made!")

while True:
    # Gets the incoming message
    message = f"{clientsocket.recv(1024).decode()}"
    #! data_to_send = my_sdc.get_vehicle_data()
    data_to_send = message
    ser.write(data_to_send.encode())

    try:
        my_sdc.serial_msg = ser.readline().decode().replace('\n', '')
    except:
        my_sdc.add_log("Error in reading serial message!")
    if len(my_sdc.serial_msg) == 0:
        my_sdc.add_log("Time out! in serial connection")
    my_sdc.add_log(
        f"Data received {my_sdc.serial_msg}",)

my_sdc.add_log(
    "Endless while loop ended! Camera is somehow unable to fetch images")
