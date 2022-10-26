<div align="center">
  <!-- <img src="./img/ff_logo2013.png" width="200px"> -->
  <h1>Self-Driving car using Raspberry pi 4</h1>
</div>

<p align="center">
  This is a Self-Driving car (SDC) project which uses pi pico as a slave device and various sensor in order to work properly.<br><img alt="GitHub all releases" src="https://img.shields.io/github/downloads/AmanRathoreP/Bluetooth-wireless-car-with-various-features/total">
</p>


## Features

* Traffic signs detection
* ...

## Quick start

First setup pico-sdk c environment ...

### About the files
```
📦Raspberry-Pi-4-Self-Driving-Vechicle
 ┣ 📂others                         # Contains some of the informative files, see internal 📜README.md from more details
 ┃ ┗ 📜README.md                    # Internal 📜README of the directory 📂others
 ┣ 📂resources                      # Files which are useful as a reference, see internal 📜README.md from more details
 ┃ ┗ 📜README.md                    # Internal 📜README of the directory 📂resources
 ┣ 📂src                            # Contains all the source code of the project
 ┃ ┣ 📂master(Raspberry Pi 4B)      # files related to master device i.e. Raspberry pi 4
 ┃ ┃ ┗ 📂src                        # source code of the master device i.e. Raspberry pi 4
 ┃ ┃ ┃ ┣ 📂packages                 # contains some of the files used by the master device
 ┃ ┃ ┃ ┃ ┗ 📜my_class.py            # file containing some of the important classes and functions
 ┃ ┃ ┃ ┗ 📜main.py                  # file responsible for execution of instructions of master device
 ┃ ┣ 📂Observer(Computer)           # files related to observer i.e. external computer(desktop in my case)
 ┃ ┃ ┗ 📂src                        # source code of the observer i.e. external computer(desktop in my case)
 ┃ ┃ ┃ ┣ 📜main.py                  # file responsible for the socket connection between the observer and the master
 ┃ ┃ ┃ ┗ 📜main_recv_img.py         # file responsible for video streaming from master to observer
 ┃ ┗ 📂slave(Pi-Pico)               # files related to slave device i.e. Raspberry pi pico
 ┃ ┃ ┣ 📂include                    # files which should be included in order to use FreeRTOS. go to FreeRTOS docs to know more
 ┃ ┃ ┃ ┗ 📜FreeRTOSConfig.h         # FreeRTOS configuration file
 ┃ ┃ ┣ 📂src                        # source code of the slave device
 ┃ ┃ ┃ ┣ 📜functions.c              # contains all the functions used by 📜main.c i.e. slave device
 ┃ ┃ ┃ ┣ 📜functions.h              # header file for 📜functions.c
 ┃ ┃ ┃ ┣ 📜global_vars.c            # contains all the constants values and variables which are used across all the src files of slave
 ┃ ┃ ┃ ┣ 📜global_vars.h            # header file for 📜global_vars.c
 ┃ ┃ ┃ ┗ 📜main.c                   # file responsible for the execution of all the instructions given to slave 
 ┃ ┃ ┣ 📜CMakeLists.txt             # file used to create a build to flash into slave
 ┃ ┃ ┣ 📜pico_sdk_import.cmake      # file used in build see pi-pico SDK docs from more details
 ┃ ┃ ┗ 📜setup.md                   # file containing information about creating successful build from all the source files
 ┣ 📜.gitignore                     # file containing information about which files should be remain untracked by git
 ┣ 📜issues.md                      # file containing some of the issues/problems regarding the project
 ┗ 📜README.md                      # file containing all these lines and detailed information about the project
```

## Contributing [![contributions welcome](https://img.shields.io/badge/contributions-welcome-brightgreen.svg?style=flat)](issues.md)

Thank you for considering contributing to Bluetooth wireless car!

First note we have a code of conduct, please follow it in all your interactions with the program files.

We welcome any type of contribution, _not only code_. You can help with:
- **QA**: File bug reports, the more details you can give the better (e.g. images or videos)
- **Code**: Take a look at the [open issues](issues.md). Even if you can't write the code yourself, you can comment on them, showing that you care about a given issue matters. It helps us to handel them
- **New Features**: You can suggest an modifications or just ask for advancements in the old features of SDC.

## Demo

Will be available soon in the form of videos and images

## Author

- [@Aman](https://www.github.com/AmanRathoreP)
   - [GitHub](https://www.github.com/AmanRathoreM)
   - [Instagram](https://www.instagram.com/aman__0864/)
   - [Telegram](https://t.me/aman0864)
   - Email -> *aman.proj.rel@gmail.com*

## License

[MIT License](https://choosealicense.com/licenses/mit/)

Copyright (c) 2022, Aman Rathore

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.