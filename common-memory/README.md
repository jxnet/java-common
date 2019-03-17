Common Memory
-------

Fast and lightweight java library for allocating native/direct memory buffer. This project inspired by Netty project (`netty-buffer`) and some funtionality will similar with `netty-buffer` it self. Netty project (`netty-buffer`) provide direct and heap buffer (and also have `pooling-buffer` machanism). I only need direct buffer for Jxnet to minimized unnecessary memory copy from `ring-buffer/circular-buffer` that provided by pcap library. That's why `common-memory` is born.