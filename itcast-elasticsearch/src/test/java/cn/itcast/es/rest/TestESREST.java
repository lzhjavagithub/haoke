package cn.itcast.es.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpHost;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TestESREST {

    private RestClient restClient;

    private static final ObjectMapper mapper = new ObjectMapper();

    @Before
    public void esRest() {
        RestClientBuilder restClientBuilder = RestClient.builder(
                new HttpHost("192.168.103.130",9200,"http")
        );

        restClientBuilder.setFailureListener(new RestClient.FailureListener(){
            @Override
            public void onFailure(Node node) {
                System.out.println("出错了..."+node);
            }
        });

        this.restClient = restClientBuilder.build();

    }

    @After
    public void close() throws IOException {
        // 跟随应用的关闭而关闭
        this.restClient.close();
    }

    @Test
    public void testInfo() throws IOException {
        Request request = new Request("get","/_cluster/state");
        Response response = this.restClient.performRequest(request);
        System.out.println("请求完成->"+ response.getRequestLine());
        System.out.println("请求完成->"+ EntityUtils.toString(response.getEntity()));
    }

    @Test
    public void testSave() throws IOException {
        Request request = new Request("post","/haoke/user");
        request.addParameter("pretty","true");
        Map map = new HashMap();
        map.put("id",1002);
        map.put("title","大牛坊社区 整租");
        map.put("price",3000);

        String jsonValue = mapper.writeValueAsString(map);
        request.setJsonEntity(jsonValue);
        Response response = this.restClient.performRequest(request);
        System.out.println("请求完成->"+ response.getRequestLine());
        System.out.println("请求完成->"+ EntityUtils.toString(response.getEntity()));
    }

}
