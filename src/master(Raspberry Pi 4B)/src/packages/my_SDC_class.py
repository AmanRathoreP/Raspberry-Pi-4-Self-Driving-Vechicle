import time
import socket

class SDC:
    last_log = None
    serial_msg = None
    img_click_time = None
    img = None

    def __init__(self, send_data):
        self.add_log("Creating object...")
        self.send_data = send_data
        if send_data:
            self.add_log("Connecting to observer...")
            try:
                self.observer = socket.socket(
                    socket.AF_INET, socket.SOCK_STREAM)
                # * Connects to server
                self.observer.connect(("192.168.0.103", 8141))
                self.send_data = True  # * this is necessary because call of func "SDC.add_log()" before establishing socket connection had forced SDC.send_data = False
                self.add_log("Observer connected successfully!")
            except:
                self.send_data = False
                self.add_log(
                    "Unable to establish socket connection!, forcing \"send_data = False\"")
        self.add_log(
            "Object successfully created!")

    def add_log(self, my_log):
        self.last_log = f"{format(time.time(),'.10f')}-> {my_log}"
        print(self.last_log)

my_sdc = SDC(True)
