import os

def collect_java_files(output_file='out.txt'):
    print("test")
    output_path = os.path.join(os.getcwd(), output_file)
    # Open the output file in write mode
    with open(output_path, 'w') as outfile:
        # Walk through all directories and subdirectories from the current directory
        for root, dirs, files in os.walk('.'):
            for file in files:
                # Check if the file has a .java extension
                if file.endswith('.java'):
                    file_path = os.path.join(root, file)
                    try:
                        # Open the .java file and append its content to out.txt
                        with open(file_path, 'r') as infile:
                            outfile.write(f"// File: {file_path}\n")
                            outfile.write(infile.read())
                            outfile.write("\n\n")
                    except Exception as e:
                        print(f"Could not read file {file_path}: {e}")
    
    # Notify the user where the file was created
    print(f"All .java files have been collected into: {output_path}")

collect_java_files()
print("test")
