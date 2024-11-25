import subprocess

maps = ["avlt", "bt", "rbt", "ht", "htm", "htoa"]
# sizes = [2**10, 2**20, 2**24]
sizes = [2 ** 16, 2**20, 2**24]
sizes = [str(size) for size in sizes]
data_types = {
    "int": ["sorted", "random", "hot"],
    "str": ["short", "diff", "long"]
}
    
repeats = 3
repeats = str(repeats)

print("Memory footprint in kb")
for data_type, data_names in data_types.items():
    for data_name in data_names:
        print("\t", data_type, data_name)
        print("\t", sizes)
        for map in maps:
            print(map)
            for size in sizes:
                args = " ".join([map, data_type, data_name, size, repeats])
                subprocess.run([f"mvn exec:java -e -q -Dexec.mainClass=\"uni.algos.TimeTest\" -Dexec.args=\"{args}\""], shell=True)
            print()
                
