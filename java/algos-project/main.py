import os
import time
import subprocess

def print_res(file_path):
    with open(file_path, "r") as file:
        for line in file:
            if line.strip():
                print(line.split())

def wait_for_file(file_path, timeout=10):
    start_time = time.time()
    while time.time() - start_time < timeout:
        if os.path.exists(file_path) and os.path.getsize(file_path) > 0:
            print(time.time()-start_time)
            return True
        time.sleep(0.001)
    return False

def main():
    file_path = "perf.data"
    # os.system("mvn exec:java")
    subprocess.run(["mvn", "exec:java", "-q"], check=True)
    # time.sleep(1)
    if wait_for_file(file_path):
        print_res(file_path)
    # else:
        # print("Timeout waiting for file to be updated.")

if __name__ == "__main__":
    main()
