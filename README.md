# Introduction to MongoDB ORM (mongodb-orm)

* MongoDB ORM is a Java-based ORM framework that simplifies the use of the SDK, making the code clearer and simpler.
* Similar to Ibatis, it encapsulates queries and execution statements in XML, isolating them from the code. It's abbreviated as MQL.

# Usage in the Project

#### Adding MongoDB ORM Support Package

* 1. Add the JAR package or Maven support

```xml
<dependency>
    <groupId>com.mongodborm</groupId>
    <artifactId>mongodb-orm</artifactId>
    <version>0.0.1-RELEASE</version>
</dependency>
```

* 2. Initialize the MongoDB template

> Initialization in Spring

```xml
<bean id="mongoTemplate" class="com.mongodb.client.MongoClientTemplate">
    <property name="factory">
        <bean class="com.mongodb.client.MongoORMFactoryBean">
            <property name="dataSource">
                <bean class="com.mongodb.client.MongoDataSource">
                    <property name="nodeList" value="127.0.0.1:27017" />
                    <property name="dbName" value="your db name" />
                    <property name="userName" value="user name" />
                    <property name="passWord" value="password" />
                    <!-- Default values can be used -->
                    <property name="connectionsPerHost" value="" />
                    <property name="threadsAllowedToBlock" value="" />
                    <property name="connectionTimeOut" value="" />
                    <property name="maxRetryTime" value="" />
                    <property name="socketTimeOut" value="" />
                </bean>
            </property>
            <property name="configLocations">
                <list>
                    <value>classpath:mql/mongo-mql.xml</value>
                </list>
            </property>
        </bean>
    </property>
</bean>
```

> Initialization in code

```java
try {
    Resource resource = new ClassPathResource("mongo-mql.xml");
    MongoORMFactoryBean factory = new MongoORMFactoryBean();
    factory.setConfigLocations(new Resource[]{resource});
    factory.init();
    MongoClientTemplate template = new MongoClientTemplate();
    template.setFactory(factory);
    template.init();
} catch(Exception e) {
    e.printStackTrace();
}
```

#### Writing MQL (MongoDB Query Language)

* Mapping

```xml
<mapping id="model" class="test.mongodborm.Model">
    <property column="_id" name="id" />
    <property column="name" name="name" />
    <property column="time" name="time" value="0" />
    <property column="status" name="status" />
</mapping>

<mapping id="extendModel" class="test.mongodborm.Model" extends="model">
    <property column="newProperty" name="newProperty" />
</mapping>
```

* Select

```xml
<select id="queryModelList" collection="test_sample">
    <query class="java.lang.String">
        <property column="name" name="${value}" />
    </query>
    <field mapping="model" />
    <order>
        <property column="time" value="desc" />
    </order>
</select>
```

* Update/FindAndModify

```xml
<update id="updateModel" collection="test_sample">
    <query class="test.mongodborm.Model$Child">
        <property column="name" name="name" ignoreNull="true" />
        <property column="time" operate="gte" value="0" type="number" />
        <property column="status" operate="in">
            <list type="number">0,1</list>
        </property>
    </query>
    <action class="java.util.Map">
        <property column="name" name="name" operate="set" />
        <property column="status" operate="set" />
    </action>
</update>
```

* Nested Query

```xml
<select id="queryModelList3" collection="test_sample">
    <query class="java.lang.String">
        <property column="_id" value="${value}" />
        <property column="time" value="0" type="number" />
    </query>
    <field class="java.util.Map">
        <!-- ... (continued) ... -->
    </field>
    <order class="java.util.Map">
        <property column="time" name="time" value="desc" />
    </order>
</select>
```

#### Usage of the Template (Templet)

```java
Model model = mongoTemplate.findOne("queryModelList", "yuxiangping");

List<Model> list = mongoTemplate.findOne("queryModelList", "");

Model model = new Model();
model.setTime(1L);
Map<String, String> action = new HashMap<String, String>();
action.put("name", "yuxiangping-update");
int update = mongoTemplate.update("updateModel", model, action);
```

> For more usage methods, refer to [sample.xml](https://github.com/yuxiangping/mongodb-orm/blob/master/src/main/resources/sample-sql.xml)

# Resources

 [Usage Tutorial](https://github.com/yuxiangping/mongodb-orm/wiki)

 [Issues](https://github.com/yuxiangping/mongodb-orm/issues)