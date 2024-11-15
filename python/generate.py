import random
import string

def save_to_file(data, filename):
    DATA_DIR = r"data/"
    with open(DATA_DIR + filename, 'w') as f:
        for num in data:
            f.write(f"{num}\n")

def sorted_nums(size):
    return [i for i in range(size)]

def random_nums(size, start, end):
    return [random.randint(start,end) for _ in range(size)]

def random_strings(size, len_min, len_max):
    return [''.join(random.choices(string.ascii_lowercase, k=random.randint(len_min, len_max))) for _ in range(size)] 

def generate_dif_sizes(func, *args):
    sizes = [1_000, 1_000_000, 100_000_000]
    for size in sizes:
        save_to_file(func(size, *args), f"{func.__name__}_{str(size)}")

def main():
    sizes = [1_000, 1_000_000, 100_000_000]
    for size in sizes:
        save_to_file(sorted_nums(size), f"sorted_nums_{size}")
        save_to_file(random_nums(size, 0, size), f"random_nums_{size}")
        save_to_file(random_nums(size, 0, 2**31-1), f"random_nums_high_distr{size}")
        save_to_file(random_strings(size, 8, 8), f"random_strings_len8_{size}")
        save_to_file(random_strings(size, 3, 16), f"random_strings_len3_16_{size}")

    
if __name__ == "__main__":
    main()
