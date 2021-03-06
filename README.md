# happyhttp
简单而强大的http client 库

	public static void sampleGet(){
		String text = HttpBuilder.newGet("http://www.xxx.com/").execute().getBodyAsText();
		System.err.println(text);
	}
	
	public static void samplePost(){
		HttpResponse response =	HttpBuilder
									.newPost("http://www.xxx.com/")
									.addFormField("a", "1")
									.addFormField("b", "2")
									.execute();
		// print http response header
		for(String name : response.getHeaderMap().keySet()){
			System.err.println(name + ":" + response.getHeaderMap().get(name));
		}
		
		// print http response body
		System.err.println(response.getBodyAsText());
	}

	public static void sampleJson(){
		String json = "{\"a\":\"1\"}";
		HttpResponse response =	HttpBuilder
									.newPost("http://www.xxx.com/")
									.setJsonBody(json)
									.execute();
									
		System.err.println(response.getBodyAsText());
	}
	
	public static void sampleProxy(){
		String text = HttpBuilder.newGet("http://www.xxx.com/")
								.setProxy("x.x.x.x", 3128, null, null)
								.execute()
								.getBodyAsText();
		
		System.err.println(text);
	}
	
	public static void sampleDownload(){
		HttpBuilder.newGet("http://www.xxx.com/").download(new File("a.html"));
	}

	public static void sampleMultiPart(){
		HttpResponse response =	HttpBuilder
									.newPost("http://localhost:8080/DemoServlet/upload")
									.addMultiPartFormField("a", "1")
									.addMultiPartFormField("f1", "abc".getBytes())
									.setProxy("127.0.0.1", 8888, null, null)
									.execute();
									
		System.err.println(response.getBodyAsText());
	}
	
## maven
	<dependency>
	    <groupId>com.github.yuancihang</groupId>
	    <artifactId>happyhttp</artifactId>
	    <version>1.2</version>
	</dependency>
	
## gradle
	compile 'com.github.yuancihang:happyhttp:1.2'
