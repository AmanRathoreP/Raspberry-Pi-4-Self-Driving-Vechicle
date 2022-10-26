import socket
from keyboard import is_pressed
from time import sleep as sl
import numpy as np

s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
print("connecting to rpi...")
s.connect(('192.168.0.105', 8947))  # Connects to server
print("rpi connected!")

vehicle_vals = {"direction": 'x',
                "right_side_motors": 65535, "left_side_motors": 65535}
gears = np.arange(0.7, 1.001, 0.06)
gear_keys_list = ['x', 'q', 'w', 'e', 'r', 't']
control_keys = ["space", "left", "right", "down", "up"]
control_directions = ['x', 'l', 'r', 'b', 'f']

while True:
    print(
        f"""{vehicle_vals["direction"]}-{vehicle_vals["left_side_motors"]}-{vehicle_vals["right_side_motors"]}""")
    s.send(f"""{vehicle_vals["direction"]}-{vehicle_vals["left_side_motors"]}-{vehicle_vals["right_side_motors"]}\n""".encode(
    ))  # Encodes and sends message (x)

    vehicle_vals["direction"] = control_directions[0]

    for i in np.flip(np.arange(1, 6)):
        if (is_pressed(gear_keys_list[i])):
            vehicle_vals["right_side_motors"] = int(gears[i]*65535)
            vehicle_vals["left_side_motors"] = int(gears[i]*65535)

    for i in np.flip(np.arange(0, 5)):
        if (is_pressed(control_keys[i])):
            vehicle_vals["direction"] = control_directions[i]

    sl(0.0001)
