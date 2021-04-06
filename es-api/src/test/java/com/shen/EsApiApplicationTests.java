package com.shen;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeFilter;
import com.shen.entity.User;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * @Description es 7.6.x 高级客户端 api 测试
 * @author chensihua
 * @createTime 13:29 2021/4/6
 * @return
 * @version 1.0.0
 */
@SpringBootTest
class EsApiApplicationTests {


	@Autowired
	@Qualifier("restHighLevelClient")
	private RestHighLevelClient client;

	/** 
	 * @Description 索引的创建
	 * @author chensihua
	 * @param 
	 * @createTime 14:51 2021/4/6
	 * @return void
	 * @version 1.0.0
	 */
	@Test
	void testCreateIndex() throws IOException {
		// 1.创建索引请求
		CreateIndexRequest request = new CreateIndexRequest("jd_goods");
		// 2.客户端执行请求
		CreateIndexResponse response = client.indices().create(request, RequestOptions.DEFAULT);
		System.out.println(response);
	}

	/** 
	 * @Description 获取索引，判断其是否存在
	 * @author chensihua
	 * @param 
	 * @createTime 14:51 2021/4/6
	 * @return void
	 * @version 1.0.0
	 */
	@Test
	void testGetIndex() throws IOException {
		GetIndexRequest getIndex = new GetIndexRequest("shen_index");
		boolean exists = client.indices().exists(getIndex, RequestOptions.DEFAULT);
		System.out.println(exists);
	}

	/** 
	 * @Description 删除索引
	 * @author chensihua
	 * @param 
	 * @createTime 14:51 2021/4/6
	 * @return void
	 * @version 1.0.0
	 */ 
	@Test
	void testDeleteIndex() throws IOException{
		DeleteIndexRequest deleteIndex = new DeleteIndexRequest("jd_goods");
		AcknowledgedResponse delete = client.indices().delete(deleteIndex, RequestOptions.DEFAULT);
		System.out.println(delete.isAcknowledged());
	}

	/** 
	 * @Description 添加文档
	 * @author chensihua
	 * @param 
	 * @createTime 14:51 2021/4/6
	 * @return void
	 * @version 1.0.0
	 */
	@Test
	void testAddDocument() throws IOException {
		// 创建对象
		User user = new User("shen",24);
		// 创建请求
		IndexRequest request = new IndexRequest("shen_index");

		// 规则  PUT /shen_index/_doc/1
		request.id("1");
		request.timeout(TimeValue.timeValueSeconds(1));

		// 数据放入请求
		IndexRequest source = request.source(JSON.toJSONString(user), XContentType.JSON);

		//客户端发送请求
		IndexResponse index = client.index(request, RequestOptions.DEFAULT);

		System.out.println(index.toString());
		System.out.println(index.status()); // 对应命令返回的状态 CREATED
	}

	/** 
	 * @Description 获取文档 GET /index/doc/1
	 * @author chensihua
	 * @param 
	 * @createTime 14:51 2021/4/6
	 * @return void
	 * @version 1.0.0
	 */
	@Test
	void testGetDocument() throws IOException{
		GetRequest getRequest = new GetRequest("shen_index","1");
		// 不获取返回的 _source 的上下文
		getRequest.fetchSourceContext(new FetchSourceContext(false));
		getRequest.storedFields("_none_");

		boolean exists = client.exists(getRequest, RequestOptions.DEFAULT);
		System.out.println(exists);
	}

	/** 
	 * @Description 获取文档的信息
	 * @author chensihua
	 * @param 
	 * @createTime 14:51 2021/4/6
	 * @return void
	 * @version 1.0.0
	 */ 
	@Test
	void testGetDocumentInfo() throws IOException{
		GetRequest getRequest = new GetRequest("shen_index","1");
		GetResponse response = client.get(getRequest, RequestOptions.DEFAULT);
		System.out.println(response.getSourceAsString()); // 打印文档内容
		System.out.println(response);
	}

	/** 
	 * @Description 更新文档
	 * @author chensihua
	 * @createTime 14:51 2021/4/6
	 * @return 
	 * @version 1.0.0
	 */
	@Test
	void testUpdateDocument() throws IOException{
		UpdateRequest updateRequest = new UpdateRequest("shen_index","1");
		updateRequest.timeout("1s");
		User user = new User("神", 25);
		updateRequest.doc(JSON.toJSONString(user),XContentType.JSON);

		UpdateResponse update = client.update(updateRequest, RequestOptions.DEFAULT);
		System.out.println(update.status());
	}

	/** 
	 * @Description 删除文档
	 * @author chensihua
	 * @param 
	 * @createTime 14:52 2021/4/6
	 * @return void
	 * @version 1.0.0
	 */ 
	@Test
	void testDeleteDocument() throws IOException{
		DeleteRequest deleteRequest = new DeleteRequest("shen_index","1");
		deleteRequest.timeout("1s");

		DeleteResponse delete = client.delete(deleteRequest, RequestOptions.DEFAULT);
		System.out.println(delete.status());
	}

	/**
	 * @Description 批量请求
	 * @author chensihua
	 * @param
	 * @createTime 14:58 2021/4/6
	 * @return void
	 * @version 1.0.0
	 */
	@Test
	void testBulkkRequest() throws IOException{
		BulkRequest bulkRequest = new BulkRequest();
		bulkRequest.timeout("10s");
		ArrayList<User> userList = new ArrayList<>();
		userList.add(new User("shen1", 25));
		userList.add(new User("shen2", 25));
		userList.add(new User("shen3", 25));
		userList.add(new User("shen4", 25));
		userList.add(new User("shen5", 25));

		// 批处理请求
		for (int i = 0; i < userList.size(); i++) {
			// 批量更新或者批量删除在此修改对应的请求
			bulkRequest.add(new IndexRequest("shen_index")
					.id("" + (i + 1))
					.source(JSON.toJSONString(userList.get(i)), XContentType.JSON));
		}

		BulkResponse bulk = client.bulk(bulkRequest, RequestOptions.DEFAULT);
		System.out.println(bulk.hasFailures());
	}

	/**
	 * @Description 查询
	 * 				SearchSourceBuilder 条件构造
	 * 				HighlightBuilder 构建高亮
	 * 				TermQueryBuilder 精确查询
	 * 				MatchAllQueryBuilder 匹配全部
	 * 				......
	 * @author chensihua
	 * @createTime 15:16 2021/4/6
	 * @return void
	 * @version 1.0.0
	 */
	@Test
	void testSearch() throws IOException {
		SearchRequest request = new SearchRequest("jd_goods");
		// 构建搜索条件
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		// 查询条件 使用 QueryBuilders 工具类
			// QueryBuilders.termQuery 精确匹配
			// QueryBuilders.matchAllQuery 匹配所有
		TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("title", "追风筝的人");
//		MatchAllQueryBuilder matchAllQueryBuilder = QueryBuilders.matchAllQuery();

		searchSourceBuilder.query(termQueryBuilder);
		searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));

		request.source(searchSourceBuilder);

		SearchResponse searchResponse = client.search(request, RequestOptions.DEFAULT);
		System.out.println(JSON.toJSONString(searchResponse.getHits()));
		System.out.println("======================");
		for (SearchHit documentFields : searchResponse.getHits().getHits()) {
			System.out.println(documentFields.getSourceAsMap());
		}

	}
}
