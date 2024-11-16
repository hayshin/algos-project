from hash_table import HashTable
from collections import Counter
import timeit
from binary_search_tree import BinarySearchTree
from avl import AVLTree
from splay_tree import SplayTree 
from red_black_tree import RedBlackTree
from scalene import scalene_profiler

def read_from_file(filename, is_int):
    DATA_DIR = r"data/"
    data = []
    with open(DATA_DIR + filename, 'r') as f:
        if is_int:
            data = [int(line)for line in f]
        else:
            data = [line for line in f]
    return data

def test_structure(data):
    tree = HashTable(len(data))
    for value in data:
        tree.set(value, value)
    

def main():
    repets = 1
    sizes = [1_000_000_00]
    # sizes = [1_000, 1_000_000, 100_000_000]
    for size in sizes:
        data = read_from_file(f"random_nums_{size}", True)
        # save_to_file(random_nums(size, 0, size), f"random_nums_{size}")
        # save_to_file(random_nums(size, 0, 2**31-1), f"random_nums_high_range{size}")
        # save_to_file(random_strings(size, 8, 8), f"random_strings_len8_{size}")
        # save_to_file(random_strings(size, 3, 16), f"random_strings_len3_16_{size}")
        # save_to_file(random_nums_with_distr(size, 0, size, 0.1), f"random_nums_10-90_distr_{size}")
        duration = timeit.timeit(lambda: test_structure(data), number=repets)
        print(f"Average time for {repets} runs: {duration /repets:.6f} seconds per run")

if __name__ == "__main__":
    main()
