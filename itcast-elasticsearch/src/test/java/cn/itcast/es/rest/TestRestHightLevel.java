package cn.itcast.es.rest;

import org.apache.http.HttpHost;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TestRestHightLevel {

    private RestHighLevelClient restHighLevelClient;

    @Before
    public void init() {
        RestClientBuilder restClientBuilder = RestClient.builder(
                new HttpHost("192.168.103.130", 9200)
        );
        this.restHighLevelClient = new RestHighLevelClient(restClientBuilder);
    }

    @After
    public void close() throws IOException {
        // 跟随应用的关闭而关闭
        this.restHighLevelClient.close();
    }

    /**
     * 同步操作 新增文档
     * @throws Exception
     */
    @Test
    public void testSave() throws Exception {

        Map map = new HashMap();
        map.put("id", 1002);
        map.put("title", "大牛坊社区 合租 一室一厅");
        map.put("price", 2000);

        IndexRequest indexRequest = new IndexRequest("haoke", "user").source(map);

        IndexResponse response = this.restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);

        String id = response.getId();
        System.out.println("id -> " + id);
        System.out.println("version -> " + response.getVersion());
        System.out.println("result -> " + response.getResult());
        System.out.println("type -> " + response.getType());
    }

    /**
     * 删除数据
     */
    @Test
    public void testDelete() throws IOException {
        DeleteRequest deleteRequest = new DeleteRequest("haoke","user","1001");
        DeleteResponse deleteResponse = this.restHighLevelClient.delete(deleteRequest, RequestOptions.DEFAULT);
        System.out.println(deleteResponse.status());
    }
}
