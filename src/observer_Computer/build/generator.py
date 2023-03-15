"""This is a simple python script to generate the file containing all the files of a particular type"""
import os
import argparse

# Set up argument parser
parser = argparse.ArgumentParser()
parser.add_argument("folder_path", help="path to folder to search")
parser.add_argument("extension", help="file extension to search for (without the dot)")
parser.add_argument("output_file", help="file to write output to")
args = parser.parse_args()

# Define a function to search for files with the specified extension
def find_files(path, extension):
    for dirpath, dirnames, filenames in os.walk(path):
        for filename in filenames:
            if filename.endswith('.' + extension):
                yield os.path.join(dirpath, filename)

# Find files with the specified extension
matching_files = find_files(args.folder_path, args.extension)

# Write the file paths to the output file
with open(args.output_file, 'w') as f:
    f.write('\n'.join(matching_files) + '\n')