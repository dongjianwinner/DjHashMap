package com.dj.DjHashMap;

public class Djhashmap<K, V> {
	public Double Expansion_Factor = 0.75;
	public Node[] data = null;
	public int DefaultSize = 16;
	public int size = 0;

	public class Node<K, V> {
		public Node next;
		public K key;
		public V value;

		public Node next() {
			return next;
		}

		public Node(Node next, K key, V value) {
			super();
			this.next = next;
			this.key = key;
			this.value = value;
		}

	}

	public void put(K k, V v) {
		if (this.data == null) {
			data = new Node[DefaultSize];
		}
		int code = k.hashCode();
		// 获取下标记
		Node newNode = new Node(null, k, v);

		int index = code % DefaultSize;

		Double limitsize = DefaultSize * Expansion_Factor;
		// 当当前Map的数量大于限制值的时候，实现扩容
		if (size > limitsize) {
			int oldDefaultSize = DefaultSize;
			DefaultSize = DefaultSize * 2;
			Node[] newArray = new Node[DefaultSize];
			for (int i = 0; i < oldDefaultSize; i++) {
				Node node = this.data[i];
				// 清空原来数组的值，垃圾回收机制
				this.data[i] = null;
				while (node != null) {
					int index2 = node.key.hashCode() % DefaultSize;
					// 新数组赋值
					if (newArray[index2] == null) {
						newArray[index2] = node;
					} else {
						Node newArrayNode = newArray[index2];
						node.next = newArrayNode;
						newArray[index2] = node;
					}

					node = node.next();
				}
			}
			data = newArray;
			printMap(this);
		}

		// 如果属猪没有值，就添加数据到数组
		if (data[index] == null) {

			data[index] = newNode;
			size++;

		} else// 如果数组在该位置有值
		{
			// 循环查询该位置的数据，如果有链表结构 循环查询该链表 是否有相同的key ，如果有相同的key 就覆盖 如果没有相同的key
			// 就在该位置的链表头添加新元素
			Node tempNode = data[index];
			while (tempNode != null) {

				// ，如果有相同的key 就覆盖
				if (tempNode.key.equals(k) || tempNode.key == k) {
					tempNode.value = v;
					break;
				}
				// 说明已经是链表的最后一个元素
				if (tempNode.next() == null) {
					// 就在该位置的链表头添加新元素
					newNode.next = data[index];
					data[index] = newNode;
					size++;
					break;
				}

				tempNode = tempNode.next();
			}

		}
	}

	/**
	 * 打印Map
	 * 
	 * @param map
	 */
	public static void printMap(Djhashmap map) {
		for (int i = 0; i < map.DefaultSize; i++) {
			Djhashmap.Node node = map.data[i];
			while (node != null) {
				System.out.print("[{Key:" + node.key + "   Value:" + node.value + "}]");
				node = node.next();
			}
			System.out.println();
		}
		System.out.println(map.DefaultSize
				+ "*******************************************************************************************");
	}

	public static void main(String[] args) {
		Djhashmap<String, String> map = new Djhashmap<String, String>();
		for (int i = 1; i < 50; i++) {
			map.put("key"+i, "value = " + i);
			System.out.println(map.size);
		}

		//map.put("key10", "我是替代1的数字");

		printMap(map);
	}
}
