import json

import os

def get_test_files():
    directory = "tests"
    files = [f for f in os.listdir(directory) if os.path.isfile(os.path.join(directory, f))]
    return sorted(files)

def read_json(file_path):
    s = ""
    with open(file_path, "r") as file:
        for line in file:
            if line.strip():
                s += line
    datas = s.split("}")
    jsons = []
    for data in datas:
        if data.strip():
            data += "}"
            try:
                jsons.append(json.loads(data))
            except:
                print(data)
    return jsons

def create_separator(column_widths):
    return "+".join(["-" * (width + 2) for width in column_widths])

# Function to format a row
def format_row(data, column_widths):
    return "| " + " | ".join(f"{str(value):<{width}}" for value, width in zip(data, column_widths)) + " |"

def main():
    trees = ["avlt", "bt", "ht", "htm", "htoa", "rbt"]
    trees.sort()
    print(trees)
    datas = {}
    for file_path in get_test_files():
        args = file_path.split("_")
        tree = args[0]
        data_type = args[1]
        data_name = args[2]
        size = args[3]
        

        code = "_".join([data_type, data_name, size, tree]) 
        if code not in datas:
            datas[code] = []
        file_path = "tests/" + file_path
        events = read_json(file_path)
        for event in events:
            event_name = event["event"]
            value = float(event['counter-value'])
            value /= int(size)
            datas[code].append(value)
    keys = sorted(datas.keys())
    prev = ""
    for key in keys:
        data_type, data_name,size,tree = key.split("_")
        if data_type+data_name+size != prev:
            print(data_type, data_name, size)
            prev = data_type+data_name+size        
        print(tree, " ")
        for val in datas[key]:
            print(val, end=" ")
        print()

    # with open("table.txt", "w") as file:
    #     for data_name, events in datas.items():
    #         file.write(data_name + "\n")
    #         # for event_name, values in events.items():
    #         #     file.write(event_name, values + "\n")

    #         column_widths = [
    #                 max(len("Event Name"), max(len(event) for event in events.keys())),
    #                 max(len("Values"), 
    #                     max(len(", ".join(f"        {value:.2f}" for value in values)) for values in events.values()))
    #             ]
    
    #             # file.write table heade + "\nr
    #         file.write(create_separator(column_widths) + "\n")
    #         file.write(format_row(["Event Name", "Values"], column_widths) + "\n")
    #         file.write(create_separator(column_widths) + "\n")

    #         # file.write table row + "\ns
    #         for event_name, values in events.items():
    #             formatted_values = ", ".join(f"        {value:.2f}" for value in values)
    #             file.write(format_row([event_name, formatted_values], column_widths) + "\n")

    #         # file.write footer separato + "\nr
    #         file.write(create_separator(column_widths) + "\n")
    #         file.write( "\n")

if __name__ == "__main__":
    main()
