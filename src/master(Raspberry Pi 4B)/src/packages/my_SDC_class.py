import random
import io
import time
import threading
import picamera
from cv2 import imdecode
from cv2 import imwrite as imw
import cv2
import numpy as np
from PIL import Image as PILImage


class ImageProcessor(threading.Thread):
    def __init__(self, owner):
        super(ImageProcessor, self).__init__()
        self.stream = io.BytesIO()
        self.event = threading.Event()
        self.terminated = False
        self.owner = owner
        self.start()

    def run(self):
        # This method runs in a separate thread
        while not self.terminated:
            # Wait for an image to be written to the stream
            if self.event.wait(1):
                try:
                    self.stream.seek(0)
                    # Read the image and do some processing on it
                    # Image.open(self.stream)
                    # ...
                    # ...
                    # Set done to True if you want the script to terminate
                    # at some point
                    # self.owner.done=True

                    #* Do all the image processing stuff here
                    #* Do all the image processing stuff here
                    #* Do all the image processing stuff here
                    #* Do all the image processing stuff here
                    #* Do all the image processing stuff here

                    self.my_image_click_time = time.time()
                    self.my_raw_img = cv2.cvtColor(imdecode(np.fromstring(
                        self.stream.getvalue(), dtype=np.uint8), 1), cv2.COLOR_BGR2GRAY)

                    imw(f"captured_imgs/clicked on - {time.time()}.jpg",
                        self.my_raw_img)

                finally:
                    # Reset the stream and event
                    self.stream.seek(0)
                    self.stream.truncate()
                    self.event.clear()
                    # Return ourselves to the available pool
                    with self.owner.lock:
                        self.owner.pool.append(self)


class ProcessOutput(object):
    def __init__(self):
        self.done = False
        # Construct a pool of 4 image processors along with a lock
        # to control access between threads
        self.lock = threading.Lock()
        self.pool = [ImageProcessor(self) for i in range(4)]
        self.processor = None

    def write(self, buf):
        if buf.startswith(b'\xff\xd8'):
            # New frame; set the current processor going and grab
            # a spare one
            if self.processor:
                self.processor.event.set()
            with self.lock:
                if self.pool:
                    self.processor = self.pool.pop()
                else:
                    # No processor's available, we'll have to skip
                    # this frame; you may want to print a warning
                    # here to see whether you hit this case
                    self.processor = None
        if self.processor:
            self.processor.stream.write(buf)

    def flush(self):
        # When told to flush (this indicates end of recording), shut
        # down in an orderly fashion. First, add the current processor
        # back to the pool
        if self.processor:
            with self.lock:
                self.pool.append(self.processor)
                self.processor = None
        # Now, empty the pool, joining each thread as we go
        while True:
            with self.lock:
                try:
                    proc = self.pool.pop()
                except IndexError:
                    pass  # pool is empty
            proc.terminated = True
            proc.join()

class SDC:
    last_log = None
    serial_msg = None

    def __init__(self):
        self.add_log("object created created(I'm in init of class \"SDC\")")

    def add_log(self, my_log):
        print(f"{format(time.time(),'.10f')}-> {my_log}")
        self.last_log = my_log

    def random_movement(self):
        return f"""{random.choice(['f','b','r','l'])}-{random.randint(15535,65535)}-{random.randint(15535,65535)}\n"""

    def get_vehicle_data(self):
        return self.random_movement()

    def __capture_image(self):
        pass
