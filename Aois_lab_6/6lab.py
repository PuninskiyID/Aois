




class HashTable:

    def __init__(self):

        self.size = 20
        self.table = [[] for _ in range(self.size)]

    def Add(self, key, value):
        hash_key = self.FindHash(key)
        for item in self.table[hash_key]:
            if item[0] == key:
                item[1] = value
                return
        self.table[hash_key].append((key, value))

    def Remove(self, key):
        hash_key = self.FindHash(key)
        for idx, item in enumerate(self.table[hash_key]):
            if item[0] == key:
                del self.table[hash_key][idx]
                return

    def FindHash(self, key):
        hash_value = 0
        for char in str(key):
            hash_value += ord(char)
        return hash_value % self.size

    

    def Get(self, key):
        hash_key = self.FindHash(key)
        for item in self.table[hash_key]:
            if item[0] == key:
                return item[1]
        return None

    def ConsoleOutput(self):
        for i in range(self.size):
            for item in self.table[i]:
                print(f"{i} : {item[0]} : {item[1]}")






def main():

    ht = HashTable()

    ht.Add("geometry","a concept that represents the study of shapes, sizes, and positions of objects")
    ht.Add("algorithm","a concept that represents a set of instructions for solving a problem")
    ht.Add("function", "a concept that represents a relationship between two variables")
    ht.Add("square", "a concept representing a quadrilateral with right angles")
    ht.Add("triangle", "a concept representing a polygon with three sides")
    ht.Add("theorem", "a concept that represents a statement that has been proven to be true")
    ht.Add("probability", "a concept that represents the likelihood of an event occurring")
    ht.Add("equation", "a concept that represents a statement of equality between two expressions")
    ht.Add("rectangle", "a concept representing a polygon with four sides")
    ht.Add("matrix", "a concept that represents a collection of numbers arranged in a grid")

    print("Содержимое таблицы:")
    ht.ConsoleOutput()

    print("\nСодержимое таблицы после удаления элементов:")
    ht.Remove("geometry")
    ht.Remove("equation")
    ht.Remove("probability")
    ht.ConsoleOutput()

    Key = "rectangle"
    print("\nВывод информации по ключу", Key + ":")
    print(ht.Get(Key))


main()
