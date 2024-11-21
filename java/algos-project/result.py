import json

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

def main():
    jsons = read_json("tests/avl.json")
    for obj in jsons:
        print(f"{float(obj['counter-value']):,}")
    print()
    # jsons = read_json("perf.json")
    # for obj in jsons:
        # print(f"{float(obj['counter-value'])/100:,}")
    # print()

if __name__ == "__main__":
    main()
