import socket
# import random
import keyboard as kb
from time import sleep as sl
from numpy import arange

s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
print("connecting to rpi...")
s.connect(('192.168.0.105', 8947))  # Connects to server
print("rpi connected!")

vehicle_vals = {"direction": 'x',
                "right_side_motors": 65535, "left_side_motors": 65535}
gears = arange(0.7, 1.001, 0.06)

while True:
    print(
        f"""{vehicle_vals["direction"]}-{vehicle_vals["left_side_motors"]}-{vehicle_vals["right_side_motors"]}""")
    s.send(f"""{vehicle_vals["direction"]}-{vehicle_vals["left_side_motors"]}-{vehicle_vals["right_side_motors"]}\n""".encode(
    ))  # Encodes and sends message (x)

    if (kb.is_pressed('q')):
        vehicle_vals["right_side_motors"] = int(gears[1]*65535)
        vehicle_vals["left_side_motors"] = int(gears[1]*65535)
    elif (kb.is_pressed('w')):
        vehicle_vals["right_side_motors"] = int(gears[2]*65535)
        vehicle_vals["left_side_motors"] = int(gears[2]*65535)
    elif (kb.is_pressed('e')):
        vehicle_vals["right_side_motors"] = int(gears[3]*65535)
        vehicle_vals["left_side_motors"] = int(gears[3]*65535)
    elif (kb.is_pressed('r')):
        vehicle_vals["right_side_motors"] = int(gears[4]*65535)
        vehicle_vals["left_side_motors"] = int(gears[4]*65535)
    elif (kb.is_pressed('t')):
        vehicle_vals["right_side_motors"] = int(gears[5]*65535)
        vehicle_vals["left_side_motors"] = int(gears[5]*65535)

    if (kb.is_pressed("space")):
        vehicle_vals["direction"] = 'x'
    elif (kb.is_pressed("left")):
        vehicle_vals["direction"] = 'l'
    elif (kb.is_pressed("right")):
        vehicle_vals["direction"] = 'r'
    elif (kb.is_pressed("down")):
        vehicle_vals["direction"] = 'b'
    elif (kb.is_pressed("up")):
        vehicle_vals["direction"] = 'f'
    else:
        vehicle_vals["direction"] = 'x'
    sl(0.0001)
