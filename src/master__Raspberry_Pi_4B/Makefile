# NOTE: if you are having your file structure in the following way
# ├── lib
# └── src
#     ├── main.cpp
#     ├── main.py
#     ├── other.cpp
#     ├── packages
#     │   └── my_class.py
#     └── src
#         └── ot2.cpp
# then you need to have build directory structure in the following way if you don't want any build errors
# ├── Makefile
# ├── build
# │   ├── main.exe
# │   └── obj
# │       ├── main.o
# │       ├── other.o
# │       └── src
# │           └── ot2.o
# ├── include
# ├── lib
# don't worry for *.o, these files will be automatically be created files just create the nested directoy structure



CC=g++
OPTIMIZATION=-O0
CFLAGS=-g -Wall $(OPTIMIZATION)
SRC=src
BIN=build
RUNNABLE=main
OBJ=$(BIN)/obj

SRCS= $(wildcard $(SRC)/*.cpp) $(wildcard $(SRC)/*/*.cpp) $(wildcard $(SRC)/*/*/*.cpp) $(wildcard $(SRC)/*/*/*/*.cpp)
OBJS=$(patsubst $(SRC)/%.cpp,$(OBJ)/%.o,$(SRCS))


$(BIN)/$(RUNNABLE): $(OBJS)
	$(CC) $(CFLAGS) $^ -o $@

run:$(BIN)/$(RUNNABLE)
	@echo "$(SRCS)"
	@echo "$(OBJS)"
	$(BIN)/$(RUNNABLE)

$(OBJ)/%.o: $(SRC)/%.cpp
	$(CC) $(CFLAGS) -c $< -o $@

clean:
	# TODO: rm -f $(wildcard $(OBJ)/*.o) $(wildcard $(OBJ)/*/*.o) $(wildcard $(OBJ)/*/*/*.o) $(wildcard $(OBJ)/*/*/*/*.o)
