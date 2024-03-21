# Workshop Spring Boot Cassandra DevSuperior

Este é o material de apoio que a DevSuperior disponibiliza para os alunos como referência para o projeto.

### Modelo conceitual

https://docs.google.com/document/d/1oj4e9iaNefOIwKgaLVZ7_mteEXxoVb8wpBs7VcUe4sE/edit?usp=sharing

### Visão geral: column store database

https://database.guide/what-is-a-column-store-database/

### Container Docker do Cassandra para desenvolvimento
```
docker run -d -p 9042:9042 --name cassandra1 cassandra:3.11.10
```
```
docker exec -it cassandra1 bash
```
### cqlsh
```
describe keyspaces

CREATE KEYSPACE testdb WITH replication = {'class': 'SimpleStrategy', 'replication_factor' : 1};

use testdb;

describe tables

CREATE TABLE post(id uuid, moment timestamp, body text, author varchar, PRIMARY KEY (id));

INSERT INTO post (id, moment, body, author) VALUES (uuid(), '2021-02-26T10:00:00Z', 'Bom dia!', 'Bob');
INSERT INTO post (id, moment, body, author) VALUES (uuid(), '2021-02-27T10:00:00Z', 'Partiu viagem', 'Maria');
INSERT INTO post (id, moment, body, author) VALUES (uuid(), '2021-02-27T10:00:00Z', 'Que dia bonito!', 'Maria');

SELECT * FROM post;

SELECT * FROM post WHERE author = 'Maria' ALLOW FILTERING;

CREATE CUSTOM INDEX body_idx ON post (body) USING 'org.apache.cassandra.index.sasi.SASIIndex' WITH OPTIONS = {'mode': 'CONTAINS', 'analyzer_class': 'org.apache.cassandra.index.sasi.analyzer.NonTokenizingAnalyzer','case_sensitive': 'false'};

SELECT * FROM post WHERE body LIKE '%MORNING%';
```
### Postman collection

(baixe o arquivo DSProducts.postman_collection.json que está aqui na pasta do projeto, e importe-o no seu Postman)

### Trechos de código

Arquivo properties
```
spring.data.cassandra.contact-points=localhost
spring.data.cassandra.keyspace-name=productsdb
spring.data.cassandra.port=9042

spring.data.cassandra.local-datacenter=datacenter1
```

Classe CassandraConfig

```java
@Configuration
public class CassandraConfig extends AbstractCassandraConfiguration {

	@Value("${spring.data.cassandra.keyspace-name}")
	private String keyspace;

	@Value("${spring.data.cassandra.contact-points}")
	private String contactPoint;

	@Value("${spring.data.cassandra.port}")
	private int port;
	
	@Value("${spring.data.cassandra.local-datacenter}")
	private String localDatacenter;

	@Override
	public String getContactPoints() {
		return contactPoint;
	}

	@Override
	protected int getPort() {
		return port;
	}

	@Override
	public SchemaAction getSchemaAction() {
		return SchemaAction.CREATE_IF_NOT_EXISTS;
	}

	@Override
	protected List<CreateKeyspaceSpecification> getKeyspaceCreations() {
		return Collections.singletonList(CreateKeyspaceSpecification.createKeyspace(keyspace).ifNotExists()
				.with(KeyspaceOption.DURABLE_WRITES, true).withSimpleReplication(1L));
	}

	@Override
	protected String getLocalDataCenter() {
		return localDatacenter;
	}

	@Override
	protected String getKeyspaceName() {
		return keyspace;
	}

 	//Trocar o caminho do pacote das entidades pelo do seu projeto 
	@Override
	public String[] getEntityBasePackages() {
		return new String[] { "com.devsuperior.meuprojeto.model.entities" };
	}
}
```

## Recursos adicionais

### Documentação Cassandra

https://cassandra.apache.org/doc/latest

### Spring Data Cassandra

https://docs.spring.io/spring-data/cassandra/docs/current/reference/html

### Tutorial no Youtube

https://www.youtube.com/watch?v=s1xc1HVsRk0&list=PLalrWAGybpB-L1PGA-NfFu2uiWHEsdscD

### Projeto exemplo

https://github.com/rahul-ghadge/spring-boot-cassandra-crud

### Localização dos volumes Docker no Windows

https://stackoverflow.com/questions/43181654/locating-data-volumes-in-docker-desktop-windows
