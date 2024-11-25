import subprocess

maps = ["avlt", "st", "bt", "rbt", "ht"]
sizes = [2**10, 2**20, 2**24]
data_types = {
    "int": ["sorted", "random", "hot"],
    "str": ["short", "diff", "long"]
}
    
repeats = 1

for map in maps:
    for size in sizes:
        for data_type, data_names in data_types:
            for data_name in data_names:
                args = " ".join(map, data_type, data_name, size, repeats)
                subprocess.run(["mvn exec:java -q -Dexec.args=\"\""] shell=True)

                
