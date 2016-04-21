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


