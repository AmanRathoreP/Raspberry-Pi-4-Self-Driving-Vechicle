import serial
from packages.my_SDC_class import SDC
from time import sleep as sl

my_sdc = SDC()

# * timeout=0.001 is also enough
ser = serial.Serial('/dev/ttyACM0', 115200, timeout=0.009)

# listen for the input, exit if nothing received in timeout period
while True:
    data_to_send = my_sdc.get_vehicle_data()
    ser.write(data_to_send.encode())
    sl(1)

    try:
        my_sdc.serial_msg = ser.readline().decode().replace('\n', '')
    except:
        my_sdc.add_log("Error in reading serial message!")
    if len(my_sdc.serial_msg) == 0:
        my_sdc.add_log("Time out! in serial connection")
    my_sdc.add_log(f"Data received {my_sdc.serial_msg}")
