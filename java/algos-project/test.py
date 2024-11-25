import subprocess

maps = ["avlt", "bt", "rbt",  "ht","htm", "htoa"]
# sizes = [2**10, 2**20, 2**24]
sizes = [2**20]
sizes = [str(size) for size in sizes]
data_types = {
    "int": ["sorted", "random", "hot"],
    "str": ["short", "diff", "long"]
}
    
repeats = 1
repeats = str(repeats)

for map in maps:
    for size in sizes:
        for data_type, data_names in data_types.items():
            for data_name in data_names:
                args = " ".join([map, data_type, data_name, size, repeats])
                subprocess.run([f"mvn exec:java -e -q -Dexec.args=\"{args}\""], shell=True)

                
