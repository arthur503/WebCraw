WebCraw
=======

主要用于关于网页内容抓取（测试网站为qidian小说），包括：

已完成：

* 【10.11日更新】使用jsoup进行html解析网页获取所需url链接，利用正则表达式提取网页内容（需完善）；
* 【10.11日更新】构建小说的数据结构，包括：site、category、book、chapter；
* 【10.11日更新】使用Crawler通过HttpURLConnection下载并保存文本；
* 【10.19日更新】使用BloomFilter（8个hash函数，10000个bit位）进行网页url查重；
* 【10.19日更新】多线程下载（使用ExecutorService cachedThreadPool，使用synchronized volatile关键字对解析、下载、写入文件的方法和重要参数等同步）；
* 【10.19日更新】加入容错处理（下载章节出错后，使用BloomFilter检查，若是新url则再次加入到tasklist中；若已重试过一次，则放弃）

待完成：

* 使用相似哈希算法SimHash进行内容相似度审查（查看是否侵犯版权）；
* 增量更新；
