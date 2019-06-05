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
		// ��ȡ�±��
		Node newNode = new Node(null, k, v);

		int index = code % DefaultSize;

		Double limitsize = DefaultSize * Expansion_Factor;
		// ����ǰMap��������������ֵ��ʱ��ʵ������
		if (size > limitsize) {
			int oldDefaultSize = DefaultSize;
			DefaultSize = DefaultSize * 2;
			Node[] newArray = new Node[DefaultSize];
			for (int i = 0; i < oldDefaultSize; i++) {
				Node node = this.data[i];
				// ���ԭ�������ֵ���������ջ���
				this.data[i] = null;
				while (node != null) {
					int index2 = node.key.hashCode() % DefaultSize;
					// �����鸳ֵ
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

		// �������û��ֵ����������ݵ�����
		if (data[index] == null) {

			data[index] = newNode;
			size++;

		} else// ��������ڸ�λ����ֵ
		{
			// ѭ����ѯ��λ�õ����ݣ����������ṹ ѭ����ѯ������ �Ƿ�����ͬ��key ���������ͬ��key �͸��� ���û����ͬ��key
			// ���ڸ�λ�õ�����ͷ�����Ԫ��
			Node tempNode = data[index];
			while (tempNode != null) {

				// ���������ͬ��key �͸���
				if (tempNode.key.equals(k) || tempNode.key == k) {
					tempNode.value = v;
					break;
				}
				// ˵���Ѿ�����������һ��Ԫ��
				if (tempNode.next() == null) {
					// ���ڸ�λ�õ�����ͷ�����Ԫ��
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
	 * ��ӡMap
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

		//map.put("key10", "�������1������");

		printMap(map);
	}
}
