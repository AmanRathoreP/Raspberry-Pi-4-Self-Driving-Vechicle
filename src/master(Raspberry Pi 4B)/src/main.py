import serial
from packages.my_SDC_class import *
from time import sleep as sl

#! my_sdc = SDC() #* This is declared in the end of the file "my_SDC_class.py"

# * timeout=0.001 is also enough
ser = serial.Serial('/dev/ttyACM0', 115200, timeout=0.009)

camera = picamera.PiCamera(resolution='VGA')
camera.resolution = (340, 240)
camera.framerate = 90
camera.color_effects = (128, 128)
camera.start_preview()
my_sdc.add_log("initializing camera...")
time.sleep(2)  # * giving time to camera to initialize
my_sdc.add_log("camera initialized!")
output = ProcessOutput()
camera.start_recording(output, format='mjpeg')

#* listen for the input, exit if nothing received in timeout period
while not output.done:
    camera.wait_recording(0.069)
    my_sdc.add_log("img captured", send_data_to_observer=False)
    data_to_send = my_sdc.get_vehicle_data()
    ser.write(data_to_send.encode())

    try:
        my_sdc.serial_msg = ser.readline().decode().replace('\n', '')
    except:
        my_sdc.add_log("Error in reading serial message!")
    if len(my_sdc.serial_msg) == 0:
        my_sdc.add_log("Time out! in serial connection")
    my_sdc.add_log(
        f"Data received {my_sdc.serial_msg}", send_data_to_observer=True)

my_sdc.add_log(
    "Endless while loop ended! Camera is somehow unable to fetch images")
camera.stop_recording()
camera.close()
