---
layout: entry
title: "Primer 찾기 대소동 - Exact string searching (Aho-Corasick)"
author: hongiiv
author-email: hongiiv@gmail.com
description: FASTQ 파일과 같이 대량의 서열 데이터에서 원하는 primer sequence 찾기
publish: true
---

Aho-Corasick
------------------
"긴 문자열에서 여러 개의 단어를 동시에 찾을 때 선형 시간복잡도 (이론상 최소)를 가지는 알고리즘. Trie 기반의 자료구조에, KMP 알고리즘처럼 실패했을 경우 어디까지 건너뛰어야 할지 알려주는 장치를 만들었다."

이상 Aho-Corasick 알고리즘에 대한 설명은 생략...

Primer sequence
------------------
Amplicon 방식으로 Sequencing을 수행하고 나면 amplicon을 디자인시 사용한 primer sequence를 제공한다. 여기서는 primer sequence를 trimming을 한다거나 하는 것에 대한 것이 아니라 과연 sequencing된 파일에서 해당 amplicon을 확인하여 raw fastq 파일만을 보고 해당 파일이 어떤 amplicon set를 사용했는지를 확인하는 방법에 대해 설명한다. 다양한 custom design panel을 보유하고 있거나, panel에 따라서 분석 pipeline이 다른 경우 유용하게 사용될 수 있다.

```sh
AMPL7153017787-F	GGGGAAGTAAGTAGGCCTGGGCCTGGCTGC
AMPL7153017787-R	GGCCCTGAATTGGCAAACTTGGGTTGGGGG
AMPL7153017830-F	CCCAGCTCAGCCAGCTCCAGAAGCAGGTGA
AMPL7153017830-R	CCACCCCCACCCCCACCCCCAACCCTGGGC
```

Custom panel 또는 Qiagen의 GeneRead, Agilent SureSelect, Bioo Scientific의 NEXTflex와 같은 상용 제품의 경우 위와 같이 디자인에 사용한 primer sequence를 제공한다. 해당 제품으로 sequencing을 수행한 raw fastq 파일에 해당 primer sequence가 보이지 않거나 너무 적다거나 하는 경우에는 한번 고민해야 할 것이다. 우리는 <u>긴 문자열(fastq files)에서 여러 개의 단어(primer sequence)를 동시에 찾아서 그 빈도를 확인</u>할 것이다.

Reverse Complement
------------------
Reverse complement와 같이 우리가 다루는 데이터는 몇가지 특성을 고려해야 한다. 단순히 string serarch가 아니란 말이다. 위에서 나온 `AMPL7153017787-F` primer는 `GGGGAAGTAAGTAGGCCTGGGCCTGGCTGC`라는 문자열과 `GCAGCCAGGCCCAGGCCTACTTACTTCCCC`라는 두개의 문자열을 찾아야 하는 것이다.

Map(tree) 만들기
------------------
모든 primer sequence와 reverse complement sequence에 대해서 map(tree)를 생성한다.

```java
String line;
BufferedReader reader = new BufferedReader(new FileReader("primer_sequence.txt"));
while ((line = reader.readLine()) != null)
{
	line = line.toUpperCase();
	tree.add(line.getBytes(), line);
	tree.add(revcomp(line).getBytes(), revcomp(line));
}
tree.prepare();
```		

FASTQ 파일 search
------------------

```java
BufferedReader reader = new BufferedReader(new FileReader("sequencing.fastq"));
while ((line = reader.readLine()) != null)
{
	Iterator iter = tree.search(line.getBytes());
	while(iter.hasNext()){ 
		SearchResult result = (SearchResult) iter.next();
		System.out.println(result.getOutputs());
		System.out.println("line at: "+nReads+" Found at index: " + result.getLastIndex());
		termsThatHit.addAll(result.getOutputs());
	}
	
}
```
