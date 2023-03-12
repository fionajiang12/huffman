# Huffman Compression README

The goal of a compression algorithm is to take a sequence of bytes and transform it into a different sequence of fewer bytes, such that the original can be recovered. Because compression algorithms reduce the size of a file, they allow quicker transmission of files over the network, benefiting everyone on that link.

Compression algorithms can be lossless (like ZIP) or lossy (like JPEG). Lossy compression is useful for images, music, and video because multimedia is an emergent property of its file formats; in other words, we don’t care if there are slight imperfections in a JPEG image. On the other hand, text-based files must be compressed without any data loss.

In this project, I implemented Huffman Coding that is used as part of larger compression schemes, such as ZIP. In order to implement it, I implemented a binary min heap which will be used as a priority queue.

## 中文翻译

这个压缩算法的目标是获取一个字节序列并将其转换为不同的字节数更少的序列，以便可以恢复原始序列。由于压缩算法减小了文件的大小，因此它们允许通过网络更快地传输文件，从而使该链接上的每个人都受益。

压缩算法可以是无损的（如 ZIP）或有损的（如 JPEG）。有损压缩对图像、音乐和视频很有用，因为多媒体是其文件格式的一个新兴属性；换句话说，我们不关心 JPEG 图像中是否存在轻微缺陷。另一方面，基于文本的文件必须在不丢失任何数据的情况下进行压缩。

在这个项目中，我实现了霍夫曼编码，它被用作更大的压缩方案的一部分，例如 ZIP。为了实现它，我实现了一个二进制最小堆，它将用作优先级队列。
