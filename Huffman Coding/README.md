# Huffman Coding

Huffman coding is a lossless data compression algorithm. The idea is to assign variable-length codes to input characters, lengths of the assigned codes are based on the frequencies of corresponding characters. The most frequent character gets the smallest code and the least frequent character gets the largest code.

This process is done in two major steps:
1) Build a Huffman Tree from input characters.
2) Traverse the Huffman Tree and assign codes to characters.
