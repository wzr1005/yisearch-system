cd /usr/local/Cellar/elasticsearch/
#mkdir "data1" "data2" "log1" "log2"
node0/bin/elasticsearch -d
node1/bin/elasticsearch -d
node2/bin/elasticsearch -d
wait
cd /usr/local/Cellar/kibana-6.8.23-darwin-x86_64
bin/kibanan