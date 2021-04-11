
## About The Project

Create termination for contract and its articles

### Built With
* [Apache Maven](https://maven.apache.org/index.html)
* [Jakarta EE](https://jakarta.ee)
    * [Context Dependency Injection](https://jakarta.ee/specifications/cdi/2.0)
    * RESTful Web Services ([Spec](https://jakarta.ee/specifications/restful-ws/) / [Impl](https://resteasy.github.io/))
* Testing
    * [AssertJ](https://joel-costigliola.github.io/assertj/)
    * [Mockito](https://site.mockito.org/)
    * [WeldUnit](https://weld.cdi-spec.org/news/2017/12/19/weld-meets-junit5/)
    
### Installation
Clone and build [project]((https://bitbucket.1and1.org/projects/HOSTBSS-ORDER/repos/cisba-order/browse)):
```sh
mvn clean install
```

## Technical Documentation

### Hot to create an Maven project
https://www.jetbrains.com/help/idea/2020.3/maven-support.html

### Register web context for your application

   Create a file jpc.properties in folder resource/MET-INF
   Configuration property deployment.unit.name with your web-resource-name
      deployment.unit.name = cucoda-termination

   Hint: the web-resource-name is to configure in the file web.xml also


### Database Configuration in Wildfly

* Do create a Postgresql Database
   

* Configure PostgreSQL JDBC Driver as a Module in standalone.xml of wildfly
    
    *   Web-Source:

        https://ralph.blog.imixs.com/2016/10/22/wildfly-install-postgresql-jdbc-driver-as-a-module/
        https://www.stenusys.com/how_to_setup_postgresql_datasource_with_wildfly/
   
    *  download an add driver to wildfly-11.0.0.
      folder: /modules/system/layers/base/org/postgresql/main$
   
    *  add current file module.xml to same folder
```sh
<?xml version="1.0" encoding="UTF-8"?>
<module xmlns="urn:jboss:module:1.1" name="org.postgresql">
    <resources>
        <resource-root path="postgresql-9.4.1211.jar"/>
    </resources>
    <dependencies>
        <module name="javax.api"/>
        <module name="javax.transaction.api"/>
    </dependencies>
</module>
```
 
*  Configure datasource for your database. 
      Therefore, add persistence.xml file to folder resources/META-INF

```sh
<persistence-unit name="primary">
    <jta-data-source>java:jboss/datasources/cucodaDS</jta-data-source>
        <properties>
            <property name="hibernate.hbm2ddl.auto" value="update"/>
            <property name="hibernate.show_sql" value="false"/>
        </properties>
</persistence-unit>
```

### Configure Security for your application

*  Add security domain to standalone.xml
   <security-domains>

```sh
<security-domain name="my-aktion" cache-type="default">
    <authentication>
        <login-module code="Database" flag="required">
            <module-option name="dsJndiName" value="java:jboss/datasources/cucodaDS"/>
            <module-option name="principalsQuery" value="select password from organizer where email=?"/>
            <module-option name="rolesQuery" value="select 'Organizer','Roles' from organizer where email=?"/>
            <module-option name="hashAlgorithm" value="SHA-256"/>
            <module-option name="hashEncoding" value="hex"/>
            <module-option name="hashCharset" value="UTF-8"/>
        </login-module>
    </authentication>
</security-domain>
```

   As you can see you need a table in the database source 'cucodaDS' to get principals and roles of authenticated client.
   Let use create one in the next step.


*  Add Table 'organizer' to Database

   * Create Table
 ```sh   
CREATE TABLE organizer(email VARCHAR NOT NULL, firstname VARCHAR NOT NULL, lastname VARCHAR NOT NULL, password VARCHAR NOT NULL);
```

   *   Insert test data
      Password can be created by: hash('SHA256', stringtoutf8('secret'), 1)
   
H2-Database call: 

```sh
insert into Organizer (email, firstname, lastname, password) values ('max@mustermann.de', 'Max', 'Mustermann', hash('SHA256', stringtoutf8('secret'), 1));
```

An encrypted password 'secret' has a hash value '2bb80d537b1da3e38bd30361aa855686bde0eacd7162fef6a25fe97bf527a25b'

Insert to current test dataset to your DB.
 ```sh
insert into organizer (email, firstname, lastname, password) values ('max@mustermann.de', 'Max', 'Mustermann', '2bb80d537b1da3e38bd30361aa855686bde0eacd7162fef6a25fe97bf527a25b');
```

*  To secure the application you need additional configuration files in folder webapp/WEB-INF

   Configure auth-method, web-resource, role-name and security-role in file web.xml

```sh
<login-config>
    <auth-method>BASIC</auth-method>
    <realm-name>MyAction - Basic Authentications</realm-name>
</login-config>
<security-constraint>
    <web-resource-collection>
        <web-resource-name>cucoda-termination</web-resource-name>
        <url-pattern>/*</url-pattern>
    </web-resource-collection>
    <auth-constraint>
       <role-name>Organizer</role-name>
    </auth-constraint>
</security-constraint>
<security-role>
    <role-name>Organizer</role-name>
</security-role>
```

   Configure security-domain in the file jboss-web.xml   

```sh
<jboss-web>
    <security-domain>my-aktion</security-domain>
</jboss-web>
```


*  To enable EJB security create the file jboss-ejb3.xml
```sh
<?xml version="1.1" encoding="UTF-8"?>
    <jboss:ejb-jar xmlns:jboss="http://www.jboss.com/xml/ns/javaee"
        xmlns="http://java.sun.com/xml/ns/javaee" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xmlns:s="urn:security"
        xsi:schemaLocation="http://www.jboss.com/xml/ns/javaee http://www.jboss.org/j2ee/schema/jboss-ejb3-2_0.xsd http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/ejb-jar_3_1.xsd" version="3.1" impl-version="2.0">

<assembly-descriptor>
    <s:security>
        <ejb-name>*</ejb-name>
        <s:security-domain>my-aktion</s:security-domain>
    </s:security>
</assembly-descriptor></jboss:ejb-jar>
```