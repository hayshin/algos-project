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

def random_nums_with_distr(size, start, end, hot_percentage=0.1):
    # Define the range of numbers
    all_numbers = list(range(start, end + 1))
    hot_count = int(len(all_numbers) * hot_percentage)
    
    # Divide into hot and cold numbers
    hot_numbers = random.sample(all_numbers, hot_count)
    cold_numbers = [num for num in all_numbers if num not in hot_numbers]
    
    # Define probabilities
    hot_weight = 0.9 / hot_count  # Total weight for hot numbers is 90%
    cold_weight = 0.1 / len(cold_numbers)  # Total weight for cold numbers is 10%
    
    weights = {num: hot_weight for num in hot_numbers}
    weights.update({num: cold_weight for num in cold_numbers})
    
    # Generate the dataset
    dataset = random.choices(all_numbers, weights=[weights[num] for num in all_numbers], k=size)
    return dataset

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
        save_to_file(random_nums(size, 0, 2**31-1), f"random_nums_high_range{size}")
        save_to_file(random_strings(size, 8, 8), f"random_strings_len8_{size}")
        save_to_file(random_strings(size, 3, 16), f"random_strings_len3_16_{size}")
        save_to_file(random_nums_with_distr(size, 0, size, 0.1), f"random_nums_10-90_distr_{size}")

    
if __name__ == "__main__":
    main()
