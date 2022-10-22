import random


class SDC:
    last_log = None
    serial_msg = None

    def __init__(self):
        self.add_log("object created created(I'm in init of class \"SDC\")")

    def add_log(self, my_log):
        print(my_log)
        self.last_log = my_log

    def random_movement(self):
        return f"""{random.choice(['f','b','r','l'])}-{random.randint(15535,65535)}-{random.randint(15535,65535)}\n"""

    def get_vehicle_data(self):
        return self.random_movement()

    def __capture_image(self):
        pass
