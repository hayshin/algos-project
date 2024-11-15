from hash_table import HashTable
        
def main():

    ht = HashTable(5) 
  
    ht.add("apple", 3) 
    ht.add("banana", 2) 
    ht.add("cherry", 5) 
    print(ht)
    print("apple" in ht)  # True 
    print("durian" in ht)  # False 
  
    # Get th for a key 
    print(ht.get("banana"))  # 2 
  
    # Update th for a key 
    ht.add("banana", 4) 
    print(ht.get("banana"))  # 4 
  
    ht.remove("apple") 
    # Check the size of the hash table 
    print(len(ht))  # 3
    print(ht)

if __name__ == "__main__":
    main()
