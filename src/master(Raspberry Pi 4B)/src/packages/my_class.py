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
import socket
import io
import struct

_base_img = cv2.imread(
    "/home/aman/PROJECT/project_files/master_code/src/stop sign.png", 0)


class img_prs():
    def __init__(self, img, show_img=False) -> None:
        self.raw_img = img
        self.processed_img = self.raw_img
        self.img_result = ""
        # self.img_result = "img not processed!"

    def __find_features(self, MIN_MATCH_COUNT=13):
        # Initiate SIFT detector
        sift = cv2.SIFT_create()
        # find the keypoints and descriptors with SIFT
        kp1, des1 = sift.detectAndCompute(_base_img, None)
        kp2, des2 = sift.detectAndCompute(self.raw_img, None)
        FLANN_INDEX_KDTREE = 1
        index_params = dict(algorithm=FLANN_INDEX_KDTREE, trees=5)
        search_params = dict(checks=50)
        flann = cv2.FlannBasedMatcher(index_params, search_params)
        matches = flann.knnMatch(des1, des2, k=2)
        # store all the good matches as per Lowe's ratio test.
        good = []
        for m, n in matches:
            if m.distance < 0.7*n.distance:
                good.append(m)
        if len(good) > MIN_MATCH_COUNT:
            self.img_result = 'y'  # * "y" = yes if stop sign is detected
        else:
            self.img_result = 'n'  # * "n" = no if stop sign is not detected

    def final_result(self):
        self.__find_features(11)
        return self.img_result


class ImageProcessor(threading.Thread):
    def __init__(self, owner):
        super(ImageProcessor, self).__init__()
        self.stream = io.BytesIO()
        self.event = threading.Event()
        self.terminated = False
        self.owner = owner
        self.start()

    def run(self):
        # * This method runs in a separate thread
        while not self.terminated:
            # * Wait for an image to be written to the stream
            if self.event.wait(1):
                try:
                    my_sdc.observer_stream = self.stream
                    self.stream.seek(0)
                    # * Read the image and do some processing on it
                    # * Image.open(self.stream)
                    # * ...
                    # * ...
                    # * Set done to True if you want the script to terminate
                    # * at some point
                    # * self.owner.done=True

                    # * Do all the image processing stuff here
                    # * Do all the image processing stuff here
                    # * Do all the image processing stuff here
                    # * Do all the image processing stuff here
                    # * Do all the image processing stuff here

                    my_sdc.img_click_time = time.time()
                    my_sdc.img = cv2.cvtColor(imdecode(np.fromstring(
                        self.stream.getvalue(), dtype=np.uint8), 1), cv2.COLOR_BGR2GRAY)
                    my_sdc.add_log(img_prs(my_sdc.img).final_result()*50)
                    # my_sdc.send_img()

                finally:
                    # * Reset the stream and event
                    self.stream.seek(0)
                    self.stream.truncate()
                    self.event.clear()
                    # * Return ourselves to the available pool
                    with self.owner.lock:
                        self.owner.pool.append(self)


class ProcessOutput(object):
    def __init__(self):
        self.done = False
        # * Construct a pool of 4 image processors along with a lock
        # * to control access between threads
        self.lock = threading.Lock()
        self.pool = [ImageProcessor(self) for i in range(4)]
        self.processor = None

    def write(self, buf):
        if buf.startswith(b'\xff\xd8'):
            # * New frame; set the current processor going and grab
            # * a spare one
            if self.processor:
                self.processor.event.set()
            with self.lock:
                if self.pool:
                    self.processor = self.pool.pop()
                else:
                    # * No processor's available, we'll have to skip
                    # * this frame; you may want to print a warning
                    # * here to see whether you hit this case
                    self.processor = None
        if self.processor:
            self.processor.stream.write(buf)

    def flush(self):
        # * When told to flush (this indicates end of recording), shut
        # * down in an orderly fashion. First, add the current processor
        # * back to the pool
        if self.processor:
            with self.lock:
                self.pool.append(self.processor)
                self.processor = None
        # * Now, empty the pool, joining each thread as we go
        while True:
            with self.lock:
                try:
                    proc = self.pool.pop()
                except IndexError:
                    pass  # * pool is empty
            proc.terminated = True
            proc.join()


class SDC:
    last_log = None
    serial_msg = None
    img_click_time = None
    img = None
    send_data = False

    def send_img(self):
        self.observer_connection_obj.write(
            struct.pack('<L',  self.observer_stream.tell()))
        self.observer_connection_obj.flush()
        self.observer_stream.seek(0)
        self.observer_connection_obj.write(self.observer_stream.read())
        self.observer_stream.seek(0)
        self.observer_stream.truncate()

        print("Sending")

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
                self.observer_connection_obj = self.observer.makefile('wb')
                self.observer_stream = io.BytesIO()
                self.send_data = True  # * this is necessary because call of func "SDC.add_log()" before establishing socket connection had forced SDC.send_data = False
                self.add_log("Observer connected successfully!")
            except:
                self.send_data = False
                self.add_log(
                    "Unable to establish socket connection!, forcing \"send_data = False\"")
        self.add_log(
            "Object successfully created!")

    def add_log(self, my_log, send_data_to_observer=False):
        self.last_log = f"{format(time.time(),'.10f')}-> {my_log}"
        print(self.last_log)
        if (self.send_data & send_data_to_observer):
            self.__send_data_to_observer(self.last_log)

    def random_movement(self):
        return f"""{random.choice(['f','b','r','l'])}-{random.randint(15535,65535)}-{random.randint(15535,65535)}\n"""

    def get_vehicle_data(self):
        return self.random_movement()

    def send_data_to_observer(self, data_to_send, force_send=False):
        """This takes the data in the form of ... and send it to the observer(computer in my case)
        Parameters
        ----------
        data_to_send : str
            This is your data in the from of string which you want to send to observer via socket.
        force_send : bool, default=False
            This defines weather you want to send data to observer even when given object is configured to not send data to observer.
        """
        if (self.send_data):
            self.__send_data_to_observer(data_to_send)
        elif(force_send):
            self.add_log("Force send to observer!")
            self.__send_data_to_observer(data_to_send)
        else:
            self.add_log("Useless call of \"send_data_to_observer(...)\"!")

    def __send_data_to_observer(self, data):
        """This takes the data in the form of string and sends it to the observer(computer in my case)
        """
        try:
            self.observer.send(data.encode())
        except:
            self.send_data = False
            self.add_log(
                "Unable communicate to observer via socket!, forcing \"send_data = False\"")


my_sdc = SDC(True)
