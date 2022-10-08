import sys
import serial
import time

ser = serial.Serial('/dev/ttyACM0', 115200, timeout=80)

# listen for the input, exit if nothing received in timeout period
while True:

   ser.write(f'''{input("Enter data to serial: ")}\n'''.encode())
   # ser.write("0\n".encode())

   line = ser.readline()
   if len(line) == 0:
      print("Time out! Exit.\n")
      sys.exit()
   print(line.decode())
